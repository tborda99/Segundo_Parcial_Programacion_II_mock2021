package Entities;

import Exceptions.EntidadYaExiste;
import uy.edu.um.adt.hash.MyHash;
import uy.edu.um.adt.hash.MyHashImpl;
import uy.edu.um.adt.linkedlist.MyList;
import java.util.Objects;

public class Servicio implements Comparable<Servicio>{

    //VARIABLES

    private String nombre;
    private MyHash<Long,Factura> facturas;



    //CONSTRUCTOR

    public Servicio() {
    }

    public Servicio(String nombre, MyHash<Long, Factura> facturas) {
        this.nombre = nombre;
        this.facturas = facturas;
    }

    public Servicio(String nombre) {
        this.nombre = nombre;
        this.facturas = new MyHashImpl<>();
    }

    //GETTERS & SETTERS

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public MyHash<Long, Factura> getFacturas() {
        return facturas;
    }

    public void setFacturas(MyHash<Long, Factura> facturas) {
        this.facturas = facturas;
    }

    public void agregarFactura(Factura factura) throws EntidadYaExiste {
        if (this.facturas.contains(factura.getNumeroFactura())){
            throw new EntidadYaExiste();
        }else{
            this.facturas.put(factura.getNumeroFactura(),factura);
        }

    }

    //EQUALS HASHCODE

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Servicio servicio = (Servicio) o;
        return Objects.equals(nombre.toLowerCase().trim(), servicio.nombre.toLowerCase().trim());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }


    //AUXILIARES
    public MyList<Factura> facturasList(){
        return this.facturas.values();
    }

    public boolean tieneFactura(Long numeroFactura){
        return this.facturas.contains(numeroFactura);
    }

    public int cantidadDeudaVencidas(){
        MyList<Factura> facturas = facturasList();
        int size = facturas.size();
        int countDeudasVencidas = 0;
        for (int i = 0; i < size; i++) {
            if (facturas.get(i).pagoVencido()){
                countDeudasVencidas ++;
            }

        }
        return countDeudasVencidas;
    }

    @Override
    public int compareTo(Servicio o) {
        if (this.cantidadDeudaVencidas() < o.cantidadDeudaVencidas()) {
            return -1; //Menor
        } else if (this.cantidadDeudaVencidas() > o.cantidadDeudaVencidas()) {
            return 1; //Mayor
        } else {
            return 0; //Igual
        }
    }
}
