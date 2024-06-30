package es.upm.tp;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Vuelo es una clase que encapsula las variables de asientos,
 * id, avion, origen, terminalOrigen, salida, destino, terminalDestino,
 * llegada y precio que conforman la información relativa a un vuelo,
 * además de una serie de métodos que implementan distintas funcionalidades
 * al vuelo.
 *
 * @author Adrián Yepes Mora
 * @author Pedro Salvo Saa
 * @version 1.0
 */
public class Vuelo {

    /**
     * ID del vuelo.
     */
    private String id;

    /**
     * Avión empleado en el vuelo.
     */
    private Avion avion;

    /**
     * Aeropuerto de origen del vuelo.
     */
    private Aeropuerto origen;

    /**
     * Número de terminales del aeropuerto de origen.
     */
    private int terminalOrigen;

    /**
     * Fecha de salida del vuelo.
     */
    private Fecha salida;

    /**
     * Aeropuerto de destino del vuelo.
     */
    private Aeropuerto destino;

    /**
     * Número de terminales del aeropuerto de destino.
     */
    private int terminalDestino;

    /**
     * Fecha de llegada del vuelo.
     */
    private Fecha llegada;

    /**
     * Precio base del vuelo.
     */
    private double precio;
    /**
     * Matriz de asientos.
     */
    private boolean[][] asientos;

    /**
     * Lista de billetes comprados para el vuelo.
     */
    private ListaBilletes listaBilletes;

    /**
     * Constructor de la clase Vuelo usado para inicializar la matriz
     * de asientos y definir el ID del vuelo, el avión empleado en el vuelo,
     * el aeropuerto de origen y destino y sus respectivos terminales,
     * la fecha de salida y llegada del vuelo y el precio base del
     * vuelo.
     *
     * @param id              ID del vuelo.
     * @param avion           Avión empleado en el vuelo.
     * @param origen          Aeropuerto de origen.
     * @param terminalOrigen  Número del terminal del aeropuerto de origen.
     * @param salida          Fecha de salida del vuelo.
     * @param destino         Aeropuerto de destino.
     * @param terminalDestino Número del terminal del aeropuerto de destino.
     * @param llegada         Fecha de llegada del vuelo.
     * @param precio          Precio base del vuelo.
     */
    public Vuelo(String id, Avion avion, Aeropuerto origen, int terminalOrigen, Fecha salida, Aeropuerto destino, int terminalDestino, Fecha llegada, double precio) {
        this.id = id;
        this.avion = avion;
        this.origen = origen;
        this.terminalOrigen = terminalOrigen;
        this.salida = salida;
        this.destino = destino;
        this.terminalDestino = terminalDestino;
        this.llegada = llegada;
        this.precio = precio;
        listaBilletes = new ListaBilletes(avion.getColumnas() * avion.getFilas());
        asientos = new boolean[avion.getFilas()][avion.getColumnas()];
        for (int i = 0; i < asientos.length; i++) {
            for (int j = 0; j < asientos[i].length; j++) {
                asientos[i][j] = false;
            }
        }
    }

    /**
     * Getter del atributo id.
     *
     * @return ID del vuelo.
     */
    public String getId() {
        return id;
    }

    /**
     * Getter del atributo avion.
     *
     * @return Avión empleado en el vuelo.
     */
    public Avion getAvion() {
        return avion;
    }

    /**
     * Getter del atributo origen.
     *
     * @return Aeropuerto de origen.
     */
    public Aeropuerto getOrigen() {
        return origen;
    }

    /**
     * Getter del atributo terminalOrigen.
     *
     * @return El número de terminales del aeropuerto de origen.
     */
    public int getTerminalOrigen() {
        return terminalOrigen;
    }

    /**
     * Getter del atributo salida.
     *
     * @return Fecha de salida del vuelo.
     */
    public Fecha getSalida() {
        return salida;
    }

    /**
     * Getter del atributo destino.
     *
     * @return Aeropuerto de destino.
     */
    public Aeropuerto getDestino() {
        return destino;
    }

