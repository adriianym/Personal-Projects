package es.upm.tp;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Billete es una clase que encapsula las variables de localizador,
 * vuelo, pasajero, tipo, fila, columna y precio respectivas a cada
 * billete, además de una serie de métodos que implementan distintas
 * funcionalidades al billete.
 *
 * @author Adrián Yepes Mora
 * @author Pedro Salvo Saa
 * @version 1.0
 */
public class Billete {
    /**
     * Tipos de billete disponibles.
     */
    enum TIPO {TURISTA, PREFERENTE, PRIMERA}

    /**
     * Localizador del billete.
     */
    private String localizador;
    /**
     * Vuelo del que se ha comprado el billete.
     */
    private Vuelo vuelo;
    /**
     * Pasajero que ha comprado el billete.
     */
    private Pasajero pasajero;
    /**
     * Tipo de billete comprado.
     */
    private TIPO tipo;
    /**
     * Fila del asiento.
     */
    private int fila;
    /**
     * Columna del asiento.
     */
    private int columna;
    /**
     * Precio del billete.
     */
    private double precio;

    /**
     * Constructor de la clase Billete usado para definir
     * el localizador, el vuelo el pasajero, el tipo de billete,
     * la fila y columna del asiento y el precio del billete.
     *
     * @param localizador Localizador del billete.
     * @param vuelo       Vuelo del que se ha comprado el billete.
     * @param pasajero    Pasajero que ha comprado el billete.
     * @param tipo        Tipo de billete comprado.
     * @param fila        Fila del asiento.
     * @param columna     Columna del asiento.
     * @param precio      Precio del billete.
     */
    public Billete(String localizador, Vuelo vuelo, Pasajero pasajero, TIPO tipo, int fila, int columna, double precio) {
        this.localizador = localizador;
        this.vuelo = vuelo;
        this.pasajero = pasajero;
        this.tipo = tipo;
        this.fila = fila;
        this.columna = columna;
        this.precio = precio;
    }

    /**
     * Getter del atributo localizador.
     *
     * @return Localizador del billete.
     */
    public String getLocalizador() {
        return localizador;
    }

    /**
     * Getter del atributo vuelo.
     *
     * @return Vuelo del que se ha comprado el billete.
     */
    public Vuelo getVuelo() {
        return vuelo;
    }

    /**
     * Getter del atributo pasajero.
     *
     * @return Pasajero que ha comprado el billete.
     */
    public Pasajero getPasajero() {
        return pasajero;
    }

    /**
     * Getter del atributo tipo.
     *
     * @return Tipo de billete comprado.
     */
    public TIPO getTipo() {
        return tipo;
    }

    /**
     * Getter del atributo fila.
     *
     * @return Fila del asiento.
     */
    public int getFila() {
        return fila;
    }

    /**
     * Getter del atributo columna.
     *
     * @return Columna del asiento.
     */
    public int getColumna() {
        return columna;
    }

    /**
     * Método que devuelve el asiento del billete con fila y
     * columna indicado.
     *
     * @return Número del asiento que le corresponde al billete.
     */
    public String getAsiento() {
        return fila + "" + (char) (columna + 'A' - 1);
    }

    /**
     * Getter del atributo precio.
     *
     * @return Precio del billete.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Método que retorna una cadena de texto con la información
     * completa relativa al billete.
     *
     * @return Cadena de texto con la información completa relativa al billete.
     */
    public String toString() {
        return String.format("Billete %s para %s en asiento %s (%s) por %.2f€",
                localizador, vuelo.toStringSimple(), getAsiento(), getTipo(), getPrecio());
    }

    /**
     * Método que se encarga de cancelar el billete, eliminándolo
     * de la lista de billetes del vuelo y del pasajero correspondiente.
     *
     * @return Devuelve true si se ha podido cancelar correctamente
     * y false si no se ha podido cancelar.
     */
    public boolean cancelar() {
        return pasajero.cancelarBillete(localizador);
    }

