package org.example.persistencia;

import org.example.controlador.IAccesoMascotas;
import org.example.controlador.IAccesoUsuarios;
import org.example.modelo.Cuidado;

import java.io.IOException;
import java.util.List;

public interface IPersistenciaCuidado {
    void guardar(Cuidado cuidado) throws IOException;

    void borrar(String riac) throws IOException;

    List<Cuidado> cargar(IAccesoUsuarios accesoUsuarios, IAccesoMascotas accesoMascotas) throws IOException;
}