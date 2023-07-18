import Entities.Cliente;
import Entities.Factura;
import Entities.Servicio;
import Exceptions.EntidadNoExiste;
import Exceptions.EntidadYaExiste;
import Exceptions.InformacionInvalida;
import uy.edu.um.adt.linkedlist.MyList;

import java.time.LocalDate;

public interface MiCuentaFacilMgr {

    void agregarFactura(String nombreServicio, Long cedulaCliente, String concepto, LocalDate vencimiento, Long importe) throws InformacionInvalida, EntidadYaExiste;

    MyList<Factura> obtenerFacturasNoPagas(Long cedulaCliente) throws EntidadNoExiste, InformacionInvalida;

    void pagarFactura(String nombreServicio, Long numeroFactura, Long cedulaCiente, LocalDate fechaPago) throws InformacionInvalida, EntidadNoExiste;

    MyList<Cliente> obtenerClientesConDeudaVencida();

    MyList<Servicio> obtenerRankingServiciosPagosVencidos();

}
