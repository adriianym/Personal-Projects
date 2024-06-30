package es.upm.tp;

import java.io.File;
import java.util.Random;
import java.util.Scanner;

/**
 * AirUPM es una clase que encapsula una serie
 * de métodos que implementan distintas funcionalidades
 * para el funcionamiento del resto de clases.
 *
 * @author Adrián Yepes Mora
 * @author Pedro Salvo Saa
 * @version 1.0
 */
public class AirUPM {
    /**
     * Capacidad máxima de aeropuertos.
     */
    private int maxAeropuertos;
    /**
     * Capacidad máxima de aviones.
     */
    private int maxAviones;
    /**
     * Capacidad máxima de vuelos.
     */
    private int maxVuelos;
    /**
     * Capacidad máxima de pasajeros.
     */
    private int maxPasajeros;
    /**
     * Capacidad máxima de billetes por pasajero.
     */
    private int maxBilletesPasajero;

    /**
     * Objeto ListaAeropuertos.
     */
    private ListaAeropuertos listaAeropuertos;

    /**
     * Objeto ListaAviones.
     */
    private ListaAviones listaAviones;
    /**
     * Objeto ListaVuelos.
     */
    private ListaVuelos listaVuelos;
    /**
     * Objeto ListaPasajeros.
     */
    private ListaPasajeros listaPasajeros;

    /**
     * Constructor de la clase AirUPM que defines los atributos
     * maxAeropuertos, maxAviones, maxVuelos, maxPasajeros y
     * maxBilletesPasajero.
     *
     * @param maxAeropuertos      Capacidad máxima de aeropuertos.
     * @param maxAviones          Capacidad máxima de aviones.
     * @param maxVuelos           Capacidad máxima de vuelos.
     * @param maxPasajeros        Capacidad máxima de pasajeros.
     * @param maxBilletesPasajero Capacidad máxima de billetes por pasajero.
     */
    public AirUPM(int maxAeropuertos, int maxAviones, int maxVuelos, int maxPasajeros, int maxBilletesPasajero) {
        this.maxAeropuertos = maxAeropuertos;
        this.maxAviones = maxAviones;
        this.maxVuelos = maxVuelos;
        this.maxPasajeros = maxPasajeros;
        this.maxBilletesPasajero = maxBilletesPasajero;
    }

    /**
     * Método que se encarga de leer los datos de los ficheros dados
     * como parámetros y los agrega a AirUPM.
     *
     * @param ficheroAeropuertos Nombre del fichero Aeropuertos.
     * @param ficheroAviones     Nombre del fichero Aviones.
     * @param ficheroVuelos      Nombre del fichero Vuelos.
     * @param ficheroPasajeros   Nombre del fichero Pasajeros.
     * @param ficheroBilletes    Nombre del fichero Billetes.
     */
    public void cargarDatos(String ficheroAeropuertos, String ficheroAviones, String ficheroVuelos, String ficheroPasajeros, String ficheroBilletes) {
        listaAeropuertos = ListaAeropuertos.leerAeropuertosCsv(ficheroAeropuertos, maxAeropuertos);
        listaAviones = ListaAviones.leerAvionesCsv(ficheroAviones, maxAviones);
        listaVuelos = ListaVuelos.leerVuelosCsv(ficheroVuelos, maxVuelos, listaAeropuertos, listaAviones);
        listaPasajeros = ListaPasajeros.leerPasajerosCsv(ficheroPasajeros, maxPasajeros, maxBilletesPasajero);
        ListaBilletes.leerBilletesCsv(ficheroBilletes, listaVuelos, listaPasajeros);

    }

