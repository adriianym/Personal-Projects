package org.example.vista;

import org.example.modelo.Mascota;

import java.util.List;

public class VistaCuidado implements IVistaCuidado{

    public VistaCuidado() {
    }

    public void cuidadoCreado() {
        System.out.println(" -Se ha asignado el cuidado a su mascota correctamente.");
    }

    public void cuidadoAsignado() {
        System.out.println(" -Se te ha asignado el cuidado correctamente.");
    }

    public void solicitarPanchopuntos() {
        System.out.println(" -Introduzca los panchopuntos para el cuidado.");
    }

    public void solicitarFechaInicio() {
        System.out.println(" -Introduzca una fecha de inicio para el cuidado (dd-MM-yyyy).");
    }

    public void solicitarFechaFin() {
        System.out.println(" -Introduzca una fecha de fin para el cuidado (dd-MM-yyyy).");
    }

    public void seleccionarMascota(List<Mascota> mascotas) {
        System.out.println(" -¿A cuál de tus mascotas quieres que la cuiden? (Indicar número)");
        int num = 0;
        for (Mascota mascota : mascotas) {
            num++;
            System.out.println(String.format("\t%s-RIAC: %s, nº póliza: %s, nombre: %s, localidad: %s, descripción: %s", num, mascota.getRiac(), mascota.getPoliza(), mascota.getNombre(), mascota.getDescripcion(), mascota.getLocalidad()));
        }
    }
}