    /**
     * Getter del atributo terminalDestino.
     *
     * @return Número de terminales del aeropuerto de destino.
     */
    public int getTerminalDestino() {
        return terminalDestino;
    }

    /**
     * Getter del atributo llegada.
     *
     * @return Fecha de destino del vuelo.
     */
    public Fecha getLlegada() {
        return llegada;
    }

    /**
     * Getter del atributo precio.
     *
     * @return Precio base del vuelo.
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * Método que calcula el precio de clase preferente.
     *
     * @return Precio de clase preferente del vuelo.
     */
    public double getPrecioPreferente() {
        return precio * 1.25;
    }

    /**
     * Método que calcula el precio de primera clase.
     *
     * @return Precio de primera clase del vuelo.
     */
    public double getPrecioPrimera() {
        return precio * 1.5;
    }

    /**
     * Método que devuelve el número de asientos libres del avión.
     *
     * @return El número de asientos libres.
     */
    public int numAsientosLibres() {
        return (avion.getFilas() * avion.getColumnas()) - listaBilletes.getOcupacion();
    }

    /**
     * Método que comprueba si el vuelo está lleno o no.
     *
     * @return Devuelve true si el vuelo está lleno y false
     * si no lo está.
     */
    public boolean vueloLleno() {
        return numAsientosLibres() == 0;
    }

    /**
     * Método que comprueba si un asiento, dado su fila y su columna,
     * se encuentra ocupado.
     *
     * @param fila    Fila del asiento.
     * @param columna Columna del asiento.
     * @return Devuelve true si el asiento se encuentra ocupado y
     * false si no lo está.
     */
    public boolean asientoOcupado(int fila, int columna) {
        return asientos[fila - 1][columna - 1];
    }

    /**
     * Método que se encarga de buscar un billete en
     * la listaBilletes dado su localizador como parámetro.
     *
     * @param localizador Localizador del billete a buscar.
     * @return Devuelve el objeto de clase Billete que
     * se corresponda con el localizador dado como parámetro.
     * Si no lo encuentra devuelve null.
     */
    public Billete buscarBillete(String localizador) {
        return listaBilletes.buscarBillete(localizador);
    }

    /**
     * Método que se encarga de buscar un billete en
     * la listaBilletes dadas su fila y columna
     * como parámetro.
     *
     * @param fila    Fila del billete a buscar.
     * @param columna Columna del billete a buscar.
     * @return Devuelve el objeto de clase Billete que
     * se corresponda con su ID de vuelo, fila y columna
     * dadas como parámetro.
     * Si se encuentra libre o si excede el límite de fila
     * y columna devuelve null.
     */
    public Billete buscarBillete(int fila, int columna) {
        return listaBilletes.buscarBillete(getId(), fila, columna);
    }

    /**
     * Método que se encarga de ocupar un asiento si se encuentra
     * desocupado y de insertar el billete en la lista de billetes
     * del vuelo.
     *
     * @param billete Objeto de tipo Billete del que se trata de ocupar
     *                su asiento.
     * @return Devuelve true si se ha podido ocupar correctamente
     * y false si este ya se encuentra ocupado.
     */
    public boolean ocuparAsiento(Billete billete) {
        boolean ocupado = false;
        if (!asientos[billete.getFila() - 1][billete.getColumna() - 1]) {
            asientos[billete.getFila() - 1][billete.getColumna() - 1] = true;
            listaBilletes.insertarBillete(billete);
            ocupado = true;
        }
        return ocupado;
    }

    /**
     * Método que se encarga de ocupar un asiento si se encuentra
     * ocupado y de eliminar el billete en la lista de billetes
     * del vuelo.
     *
     * @param localizador Localizador del billete.
     * @return Devuelve true si se ha podido desocupar correctamente
     * y false si este ya se encuentra desocupado.
     */
    public boolean desocuparAsiento(String localizador) {
        boolean desocupado = false;
        Billete billete = buscarBillete(localizador);
        if (asientos[billete.getFila() - 1][billete.getColumna() - 1]) {
            asientos[billete.getFila() - 1][billete.getColumna() - 1] = false;
            listaBilletes.eliminarBillete(localizador);
            desocupado = true;
        }
        return desocupado;
    }

