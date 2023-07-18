import Entities.Cliente;
import Entities.Factura;
import Entities.Servicio;
import Exceptions.EntidadNoExiste;
import Exceptions.EntidadYaExiste;
import Exceptions.InformacionInvalida;
import uy.edu.um.adt.linkedlist.MyLinkedListImpl;
import uy.edu.um.adt.linkedlist.MyList;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MiCuentaFacilMgrImpTest {

    private MiCuentaFacilMgrImp miCuenta;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        miCuenta = new MiCuentaFacilMgrImp();
        Factura.setProximoNumeroFactura(1);
    }

    @org.junit.jupiter.api.Test
    void agregarFactura() throws InformacionInvalida, EntidadYaExiste {
        Cliente cliente = new Cliente(123456);
        Servicio servicio = new Servicio("UTE");

        miCuenta.agregarFactura(servicio.getNombre(), cliente.getCedula(),"prueba",LocalDate.of(2022,6,12),1000L);

        assertTrue(miCuenta.getClientes().contains(123456L));
        assertTrue(miCuenta.getClientes().find(123456L).tieneFactura(1L));

        assertEquals(miCuenta.getServicios().size(),1);
        assertEquals(miCuenta.getServicios().get("ute").getNombre(),"ute");
        assertEquals(miCuenta.getServicios().get("ute").getFacturas().size(),1);
        assertEquals(miCuenta.getServicios().get("ute").getFacturas().values().get(0).getNumeroFactura(),1L);


    }

    @org.junit.jupiter.api.Test
    void obtenerFacturasNoPagas() throws InformacionInvalida, EntidadYaExiste, EntidadNoExiste {
        Cliente cliente = new Cliente(123456);
        Servicio servicio = new Servicio("UTE");

        miCuenta.agregarFactura(servicio.getNombre(), cliente.getCedula(),"prueba",LocalDate.of(2022,6,12),1000L);
        miCuenta.agregarFactura(servicio.getNombre(), cliente.getCedula(),"prueba",LocalDate.of(2022,7,12),1000L);

        MyList<Factura> facturasNoPagas = miCuenta.obtenerFacturasNoPagas(123456L);

        assertTrue(facturasNoPagas.size() ==2);
        assertEquals(facturasNoPagas.get(0).getNumeroFactura(),1L);
        assertEquals(facturasNoPagas.get(1).getNumeroFactura(),2L);

    }

    @org.junit.jupiter.api.Test
    void pagarFactura() throws InformacionInvalida, EntidadYaExiste, EntidadNoExiste {
        Cliente cliente = new Cliente(123456);
        Servicio servicio = new Servicio("UTE");

        miCuenta.agregarFactura(servicio.getNombre(), cliente.getCedula(),"prueba",LocalDate.of(2022,6,12),1000L);
        miCuenta.agregarFactura(servicio.getNombre(), cliente.getCedula(),"prueba",LocalDate.of(2022,7,12),1000L);

        MyList<Factura> facturasNoPagas = miCuenta.obtenerFacturasNoPagas(123456L);
        assertTrue(facturasNoPagas.size() ==2);

        miCuenta.pagarFactura("ute",1L,123456L,null);

        facturasNoPagas = miCuenta.obtenerFacturasNoPagas(123456L);
        assertTrue(facturasNoPagas.size() ==1);

        assertTrue(miCuenta.getClientes().find(123456L).facturasList().get(0).isEstaPaga());
        assertFalse(miCuenta.getClientes().find(123456L).facturasList().get(1).isEstaPaga());

    }

    @org.junit.jupiter.api.Test
    void obtenerClientesConDeudaVencida() throws InformacionInvalida, EntidadYaExiste {
        Cliente cliente1 = new Cliente(123456);
        Cliente cliente2 = new Cliente(213456);
        Servicio servicio = new Servicio("UTE");

        miCuenta.agregarFactura(servicio.getNombre(), cliente1.getCedula(),"prueba",LocalDate.of(2022,6,12),1000L);
        miCuenta.agregarFactura(servicio.getNombre(), cliente1.getCedula(),"prueba",LocalDate.of(2022,7,12),1000L);
        miCuenta.agregarFactura(servicio.getNombre(), cliente1.getCedula(),"prueba",LocalDate.of(2022,8,12),1000L);
        miCuenta.agregarFactura(servicio.getNombre(), cliente1.getCedula(),"prueba",LocalDate.of(2022,9,12),1000L);

        miCuenta.agregarFactura(servicio.getNombre(), cliente2.getCedula(),"prueba",LocalDate.of(2022,6,12),1000L);
        miCuenta.agregarFactura(servicio.getNombre(), cliente2.getCedula(),"prueba",LocalDate.of(2022,7,12),1000L);
        miCuenta.agregarFactura(servicio.getNombre(), cliente2.getCedula(),"prueba",LocalDate.of(2022,8,12),1000L);

        MyList<Cliente> clientesDuedaVen =  miCuenta.obtenerClientesConDeudaVencida();

        assertEquals(clientesDuedaVen.get(0).getCedula(),123456);
        assertEquals(clientesDuedaVen.get(1).getCedula(),213456);
        assertEquals(clientesDuedaVen.size(),2);

    }

    @org.junit.jupiter.api.Test
    void obtenerRankingServiciosPagosVencidos() throws InformacionInvalida, EntidadYaExiste {
        Cliente cliente1 = new Cliente(123456);
        Cliente cliente2 = new Cliente(213456);
        Servicio servicio1 = new Servicio("UTE");
        Servicio servicio2 = new Servicio("Antel");

        miCuenta.agregarFactura(servicio1.getNombre(), cliente1.getCedula(),"prueba",LocalDate.of(2022,6,12),1000L);
        miCuenta.agregarFactura(servicio1.getNombre(), cliente1.getCedula(),"prueba",LocalDate.of(2022,7,12),1000L);
        miCuenta.agregarFactura(servicio1.getNombre(), cliente1.getCedula(),"prueba",LocalDate.of(2022,8,12),1000L);
        miCuenta.agregarFactura(servicio1.getNombre(), cliente1.getCedula(),"prueba",LocalDate.of(2022,9,12),1000L);

        miCuenta.agregarFactura(servicio1.getNombre(), cliente2.getCedula(),"prueba",LocalDate.of(2022,6,12),1000L);
        miCuenta.agregarFactura(servicio1.getNombre(), cliente2.getCedula(),"prueba",LocalDate.of(2022,7,12),1000L);
        miCuenta.agregarFactura(servicio1.getNombre(), cliente2.getCedula(),"prueba",LocalDate.of(2022,8,12),1000L);

        miCuenta.agregarFactura(servicio2.getNombre(), cliente2.getCedula(),"prueba",LocalDate.of(2022,6,12),1000L);
        miCuenta.agregarFactura(servicio2.getNombre(), cliente2.getCedula(),"prueba",LocalDate.of(2022,7,12),1000L);
        miCuenta.agregarFactura(servicio2.getNombre(), cliente2.getCedula(),"prueba",LocalDate.of(2022,8,12),1000L);

        MyList<Servicio> rankingServicios =  miCuenta.obtenerRankingServiciosPagosVencidos();

        assertEquals(rankingServicios.size(),2);
        assertEquals(rankingServicios.get(0).getNombre(),"ute");
        assertEquals(rankingServicios.get(1).getNombre(),"antel");

    }

    //Auxiliares

    @org.junit.jupiter.api.Test
    void facturasAutoIncrement() {

        Cliente cliente = new Cliente(123456);
        Servicio servicio = new Servicio("UTE");

        for (int i = 0; i < 12; i++) {
            Factura factura1 = new Factura("prueba", LocalDate.of(2022,1+i,12),1000,cliente,servicio);
            assertEquals(factura1.getNumeroFactura(),i+1);

        }

    }

}