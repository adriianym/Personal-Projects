package org.example.controlador;

import org.example.sistema.Sistema;
import org.example.sistema.ISistema;

public abstract class Controlador {
    protected ISistema interfazSistema;
    public Controlador() {
        interfazSistema = Sistema.getInstance();
    }
}