    /**
     * Método que añade los billetes al final de un fichero CSV,
     * sin sobreescribirlo.
     *
     * @param fichero Nombre del fichero CSV.
     * @return Devuelve true si se han añadido correctamente a
     * las listas y false si ha ocurrido algún problema.
     */
    public boolean aniadirBilletesCsv(String fichero) {
        return listaBilletes.aniadirBilletesCsv(fichero);
    }

    /**
     * Método que retorna una cadena de texto con la información
     * completa relativa al vuelo.
     *
     * @return Cadena de texto con la información completa relativa al vuelo.
     */
    public String toString() {
        return String.format("Vuelo %s de %s T%d (%s) \na %s T%d (%s) en %s por %,.2f€, \nasientos libres: %d",
                id, origen.toStringSimple(), getTerminalOrigen(), salida.toString(), destino.toStringSimple(),
                getTerminalDestino(), llegada.toString(), avion.toStringSimple(), precio, numAsientosLibres());
    }

    /**
     * Método que retorna una cadena de texto con la información
     * simplificada relativa al vuelo.
     *
     * @return Cadena de texto con la información simplificada relativa al vuelo.
     */
    public String toStringSimple() {
        return String.format("Vuelo %s de %s T%d (%s) a %s T%d \n(%s)", id,
                origen.getCodigo(), getTerminalOrigen(), salida.toString(),
                destino.getCodigo(), getTerminalDestino(), llegada.toString());
    }

    /**
     * Método que comprueba si los códigos IATA de origen y destino de los
     * aeropuertos de origen y destino respectivos y la fecha de salida
     * del vuelo son iguales a los dados como parámetro.
     *
     * @param codigoOrigen  Código IATA del aeropuerto de origen.
     * @param codigoDestino Código IATA del aeropuerto de destino.
     * @param fecha         Fecha de salida del vuelo.
     * @return Devuelve true si los códigos de origen y destino y la fecha
     * son iguales a los dados como parámetro y false si no lo son.
     */
    public boolean coincide(String codigoOrigen, String codigoDestino, Fecha fecha) {
        return getOrigen().getCodigo().equals(codigoOrigen) &&
                getDestino().getCodigo().equals(codigoDestino) && getSalida().coincide(fecha);
    }

    /**
     * Método que se encarga de mostrar la matriz de
     * asientos del vuelo.
     */
    public void imprimirMatrizAsientos() {
        String ocupado;
        System.out.print("   ");
        for (int i = 'A'; i < 'A' + getAvion().getColumnas(); i++) {
            System.out.print((char) i + "  ");
        }
        for (int i = 1; i <= getAvion().getFilas(); i++) {
            System.out.printf("\n%2d", i);
            for (int j = 1; j <= getAvion().getColumnas(); j++) {
                if (asientoOcupado(i, j)) ocupado = "X";
                else ocupado = " ";
                if (i == 1) {
                    System.out.printf("(%s)", ocupado);
                } else if (i <= 5) {
                    System.out.printf("{%s}", ocupado);
                } else {
                    System.out.printf("[%s]", ocupado);
                }
            }
        }
        System.out.println();
    }

