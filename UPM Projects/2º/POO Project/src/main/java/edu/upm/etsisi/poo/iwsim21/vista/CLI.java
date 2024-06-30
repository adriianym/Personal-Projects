package edu.upm.etsisi.poo.iwsim21.vista;

import edu.upm.etsisi.poo.iwsim21.modelo.usuario.Usuario;

import java.util.Scanner;

public class CLI {

    /**
     * Instancia fija de CLI.
     */
    private static CLI instance;
    /**
     * Objeto Scanner.
     */
    final private Scanner S = new Scanner(System.in);

    /**
     * Constructor privado de CLI.
     */
    private CLI() {
    }

    /**
     * Método que muestra el mensaje de bienvenida.
     */
    public void welcome() {
        System.out.println("  App de Gestión de Planes Sociales  ");
        System.out.println("-------------------------------------\n");
    }

    /**
     * Método que muestra los comandos y su sintáxis.
     */
    public void comandos() {
        System.out.println("COMANDOS:");
        System.out.println(" -registro:               <<nombre>>;<<edad>>;<<teléfono>>;<<contraseña>>");
        System.out.println(" -login:                  <<usuario>>;<<contraseña>>");
        System.out.println(" -logout");
        System.out.println(" -crear-actividad:        <<tipo>>;<<nombre>>;<<descripción>>;<<duración>>;<<coste>>;<[aforo]>");
        System.out.println(" -crear-plan:             <<nombre>>;<<dd/MM/yyyy-HH:mm>>;<<lugar>>;<[capacidad]>");
        System.out.println(" -editar-plan:            <<id>>;<<nombre>>;<<dd/MM/yyyy-HH:mm>>;<<lugar>>;<[capacidad]>");
        System.out.println(" -borrar-plan:            <<id>>");
        System.out.println(" -añadir-actividad-plan:  <<idPlan>>;<<idActividad>>");
        System.out.println(" -borrar-actividad-plan:  <<idPlan>>;<<idActividad>>");
        System.out.println(" -unirse-plan:            <<id>>");
        System.out.println(" -abandonar-plan:         <<id>>");
        System.out.println(" -planes");
        System.out.println(" -listar-planes-fecha:    <<dd/MM/yyyy-HH:mm>>");
        System.out.println(" -mis-planes");
        System.out.println(" -coste-plan:             <<id>>");
        System.out.println(" -busqueda-planes:        <<consulta (coste / lugar / fecha)>>;<<atributo>>");
        System.out.println(" -busqueda-actividades:   <<consulta (nombre / capacidad / duracion)>>;<<atributo>>");
        System.out.println(" -valorar-plan:           <<id>>;<<valoración>>");
        System.out.println(" -valoracion-plan:        <<id>>");
    }

    /**
     * Método que obtiene el operando completo por teclado.
     *
     * @param usuario Usuario para mostrarle o no si procede.
     * @return Operando dado por teclado.
     */
    public String obtenerOperando(Usuario usuario) {
        if (usuario == null) System.out.print("gps> ");
        else System.out.print("gps-" + usuario.getNombre() + "> ");
        return S.nextLine();
    }

    /**
     * Método que se encarga de emitir mensajes concretos.
     *
     * @param mensaje Mensaje que se desea emitir.
     */
    public void emitirMensaje(String mensaje) {
        System.out.println("   " + mensaje);
    }

    /**
     * Método getInstance de CLI según el patron Singleton.
     *
     * @return Devuelve la instancia única de CLI.
     */
    public static CLI getInstance() {
        if (instance == null) instance = new CLI();
        return instance;
    }
}