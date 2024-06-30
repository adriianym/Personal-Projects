package edu.upm.etsisi.poo.iwsim21;

import edu.upm.etsisi.poo.iwsim21.exceptions.*;
import edu.upm.etsisi.poo.iwsim21.controlador.ControladorActividad;
import edu.upm.etsisi.poo.iwsim21.controlador.ControladorPlan;
import edu.upm.etsisi.poo.iwsim21.controlador.ControladorUsuario;
import edu.upm.etsisi.poo.iwsim21.modelo.actividad.Actividad;
import edu.upm.etsisi.poo.iwsim21.modelo.actividad.Cine;
import edu.upm.etsisi.poo.iwsim21.modelo.actividad.Teatro;
import edu.upm.etsisi.poo.iwsim21.modelo.plan.Plan;
import edu.upm.etsisi.poo.iwsim21.modelo.usuario.Usuario;
import edu.upm.etsisi.poo.iwsim21.vista.CLI;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * App gestiona toda la aplicación de gestión de planes sociales.
 *
 * @author Dámaso Ruiz Campos
 * @author Carlos Rubio Medina
 * @author Adrián Yepes Mora
 * @version 1.0
 */


public class App {
    private ControladorActividad controladorActividad;
    private ControladorUsuario controladorUsuario;
    private ControladorPlan controladorPlan;
    private GregorianCalendar fechaPresente;
    private Usuario usuarioLogeado;
    private CLI cli;

    /**
     * Método main de App.
     *
     * @param args
     */
    public static void main(String[] args) {
        App app = new App();
        try {
            app.init();
            app.start();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.out.println("   Error: " + e.getMessage());
        }

    }

    /**
     * Constructor de App.
     */
    public App() {
        controladorActividad = new ControladorActividad();
        controladorUsuario = new ControladorUsuario();
        controladorPlan = new ControladorPlan();
        fechaPresente = new GregorianCalendar();
        usuarioLogeado = null;
        cli = CLI.getInstance();
    }

    /**
     * Método init que inicia la fecha y el poblador.
     *
     * @throws ParseException
     */
    public void init() throws ParseException {
        cli.welcome();
        fechaPresente.setTime(new Date());

        poblador();
    }

    /**
     * Método que inicia el programa.
     */
    public void start() {
        cli.comandos();

        //Bucle que impide salir del programa.
        while (true) {
            try {
                //Se piden el input y se obtienen el comando y los operandos según su longitud.
                String[] input = cli.obtenerOperando(usuarioLogeado).trim().split(":", 2);
                String comando = input[0].trim().toLowerCase();
                String[] operandos = null;
                if (input.length > 1) operandos = input[1].trim().split(";");
                aplicarComando(comando, operandos);
            } catch (NumberFormatException numbEx) {
                System.out.println("Datos erróneos.");
            } catch (Exception ex) {
                System.out.println("   Error: " + ex.getMessage());
            }
        }
    }

