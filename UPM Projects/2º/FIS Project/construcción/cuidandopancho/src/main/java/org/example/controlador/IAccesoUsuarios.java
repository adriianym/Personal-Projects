package org.example.controlador;

import org.example.modelo.Cuidador;
import org.example.modelo.Dueno;

public interface IAccesoUsuarios {
    Dueno getDuenoId(String id);

    Cuidador getCuidadorId(String id);
}