package edu.upm.etsisi.poo.iwsim21.modelo.plan;

import edu.upm.etsisi.poo.iwsim21.modelo.actividad.Actividad;
import edu.upm.etsisi.poo.iwsim21.modelo.usuario.Usuario;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class Plan {

    /**
     * Incremento que fija el ID del plan.
     */
    private static int incremento = -1;
    /**
     * ID del plan.
     */
    private int id;
    /**
     * Usuario que es propietario del plan.
     */
    private Usuario propietario;
    /**
     * String que designa el nombre del plan.
     */
    private String nombrePlan;
    /**
     * String que designa la fecha del plan.
     */
    private String fecha;
    /**
     * Fecha del plan en formato GregorianCalendar.
     */
    private GregorianCalendar fechaDate;
    /**
     * String que designa el lugar del plan.
     */
    private String lugar;
    /**
     * Entero que designa la capacidad del plan.
     */
    private int capacidadPlan;
    /**
     * Entero que designa la duración del plan.
     */
    private int duracionPlan;
    /**
     * Double que designa el coste del plan.
     */
    private double costePlan;
    /**
     * Lista Actividad de actividades del plan.
     */
    private List<Actividad> actividades;
    /**
     * Lista Usuario de participantes del plan.
     */
    private List<Usuario> participantes;
    /**
     * Lista Double de valoraciones del plan.
     */
    private List<Double> valoraciones;
    /**
     * Double que designa la valoración del plan.
     */
    private double valoracion;

    /**
     * Constructor de Plan.
     *
     * @param propietario   String que designa el nombre del plan.
     * @param nombrePlan    String que designa el nombre del plan.
     * @param fecha         String que designa la fecha del plan.
     * @param lugar         String que designa el lugar del plan.
     * @param capacidadPlan Entero que designa la capacidad del plan.
     */
    public Plan(Usuario propietario, String nombrePlan, String fecha, String lugar, int capacidadPlan) {
        incremento++;
        this.id = incremento;
        this.propietario = propietario;
        this.nombrePlan = nombrePlan;
        this.lugar = lugar;
        this.capacidadPlan = capacidadPlan;
        this.fecha = fecha;
        this.fechaDate = convertirFecha(fecha);
        actividades = new ArrayList<>();
        participantes = new ArrayList<>();
        valoraciones = new ArrayList<>();
        duracionPlan = 0;
        costePlan = 0.0;
        valoracion = 0.0;
    }

    /**
     * Getter de ID.
     *
     * @return ID del plan.
     */
    public int getId() {
        return id;
    }

    /**
     * Getter de propietario.
     *
     * @return Propietario del plan.
     */
    public Usuario getPropietario() {
        return propietario;
    }

    /**
     * Getter de nombrePlan.
     *
     * @return Nombre del plan.
     */
    public String getNombrePlan() {
        return nombrePlan;
    }

    /**
     * Setter de nombrePlan.
     *
     * @param nombrePlan String que designa el nombre del plan.
     */
    public void setNombrePlan(String nombrePlan) {
        this.nombrePlan = nombrePlan;
    }

    /**
     * Getter de fechaDate.
     *
     * @return Fecha del plan en formato GregorianCalendar.
     */
    public GregorianCalendar getFechaDate() {
        return fechaDate;
    }

    /**
     * Setter de fechaDate.
     *
     * @param fechaDate Fecha del plan en formato GregorianCalendar.
     */
    public void setFechaDate(String fechaDate) {
        this.fechaDate = convertirFecha(fechaDate);
    }

    /**
     * Método que convierte un String con una fecha en un GregorianCalendar.
     *
     * @param fecha String con la fecha del plan.
     * @return Fecha en formato GregorianCalendar.
     */
    public GregorianCalendar convertirFecha(String fecha) {
        try {
            SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
            Date f = simpleDate.parse(fecha);
            GregorianCalendar fechaDate = new GregorianCalendar();
            fechaDate.setTime(f);
            return fechaDate;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Getter de lugar.
     *
     * @return Lugar del plan.
     */
    public String getLugar() {
        return lugar;
    }

    /**
     * Setter de lugar.
     *
     * @param lugar Lugar del plan.
     */
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    /**
     * Getter de capacidadPlan.
     *
     * @return Capacidad del plan.
     */
    public int getCapacidadPlan() {
        return capacidadPlan;
    }

    /**
     * Setter de capacidadPlan.
     *
     * @param capacidadPlan Capacidad del plan.
     */
    public void setCapacidadPlan(int capacidadPlan) {
        this.capacidadPlan = capacidadPlan;
    }

    /**
     * Método que suma o resta una duración al plan.
     *
     * @param duracion Duración para sumar/restar al plan.
     */
    public void sumaDuracion(int duracion) {
        this.duracionPlan += duracion; // suma y resta
        if (getActividades().size() > 1 && duracion > 0) this.duracionPlan += 20; // suma
        else if (getActividades().size() > 1 && duracion < 0) this.duracionPlan -= 20; // resta
    }

    /**
     * Getter de costePlan.
     *
     * @return Coste del plan.
     */
    public Double getCostePlan() {
        return costePlan;
    }

    /**
     * Método que suma o resta un coste al plan.
     *
     * @param coste Coste para sumar/restar al plan.
     */
    public void sumaCoste(double coste) {
        this.costePlan += coste;
    }

    /**
     * Getter de actividades.
     *
     * @return Lista de actividades del plan.
     */
    public List<Actividad> getActividades() {
        return actividades;
    }

    /**
     * Getter de participantes.
     *
     * @return Lista de participantes del plan.
     */
    public List<Usuario> getParticipantes() {
        return participantes;
    }

    /**
     * Getter de valoraciones.
     *
     * @return Lista de valoraciones del plan.
     */
    public List<Double> getValoraciones() {
        return valoraciones;
    }

    /**
     * Getter de valoracion.
     *
     * @return Valoración del plan.
     */
    public double getValoracion() {
        return valoracion;
    }

    /**
     * Método que actualiza el valor de valoración según los elementos de valoraciones.
     */
    public void actualizaValoracion() {
        int sumaValoraciones = 0;
        for (int i = 0; i < valoraciones.size(); i++)
            sumaValoraciones += valoraciones.get(i);
        double media = (double) sumaValoraciones / this.valoraciones.size();
        valoracion = media;
    }

    /**
     * toString del plan.
     *
     * @return Cadena de carácteres con la información del plan.
     */
    public String toString() {
        return "id:" + id + "; propietario:" + propietario.getNombre() + "; nombre:" + nombrePlan + "; fecha:" + fecha + "; lugar:" + lugar;
    }

    /**
     * toString del plan para listarlos con participantes.
     *
     * @return Cadena de carácteres con la información del plan y sus participantes.
     */
    public String toStringListarPlanes() {
        if (capacidadPlan != Integer.MAX_VALUE)
            return toString() + ";plazas:" + (capacidadPlan - participantes.size()) + "; participantes:" + participantes.size();
        else return toString() + ";plazas: Sin límite; participantes:" + participantes.size();
    }

    /**
     * toString extendido del plan.
     *
     * @return Cadena de carácteres con la información del plan de manera extendida.
     */
    public String toStringExtendido() {
        if (capacidadPlan != Integer.MAX_VALUE)
            return toString() + "; aforo:" + capacidadPlan + "; duración:" + duracionPlan + " min; coste:" + costePlan + " €; actividades:";
        else
            return toString() + "; aforo:Sin límite; duración:" + duracionPlan + " min; coste:" + costePlan + " €; actividades:";
    }
}