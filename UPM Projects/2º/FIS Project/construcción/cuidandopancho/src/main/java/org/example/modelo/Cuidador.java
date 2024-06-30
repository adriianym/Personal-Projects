//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Cuidador.java
//  @ Date : 09/05/2024
//  @ Author : 
//
//

package org.example.modelo;

import java.util.ArrayList;
import java.util.List;

public class Cuidador extends Usuario {
    private String descripcion;
    private Double tarifa;
    private int panchopuntos;
    private List<Cuidado> listaCuidados;

    public Cuidador(String id, String nombre, EnumProveedor proveedor, String descripcion, Double tarifa) {
        super(id, nombre, proveedor);
        this.descripcion = descripcion;
        this.tarifa = tarifa;
        panchopuntos = 0;
        listaCuidados = new ArrayList<>();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getTarifa() {
        return tarifa;
    }

    public void setTarifa(Double tarifa) {
        this.tarifa = tarifa;
    }

    public int getPanchopuntos() {
        return panchopuntos;
    }

    public void addPanchopuntos(int panchoPuntos) {
        this.panchopuntos += panchoPuntos;
    }

    public void addCuidado(Cuidado cuidado) {
        listaCuidados.add(cuidado);
    }

    public void delCuidado(Cuidado cuidado) {
        listaCuidados.remove(cuidado);
    }

    public List<Cuidado> getListaCuidados() {
        return listaCuidados;
    }

    public void setListaCuidados(List<Cuidado> listaCuidados) {
        this.listaCuidados = listaCuidados;
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s;%s;%s", nombre, id, proveedor, descripcion, tarifa, getClass().getSimpleName());
    }
}