    /**
     * Método que se encarga de almacenar los datos de AirUPM en
     * los ficheros CSV dados como parámetros.
     *
     * @param ficheroAeropuertos Nombre del fichero Aeropuertos.
     * @param ficheroAviones     Nombre del fichero Aviones.
     * @param ficheroVuelos      Nombre del fichero Vuelos.
     * @param ficheroPasajeros   Nombre del fichero Pasajeros.
     * @param ficheroBilletes    Nombre del fichero Billetes.
     * @return Devuelve true si los datos se han almacenado correctamente
     * y false si ha ocurrido algún problema.
     */
    public boolean guardarDatos(String ficheroAeropuertos, String ficheroAviones, String ficheroVuelos, String ficheroPasajeros, String ficheroBilletes) {
        boolean billetesGuardados = false;
        File archivo = new File(ficheroBilletes);
        if (archivo.exists()) archivo.delete();
        for (int i = 0; i < listaVuelos.getOcupacion(); i++) {
            if (listaVuelos.getVuelo(i).aniadirBilletesCsv(ficheroBilletes))
                billetesGuardados = true;
        }
        return listaAeropuertos.escribirAeropuertosCsv(ficheroAeropuertos) && listaAviones.escribirAvionesCsv(ficheroAviones)
                && listaVuelos.escribirVuelosCsv(ficheroVuelos) && listaPasajeros.escribirPasajerosCsv(ficheroPasajeros)
                && !billetesGuardados;
    }

    /**
     * Método que comprueba si se ha alcanzado la cantidad máxima de vuelos.
     *
     * @return Devuelve true si se ha alcanzado la cantidad máxima de
     * vuelos y false si no.
     */
    public boolean maxVuelosAlcanzado() {
        return listaVuelos.estaLlena();
    }

    /**
     * Método que inserta un vuelo en la lista de vuelos.
     *
     * @param vuelo Es el vuelo que se quiere insertar.
     * @return Devuelve true si se ha insertado el vuelo
     * correctamente y false si no.
     */
    public boolean insertarVuelo(Vuelo vuelo) {
        boolean insertado = false;
        if (!maxVuelosAlcanzado()) {
            listaVuelos.insertarVuelo(vuelo);
            insertado = true;
        }
        return insertado;
    }

    /**
     * Método que comprueba si se ha alcanzado la cantidad máxima de pasajeros.
     *
     * @return Devuelve true si se ha alcanzado la cantidad máxima de
     * pasajeros y false si no.
     */
    public boolean maxPasajerosAlcanzado() {
        return listaPasajeros.estaLlena();
    }

    /**
     * Método que inserta un pasajero en la lista de pasajeros.
     *
     * @param pasajero Es el pasajero que se quiere insertar.
     * @return Devuelve true si se ha insertado el pasajero
     * correctamente y false si no.
     */
    public boolean insertarPasajero(Pasajero pasajero) {
        boolean insertado = false;
        if (!maxPasajerosAlcanzado()) {
            listaPasajeros.insertarPasajero(pasajero);
            insertado = true;
        }
        return insertado;
    }

    /**
     * Método que se encarga de buscar una serie de vuelos entre dos aeropuertos,
     * cuyo código IATA es pasado como parámetro, en una determinada fecha, pasada como
     * parámetro.
     *
     * @param teclado Se encarga de solicitar por teclado los códigos
     *                IATA de los aeropuertos y la fecha correspondiente.
     * @return Devuelve un objeto ListaVuelos si encuentra los vuelos a
     * buscar y null si no los encuentra.
     */
    public ListaVuelos buscarVuelo(Scanner teclado) {
        Aeropuerto aeropuertoOrigen = listaAeropuertos.seleccionarAeropuerto(teclado, "Ingrese código de Aeropuerto Origen:");
        Aeropuerto aeropuertoDestino = listaAeropuertos.seleccionarAeropuerto(teclado, "Ingrese código de Aeropuerto Destino:");
        Fecha fechaVuelo = Utilidades.leerFecha(teclado, "Fecha de Salida:");
        return listaVuelos.buscarVuelos(aeropuertoOrigen.getCodigo(), aeropuertoDestino.getCodigo(), fechaVuelo);
    }

