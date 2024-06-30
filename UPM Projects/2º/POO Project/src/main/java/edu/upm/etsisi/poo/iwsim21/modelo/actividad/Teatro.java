package edu.upm.etsisi.poo.iwsim21.modelo.actividad;

import edu.upm.etsisi.poo.iwsim21.modelo.usuario.Usuario;

public class Teatro extends Actividad {

    /**
     * Constructor de Teatro.
     *
     * @param nombreActividad      String que designa el nombre de la actividad.
     * @param descripcionDetallada String que designa la descripción de la actividad.
     * @param duracion             Entero que designa la duración de la actividad.
     * @param coste                Double que designa el coste de la actividad.
     * @param aforo                Entero que designa el aforo de la actividad.
     */
    public Teatro(String nombreActividad, String descripcionDetallada, int duracion, double coste, int aforo) {
        super(nombreActividad, descripcionDetallada, duracion, coste, aforo);
    }

    /**
     * Getter de costeAct según el usuario.
     *
     * @return Coste de la actividad.
     */
    @Override
    public double getCosteAct(Usuario usuario) {
        if (usuario.getEdad() <= 25) return costeAct * 0.5;
        else if (usuario.getEdad() >= 65) return costeAct - costeAct * 0.7;
        return costeAct;
    }
}