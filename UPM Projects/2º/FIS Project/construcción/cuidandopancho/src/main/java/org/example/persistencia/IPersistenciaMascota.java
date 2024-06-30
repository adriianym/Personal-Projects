package org.example.persistencia;

import org.example.controlador.IAccesoUsuarios;
import org.example.modelo.Mascota;

import java.io.IOException;
import java.util.List;

public interface IPersistenciaMascota {
    void guardar(Mascota mascota) throws IOException;

    void borrar(String riac) throws IOException;

    List<Mascota> cargar(IAccesoUsuarios accesoUsuarios) throws IOException;
}