    /**
     * Método que se encarga de comprar un billete para un vuelo específico,
     * pidiendo por teclado los datos necesarios al usuario por teclado.
     * Si la lista de pasajeros está vacía, se creará un pasajero nuevo; si
     * está llena, seleccionará un pasajero, y en cualquier otro caso, deberá
     * preguntar al usuario si crear o seleccionar un pasajero.
     *
     * @param teclado Se encarga de pedir por teclado la información necesaria
     *                para la compra del billete.
     * @param rand    Objeto de tipo Random.
     * @param vuelo   Se trata del vuelo en el que se quiere comprar un billete.
     */
    public void comprarBillete(Scanner teclado, Random rand, Vuelo vuelo) {
        if (!vuelo.vueloLleno()) {
            Pasajero pasajero1 = null;
            char siNo;
            if (!listaPasajeros.estaLlena()) {
                do {
                    siNo = Utilidades.leerLetra(teclado, "¿Comprar billete para un nuevo pasajero (n), o para uno ya existente (e)?",
                            'a', 'z');
                    if (siNo != 'e' && siNo != 'n') System.out.println("El valor de entrada debe ser 'n' o 'e'");
                } while (siNo != 'e' && siNo != 'n');
                if (siNo == 'e')
                    pasajero1 = listaPasajeros.seleccionarPasajero(teclado, "Ingrese DNI del pasajero:");
                else if (maxPasajerosAlcanzado())
                    System.out.println("No se pueden dar de alta más pasajeros.");
                else {
                    pasajero1 = Pasajero.altaPasajero(teclado, listaPasajeros, maxBilletesPasajero);
                    insertarPasajero(pasajero1);
                }
            } else pasajero1 = listaPasajeros.seleccionarPasajero(teclado, "Ingrese DNI del pasajero:");
            if (pasajero1 != null) {
                if (!pasajero1.maxBilletesAlcanzado()) {
                    Billete billete1 = Billete.altaBillete(teclado, rand, vuelo, pasajero1);
                    billete1.getPasajero().aniadirBillete(billete1);
                    billete1.getVuelo().ocuparAsiento(billete1);
                    System.out.printf("Billete %s comprado con éxito.\n", billete1.getLocalizador());
                } else System.out.println("El Pasajero seleccionado no puede adquirir más billetes.");
            }
        } else System.out.printf("El vuelo %s está lleno, no se pueden comprar más billetes\n", vuelo.getId());
    }

    /**
     * Método estático que muestra un menú por pantalla y
     * recoge una opción por teclado.
     *
     * @param teclado Objeto Scanner que se encarga de recoger la
     *                opción por teclado.
     * @return La opción que ha sido elegida.
     */
    public static int menu(Scanner teclado) {
        System.out.println("1. Alta Vuelo\n2. Alta Pasajero\n3. Buscar Vuelo\n" +
                "4. Mostrar billetes de Pasajero\n5. Generar lista de Pasajeros\n0. Salir");
        return Utilidades.leerNumero(teclado, "Seleccione opción:", 0, 5);
    }

