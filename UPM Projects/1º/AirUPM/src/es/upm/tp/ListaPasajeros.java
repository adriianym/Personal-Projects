package es.upm.tp;

import java.io.*;
import java.util.Scanner;

/**
 * ListaPasajeros es una clase que encapsula la lista de
 * pasajeros y las variables enteras que definen la capacidad
 * máxima de dicha lista y su ocupación.
 *
 * @author Adrián Yepes Mora
 * @author Pedro Salvo Saa
 * @version 1.0
 */
public class ListaPasajeros {
    /**
     * Array de la clase Pasajero.
     */
    private Pasajero[] listaPasajeros;
    /**
     * Ocupación de la lista.
     */
    private int ocupacion;
    /**
     * Capacidad de la lista.
     */
    private int capacidad;

    /**
     * Constructor de la clase ListaPasajeros que
     * define los atributos capacidad y ocupación y además
     * instancia listaPasajeros fijando su capacidad máxima.
     *
     * @param capacidad Indica la capacidad máxima de la lista.
     */
    public ListaPasajeros(int capacidad) {
        this.capacidad = capacidad;
        ocupacion = 0;
        listaPasajeros = new Pasajero[capacidad];
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
        return getOcupacion() >= capacidad;
    }

    /**
     * Método que devuelve un elemento de la lista de pasajeros
     * pasando su posición en el array como parámetro.
     *
     * @param i Posición en la lista del pasajero que se quiere obtener.
     * @return Un objeto de tipo Pasajero.
     */
    public Pasajero getPasajero(int i) {
        return listaPasajeros[i];
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
        if (!estaLlena()) {
            listaPasajeros[ocupacion] = pasajero;
            ocupacion++;
            insertado = true;
        }
        return insertado;
    }

    /**
     * Método que se encarga de buscar un pasajero en
     * la lista de pasajeros dado su DNI como parámetro.
     *
     * @param dni DNI del pasajero a buscar.
     * @return Devuelve el objeto de clase Pasajero que
     * se corresponda con el DNI dado como parámetro.
     * Si no lo encuentra devuelve null.
     */
    public Pasajero buscarPasajeroDNI(String dni) {
        Pasajero pasajero = null;
        for (int i = 0; i < ocupacion; i++) {
            if (listaPasajeros[i].getDNI().equals(dni)) pasajero = listaPasajeros[i];
        }
        return pasajero;
    }

    /**
     * Método que se encarga de buscar un pasajero en
     * la lista de pasajeros dado su email como parámetro.
     *
     * @param email Email del pasajero a buscar.
     * @return Devuelve el objeto de clase Pasajero que
     * se corresponda con el email dado como parámetro.
     * Si no lo encuentra devuelve null.
     */
    public Pasajero buscarPasajeroEmail(String email) {
        Pasajero pasajero = null;
        for (int i = 0; i < ocupacion; i++) {
            if (listaPasajeros[i].getEmail().equals(email)) pasajero = listaPasajeros[i];
        }
        return pasajero;
    }

    /**
     * Método que selecciona un pasajero existente a partir de su DNI,
     * usando el mensaje pasado como parámetro para la solicitud.
     *
     * @param teclado Se encarga de recoger por teclado el DNI deseado.
     * @param mensaje Mensaje que se muestra cuando se solicita el DNI por teclado.
     * @return Devuelve el objeto Pasajero que se corresponda con el
     * DNI dado por teclado.
     */
    public Pasajero seleccionarPasajero(Scanner teclado, String mensaje) {
        Pasajero pasajero;
        do {
            System.out.print(mensaje);
            pasajero = buscarPasajeroDNI(teclado.next().toUpperCase());
            teclado.nextLine();
            if (pasajero == null) {
                System.out.println("DNI no encontrado.");
            }
        } while (pasajero == null);
        return pasajero;
    }

    /**
     * Método que genera un fichero CSV con la lista de pasajeros.
     *
     * @param fichero Nombre del fichero CSV.
     * @return Devuelve true si se ha generado correctamente el
     * fichero CSV y false si ha ocurrido algún problema.
     */
    public boolean escribirPasajerosCsv(String fichero) {
        boolean escrito = false;
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(fichero);
            for (int i = 0; i < ocupacion; i++) {
                pw.println(getPasajero(i).getNombre() + ";" + getPasajero(i).getApellidos() + ";"
                        + getPasajero(i).getNumeroDNI() + ";" + getPasajero(i).getLetraDNI() + ";"
                        + getPasajero(i).getEmail());
            }
            escrito = true;
        } catch (IOException error) {
            System.out.println("Error de escritura en fichero Pasajeros.");
        } finally {
            if (pw != null) pw.close();
        }
        return escrito;
    }

    /**
     * Método estático que genera una lista de pasajeros a partir de un fichero CSV,
     * fijando su capacidad máxima de la dada como parámetro y fijando la cantidad
     * máxima de billetes por pasajero.
     *
     * @param fichero             Nombre del fichero CSV.
     * @param capacidad           Capacidad máxima de la lista.
     * @param maxBilletesPasajero Cantidad máxima de billetes por pasajero.
     * @return Un objeto de tipo ListaPasajeros y null si ha ocurrido
     * algún problema
     */
    public static ListaPasajeros leerPasajerosCsv(String fichero, int capacidad, int maxBilletesPasajero) {
        ListaPasajeros listaPasajeros1 = null;
        BufferedReader br = null;
        Pasajero pasajero1;
        String[] partes;
        String linea;
        try {
            br = new BufferedReader(new FileReader(fichero));
            listaPasajeros1 = new ListaPasajeros(capacidad);
            while ((linea = br.readLine()) != null) {
                partes = linea.split(";");
                pasajero1 = new Pasajero(partes[0], partes[1], Long.parseLong(partes[2]), partes[3].charAt(0), partes[4], maxBilletesPasajero);
                listaPasajeros1.insertarPasajero(pasajero1);
            }
        } catch (FileNotFoundException error) {
            System.out.println("Fichero Pasajeros no encontrado.");
        } catch (IOException error) {
            System.out.println("Error de lectura de fichero Pasajeros.");
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException error) {
                System.out.println("Error de cierre de fichero Pasajeros.");
            }
        }
        return listaPasajeros1;
    }
}