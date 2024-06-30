package org.example.vista;

import org.example.modelo.Cuidado;

import java.util.List;

public interface IVistaCuidador {
    void seleccionarCuidados(List<Cuidado> cuidados);

    void tusCuidados(List<Cuidado> cuidados);
}