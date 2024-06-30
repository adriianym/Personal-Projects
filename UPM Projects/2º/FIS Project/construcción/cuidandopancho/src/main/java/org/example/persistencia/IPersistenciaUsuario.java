package org.example.persistencia;

import org.example.modelo.Usuario;

import java.io.IOException;
import java.util.List;

public interface IPersistenciaUsuario {
    void guardar(Usuario usuario) throws IOException;

    void borrar(String id) throws IOException;

    List<Usuario> cargar() throws IOException;
}