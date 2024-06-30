package edu.upm.etsisi.poo.iwsim21.controlador;

import edu.upm.etsisi.poo.iwsim21.exceptions.*;
import edu.upm.etsisi.poo.iwsim21.modelo.actividad.Actividad;
import edu.upm.etsisi.poo.iwsim21.modelo.plan.Plan;
import edu.upm.etsisi.poo.iwsim21.modelo.usuario.Usuario;
import edu.upm.etsisi.poo.iwsim21.vista.CLI;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ControladorPlan {

    /**
     * Lista Plan de planes creados.
     */
    private List<Plan> listaPlan;
    /**
     * CLI del proyecto.
     */
    private CLI cli;

    /**
     * Constructor de ControladorPlan.
     */
    public ControladorPlan() {
        listaPlan = new ArrayList<>();
        cli = CLI.getInstance();
    }

    /**
     * Método que se encarga de crear el plan comprobando sus datos.
     *
     * @param propietario Propietario del plan.
     * @param nombre      Nombre del plan.
     * @param fecha       Fecha del plan.
     * @param lugar       Lugar del plan.
     * @param capacidad   Capacidad del plan (opcional).
     */
    public void crearPlan(Usuario propietario, String nombre, String fecha, String lugar, int capacidad) {
        Plan planCreado = new Plan(propietario, nombre, fecha, lugar, capacidad);

        //El propietario del plan debe pertenecer a este.
        listaPlan.add(planCreado);
        planCreado.getParticipantes().add(propietario);
        cli.emitirMensaje("El plan ha sido creado con éxito.");
    }

    /**
     * Método que se encarga de editar un plan concreto.
     *
     * @param id          ID del plan que se quiere editar.
     * @param propietario Usuario del que se comprueba si es propietario del plan para editarlo.
     * @param nombre      Nombre nuevo del plan.
     * @param fecha       Fecha nueva del plan.
     * @param lugar       Lugar nuevo del plan.
     * @param capacidad   Capacidad nueva del plan (opcional).
     */
    public void editarPlan(int id, Usuario propietario, String nombre, String fecha, String lugar, int capacidad) throws NonExistentDataException, NoPermissionException {
        Plan planEditar = getPlanId(id);
        if (planEditar != null) {
            if (planEditar.getPropietario() == propietario) {
                planEditar.setNombrePlan(nombre);
                planEditar.setFechaDate(fecha);
                planEditar.setLugar(lugar);
                planEditar.setCapacidadPlan(capacidad);
                cli.emitirMensaje("El plan ha sido editado con éxito.");
            } else throw new NoPermissionException();
        } else throw new NonExistentPlanException();
    }

    /**
     * Método que se encarga de eliminar un plan.
     *
     * @param propietario Usuario del que se comprueba si es propietario del plan para borrarlo.
     * @param id          ID del plan que se desea eliminar.
     */
    public void borrarPlan(Usuario propietario, int id) throws NonExistentDataException, NoPermissionException {
        Plan planBuscado = getPlanId(id);
        if (planBuscado != null) {
            if (planBuscado.getPropietario().equals(propietario)) {
                listaPlan.remove(planBuscado);
                cli.emitirMensaje("Plan borrado correctamente.");
            } else throw new NoPermissionException();
        } else throw new NonExistentPlanException();
    }

    /**
     * Método que se encarga de añadir una actividad a un plan.
     *
     * @param idPlan      ID del plan deseado.
     * @param actividad   ID de la actividad deseada.
     * @param propietario Usuario.
     */
    public void anadirActividadPlan(int idPlan, Actividad actividad, Usuario propietario) throws NonExistentDataException, NoPermissionException {
        Plan planBuscado = getPlanId(idPlan);

        // Si planBuscado cumple el if -> añado, si no error
        if (planBuscado != null) {
            if (planBuscado.getPropietario().equals(propietario)) {
                int aforoAct = 0;
                for (Actividad actividadAct : planBuscado.getActividades())
                    aforoAct += actividadAct.getAforo();
                if (aforoAct + actividad.getAforo() <= planBuscado.getCapacidadPlan()) {
                    planBuscado.getActividades().add(actividad);
                    planBuscado.sumaDuracion(actividad.getDuracionAct());
                    planBuscado.sumaCoste(actividad.getCosteAct());
                    cli.emitirMensaje("Actividad añadida correctamente.");
                } else throw new NoCapacityException();
            } else throw new NoPermissionException("Solo el propietario puede alterar un plan.");
        } else throw new NonExistentPlanException();
    }

    /**
     * Método que se encarga de eliminar una actividad de un plan.
     *
     * @param idPlan      ID del plan deseado.
     * @param actividad   ID de la actividad deseada.
     * @param propietario Usuario.
     */
    public void eliminarActividadPlan(int idPlan, Actividad actividad, Usuario propietario) throws NonExistentDataException, NoPermissionException {
        Plan planBuscado = getPlanId(idPlan);
        boolean flag = false;
        // Si planBuscado cumple el if -> elimino, si no mensaje
        if (planBuscado != null) {
            if (planBuscado.getPropietario().equals(propietario)) {
                // busco actividad en la lista de actividades del plan
                for (int i = 0; i < planBuscado.getActividades().size() && !flag; i++) {
                    // si encuentro elimino, resto duracion y resto coste del plan.
                    if (planBuscado.getActividades().get(i).getId() == actividad.getId()) {
                        planBuscado.getActividades().remove(actividad);
                        planBuscado.sumaDuracion(actividad.getDuracionAct() * (-1)); //resto duracion
                        planBuscado.sumaCoste(actividad.getCosteAct() * (-1)); //resto coste
                        flag = true;
                        cli.emitirMensaje("Actividad eliminada correctamente.");
                    }
                }
                if (!flag) throw new NonExistentActivityException("La actividad seleccionada no pertenece a este plan");
            } else throw new NoPermissionException("Solo el propietario puede alterar un plan.");
        } else throw new NonExistentPlanException();
    }

    /**
     * Método que se encarga de añadir un usuario a un plan.
     *
     * @param participante Usuario que se quiere añadir al plan.
     * @param id           ID del plan deseado.
     * @param fecha        Fecha presente.
     */
    public void joinPlan(Usuario participante, int id, GregorianCalendar fecha) throws NonExistentDataException, NoPermissionException {
        Plan planbuscado = getPlanId(id);
        if (planbuscado != null) {
            if (planbuscado.getFechaDate().after(fecha)) {
                if (!contieneUser(id, participante)) {
                    if (!haySolapamiento(participante, id)) {
                        if (planbuscado.getCapacidadPlan() == Integer.MAX_VALUE || ((planbuscado.getCapacidadPlan() - (planbuscado.getParticipantes().size() + 1)) != 0)) {
                            planbuscado.getParticipantes().add(participante);
                            cli.emitirMensaje("Te has unido al plan " + planbuscado.getId() + " correctamente.");
                        } else throw new NoCapacityException();
                    } else
                        throw new NoPermissionException("No puedes unirte a un plan que se solapa en el tiempo con otros en los que estás unido.");
                } else throw new NoPermissionException("Usted ya se ha unido a el plan.");
            } else throw new NoPermissionException("No puedes unirte a un plan pasado.");
        } else throw new NonExistentPlanException();
    }

    /**
     * Método que se encarga de eliminar un usuario de un plan.
     *
     * @param participante Usuario que se quiere eliminar del plan.
     * @param id           ID del plan deseado.
     * @param fecha        Fecha presente.
     */
    public void leavePlan(Usuario participante, int id, GregorianCalendar fecha) throws NonExistentDataException, NoPermissionException {
        Plan planSelec = getPlanId(id);
        if (planSelec != null) {
            if (planSelec.getFechaDate().after(fecha)) {
                if (contieneUser(id, participante)) {
                    planSelec.getParticipantes().remove(participante);
                    cli.emitirMensaje("Has abandonado el plan " + planSelec.getId() + " correctamente.");
                } else throw new NoPermissionException("No puedes dejar un plan en el que no estás.");
            } else throw new NoPermissionException("No puedes abandonar un plan que ya ha pasado.");

        } else throw new NonExistentPlanException();

    }

    /**
     * Método que se encarga de valorar un plan.
     *
     * @param id          ID del plan deseado.
     * @param valoracion  Valoración deseada del plan.
     * @param fechaActual Fecha presente.
     * @param user        Usuario que quiere valorar el plan.
     */
    public void valorarPlan(int id, double valoracion, GregorianCalendar fechaActual, Usuario user) throws NonExistentDataException, NoPermissionException {
        Plan plan = getPlanId(id);
        if (plan != null) {
            if (plan.getFechaDate().before(fechaActual)) {
                if (contieneUser(id, user)) {
                    plan.getValoraciones().add(valoracion);
                    plan.actualizaValoracion();
                } else throw new NoPermissionException("Debes pertenecer al plan para valorarlo.");
            } else throw new NoPermissionException("Solo se pueden valorar planes pasados.");
        } else throw new NonExistentPlanException();
    }

    /**
     * Método que devuelve la valoración de un plan.
     *
     * @param id ID del plan deseado.
     */
    public void valoracionPlan(int id) throws NonExistentDataException {
        Plan plan = getPlanId(id);
        if (plan != null) {
            cli.emitirMensaje(String.format("Valoración: %.2f", plan.getValoracion()));
        } else throw new NonExistentPlanException();
    }

    /**
     * Método que devuelve los planes disponibles.
     */
    public void planes() {
        cli.emitirMensaje("Planes:");
        for (Plan plan : listaPlan)
            cli.emitirMensaje(" -" + plan.toString());
    }

    /**
     * Método que lista los planes posteriores a una fecha.
     *
     * @param fecha Fecha mínima.
     */
    public void listarPlan(String fecha) {
        cli.emitirMensaje("Planes:");

        //Transformo un string en una fecha de tipo GregorianCalendar
        GregorianCalendar fechaBusqueda = new GregorianCalendar();
        setFechaDate(fecha, fechaBusqueda);

        //Busco planes posteriores
        List<Plan> fechasPosteriores = new ArrayList<>();
        for (Plan plan : listaPlan)
            if (plan.getFechaDate().compareTo(fechaBusqueda) >= 0) fechasPosteriores.add(plan);

        //Ordeno
        ordenarPorFecha(fechasPosteriores, 0, fechasPosteriores.size() - 1);
        for (Plan planImprimir : fechasPosteriores)
            cli.emitirMensaje(" -" + planImprimir.toStringListarPlanes());
    }

    /**
     * Método que devuelve los planes a los que pertenece un usuario.
     *
     * @param usuario Usuario del que se quiere conocer sus planes.
     */
    public void misPlanes(Usuario usuario) {
        cli.emitirMensaje("Mis planes:");

        // Para cada plan veo si está el usuario, si está listo ese plan.
        for (Plan p : listaPlan) {
            if (contieneUser(p.getId(), usuario)) cli.emitirMensaje(" -" + p.toString());
        }
    }

    /**
     * Método que devuelve el coste total de un plan para un usuario concreto.
     *
     * @param usuario Usuario que debe pertenecer al plan.
     * @param id      ID del plan deseado.
     */
    public void costeTotal(Usuario usuario, int id) throws NonExistentDataException, NoPermissionException {
        Plan planBuscado = getPlanId(id);
        if (planBuscado != null) {
            if (contieneUser(id, usuario)) {
                Double coste = 0.0;
                for (Actividad a : planBuscado.getActividades())
                    coste += a.getCosteAct(usuario);
                cli.emitirMensaje("Coste: " + coste + "€");
            } else throw new NoPermissionException("No perteneces al plan seleccionado.");
        } else throw new NonExistentPlanException();
    }

    /**
     * Realiza la BÚSQUEDA de un plan según su coste.
     *
     * @param coste Coste del plan que se desea como máximo.
     */
    public void consultaPlanesCoste(int coste) {
        cli.emitirMensaje("Planes:");
        for (Plan plan : listaPlan) {
            if (plan.getCostePlan() <= coste) cli.emitirMensaje(" -" + plan.toString());
        }
    }

    /**
     * Realiza la BÚSQUEDA de un plan según su fecha.
     *
     * @param fecha Fecha del plan que se desea.
     */
    public void consultaPlanesFecha(String fecha) {
        cli.emitirMensaje("Planes:");
        GregorianCalendar fechaBuscada = new GregorianCalendar();
        setFechaDate(fecha, fechaBuscada);
        for (Plan plan : listaPlan)
            if (plan.getFechaDate().equals(fechaBuscada)) cli.emitirMensaje(" -" + plan.toString());
    }

    /**
     * Realiza la BÚSQUEDA de un plan según su lugar.
     *
     * @param lugar String deseado que debe de estar contenido en el lugar del plan.
     */
    public void consultaPlanesLugar(String lugar) {
        cli.emitirMensaje("Planes:");
        for (Plan p : listaPlan)
            if (p.getLugar().contains(lugar)) cli.emitirMensaje(" -" + p.toString());
    }

    //MÉTODOS ADICIONALES

    /**
     * Método que comprueba si un usuario pertenece a un plan.
     *
     * @param id   ID del plan deseado.
     * @param user Usuario que se desea comprobar.
     * @return True si el usuario pertenece al plan y false si no.
     */
    public boolean contieneUser(int id, Usuario user) {
        boolean contiene = false;
        Plan p = getPlanId(id);
        if (p != null) {
            for (int i = 0; i < p.getParticipantes().size() && !contiene; i++) {
                Usuario u = p.getParticipantes().get(i);
                if (u.equals(user)) contiene = true;
            }
        }
        return contiene;
    }

    /**
     * Método que se encarga de ordenar una lista de planes según su fecha.
     *
     * @param lista Lista de planes que se desea ordenar.
     * @param i0    Valor mínimo.
     * @param iN    Valor máximo.
     */
    public void ordenarPorFecha(List<Plan> lista, int i0, int iN) {
        if (i0 < iN) {
            int k = ordenarPorFechaAux(lista, i0, iN);
            ordenarPorFecha(lista, i0, k - 1);
            ordenarPorFecha(lista, k + 1, iN);
        }
    }

    /**
     * Método auxiliar de ordenarPorFecha.
     *
     * @param lista Lista de planes que se desea ordenar.
     * @param i0    Valor mínimo.
     * @param iN    Valor máximo.
     * @return Puntero.
     */
    private int ordenarPorFechaAux(List<Plan> lista, int i0, int iN) {
        GregorianCalendar pivot = lista.get(iN).getFechaDate();
        int i = (i0 - 1);
        for (int j = i0; j < iN; j++) {
            if (lista.get(j).getFechaDate().compareTo(pivot) <= 0) {
                i++;
                Plan temp = lista.get(i);
                lista.set(i, lista.get(j));
                lista.set(j, temp);
            }
        }
        Plan temp = lista.get(i + 1);
        lista.set(i + 1, lista.get(iN));
        lista.set(iN, temp);
        return (i + 1);
    }

    /**
     * Método que comprueba si un plan dado se solapa con los planes de un usuario.
     *
     * @param usuario Usuario del que se desea conocer el solapamiento.
     * @param id      ID del plan que se quiere comprobar.
     * @return True si el plan deseado se solapa con los del usuario y false si no.
     */
    public boolean haySolapamiento(Usuario usuario, int id) {
        boolean seSolapan = false;
        Plan plan, p = getPlanId(id);
        if (p != null) {
            for (int i = 0; i < listaPlan.size() && !seSolapan; i++) {
                if (contieneUser(i, usuario)) {
                    plan = getPlanId(i);
                    if (plan.getFechaDate().compareTo(p.getFechaDate()) == 0) seSolapan = true;
                }
            }
        }
        return seSolapan;
    }

    /**
     * Método que actualiza una fecha de tipo GregorianCalendar según un String.
     *
     * @param fecha  String con la fecha deseada.
     * @param objeto Fecha en formato GregorianCalendar que se quiere actualizar.
     */
    public void setFechaDate(String fecha, GregorianCalendar objeto) throws RuntimeException {
        try {
            SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy-HH:mm");
            Date f = simpleDate.parse(fecha);
            objeto.setTime(f);
        } catch (ParseException e) {
            throw new RuntimeException(e);
	    System.out.print("HOla");
        }
    }

    /**
     * Método que devuelve un plan según su ID.
     *
     * @param id ID del plan deseado.
     * @return Plan que se corresponde con el ID dado si existiese.
     */
    public Plan getPlanId(int id) {
        Plan plan = null;
        for (int i = 0; i < listaPlan.size() && plan == null; i++) {
            Plan p = listaPlan.get(i);
            if (p.getId() == id) plan = p;
        }
        return plan;
    }

    /**
     * Getter de listaPlan.
     *
     * @return Lista con los planes creados.
     */
    public List<Plan> getListaPlan() {
        return listaPlan;
    }
}