    /**
     * Método que se encarga de escribir en un fichero indicado
     * una lista con los pasajeros del vuelo.
     *
     * @param fichero Nombre del fichero CSV.
     * @return Devuelve true si se ha podido escribir correctamente,
     * y false si ha ocurrido algún error.
     */
    public boolean generarListaPasajeros(String fichero) {
        boolean escrito = false;
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(fichero));
            pw.println("-----------------------------------------------------");
            pw.println("------- Lista de pasajeros en el vuelo " + id + " -------");
            pw.println("-----------------------------------------------------");
            pw.printf("%-9s%-12s%s\n", "Asiento", "Tipo", "Pasajero");
            for (int i = 1; i <= asientos.length; i++) {
                for (int j = 0; j < asientos[i - 1].length; j++) {
                    String tipo = " ", infoPasajero = " ";
                    if (asientoOcupado(i, j + 1)) {
                        tipo = buscarBillete(i, j + 1).getTipo().toString();
                        infoPasajero = buscarBillete(i, j + 1).getPasajero().toString();
                    }
                    pw.printf("%d%-8s%-12s%s\n", i, (char) ('A' + j), tipo, infoPasajero);
                }
            }
            escrito = true;
        } catch (IOException error) {
            System.out.println("Error de escritura en fichero Factura");
        } finally {
            if (pw != null) pw.close();
        }
        return escrito;
    }

    /**
     * Método estático que genera un ID de vuelo aleatorio.
     *
     * @param rand Objeto de tipo Random empleado para la parte aleatoria.
     * @return Cadena de 6 carácteres, de los cuales los 2 primeros son PM y
     * los 4 siguientes serán números aleatorios.
     */
    public static String generarID(Random rand) {
        return "PM" + rand.nextInt(0, 10) + rand.nextInt(0, 10) + rand.nextInt(0, 10) + rand.nextInt(0, 10);
    }

    /**
     * Método estático que crea y devuelve un objeto Vuelo con los datos
     * que el usuario selecciona de los aeropuertos y aviones, y solicita
     * la información de manera repetida hasta que sea válida.
     *
     * @param teclado     Se encarga de recoger por teclado los datos sobre los
     *                    aeropuertos y aviones.
     * @param rand        Objeto de tipo Random.
     * @param aeropuertos Objeto ListaAeropuertos que proporciona información sobre los aeropuertos.
     * @param aviones     Objeto ListaAviones que proporciona información sobre los aviones.
     * @param vuelos      Objeto ListaVuelos que proporciona información sobre los vuelos.
     * @return Objeto Vuelo con los datos que el usuario selecciona.
     **/
    public static Vuelo altaVuelo(Scanner teclado, Random rand, ListaAeropuertos aeropuertos, ListaAviones aviones, ListaVuelos vuelos) {
        Aeropuerto aeropuertoOrigen, aeropuertoDestino;
        Fecha fechaSalida, fechaLlegada;
        int termOrigen, termDestino;
        double precioPasaje;
        String genID;
        Avion avion1;
        aeropuertoOrigen = aeropuertos.seleccionarAeropuerto(teclado, "Ingrese código de Aeropuerto Origen:");
        termOrigen = Utilidades.leerNumero(teclado, "Ingrese Terminal Origen (1 - "
                + aeropuertoOrigen.getTerminales() + "):", 1, aeropuertoOrigen.getTerminales());
        aeropuertoDestino = aeropuertos.seleccionarAeropuerto(teclado, "Ingrese código de Aeropuerto Destino:");
        termDestino = Utilidades.leerNumero(teclado, "Ingrese Terminal Destino (1 - "
                + aeropuertoDestino.getTerminales() + "):", 1, aeropuertoDestino.getTerminales());
        avion1 = aviones.seleccionarAvion(teclado, "Ingrese matrícula de Avión:", aeropuertoOrigen.distancia(aeropuertoDestino));
        do {
            fechaSalida = Utilidades.leerFechaHora(teclado, "Fecha de Salida:");
            fechaLlegada = Utilidades.leerFechaHora(teclado, "Fecha de Llegada:");
            if (fechaLlegada.anterior(fechaSalida) || fechaSalida.coincide(fechaLlegada)) {
                System.out.println("Llegada debe ser posterior a salida.");
                fechaSalida = null;
                fechaLlegada = null;
            }
        } while (fechaSalida == null);
        precioPasaje = Utilidades.leerNumero(teclado, "Ingrese precio de pasaje:", 0.0, Double.MAX_VALUE);
        do {
            genID = generarID(rand);
        } while (vuelos.buscarVuelo(genID) != null);
        return new Vuelo(genID, avion1, aeropuertoOrigen, termOrigen, fechaSalida, aeropuertoDestino, termDestino, fechaLlegada, precioPasaje);
    }
}