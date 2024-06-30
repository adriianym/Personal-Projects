package edu.upm.etsisi.poo.iwsim21.controlador;

import edu.upm.etsisi.poo.iwsim21.exceptions.NonExistentDataException;
import edu.upm.etsisi.poo.iwsim21.modelo.actividad.Actividad;
import edu.upm.etsisi.poo.iwsim21.modelo.actividad.Cine;
import edu.upm.etsisi.poo.iwsim21.modelo.actividad.Teatro;
import edu.upm.etsisi.poo.iwsim21.vista.CLI;

import java.util.ArrayList;
import java.util.List;

public class ControladorActividad {

    /**
     * Lista Actividad con las actividades creadas.
     */
    private List<Actividad> listaActividad;
    /**
     * CLI del proyecto.
     */
    private CLI cli;

    /**
     * Constructor de ControladorActividad.
     */
    public ControladorActividad() {
        listaActividad = new ArrayList<>();
        cli = CLI.getInstance();
    }

    /**
     * Método que se encarga de crear la actividad si sus datos están correctos.
     *
     * @param tipo        Tipo de actividad deseada (generica / teatro / cine).
     * @param nombre      Nombre de la actividad.
     * @param descripcion Descripción de la actividad.
     * @param duracion    Duración de la actividad.
     * @param coste       Coste de la actividad.
     * @param capacidad   Capacidad de la actividad (opcional).
     */
    public void crearActividad(String tipo, String nombre, String descripcion, int duracion, Double coste, int capacidad) throws NonExistentDataException {
        Actividad actividadCreada = null;
        switch (tipo.toLowerCase()) {
            case "genérica":
            case "generica":
                actividadCreada = new Actividad(nombre, descripcion, duracion, coste, capacidad);
                break;
            case "teatro":
                actividadCreada = new Teatro(nombre, descripcion, duracion, coste, capacidad);
                break;
            case "cine":
                actividadCreada = new Cine(nombre, descripcion, duracion, coste, capacidad);
                break;
            default:
                throw new NonExistentDataException("Tipo de actividad inválido.");
        }

        listaActividad.add(actividadCreada);
        cli.emitirMensaje("La actividad ha sido creada.");

    }

    /**
     * Realiza la BÚSQUEDA de una actividad según su nombre.
     *
     * @param nombre String deseado que debe de estar contenido en el nombre del plan.
     */
    public void consultaActividadNombre(String nombre) {
        cli.emitirMensaje("Actividades:");
        for (Actividad actividad : listaActividad)
            if (actividad.getNombreAct().contains(nombre)) cli.emitirMensaje(" -" + actividad.toString());
    }

    /**
     * Realiza la BÚSQUEDA de una actividad según su capacidad.
     *
     * @param capacidad Capacidad de la actividad que se desea como mínimo.
     */
    public void consultaActividadCapacidad(int capacidad) {
        cli.emitirMensaje("Actividades:");
        for (Actividad act : listaActividad)
            if (act.getAforo() >= capacidad) cli.emitirMensaje(" -" + act.toString());
    }

    /**
     * Realiza la BÚSQUEDA de una actividad según su duración.
     *
     * @param duracion Duración de la actividad que se desea como máximo.
     */
    public void consultaActividadDuracion(int duracion) {
        cli.emitirMensaje("Actividades:");
        for (Actividad actividad : listaActividad) {
            if (actividad.getDuracionAct() <= duracion) cli.emitirMensaje(" -" + actividad.toString());
        }
    }

    //MÉTODOS ADICIONALES

    /**
     * Método que devuelve la actividad según su ID.
     *
     * @param id ID de la actividad que se desea encontrar.
     * @return Actividad que se corresponde con el ID dado si existiese.
     */
    public Actividad getActividadId(int id) {
        Actividad actividad = null;
        for (int i = 0; i < listaActividad.size() && actividad == null; i++) {
            Actividad a = listaActividad.get(i);
            if (a.getId() == id) actividad = a;
        }
        return actividad;
    }

    /**
     * Getter de listaActividad.
     *
     * @return Lista con las actividades creadas.
     */
    public List<Actividad> getListaActividad() {
        return listaActividad;
    }
}