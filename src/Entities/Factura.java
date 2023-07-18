package Entities;

import java.time.LocalDate;
import java.util.Objects;

public class Factura {

    //VARIABLES
    private long numeroFactura;
    private static long proximoNumeroFactura = 1;


    private String concepto;
    private LocalDate vencimiento;
    private LocalDate fechaPago;
    private long importe;
    private boolean estaPaga;

    private Cliente cliente;
    private Servicio servicio;

    //CONSTRUCTORES
    public Factura() {
        this.numeroFactura = getNextNumeroFactura();
        this.estaPaga = false;
        this.fechaPago = null;
    }

    public Factura(String concepto, LocalDate vencimiento, long importe, Cliente cliente, Servicio servicio) {
        this.numeroFactura = getNextNumeroFactura();
        this.concepto = concepto;
        this.vencimiento = vencimiento;
        this.importe = importe;
        this.estaPaga = false;
        this.fechaPago = null;
        this.cliente = cliente;
        this.servicio = servicio;
    }

    //GETTERS & SETTERS
    public long getNumeroFactura() {
        return numeroFactura;
    }

    //Autoincrement numero de factura
    private static synchronized long getNextNumeroFactura() {
        long currentNumeroFactura = proximoNumeroFactura;
        proximoNumeroFactura++;
        return currentNumeroFactura;
    }

    public static void setProximoNumeroFactura(long proximoNumeroFactura) {
        Factura.proximoNumeroFactura = proximoNumeroFactura;
    }

    public void setNumeroFactura(long numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public LocalDate getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(LocalDate vencimiento) {
        this.vencimiento = vencimiento;
    }

    public long getImporte() {
        return importe;
    }

    public void setImporte(long importe) {
        this.importe = importe;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public boolean isEstaPaga() {
        return estaPaga;
    }

    public void setEstaPaga(boolean estaPaga) {
        this.estaPaga = estaPaga;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public void pagar(LocalDate fecha){
        this.fechaPago = fecha;
        setEstaPaga(true);
    }

    //EQUALS & HASHCODE
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Factura factura = (Factura) o;
        return numeroFactura == factura.numeroFactura;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroFactura);
    }

    public long cedulaCliente(){
        return this.cliente.getCedula();
    }

    public String nombreServicio(){
        return this.servicio.getNombre();
    }

    //AUXILIARES

    public boolean esVencida(){
        LocalDate hoy = LocalDate.now();

        if(this.vencimiento.isBefore(hoy)){
            return true;
        }else{
            return false;
        }

    }
    /**
     * @return: True si se realizo el pago de forma vencida
     **/
    public boolean pagoVencido(){
        if(this.fechaPago == null && esVencida()){
            return true;
        } else if (this.vencimiento.isBefore(this.fechaPago)) {
            return true;
        }else{
            return false;
        }
    }



}