    /**
     * Método main que se encarga de cargar los datos de los ficheros CSV,
     * pasados como argumentos, en AirUPM, llama al menú de manera iterativa
     * hasta que se indique la opción Salir, y finalmente guarda los datos de
     * AirUPM en los mismos ficheros CSV.
     *
     * @param args Argumentos necesarios por el programa para funcionar correctamente.
     */
    public static void main(String[] args) {
        if (args.length == 10) {
            AirUPM airUPM = new AirUPM(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
                    Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]));
            airUPM.cargarDatos(args[5], args[6], args[7], args[8], args[9]);
            Scanner teclado = new Scanner(System.in);
            int eleccion = -1;
            Vuelo vuelo1;
            while (eleccion != 0) {
                eleccion = menu(teclado);
                switch (eleccion) {
                    case 1:
                        if (!airUPM.maxVuelosAlcanzado()) {
                            vuelo1 = Vuelo.altaVuelo(teclado, new Random(), airUPM.listaAeropuertos, airUPM.listaAviones, airUPM.listaVuelos);
                            airUPM.insertarVuelo(vuelo1);
                            System.out.println("Vuelo " + vuelo1.getId() + " creado con éxito.");
                        } else System.out.println("No se pueden dar de alta más vuelos.");
                        break;
                    case 2:
                        if (!airUPM.maxPasajerosAlcanzado()) {
                            Pasajero pasajero1 = Pasajero.altaPasajero(teclado, airUPM.listaPasajeros, airUPM.maxBilletesPasajero);
                            airUPM.insertarPasajero(pasajero1);
                            System.out.println("Pasajero con DNI " + pasajero1.getDNI() + " dado de alta con éxito.");
                        } else System.out.println("No se pueden dar de alta más pasajeros.");
                        break;
                    case 3:
                        ListaVuelos listaVuelos1 = airUPM.buscarVuelo(teclado);
                        if (listaVuelos1 == null) System.out.println("No se ha encontrado ningún vuelo.");
                        else {
                            listaVuelos1.listarVuelos();
                            if ((vuelo1 = listaVuelos1.seleccionarVuelo(teclado,
                                    "Ingrese ID de vuelo para comprar billete o escriba CANCELAR:", "CANCELAR")) != null)
                                airUPM.comprarBillete(teclado, new Random(), vuelo1);
                        }
                        break;
                    case 4:
                        Pasajero pasajero1 = airUPM.listaPasajeros.seleccionarPasajero(teclado, "Ingrese DNI del pasajero:");
                        if (pasajero1.numBilletesComprado() == 0)
                            System.out.println("El pasajero seleccionado no ha adquirido ningún billete.");
                        else {
                            pasajero1.listarBilletes();
                            Billete billete1 = pasajero1.seleccionarBillete(teclado, "Ingrese localizador del billete:");
                            char eleccionBillete;
                            do {
                                eleccionBillete = Character.toLowerCase(Utilidades.leerLetra(teclado, "¿Generar factura del billete (f)," +
                                        " cancelarlo (c) o volver al menú (m)?", 'A', 'z'));
                                if (eleccionBillete != 'f' && eleccionBillete != 'c' && eleccionBillete != 'm')
                                    System.out.println("El valor de entrada debe ser 'f', 'c' o 'm'");
                            } while (eleccionBillete != 'f' && eleccionBillete != 'c' && eleccionBillete != 'm');
                            switch (eleccionBillete) {
                                case 'f':
                                    System.out.print("Introduzca la ruta donde generar la factura:");
                                    String fichero = teclado.nextLine();
                                    if (billete1.generarFactura(fichero))
                                        System.out.printf("Factura de Billete %s generada en %s\n", billete1.getLocalizador(), fichero);
                                    break;
                                case 'c':
                                    if (billete1.cancelar())
                                        System.out.printf("Billete %s cancelado.\n", billete1.getLocalizador());
                                    break;
                            }
                        }
                        break;
                    case 5:
                        vuelo1 = airUPM.listaVuelos.seleccionarVuelo(teclado, "Ingrese ID del vuelo:", "");
                        if (vuelo1 != null) {
                            System.out.print("Introduzca la ruta donde generar la lista de pasajeros:");
                            String fichero = teclado.nextLine();
                            if (vuelo1.generarListaPasajeros(fichero))
                                System.out.printf("Lista de pasajeros del Vuelo %s generada en %s\n", vuelo1.getId(), fichero);
                        }
                        break;
                    case 0:
                        airUPM.guardarDatos(args[5], args[6], args[7], args[8], args[9]);
                }
            }
        } else System.out.println("Número de argumentos incorrecto.");
    }
}