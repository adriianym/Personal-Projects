package org.example.vista;

import org.example.modelo.Mascota;

import java.util.List;

public interface IVistaCuidado {
    void cuidadoCreado();

    void cuidadoAsignado();

    void solicitarPanchopuntos();

    void solicitarFechaInicio();

    void solicitarFechaFin();

    void seleccionarMascota(List<Mascota> mascotas);
}