package Entities;

import Exceptions.EntidadYaExiste;
import uy.edu.um.adt.hash.MyHash;
import uy.edu.um.adt.hash.MyHashImpl;
import uy.edu.um.adt.linkedlist.MyLinkedListImpl;
import uy.edu.um.adt.linkedlist.MyList;

import java.util.Objects;

public class Cliente implements Comparable<Cliente> {
    private long cedula;
    private MyHash<Long,Factura> facturas;

    public Cliente() {
    }

    public Cliente(long cedula) {
        this.cedula = cedula;
        this.facturas = new MyHashImpl<>();
    }

    public Cliente(long cedula, MyHash<Long,Factura> facturas) {
        this.cedula = cedula;
        this.facturas = facturas;
    }

    public long getCedula() {
        return cedula;
    }

    public void setCedula(long cedula) {
        this.cedula = cedula;
    }

    public MyHash<Long, Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(MyHash<Long, Factura> facturas) {
        this.facturas = facturas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return cedula == cliente.cedula;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cedula);
    }

    public void agregarFactura(Factura factura) throws EntidadYaExiste {
        if (this.facturas.contains(factura.getNumeroFactura())){
            throw new EntidadYaExiste();
        }else{
            this.facturas.put(factura.getNumeroFactura(),factura);
        }

    }

    //COMPARE TO
    @Override
    public int compareTo(Cliente o) {
        if (this.cedula < o.cedula) {
            return -1; //Menor
        } else if (this.cedula > o.cedula) {
            return 1; //Mayor
        } else {
            return 0; //Igual
        }
    }


    //AUXILIARES
    /**
     * @return MyList de facturas totales.
     */
    public MyList<Factura> facturasList(){
        return this.facturas.values();
    }

    public boolean tieneFactura(Long numeroFactura){
        return this.facturas.contains(numeroFactura);
    }

    /**
     * @return MyList de facturas no pagadas por el cliente.
     */
    public MyList<Factura> facturasNoPagas(){
        MyList<Factura> total = facturasList();
        MyList<Factura> noPagas = new MyLinkedListImpl<>();
        int sizeTotal = total.size();

        for (int i = 0; i < sizeTotal; i++) {
            Factura factAux = total.get(i);
            if(!factAux.isEstaPaga()){
                noPagas.add(factAux);
            }

        }
        return noPagas;

    }

    /**
     * @return true si el cliente tiene deuda vencida.
     */
    public boolean deudaVencida(){
        MyList<Factura> noPagas = facturasNoPagas();
        int sizeNoPagas = noPagas.size();
        if (sizeNoPagas == 0 ){
            //Si no tiene facturas para pagar no tiene deuda vencida
            return false;
        }else{

            for (int i = 0; i < sizeNoPagas; i++) {
                //Si una factura ya esta vencida, el cliente tiene deuda vencida
                if(noPagas.get(i).esVencida()){
                    return true;
                }

            }
        }
        return false;


    }


}
