package es.upm.tp;

import java.io.*;
import java.util.Scanner;

/**
 * ListaAeropuertos es una clase que encapsula la lista de
 * aeropuertos y las variables enteras que definen la capacidad
 * máxima de dicha lista y su ocupación.
 *
 * @author Adrián Yepes Mora
 * @author Pedro Salvo Saa
 * @version 1.0
 */
public class ListaAeropuertos {

    /**
     * Array de la clase Aeropuerto.
     */
    private Aeropuerto[] listaAeropuertos;

    /**
     * Ocupación de la lista.
     */
    private int ocupacion;

    /**
     * Capacidad de la lista.
     */
    private int capacidad;

    /**
     * Constructor de la clase ListaAeropuertos que
     * define los atributos capacidad y ocupación y además
     * instancia listaAeropuertos fijando su capacidad máxima.
     *
     * @param capacidad Indica la capacidad máxima de la lista.
     */
    public ListaAeropuertos(int capacidad) {
        this.capacidad = capacidad;
        this.ocupacion = 0;
        listaAeropuertos = new Aeropuerto[this.capacidad];
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
        boolean llena = false;
        if (ocupacion == capacidad) llena = true;
        return llena;
    }

    /**
     * Método que devuelve un elemento de la lista de aeropuertos
     * pasada su posición en la lista como parámetro.
     *
     * @param i Posición en la lista del aeropuerto que se quiere obtener.
     * @return Un objeto de tipo Aeropuerto.
     */
    public Aeropuerto getAeropuerto(int i) {
        return listaAeropuertos[i];
    }

    /**
     * Método que inserta un aeropuerto en la lista de aeropuertos.
     *
     * @param aeropuerto Es el aeropuerto que se quiere insertar.
     * @return Devuelve true si se ha insertado el aeropuerto
     * correctamente y false si la lista está llena.
     */
    public boolean insertarAeropuerto(Aeropuerto aeropuerto) {
        boolean insertado = false;
        if (!estaLlena()) {
            listaAeropuertos[ocupacion] = aeropuerto;
            ocupacion++;
            insertado = true;
        }
        return insertado;
    }

    /**
     * Método que se encarga de buscar un aeropuerto en
     * la lista de aeropuertos dado su código IATA como
     * parámetro.
     *
     * @param codigo Código IATA del aeropuerto a buscar.
     * @return Devuelve el objeto de clase Aeropuerto que
     * se corresponda con el código IATA dado como parámetro.
     * Si no lo encuentra devuelve null.
     */
    public Aeropuerto buscarAeropuerto(String codigo) {
        Aeropuerto aeropuerto = null;
        for (int i = 0; i < ocupacion && aeropuerto == null; i++)
            if (listaAeropuertos[i].getCodigo().equals(codigo))
                aeropuerto = listaAeropuertos[i];
        return aeropuerto;
    }

    /**
     * Método que selecciona un aeropuerto existente a partir de su código,
     * usando el mensaje pasado como parámetro para la solicitud.
     *
     * @param teclado Se encarga de recoger por teclado el código deseado.
     * @param mensaje Mensaje que se muestra cuando se solicita el código por teclado.
     * @return Devuelve el objeto Aeropuerto que se corresponda con el
     * código IATA dado por teclado.
     */
    public Aeropuerto seleccionarAeropuerto(Scanner teclado, String mensaje) {
        Aeropuerto aeropuerto;
        do {
            System.out.print(mensaje);
            aeropuerto = buscarAeropuerto(teclado.next().toUpperCase());
            teclado.nextLine();
            if (aeropuerto == null) {
                System.out.println("Código de aeropuerto no encontrado.");
            }
        } while (aeropuerto == null);
        return aeropuerto;
    }

    /**
     * Método que genera un fichero CSV con la lista de aeropuertos.
     *
     * @param nombre Nombre del fichero CSV.
     * @return Devuelve true si se ha generado correctamente el
     * fichero CSV y false si ha ocurrido algún problema.
     */
    public boolean escribirAeropuertosCsv(String nombre) {
        boolean escrito = false;
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(nombre);
            for (int i = 0; i < ocupacion; i++) {
                pw.println(listaAeropuertos[i].getNombre() + ";" + listaAeropuertos[i].getCodigo() + ";"
                        + listaAeropuertos[i].getLatitud() + ";" + listaAeropuertos[i].getLongitud() + ";"
                        + listaAeropuertos[i].getTerminales());
            }
            escrito = true;
        } catch (IOException error) {
            System.out.println("Error de escritura en fichero Aeropuertos.");
        } finally {
            if (pw != null) pw.close();
        }
        return escrito;
    }

    /**
     * Método estático que genera una lista de aeropuertos a partir
     * del fichero CSV dado como argumento.
     *
     * @param fichero   Nombre del fichero CSV.
     * @param capacidad Capacidad máxima de la lista.
     * @return Un objeto de tipo ListaAeropuertos y null si ha ocurrido
     * algún problema.
     */
    public static ListaAeropuertos leerAeropuertosCsv(String fichero, int capacidad) {
        ListaAeropuertos listaAeropuertos1 = null;
        BufferedReader br = null;
        Aeropuerto aeropuerto1;
        String linea;
        String[] partes;
        try {
            br = new BufferedReader(new FileReader(fichero));
            listaAeropuertos1 = new ListaAeropuertos(capacidad);
            for (int i = 0; i < capacidad && (linea = br.readLine()) != null; i++) {
                partes = linea.split(";");
                aeropuerto1 = new Aeropuerto(partes[0], partes[1],
                        Double.parseDouble(partes[2]), Double.parseDouble(partes[3]), Integer.parseInt(partes[4]));
                listaAeropuertos1.insertarAeropuerto(aeropuerto1);
            }
        } catch (FileNotFoundException error) {
            System.out.println("Fichero Aeropuertos no encontrado.");
        } catch (IOException error) {
            System.out.println("Error de lectura de fichero Aeropuertos.");
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException error) {
                System.out.println("Error de cierre de fichero Aeropuertos.");
            }
        }
        return listaAeropuertos1;
    }
}