    /**
     * Método que dados un comando y los operandos realiza la operación necesaria.
     *
     * @param comando   Tipo String que designa la operación a realizar.
     * @param operandos Vector de String que contiene el operando/s necesarios.
     */
    public void aplicarComando(String comando, String[] operandos) throws IncorrectInputException, NoLogException, NonExistentDataException, NoPermissionException {

        //Dependiendo el comando introducido se hace una acción u otra.
        switch (comando) {

            //Instrucciones sin login
            case "registro":
                if (operandos.length != 4) throw new IncorrectInputException("Número incorrecto de operandos.");
                controladorUsuario.registro(operandos[0], Integer.parseInt(operandos[1]), Integer.parseInt(operandos[2]), operandos[3]);
                break;
            case "login":
                if (operandos.length != 2) throw new IncorrectInputException("Número incorrecto de operandos.");
                usuarioLogeado = controladorUsuario.loginUsuario(operandos[0], operandos[1]);
                break;
            case "logout":
                usuarioLogeado = null;
                break;

            //Instrucciones con login.
            case "crear-actividad":
                if (usuarioLogeado != null) {
                    if (operandos.length != 6 && operandos.length != 5)
                        throw new IncorrectInputException("Número incorrecto de operandos.");
                    int capacidad = Integer.MAX_VALUE;
                    if (operandos.length > 5) capacidad = Integer.parseInt(operandos[5]);
                    controladorActividad.crearActividad(operandos[0], operandos[1], operandos[2], Integer.parseInt(operandos[3]), Double.parseDouble(operandos[4]), capacidad);
                } else throw new NoLogException();
                break;
            case "crear-plan":
                if (usuarioLogeado != null) {
                    if (operandos.length != 3 && operandos.length != 4)
                        throw new IncorrectInputException("Número incorrecto de operandos.");
                    int capacidad = Integer.MAX_VALUE;
                    if (operandos.length > 3) capacidad = Integer.parseInt(operandos[3]);
                    controladorPlan.crearPlan(usuarioLogeado, operandos[0], operandos[1], operandos[2], capacidad);
                } else throw new NoLogException();
                break;
            case "editar-plan":
                if (usuarioLogeado != null) {
                    if (operandos.length != 4 && operandos.length != 5)
                        throw new IncorrectInputException("Número incorrecto de operandos.");
                    int capacidad = Integer.MAX_VALUE;
                    if (operandos.length > 4) capacidad = Integer.parseInt(operandos[4]);
                    controladorPlan.editarPlan(Integer.parseInt(operandos[0]), usuarioLogeado, operandos[1], operandos[2], operandos[3], capacidad);
                } else throw new NoLogException();
                break;
            case "borrar-plan":
                if (usuarioLogeado != null) {
                    if (operandos.length != 1) throw new IncorrectInputException("Número incorrecto de operandos.");
                    controladorPlan.borrarPlan(usuarioLogeado, Integer.parseInt(operandos[0]));
                } else throw new NoLogException();
                break;
            case "añadir-actividad-plan":
                if (usuarioLogeado != null) {
                    if (operandos.length != 2) throw new IncorrectInputException("Número incorrecto de operandos.");
                    Actividad actividad = controladorActividad.getActividadId(Integer.parseInt(operandos[1]));
                    if (actividad != null)
                        controladorPlan.anadirActividadPlan(Integer.parseInt(operandos[0]), actividad, usuarioLogeado);
                    else throw new NonExistentActivityException();
                } else throw new NoLogException();
                break;
            case "borrar-actividad-plan":
                if (usuarioLogeado != null) {
                    if (operandos.length != 2) throw new IncorrectInputException("Número incorrecto de operandos.");
                    Actividad actividad = controladorActividad.getActividadId(Integer.parseInt(operandos[1]));
                    if (actividad != null)
                        controladorPlan.eliminarActividadPlan(Integer.parseInt(operandos[0]), actividad, usuarioLogeado);
                    else throw new NonExistentActivityException();
                } else throw new NoLogException();
                break;
            case "unirse-plan":
                if (usuarioLogeado != null) {
                    if (operandos.length != 1) throw new IncorrectInputException("Número incorrecto de operandos.");
                    controladorPlan.joinPlan(usuarioLogeado, Integer.parseInt(operandos[0]), fechaPresente);
                } else throw new NoLogException();
                break;
            case "abandonar-plan":
                if (usuarioLogeado != null) {
                    if (operandos.length != 1) throw new IncorrectInputException("Número incorrecto de operandos.");
                    controladorPlan.leavePlan(usuarioLogeado, Integer.parseInt(operandos[0]), fechaPresente);
                } else throw new NoLogException();
                break;
            case "valorar-plan":
                if (usuarioLogeado != null) {
                    if (operandos.length != 2) throw new IncorrectInputException("Número incorrecto de operandos.");
                    controladorPlan.valorarPlan(Integer.parseInt(operandos[0]), Integer.parseInt(operandos[1]), fechaPresente, usuarioLogeado);
                } else throw new NoLogException();
                break;
            case "valoración-plan":
            case "valoracion-plan":
                if (usuarioLogeado != null) {
                    if (operandos.length != 1) throw new IncorrectInputException("Número incorrecto de operandos.");
                    controladorPlan.valoracionPlan(Integer.parseInt(operandos[0]));
                } else throw new NoLogException();
                break;
            case "planes":
                if (usuarioLogeado != null) controladorPlan.planes();
                else throw new NoLogException();
                break;
            case "listar-planes-fecha":
                if (usuarioLogeado != null) {
                    if (operandos.length != 1) throw new IncorrectInputException("Número incorrecto de operandos.");
                    controladorPlan.listarPlan(operandos[0]);
                } else throw new NoLogException();
                break;
            case "mis-planes":
                if (usuarioLogeado != null) controladorPlan.misPlanes(usuarioLogeado);
                else throw new NoLogException();
                break;
            case "coste-plan":
                if (usuarioLogeado != null) {
                    if (operandos.length != 1) throw new IncorrectInputException("Número incorrecto de operandos.");
                    controladorPlan.costeTotal(usuarioLogeado, Integer.parseInt(operandos[0]));
                } else throw new NoLogException();
                break;
            case "busqueda-planes":
                if (usuarioLogeado != null) {
                    if (operandos.length != 2) throw new IncorrectInputException("Número incorrecto de operandos.");
                    String criterio = operandos[0];
                    switch (criterio.toLowerCase()) {
                        case "coste":
                            controladorPlan.consultaPlanesCoste(Integer.parseInt(operandos[1]));
                            break;
                        case "fecha":
                            controladorPlan.consultaPlanesFecha(operandos[1]);
                            break;
                        case "lugar":
                            controladorPlan.consultaPlanesLugar(operandos[1]);
                            break;
                        default:
                            throw new IncorrectInputException("El criterio debe ser coste, fecha o lugar.");
                    }
                } else throw new NoLogException();
                break;
            case "busqueda-actividades":
                if (usuarioLogeado != null) {
                    if (operandos.length != 2) throw new IncorrectInputException("Número incorrecto de operandos.");
                    String criterio = operandos[0];
                    switch (criterio.toLowerCase()) {
                        case "nombre":
                            controladorActividad.consultaActividadNombre(operandos[1]);
                            break;
                        case "capacidad":
                            controladorActividad.consultaActividadCapacidad(Integer.parseInt(operandos[1]));
                            break;
                        case "duración":
                        case "duracion":
                            controladorActividad.consultaActividadDuracion(Integer.parseInt(operandos[1]));
                            break;
                        default:
                            throw new IncorrectInputException("El criterio debe ser nombre, capacidad o duración.");
                    }
                } else throw new NoLogException();
                break;
            default:
                throw new IncorrectInputException("Comando desconocido.");
        }
    }

