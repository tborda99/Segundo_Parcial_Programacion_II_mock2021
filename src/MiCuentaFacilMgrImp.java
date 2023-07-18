import Entities.Cliente;
import Entities.Factura;
import Entities.Servicio;
import Exceptions.EntidadNoExiste;
import Exceptions.EntidadYaExiste;
import Exceptions.InformacionInvalida;
import uy.edu.um.adt.binarytree.MySearchBinaryTree;
import uy.edu.um.adt.binarytree.MySearchBinaryTreeImpl;
import uy.edu.um.adt.hash.MyHash;
import uy.edu.um.adt.hash.MyHashImpl;
import uy.edu.um.adt.heap.MyHeap;
import uy.edu.um.adt.heap.MyHeapImpl;
import uy.edu.um.adt.linkedlist.MyLinkedListImpl;
import uy.edu.um.adt.linkedlist.MyList;

import java.time.LocalDate;

public class MiCuentaFacilMgrImp implements MiCuentaFacilMgr{

    private MySearchBinaryTree<Long,Cliente> clientes = new MySearchBinaryTreeImpl<>();
    private MyHash<String,Servicio> servicios = new MyHashImpl<>();


    @Override
    public void agregarFactura(String nombreServicio, Long cedulaCliente, String concepto, LocalDate vencimiento, Long importe) throws InformacionInvalida, EntidadYaExiste {
        if(nombreServicio == null || cedulaCliente == null || concepto == null || vencimiento == null || importe == null){
            throw new InformacionInvalida();
        }

        Cliente cliente = buscarOCrearCliente(cedulaCliente);
        Servicio servicio = buscarOCrearServicio(nombreServicio);
        Factura factura = new Factura(concepto,vencimiento,importe,cliente,servicio);

        //Agregar si no existe
        if(!servicio.tieneFactura(factura.getNumeroFactura()) && !cliente.tieneFactura(factura.getNumeroFactura())){
            servicio.agregarFactura(factura);
            cliente.agregarFactura(factura);
        }else{
            throw new EntidadYaExiste();
        }

    }

    @Override
    public MyList<Factura> obtenerFacturasNoPagas(Long cedulaCliente) throws EntidadNoExiste, InformacionInvalida {
        if(cedulaCliente == null){
            throw new InformacionInvalida();
        }

        if(!clientes.contains(cedulaCliente)){
            throw new EntidadNoExiste();
        }

        Cliente cliente = buscarOCrearCliente(cedulaCliente);
        return cliente.facturasNoPagas();

    }

    @Override
    public void pagarFactura(String nombreServicio, Long numeroFactura, Long cedulaCiente, LocalDate fechaPago) throws InformacionInvalida, EntidadNoExiste {
        if(nombreServicio == null || numeroFactura == null || cedulaCiente == null){
            throw new InformacionInvalida();
        }
        if(fechaPago == null){
            fechaPago = LocalDate.now();
        }

        //Strings en minusucula
        nombreServicio = nombreServicio.toLowerCase().trim();


        if(servicios.contains(nombreServicio) && clientes.contains(cedulaCiente)){
            Servicio servicio = buscarOCrearServicio(nombreServicio);
            Cliente cliente = buscarOCrearCliente(cedulaCiente);

            if(cliente.tieneFactura(numeroFactura)){
                MyList<Factura> facturasNoPagas = cliente.facturasNoPagas();
                int sizeFacturas = facturasNoPagas.size();

                for (int i = 0; i < sizeFacturas; i++) {
                    Factura factura = facturasNoPagas.get(i);
                    if(factura.getNumeroFactura() == numeroFactura){
                        if (factura.isEstaPaga()){
                            //Ya estaba paga
                            throw new InformacionInvalida();
                        }else{
                            factura.pagar(fechaPago);
                        }
                    }
                }
            }else{
                throw new EntidadNoExiste();
            }
        }else{
            throw new EntidadNoExiste();
        }
    }

    @Override
    public MyList<Cliente> obtenerClientesConDeudaVencida() {
        //TODO: Asumo que se espera ordenar de menor a mayor por numero de cedula.
        MyList<Long> clientesList = clientes.inOrder();
        MyHeap<Cliente> clientesDeudaVenciada = new MyHeapImpl<>(true); //Si se busca de mayor a menor cambiar este valor por false.
        MyList<Cliente> clientesDeudaVenciadaOrdenados = new MyLinkedListImpl<>();

        int sizeClientes = clientesList.size();

        for (int i = 0; i < sizeClientes; i++) {
            Cliente clienteAux = clientes.find(clientesList.get(i));
            if(clienteAux.deudaVencida()){
                clientesDeudaVenciada.insert((clienteAux));
            }
        }

        int sizeHeap = clientesDeudaVenciada.size();
        for (int i = 0; i < sizeHeap; i++) {
            clientesDeudaVenciadaOrdenados.add(clientesDeudaVenciada.delete());
        }

        return clientesDeudaVenciadaOrdenados;
    }

    @Override
    public MyList<Servicio> obtenerRankingServiciosPagosVencidos() {

        MyList<Servicio> serviciosList = servicios.values();
        MyList<Servicio> servicioRanking = new MyLinkedListImpl<>();
        MyHeap<Servicio> servicioHeap = new MyHeapImpl<>(false);//Mayor arriba, menor abajo (Ranking)
        int size = serviciosList.size();
        for (int i = 0; i < size; i++) {
            servicioHeap.insert((serviciosList.get(i)));
        }

        int sizeHeap = servicioHeap.size();
        for (int i = 0; i < sizeHeap; i++) {
            servicioRanking.add(servicioHeap.delete());
        }

        return servicioRanking;
    }

    //AUXILIARES
    /**
     * @param cedulaCliente
     * @return cliente encontrado en el BST o creado.
     */
    private Cliente buscarOCrearCliente(Long cedulaCliente) {
        if (clientes.contains(cedulaCliente)){
            //Ya existe, devuelvo
            return clientes.find(cedulaCliente);
        }else{
            //No existe, lo creo
            Cliente cliente = new Cliente(cedulaCliente);
            clientes.add(cedulaCliente,cliente);
            return cliente;
        }

    }

    /**
     * @param nombreServicio
     * @return servicio encontrado en el Hash o creado.
     */
    private Servicio buscarOCrearServicio(String nombreServicio) {
        //Todos los strings se guardan en minuscula
        nombreServicio = nombreServicio.toLowerCase().trim();
        if (servicios.contains(nombreServicio)){
            //Ya existe, devuelvo
            return servicios.get(nombreServicio);
        }else{
            //No existe, lo creo
            Servicio servicio = new Servicio(nombreServicio);
            servicios.put(nombreServicio,servicio);
            return servicio;

        }

    }

    //GETTERS PARA TESTS


    public MySearchBinaryTree<Long, Cliente> getClientes() {
        return clientes;
    }

    public MyHash<String, Servicio> getServicios() {
        return servicios;
    }

}
