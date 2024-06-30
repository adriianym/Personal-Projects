package org.example.persistencia;

import org.example.modelo.Cuidador;
import org.example.modelo.Dueno;
import org.example.modelo.EnumProveedor;
import org.example.modelo.Usuario;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaUsuario implements IPersistenciaUsuario {
    private String archivo;

    public PersistenciaUsuario(String archivo) {
        this.archivo = archivo;
    }

    @Override
    public void guardar(Usuario usuario) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(String.format("%s\n", usuario));
        } catch (IOException e) {
            throw new IOException("Error al guardar el usuario.", e);
        }
    }

    @Override
    public void borrar(String id) throws IOException {
        try {
            List<String> lineas = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                String[] datos;

                while ((linea = br.readLine()) != null) {
                    datos = linea.trim().split(";");
                    if (!id.equals(datos[1])) lineas.add(linea);
                }
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
                for (String linea : lineas)
                    bw.write(linea + "\n");
            }
        } catch (IOException e) {
            throw new IOException("Error al borrar el usuario.", e);
        }
    }

    @Override
    public List<Usuario> cargar() throws IOException {
        List<Usuario> listaUsuarios = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            String[] datos;
            Usuario usuario;

            while ((linea = br.readLine()) != null) {
                datos = linea.split(";");
                if (datos.length > 3) {

                    if ("Dueno".equals(datos[datos.length - 1])) {
                        usuario = new Dueno(datos[1], datos[0], EnumProveedor.valueOf(datos[2]));
                    } else if ("Cuidador".equals(datos[datos.length - 1]))
                        usuario = new Cuidador(datos[1], datos[0], EnumProveedor.valueOf(datos[2]), datos[3], Double.parseDouble(datos[4]));
                    else break;

                    listaUsuarios.add(usuario);
                }
            }
        } catch (IOException e) {
            throw new IOException("Error al cargar los usuarios.", e);
        }

        return listaUsuarios;
    }
}