    /**
     * Método poblador que crea 3 usuarios, 3 planes y 3 actividades(cada una de un tipo distinto).
     */
    public void poblador() {
        Usuario usuario1 = new Usuario("Pepe", 26, 111111, "123");
        Usuario usuario2 = new Usuario("Manuel", 68, 222222, "123");
        Usuario usuario3 = new Usuario("Rodrigo", 19, 333333, "123");
        controladorUsuario.getListaUsuario().add(usuario1);
        controladorUsuario.getListaUsuario().add(usuario2);
        controladorUsuario.getListaUsuario().add(usuario3);

        controladorPlan.getListaPlan().add(new Plan(usuario2, "Día con la familia.", "24/12/2024-16:30", "Casa de los tíos.", 20));
        controladorPlan.getListaPlan().add(new Plan(usuario1, "Quedada con amigos.", "24/12/2024-17:00", "Plaza del pueblo.", Integer.MAX_VALUE));
        controladorPlan.getListaPlan().add(new Plan(usuario1, "Sesion de cine", "10/11/2025-20:30", "Yelmo cines.", Integer.MAX_VALUE));
        controladorPlan.getListaPlan().add(new Plan(usuario3, "Cena de nochevieja.", "30/12/2022-18:00", "Bar de Toledo.", 20));

        controladorActividad.getListaActividad().add(new Actividad("Irse de tapas.", "Pasar el día tomando tapas.", 80, 50.0, 5));
        controladorActividad.getListaActividad().add(new Cine("Ir al cine.", "Pasar la tarde en el cine.", 60, 25.0, 5));
        controladorActividad.getListaActividad().add(new Teatro("Ir al teatro.", "Ir a ver el teatro.", 35, 15.0, Integer.MAX_VALUE));
    }
}