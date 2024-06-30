package es.upm.tp;

import java.io.*;
import java.util.Scanner;

/**
 * ListaVuelos es una clase que encapsula la lista de
 * vuelos y las variables enteras que definen la capacidad
 * máxima de dicha lista y su ocupación.
 *
 * @author Adrián Yepes Mora
 * @author Pedro Salvo Saa
 * @version 1.0
 */
public class ListaVuelos {
    /**
     * Array de la clase Vuelo.
     */
    private Vuelo[] listaVuelos;
    /**
     * Ocupación de la lista.
     */
    private int ocupacion;
    /**
     * Capacidad de la lista.
     */
    private int capacidad;

    /**
     * Constructor de la clase ListaVuelos que
     * define los atributos capacidad y ocupación y además
     * instancia listaVuelos fijando su capacidad máxima.
     *
     * @param capacidad Indica la capacidad máxima de la lista.
     */
    public ListaVuelos(int capacidad) {
        this.capacidad = capacidad;
        ocupacion = 0;
        listaVuelos = new Vuelo[capacidad];
    }

    /**
     * Getter del atributo ocupacion.
     *
     * @return La ocupación de la lista.
     */
    public int getOcupacion() {
        return ocupacion;
    }

    /**
     * Método que comprueba si la lista está llena.
     *
     * @return True si la lista está llena y false
     * si no lo está.
     */
    public boolean estaLlena() {
        return ocupacion == capacidad;
    }

    /**
     * Método que devuelve un elemento de listaVuelos
     * pasando su posición en el array como parámetro.
     *
     * @param i Posición en la lista del vuelo que se quiere obtener.
     * @return Un objeto de tipo Vuelo.
     */
    public Vuelo getVuelo(int i) {
        return listaVuelos[i];
    }

    /**
     * Método que inserta un vuelo en listaVuelos.
     *
     * @param vuelo Es el vuelo que se quiere insertar.
     * @return Devuelve true si se ha insertado el vuelo
     * correctamente y false si no.
     */
    public boolean insertarVuelo(Vuelo vuelo) {
        boolean insertado = false;
        if (!estaLlena()) {
            listaVuelos[ocupacion] = vuelo;
            ocupacion++;
            insertado = true;
        }
        return insertado;
    }

    /**
     * Método que se encarga de buscar un vuelo en
     * la lista de vuelos dado su ID como parámetro.
     *
     * @param id ID del vuelo a buscar.
     * @return Devuelve el objeto de clase Vuelo que
     * se corresponda con el ID dado como parámetro.
     * Si no lo encuentra devuelve null.
     */
    public Vuelo buscarVuelo(String id) {
        Vuelo vuelo = null;
        for (int i = 0; i < ocupacion; i++) {
            if (listaVuelos[i].getId().equals(id)) vuelo = listaVuelos[i];
        }
        return vuelo;
    }

    /**
     * Método que devuelve un objeto ListaVuelos con los vuelos que vayan de un
     * aeropuerto de origen a otro de destino en una determinada fecha dados
     * como parámetro.
     *
     * @param codigoOrigen  Código IATA del aeropuerto de origen.
     * @param codigoDestino Código IATA del aeropuerto de destino.
     * @param fecha         Fecha de llegada del vuelo.
     * @return Un objeto ListaVuelos con los vuelos que vayan de un aeropuerto a otro
     * en una fecha dados como parámetros. Y devuelve null si no hay.
     */
    public ListaVuelos buscarVuelos(String codigoOrigen, String codigoDestino, Fecha fecha) {
        ListaVuelos vuelos = new ListaVuelos(getOcupacion());
        for (int i = 0; i < ocupacion; i++) {
            if (listaVuelos[i].coincide(codigoOrigen, codigoDestino, fecha)) {
                vuelos.listaVuelos[vuelos.ocupacion] = getVuelo(i);
                vuelos.ocupacion++;
            }
        }
        if (vuelos.ocupacion == 0) vuelos = null;
        return vuelos;
    }

    /**
     * Método que se encarga de mostrar por pantalla todos los vuelos
     * de la lista de vuelos.
     */
    public void listarVuelos() {
        for (int i = 0; i < ocupacion; i++) {
            System.out.println(getVuelo(i));
        }
    }

