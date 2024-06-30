package edu.upm.etsisi.poo.iwsim21.modelo.actividad;

import edu.upm.etsisi.poo.iwsim21.modelo.usuario.Usuario;

public class Cine extends Actividad {

    /**
     * Constructor de Cine.
     *
     * @param nombreActividad      String que designa el nombre de la actividad.
     * @param descripcionDetallada String que designa la descripción de la actividad.
     * @param duracion             Entero que designa la duración de la actividad.
     * @param coste                Double que designa el coste de la actividad.
     * @param aforo                Entero que designa el aforo de la actividad.
     */
    public Cine(String nombreActividad, String descripcionDetallada, int duracion, double coste, int aforo) {
        super(nombreActividad, descripcionDetallada, duracion, coste, aforo);
    }

    /**
     * Getter de costeAct según el usuario.
     *
     * @return Coste de la actividad.
     */
    @Override
    public double getCosteAct(Usuario usuario) {
        if (usuario.getEdad() <= 21) return costeAct * 0.5;
        return costeAct;
    }
}