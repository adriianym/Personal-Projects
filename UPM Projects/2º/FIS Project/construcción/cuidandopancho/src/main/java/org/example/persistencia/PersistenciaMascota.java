package org.example.persistencia;

import org.example.controlador.IAccesoUsuarios;
import org.example.modelo.Dueno;
import org.example.modelo.Mascota;
import org.example.modelo.MascotaExotica;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaMascota implements IPersistenciaMascota {
    private String archivo;

    public PersistenciaMascota(String archivo) {
        this.archivo = archivo;
    }

    @Override
    public void guardar(Mascota mascota) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(String.format("%s\n", mascota));
        } catch (IOException e) {
            throw new IOException("Error al guardar la mascota.", e);
        }
    }

    @Override
    public void borrar(String riac) throws IOException {
        try {
            List<String> lineas = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (!linea.startsWith(riac)) lineas.add(linea);
                }
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
                for (String linea : lineas)
                    bw.write(linea + "\n");
            }
        } catch (IOException e) {
            throw new IOException("Error al borrar la mascota.", e);
        }
    }

    @Override
    public List<Mascota> cargar(IAccesoUsuarios accesoUsuarios) throws IOException {
        List<Mascota> listaMascotas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            String[] datos;

            String idUsuario;
            Dueno usuario;

            while ((linea = br.readLine()) != null) {
                datos = linea.split(";");

                if (datos.length > 6) {
                    idUsuario = datos[datos.length - 1];
                    usuario = accesoUsuarios.getDuenoId(idUsuario);

                    if (usuario == null) {
                        throw new IOException();
                    }

                    if ("Mascota".equals(datos[datos.length - 2]))
                        listaMascotas.add(new Mascota(datos[0], Integer.parseInt(datos[1]), datos[2], datos[3], datos[4], usuario));
                    else if ("MascotaExotica".equals(datos[datos.length - 2]))
                        listaMascotas.add(new MascotaExotica(datos[0], Integer.parseInt(datos[1]), datos[2], datos[3], datos[4], usuario, null));
                }
            }
        } catch (IOException e) {
            throw new IOException("Error al cargar las mascotas.", e);
        }

        return listaMascotas;
    }
}