    /**
     * Método que selecciona un vuelo existente a partir de su ID,
     * usando el mensaje pasado como parámetro para la solicitud.
     * La función solicita repetidamente hasta que se introduzca
     * un ID correcto o hasta que se escriba la cadena cancelar.
     *
     * @param teclado  Se encarga de recoger por teclado el código deseado.
     * @param mensaje  Mensaje que se muestra cuando se solicita el ID por teclado.
     * @param cancelar Cadena de texto que se debe de escribir para salir del método.
     * @return Devuelve el objeto Vuelo que se corresponda con el ID dado por teclado.
     * Si se introduce la cadena cancelar se devuelve null.
     */
    public Vuelo seleccionarVuelo(Scanner teclado, String mensaje, String cancelar) {
        Vuelo vuelo = null;
        String id;
        do {
            System.out.print(mensaje);
            id = teclado.next().toUpperCase().trim();
            teclado.nextLine();
            if (!id.equals(cancelar) && (vuelo = buscarVuelo(id)) == null) {
                System.out.println("ID de vuelo no encontrado.");
            }
        } while (!id.equals(cancelar) && (vuelo = buscarVuelo(id)) == null);
        if (id.equals(cancelar)) vuelo = null;
        return vuelo;
    }

    /**
     * Método que genera un fichero CSV con la lista de vuelos.
     *
     * @param fichero Nombre del fichero CSV.
     * @return Devuelve true si se ha generado correctamente el
     * fichero CSV y false si ha ocurrido algún problema.
     */
    public boolean escribirVuelosCsv(String fichero) {
        boolean escrito = false;
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(fichero);
            for (int i = 0; i < ocupacion; i++) {
                pw.println(String.format("%s;%s;%s;%s;%s;%s;%s;%s;",getVuelo(i).getId(), getVuelo(i).getAvion().getMatricula(),
                        getVuelo(i).getOrigen().getCodigo(), getVuelo(i).getTerminalOrigen(), getVuelo(i).getSalida(),
                        getVuelo(i).getDestino().getCodigo(), getVuelo(i).getTerminalDestino(),
                        getVuelo(i).getLlegada()) + getVuelo(i).getPrecio());
            }
            escrito = true;
        } catch (IOException error) {
            System.out.println("Error de escritura en fichero Vuelos.");
        } finally {
            if (pw != null) pw.close();
        }
        return escrito;
    }

    /**
     * Método estático que genera una lista de vuelos a partir de un fichero CSV
     * y fijando su capacidad máxima de la dada como parámetro.
     *
     * @param fichero     Nombre del fichero CSV.
     * @param capacidad   Capacidad máxima de la lista.
     * @param aeropuertos Objeto ListaAeropuerto.
     * @param aviones     Objeto ListaAviones.
     * @return Un objeto de tipo ListaVuelos y null si ha ocurrido
     * algún problema
     */
    public static ListaVuelos leerVuelosCsv(String fichero, int capacidad, ListaAeropuertos
            aeropuertos, ListaAviones aviones) {
        ListaVuelos listaVuelos1 = new ListaVuelos(capacidad);
        BufferedReader br = null;
        String[] partes;
        String linea;
        try {
            br = new BufferedReader(new FileReader(fichero));
            for (int i = 0; i < capacidad && (linea = br.readLine()) != null; i++) {
                partes = linea.split(";");
                listaVuelos1.listaVuelos[i] = new Vuelo(partes[0], aviones.buscarAvion(partes[1]), aeropuertos.buscarAeropuerto(partes[2]),
                        Integer.parseInt(partes[3]), Fecha.fromString(partes[4]), aeropuertos.buscarAeropuerto(partes[5]),
                        Integer.parseInt(partes[6]), Fecha.fromString(partes[7]), Double.parseDouble(partes[8]));
                listaVuelos1.ocupacion++;
            }
        } catch (FileNotFoundException error) {
            System.out.println("Fichero Vuelos no encontrado.");
        } catch (IOException error) {
            System.out.println("Error de lectura de fichero Vuelos.");
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException error) {
                System.out.println("Error de cierre de fichero Vuelos.");
            }
        }
        return listaVuelos1;
    }
}