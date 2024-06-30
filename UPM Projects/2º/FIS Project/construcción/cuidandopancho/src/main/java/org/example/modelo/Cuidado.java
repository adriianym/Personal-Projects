//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : Cuidado.java
//  @ Date : 09/05/2024
//  @ Author : 
//
//

package org.example.modelo;

public class Cuidado {
    private String fechaInicio;
    private String fechaFin;
    private Double coste;
    private int panchopuntos;
    private Mascota mascota;
    private Cuidador cuidador;

    public Cuidado(int panchopuntos, Mascota mascota) {
        this.panchopuntos = panchopuntos;
        this.mascota = mascota;
        fechaInicio = null;
        fechaFin = null;
        coste = 0.0;
        cuidador = null;
        mascota.addCuidado(this);
    }

    public boolean cuidadoLibre() {
        return cuidador == null;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Double getCoste() {
        return coste;
    }

    public void setCoste(Double coste) {
        this.coste = coste;
    }

    public int getPanchopuntos() {
        return panchopuntos;
    }

    public void setPanchopuntos(int panchopuntos) {
        this.panchopuntos = panchopuntos;
    }

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }

    public Cuidador getCuidador() {
        return cuidador;
    }

    public void setCuidador(Cuidador cuidador) {
        this.cuidador = cuidador;
    }

    public String toString() {
        if (cuidadoLibre())
            return String.format("%s;%s;%s;%s;%s;SinCuidador", mascota.getRiac(), fechaInicio, fechaFin, coste, panchopuntos);
        else
            return String.format("%s;%s;%s;%s;%s;%s", mascota.getRiac(), fechaInicio, fechaFin, coste, panchopuntos, cuidador.getId());
    }
}