    /**
     * Método que imprime la información del billete en un
     * fichero CSV.
     *
     * @param fichero Nombre del fichero CSV.
     * @return Devuelve true si se ha imprimido correctamente
     * y false si no se ha podido imprimir.
     */
    public boolean generarFactura(String fichero) {
        boolean generado = false;
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(fichero));
            pw.println("----------------------------------------------------");
            pw.println("---------- Factura del billete " + localizador + " ----------");
            pw.println("----------------------------------------------------");
            pw.println("Vuelo: " + vuelo.getId());
            pw.printf("Origen: %s T%d\n", vuelo.getOrigen().toStringSimple(), vuelo.getTerminalOrigen());
            pw.printf("Destino: %s T%d\n", vuelo.getDestino().toStringSimple(), vuelo.getTerminalDestino());
            pw.printf("Salida: %s\n", vuelo.getSalida().toString());
            pw.printf("Llegada: %s\n", vuelo.getLlegada().toString());
            pw.printf("Pasajero: %s\n", pasajero.toString());
            pw.printf("Tipo de billete: %s\n", tipo);
            pw.printf("Asiento: %s\n", getAsiento());
            pw.printf("Precio: %.02f€\n", precio);
            generado = true;
        } catch (IOException error) {
            System.out.println("Error de escritura en fichero Factura");
        } finally {
            if (pw != null) pw.close();
        }
        return generado;
    }

    /**
     * Método estático que genera un localizador aleatorio.
     *
     * @param rand    Objeto de tipo Random empleado para la parte aleatoria.
     * @param idVuelo ID del vuelo asociado.
     * @return Una cadena de 10 caracteres, de los cuales los seis
     * primeros serán el ID del vuelo asociado y los 4 siguientes serán
     * letras mayúsculas aleatorias.
     */
    public static String generarLocalizador(Random rand, String idVuelo) {
        return idVuelo + "" + (char) rand.nextInt(65, 90) + (char) rand.nextInt(65, 90)
                + (char) rand.nextInt(65, 90) + (char) rand.nextInt(65, 90);
    }

    /**
     * Método estático que crea un nuevo billete para un vuelo y pasajero
     * específico, pidiendo por teclado los datos necesarios al usuario.
     * Pidiéndolos de manera repetida has que los parámetros introducidos
     * sean los correctos.
     *
     * @param teclado  Se encarga de recoger por teclado los datos deseados.
     * @param rand     Objeto de tipo Random empleado para la parte aleatoria.
     * @param vuelo    Objeto de tipo Vuelo.
     * @param pasajero Objeto de tipo Pasajero.
     * @return Un objeto de tipo Billete con los datos dados por teclado.
     */
    public static Billete altaBillete(Scanner teclado, Random rand, Vuelo vuelo, Pasajero pasajero) {
        int eleccionFila, eleccionColumna = 0;
        double precioAsiento;
        TIPO tipoBillete;
        String genLocalizador;
        vuelo.imprimirMatrizAsientos();
        System.out.println("Tipo de asiento: '[ ]' = TURISTA, '{ }' = PREFERENTE, '( )' = PRIMERA");
        do {
            eleccionFila = Utilidades.leerNumero(teclado, String.format("Ingrese fila del asiento (1 - %d):",
                    vuelo.getAvion().getFilas()), 1, vuelo.getAvion().getFilas());
            do {
                eleccionColumna = Character.toUpperCase(Utilidades.leerLetra(teclado, String.format("Ingrese columna del asiento (A - %s):",
                        (char) ('A' + vuelo.getAvion().getColumnas() - 1)), 'A', 'z')) + 1;
            } while (eleccionColumna < 'A' || eleccionColumna > (char) ('A' + vuelo.getAvion().getColumnas()));
            if (vuelo.asientoOcupado(eleccionFila, eleccionColumna -= 'A'))
                System.out.printf("El asiento %d%s ya está reservado.\n", eleccionFila, (char) ('A'- 1 + eleccionColumna));
        } while (vuelo.asientoOcupado(eleccionFila, eleccionColumna));
        do {
            genLocalizador = generarLocalizador(rand, vuelo.getId());
        } while (pasajero.buscarBillete(genLocalizador) != null);
        if (eleccionFila == 1) {
            tipoBillete = TIPO.PRIMERA;
            precioAsiento = vuelo.getPrecioPrimera();
        } else if (eleccionFila <= 5) {
            tipoBillete = TIPO.PREFERENTE;
            precioAsiento = vuelo.getPrecioPreferente();
        } else {
            tipoBillete = TIPO.TURISTA;
            precioAsiento = vuelo.getPrecio();
        }
        return new Billete(genLocalizador, vuelo, pasajero, tipoBillete, eleccionFila, eleccionColumna, precioAsiento);
    }
}