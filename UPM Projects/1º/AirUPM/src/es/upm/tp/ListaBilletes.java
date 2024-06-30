package es.upm.tp;

import java.io.*;
import java.util.Scanner;

/**
 * ListaBilletes es una clase que encapsula la lista de tipo
 * Billete y las variables enteras que definen la capacidad
 * máxima de dicha lista y su ocupación.
 *
 * @author Adrián Yepes Mora
 * @author Pedro Salvo Saa
 * @version 1.0
 */
public class ListaBilletes {
    /**
     * Array de la clase Billete.
     */
    private Billete[] listaBilletes;
    /**
     * Ocupación de la lista.
     */
    private int ocupacion;
    /**
     * Capacidad de la lista.
     */
    private int capacidad;

    /**
     * Constructor de la clase ListaBilletes que
     * define los atributos capacidad y ocupación y además
     * instancia listaBilletes fijando su capacidad máxima.
     *
     * @param capacidad Indica la capacidad máxima de la lista.
     */
    public ListaBilletes(int capacidad) {
        this.capacidad = capacidad;
        ocupacion = 0;
        listaBilletes = new Billete[capacidad];
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
        return ocupacion >= capacidad;
    }

    /**
     * Método que devuelve un elemento de listaBilletes
     * pasando su posición en el array como parámetro.
     *
     * @param i Posición en la lista del billete que se quiere obtener.
     * @return Un objeto de tipo Billete.
     */
    public Billete getBillete(int i) {
        return listaBilletes[i];
    }

    /**
     * Método que inserta un billete en listaBilletes.
     *
     * @param billete Es el billete que se quiere insertar.
     * @return Devuelve true si se ha insertado el billete
     * correctamente y false si no.
     */
    public boolean insertarBillete(Billete billete) {
        boolean insertado = false;
        if (!estaLlena()) {
            listaBilletes[ocupacion] = billete;
            ocupacion++;
            insertado = true;
        }
        return insertado;
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
        Billete billete = null;
        for (int i = 0; i < ocupacion; i++) {
            if (listaBilletes[i].getLocalizador().equals(localizador.toUpperCase())) billete = listaBilletes[i];
        }
        return billete;
    }

    /**
     * Método que se encarga de buscar un billete en
     * la listaBilletes dadas su ID de vuelo, fila y
     * columna como parámetro.
     *
     * @param idVuelo ID de vuelo del billete a buscar.
     * @param fila    Fila del billete a buscar.
     * @param columna Columna del billete a buscar.
     * @return Devuelve el objeto de clase Billete que
     * se corresponda con su ID de vuelo, fila y columna
     * dadas como parámetro.
     * Si no lo encuentra devuelve null.
     */
    public Billete buscarBillete(String idVuelo, int fila, int columna) {
        Billete billete = null;
        for (int i = 0; i < ocupacion; i++) {
            if (listaBilletes[i].getVuelo().getId().equals(idVuelo) && listaBilletes[i].getFila() == fila
                    && listaBilletes[i].getColumna() == columna) billete = listaBilletes[i];
        }
        return billete;
    }

    /**
     * Método que se encarga de eliminar un billete pasado
     * su localizador como parámetro.
     *
     * @param localizador Localizador del billete.
     * @return Devuelve true si se ha eliminado correctamente
     * y false si ha ocurrido algún problema.
     */
    public boolean eliminarBillete(String localizador) {
        boolean eliminado = false;
        int pos = 0;
        while (pos < ocupacion && listaBilletes[pos] != buscarBillete(localizador) && buscarBillete(localizador) != null)
            pos++;
        for (int i = pos + 1; i < ocupacion; i++) {
            listaBilletes[i - 1] = listaBilletes[i];
            eliminado = true;
        }
        ocupacion--;
        return eliminado;
    }

    /**
     * Método que se encarga de mostrar por pantalla todos los billetes
     * de ListaBilletes.
     */
    public void listarBilletes() {
        for (int i = 0; i < ocupacion; i++) {
            System.out.println(listaBilletes[i].toString());
        }
    }

    /**
     * Método que selecciona un billete existente a partir de su localizador,
     * usando el mensaje pasado como parámetro para la solicitud.
     *
     * @param teclado Se encarga de recoger por teclado el localizador deseado.
     * @param mensaje Mensaje que se muestra cuando se solicita el localizador por teclado.
     * @return Devuelve el objeto Billete que se corresponda con el
     * localizador dado por teclado.
     */
    public Billete seleccionarBillete(Scanner teclado, String mensaje) {
        Billete billete;
        do {
            System.out.print(mensaje);
            billete = buscarBillete(teclado.next().toUpperCase().trim());
            teclado.nextLine();
            if (billete == null) {
                System.out.println("Localizador no encontrado.");
            }
        } while (billete == null);
        return billete;
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
        boolean escrito = false;
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(fichero, true));
            for (int i = 0; i < ocupacion; i++) {
                Billete billete1 = getBillete(i);
                pw.println(String.format("%s;%s;%s;%s;%d;%d;", billete1.getLocalizador(),
                        billete1.getVuelo().getId(), billete1.getPasajero().getDNI(), billete1.getTipo().toString(),
                        billete1.getFila(), billete1.getColumna()) + billete1.getPrecio());
            }
            escrito = true;
        } catch (IOException error) {
            System.out.println("Error de escritura en fichero Billetes.");
        } finally {
            if (pw != null) pw.close();
        }
        return escrito;
    }

    /**
     * Método estático que se encarga de leer fichero CSV con los billetes
     * y los añade a las listas de sus respectivos pasajeros y vuelos.
     *
     * @param ficheroBilletes Nombre del fichero CSV.
     * @param vuelos          Objeto de tipo ListaVuelos.
     * @param pasajeros       Objeto de tipo ListaPasajeros.
     */
    public static void leerBilletesCsv(String ficheroBilletes, ListaVuelos vuelos, ListaPasajeros pasajeros) {
        BufferedReader entrada = null;
        String linea = "";
        try {
            entrada = new BufferedReader(new FileReader(ficheroBilletes));
            while ((linea = entrada.readLine()) != null) {
                String[] partes = linea.split(";");
                Vuelo vuelo1 = vuelos.buscarVuelo(partes[1]);
                Billete billete1 = new Billete(partes[0], vuelo1, pasajeros.buscarPasajeroDNI(partes[2]),
                        Billete.TIPO.valueOf(partes[3]), Integer.parseInt(partes[4]),
                        Integer.parseInt(partes[5]), Double.parseDouble(partes[6]));
                pasajeros.buscarPasajeroDNI(partes[2]).aniadirBillete(billete1);
                vuelo1.ocuparAsiento(billete1);
            }
        } catch (FileNotFoundException error) {
            System.out.println("Fichero Billetes no encontrado.");
        } catch (IOException error) {
            System.out.println("Error de lectura de fichero Billetes.");
        } finally {
            try {
                if (entrada != null) entrada.close();
            } catch (IOException error) {
                System.out.println("Error de cierre de fichero Billetes.");
            }
        }
    }
}