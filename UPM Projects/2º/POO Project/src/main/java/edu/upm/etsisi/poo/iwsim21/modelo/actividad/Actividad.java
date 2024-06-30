package edu.upm.etsisi.poo.iwsim21.modelo.actividad;

import edu.upm.etsisi.poo.iwsim21.modelo.usuario.Usuario;

public class Actividad {

    /**
     * Incremento que fija el ID de la actividad.
     */
    private static int incremento = -1;
    /**
     * ID de la actividad.
     */
    protected int id;
    /**
     * String que designa el nombre de la actividad.
     */
    protected String nombreAct;
    /**
     * String que designa la descripción de la actividad.
     */
    protected String descripcionDetallada;
    /**
     * Entero que designa la duración de la actividad.
     */
    protected int duracionAct;
    /**
     * Double que designa el coste de la actividad.
     */
    protected double costeAct;
    /**
     * Entero que designa el aforo de la actividad.
     */
    protected int aforo;

    /**
     * Constructor de Actividad.
     *
     * @param nombreAct            String que designa el nombre de la actividad.
     * @param descripcionDetallada String que designa la descripción de la actividad.
     * @param duracionAct          Entero que designa la duración de la actividad.
     * @param costeAct             Double que designa el coste de la actividad.
     * @param aforo                Entero que designa el aforo de la actividad.
     */
    public Actividad(String nombreAct, String descripcionDetallada, int duracionAct, Double costeAct, int aforo) {
        this.nombreAct = nombreAct;
        this.descripcionDetallada = descripcionDetallada;
        this.duracionAct = duracionAct;
        this.costeAct = costeAct;
        this.aforo = aforo;
        incremento++;
        this.id = incremento;
    }

    /**
     * Getter de costeAct.
     *
     * @return Coste de la actividad.
     */
    public double getCosteAct() {
        return costeAct;
    }

    /**
     * Getter de costeAct según el usuario.
     *
     * @param usuario Usuario del que se quiere su coste.
     * @return Coste de la actividad.
     */
    public double getCosteAct(Usuario usuario) {
        return costeAct;
    }

    /**
     * Getter de ID.
     *
     * @return ID de la actividad.
     */
    public int getId() {
        return id;
    }

    /**
     * Getter de nombreAct.
     *
     * @return Nombre de la actividad.
     */
    public String getNombreAct() {
        return nombreAct;
    }

    /**
     * Getter de duracionAct.
     *
     * @return Duración de la actividad.
     */
    public int getDuracionAct() {
        return duracionAct;
    }

    /**
     * Getter de aforo.
     *
     * @return Aforo de la actividad.
     */
    public int getAforo() {
        return aforo;
    }

    /**
     * toString de la actividad.
     *
     * @return Cadena de carácteres con la información de la actividad.
     */
    public String toString() {
        if (aforo != Integer.MAX_VALUE)
            return "id:" + id + "; nombre:" + nombreAct + "; descripción:" + descripcionDetallada + "; duración:" + duracionAct + " min; coste:" + costeAct + " €; aforo:" + aforo;
        else
            return "id:" + id + "; nombre:" + nombreAct + "; descripción:" + descripcionDetallada + "; duración:" + duracionAct + " min; coste:" + costeAct + " €; aforo:Sin límite";
    }
}