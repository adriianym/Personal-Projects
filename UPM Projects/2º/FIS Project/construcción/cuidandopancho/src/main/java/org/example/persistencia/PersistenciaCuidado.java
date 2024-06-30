package org.example.persistencia;

import org.example.controlador.IAccesoMascotas;
import org.example.controlador.IAccesoUsuarios;
import org.example.modelo.Cuidado;
import org.example.modelo.Cuidador;
import org.example.modelo.Mascota;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaCuidado implements IPersistenciaCuidado {
    private String archivo;

    public PersistenciaCuidado(String archivo) {
        this.archivo = archivo;
    }

    @Override
    public void guardar(Cuidado cuidado) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, true))) {
            bw.write(String.format("%s\n", cuidado));
        } catch (IOException e) {
            throw new IOException("Error al guardar el cuidado.", e);
        }
    }

    @Override
    public void borrar(String riac) throws IOException {
        try {
            List<String> lineas = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    if (!linea.contains(riac) || !linea.endsWith("SinCuidador")) lineas.add(linea);
                }
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(archivo, false))) {
                for (String linea : lineas)
                    bw.write(linea + "\n");
            }
        } catch (IOException e) {
            throw new IOException("Error al borrar el cuidado.", e);
        }
    }

    @Override
    public List<Cuidado> cargar(IAccesoUsuarios accesoUsuarios, IAccesoMascotas accesoMascotas) throws IOException {
        List<Cuidado> listaCuidados = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            String[] datos;

            String riacCuidado;
            String idCuidador;
            Cuidado cuidado;
            Cuidador cuidador;
            Mascota mascota;

            while ((linea = br.readLine()) != null) {
                datos = linea.split(";");
                riacCuidado = datos[0];

                if (datos.length > 5) {
                    mascota = accesoMascotas.getMascotaRiac(riacCuidado);

                    if (mascota == null) {
                        throw new IOException();
                    }

                    cuidado = new Cuidado(Integer.parseInt(datos[4]), mascota);
                    cuidado.setFechaInicio(datos[1]);
                    cuidado.setFechaFin(datos[2]);

                    if (!linea.endsWith("SinCuidador")) {

                        idCuidador = datos[datos.length - 1];
                        cuidador = accesoUsuarios.getCuidadorId(idCuidador);

                        if (cuidador == null) {
                            throw new IOException();
                        }

                        cuidado.setCuidador(cuidador);
                        cuidador.addCuidado(cuidado);
                        cuidado.setCoste(cuidador.getTarifa());
                    }

                    listaCuidados.add(cuidado);
                }
            }
        } catch (IOException e) {
            throw new IOException("Error al cargar los cuidados.", e);
        }

        return listaCuidados;
    }
}