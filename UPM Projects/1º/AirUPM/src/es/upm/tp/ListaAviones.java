package es.upm.tp;

import java.io.*;
import java.util.Scanner;

/**
 * ListaAviones es una clase que encapsula la lista de
 * aviones y las variables enteras que definen la capacidad
 * máxima de dicha lista y su ocupación.
 *
 * @author Adrián Yepes Mora
 * @author Pedro Salvo Saa
 * @version 1.0
 */
public class ListaAviones {

    /**
     * Array de la clase Avion.
     */
    private Avion[] aviones;

    /**
     * Capacidad máxima de la lista.
     */
    private int capacidad;

    /**
     * Ocupación de la lista.
     */
    private int ocupacion;

    /**
     * Constructor de la clase ListaAviones que
     * define los atributos capacidad y ocupación y además
     * instancia aviones fijando su capacidad máxima.
     *
     * @param capacidad Indica la capacidad máxima de la lista.
     */
    public ListaAviones(int capacidad) {
        this.capacidad = capacidad;
        ocupacion = 0;
        aviones = new Avion[capacidad];
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
        return (getOcupacion() >= capacidad);
    }

    /**
     * Método que devuelve un elemento de la lista de aviones
     * pasada su posición en la lista como parámetro.
     *
     * @param posicion Posición de la lista aviones que se quiere obtener.
     * @return Un objeto de tipo Avion.
     */
    public Avion getAvion(int posicion) {
        return aviones[posicion];
    }

    /**
     * Método que inserta un objeto Avion en la lista de aviones.
     *
     * @param avion Es el avión que se quiere insertar.
     * @return Devuelve true si se ha insertado el avión
     * correctamente y false si la lista está llena.
     */
    public boolean insertarAvion(Avion avion) {
        boolean insertado = true;
        if (!estaLlena()) {
            aviones[ocupacion] = avion;
            ocupacion++;
        } else insertado = false;
        return insertado;
    }

    /**
     * Método que se encarga de buscar un avión en la
     * lista de aviones dada su matrícula como parámetro.
     *
     * @param matricula Matrícula del avión a buscar.
     * @return Devuelve el objeto de clase Avion que
     * se corresponda con matrícula dada como parámetro.
     * Si no lo encuentra devuelve null.
     */
    public Avion buscarAvion(String matricula) {
        Avion avion = null;
        for (int i = 0; i < aviones.length && aviones[i] != null; i++) {
            if (aviones[i].getMatricula().equals(matricula)) avion = aviones[i];
        }
        return avion;
    }

    /**
     * Método que se encarga de seleccionar un avión existente
     * a partir de su matrícula, pasada como parámetro, y comprueba si dispone
     * de un alcance mayor o igual al pasado como parámetro, mostrando el mensaje
     * pasado como parámetro.
     *
     * @param teclado Se encarga de recoger por teclado la matrícula deseada.
     * @param mensaje Mensaje que se muestra cuando se solicita el código por teclado.
     * @param alcance Alcance que se compara con el del avión seleccionado.
     * @return Un objeto Avion que se corresponda con la matrícula dada
     * por teclado y cuyo alcance es mayor o igual al pasado como parámetro.
     */
    public Avion seleccionarAvion(Scanner teclado, String mensaje, double alcance) {
        Avion avion;
        do {
            System.out.print(mensaje);
            avion = buscarAvion(teclado.next().toUpperCase());
            teclado.nextLine();
            if (avion == null) {
                System.out.println("Matrícula de avión no encontrada.");
            } else if (avion.getAlcance() < alcance) {
                System.out.printf("Avión seleccionado con alcance insuficiente (menor que %.3f km).\n", alcance);
                avion = null;
            }
        } while (avion == null);
        return avion;
    }

    /**
     * Método que genera un fichero CSV con la lista de aviones.
     *
     * @param nombre Nombre del fichero CSV.
     * @return Devuelve true si se ha generado correctamente el
     * fichero CSV y false si ha ocurrido algún problema.
     */
    public boolean escribirAvionesCsv(String nombre) {
        PrintWriter salida = null;
        boolean escrito = true;
        try {
            salida = new PrintWriter(nombre);
            for (int i = 0; i < ocupacion; i++) {
                salida.println(String.format("%s;%s;%s;%s;%s;",getAvion(i).getMarca(),
                        getAvion(i).getModelo(), getAvion(i).getMatricula(),
                        getAvion(i).getFilas(), getAvion(i).getColumnas())
                        + getAvion(i).getAlcance());
            }
        } catch (IOException error) {
            System.out.println("Error de escritura en fichero Aviones.");
            escrito = false;
        } finally {
            if (salida != null) salida.close();
        }
        return escrito;
    }

    /**
     * Método estático que genera una lista de aviones a partir
     * del fichero CSV, cuyo nombre es pasado como parámetro, y
     * fija su capacidad máxima, también pasada como parámetro.
     *
     * @param fichero   Nombre del fichero CSV.
     * @param capacidad Capacidad máxima de la lista.
     * @return Un objeto de tipo ListaAviones y null si ha ocurrido
     * algún problema.
     */
    public static ListaAviones leerAvionesCsv(String fichero, int capacidad) {
        ListaAviones listaAviones1 = null;
        BufferedReader entrada = null;
        String[] partes;
        Avion avion1;
        String linea;
        try {
            entrada = new BufferedReader(new FileReader(fichero));
            listaAviones1 = new ListaAviones(capacidad);
            while ((linea = entrada.readLine()) != null) {
                partes = linea.split(";");
                avion1 = new Avion(partes[0], partes[1], partes[2],
                        Integer.parseInt(partes[4]), Integer.parseInt(partes[3]), Double.parseDouble(partes[5]));
                listaAviones1.insertarAvion(avion1);
            }
        } catch (FileNotFoundException error) {
            System.out.println("Fichero Aviones no encontrado.");
        } catch (IOException error) {
            System.out.println("Error de lectura de fichero Aviones.");
        } finally {
            try {
                if (entrada != null) entrada.close();
            } catch (IOException error) {
                System.out.println("Error de cierre de fichero Aviones.");
            }
        }
        return listaAviones1;
    }
}