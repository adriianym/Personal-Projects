package es.upm.tp;

import java.util.Scanner;

/**
 * Pasajero es una clase que encapsula las variables de nombre,
 * apellidos, numeroDNI, letraDNI y email del pasajero e inicializa
 * listaBilletes definiendo la cantidad máxima de billetes que cada
 * pasajero puede comprar como máximo, además de una serie de métodos
 * que implementan distintas funcionalidades al pasajero.
 *
 * @author Adrián Yepes Mora
 * @author Pedro Salvo Saa
 * @version 1.0
 */
public class Pasajero {
    /**
     * El nombre del pasajero.
     */
    private String nombre;
    /**
     * Los apellidos del pasajero.
     */
    private String apellidos;
    /**
     * El número del DNI.
     */
    private long numeroDNI;
    /**
     * La letra del DNI.
     */
    private char letraDNI;
    /**
     * El email del pasajero.
     */
    private String email;
    /**
     * Lista de billetes comprados por pasajero.
     */
    private ListaBilletes listaBilletes;

    /**
     * Constructor de la clase Pasajero usado para definir
     * el nombre y los apellidos del pasajero, así como su
     * número y letra del DNI y su email e inicializa la lista
     * de billetes comprados por dicho pasajero.
     *
     * @param nombre      El nombre del pasajero.
     * @param apellidos   Los apellidos del pasajero.
     * @param numeroDNI   El número del DNI.
     * @param letraDNI    La letra del DNI.
     * @param email       El email del pasajero.
     * @param maxBilletes La cantidad máxima de billetes por pasajero.
     */
    public Pasajero(String nombre, String apellidos, long numeroDNI, char letraDNI, String email, int maxBilletes) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.numeroDNI = numeroDNI;
        this.letraDNI = letraDNI;
        this.email = email;
        listaBilletes = new ListaBilletes(maxBilletes);
    }

    /**
     * Getter del atributo nombre.
     *
     * @return Nombre del pasajero.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Getter del atributo apellidos.
     *
     * @return Apellidos del pasajero.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Getter del atributo numeroDNI.
     *
     * @return Número del DNI.
     */
    public long getNumeroDNI() {
        return numeroDNI;
    }

    /**
     * Getter del atributo letraDNI.
     *
     * @return Letra del DNI.
     */
    public char getLetraDNI() {
        return letraDNI;
    }

    /**
     * Método que devuelve el DNI con su número y su letra.
     *
     * @return DNI con número y letra.
     */
    public String getDNI() {
        return String.format("%08d%s", numeroDNI, letraDNI);
    }

    /**
     * Getter del atributo email.
     *
     * @return Email del pasajero.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Método que retorna una cadena de texto con la información
     * completa relativa al pasajero.
     *
     * @return Cadena de texto con la información completa relativa al pasajero.
     */
    public String toString() {
        return String.format("%s %s, %08d%s, %s", nombre, apellidos, numeroDNI, letraDNI, email);
    }

    /**
     * Método que devuelve el número de billetes que ha
     * comprado el pasajero.
     *
     * @return Billetes comprados por pasajero.
     */
    public int numBilletesComprado() {
        return listaBilletes.getOcupacion();
    }

    /**
     * Método que comprueba si el pasajero ha alcanzado
     * la cantidad máxima de billetes que puede comprar.
     *
     * @return Devuelve true si el pasajero no puede comprar
     * más billetes y false si sí que puede.
     */
    public boolean maxBilletesAlcanzado() {
        return listaBilletes.estaLlena();
    }

    /**
     * Método que devuelve un elemento de listaBilletes
     * pasando su posición en el array como parámetro.
     *
     * @param i Posición en la lista del billete que se quiere obtener.
     * @return Un objeto de tipo Billete.
     */
    public Billete getBillete(int i) {
        return listaBilletes.getBillete(i);
    }

    /**
     * Método que inserta un billete en la lista de billetes.
     *
     * @param billete Es el billete que se quiere insertar.
     * @return Devuelve true si se ha insertado el billete
     * correctamente y false si no.
     */
    public boolean aniadirBillete(Billete billete) {
        boolean insertado = false;
        if (!maxBilletesAlcanzado()) {
            billete.getVuelo().ocuparAsiento(billete);
            listaBilletes.insertarBillete(billete);
            insertado = true;
        }
        return insertado;
    }

    /**
     * Método que se encarga de buscar un billete en
     * la lista de billetes dado su localizador como parámetro.
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
     * Método que elimina el billete de la lista de billetes
     * del pasajero dado su localizador como parámetro.
     *
     * @param localizador Localizador del billete a eliminar.
     * @return Devuelve true si se ha eliminado correctamente y
     * false si no se ha podido eliminar.
     */
    public boolean cancelarBillete(String localizador) {
        return buscarBillete(localizador).getVuelo().desocuparAsiento(localizador)
                && listaBilletes.eliminarBillete(localizador);
    }

    /**
     * Método que encapsula la funcionalidad listarBilletes
     * de ListaBilletes.
     */
    public void listarBilletes() {
        listaBilletes.listarBilletes();
    }

    /**
     * Método que encapsula la funcionalidad seleccionarBillete
     * de ListaBilletes.
     *
     * @param teclado Se encarga de recoger por teclado el localizador deseado.
     * @param mensaje Mensaje que se muestra cuando se solicita el localizador por teclado.
     * @return Devuelve el objeto Billete que se corresponda con el
     * localizador dado por teclado.
     */
    public Billete seleccionarBillete(Scanner teclado, String mensaje) {
        return listaBilletes.seleccionarBillete(teclado, mensaje);
    }

    /**
     * Método estático que crea un nuevo objeto Pasajero pidiendo por teclado los datos necesarios al usuario.
     * La función solicita repetidamente los parámetros hasta que sean todos correctos.
     *
     * @param teclado     Recoge por teclado la información relativa al pasajero.
     * @param pasajeros   Objeto de la clase ListaPasajeros.
     * @param maxBilletes Número máximo de billetes que puede tener un pasajero.
     * @return Objeto Pasajero con los datos que el usuario selecciona.
     */
    public static Pasajero altaPasajero(Scanner teclado, ListaPasajeros pasajeros, int maxBilletes) {
        String nombre, apellidos, email, dni;
        int digMal = 0;
        long numeroDNI;
        char letraDNI = ' ';
        do {
            System.out.print("Ingrese nombre:");
            nombre = teclado.nextLine().trim();
            for (int i = 0; i < nombre.length(); i++) {
                if ((nombre.charAt(i) >= 'a' && nombre.charAt(i) <= 'z') &&
                        (nombre.charAt(i) >= 'A' && nombre.charAt(i) <= 'Z')) digMal++;
            }
        } while (digMal > 0);
        digMal = 0;
        do {
            System.out.print("Ingrese apellidos:");
            apellidos = teclado.nextLine().trim();
            for (int i = 0; i < apellidos.length(); i++) {
                if ((apellidos.charAt(i) >= 'a' && apellidos.charAt(i) <= 'z') &&
                        (apellidos.charAt(i) >= 'A' && apellidos.charAt(i) <= 'Z')) digMal++;
            }
        } while (digMal > 0);
        do {
            numeroDNI = Utilidades.leerNumero(teclado, "Ingrese número de DNI:", 0, 99999999);
            letraDNI = Character.toUpperCase(Utilidades.leerLetra(teclado, "Ingrese letra de DNI:", 'A', 'z'));
            dni = String.format("%08d%s", numeroDNI, letraDNI);
            if (pasajeros.buscarPasajeroDNI(dni) != null) {
                System.out.println("DNI ya existe.");
                numeroDNI = 0;
                letraDNI = 'A';
            } else if (!correctoDNI(numeroDNI, letraDNI)) System.out.println("DNI incorrecto.");
        } while (!correctoDNI(numeroDNI, letraDNI));
        do {
            System.out.print("Ingrese email:");
            email = teclado.next().trim();
            teclado.nextLine();
            if (pasajeros.buscarPasajeroEmail(email) != null) {
                System.out.println("Email ya existe.");
                email = " ";
            } else if (!correctoEmail(email)) System.out.println("Email incorrecto.");
        } while (!correctoEmail(email));
        return new Pasajero(nombre, apellidos, numeroDNI, letraDNI, email, maxBilletes);
    }

    /**
     * Método estático que verifica si un DNI introducido
     * es real o falso basándose en los números y en su letra.
     *
     * @param numero Números del DNI.
     * @param letra  Letra del DNI.
     * @return True o false dependiendo de si es o no válido el DNI introducido.
     */
    public static boolean correctoDNI(long numero, char letra) {
        boolean res = false;
        char[] letras = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
        if (letra == letras[Integer.parseInt(String.valueOf(numero % 23))]) res = true;
        return res;
    }

    /**
     * Método estático que verifica si un correo es
     * válido o no basándose en la composición de este.
     *
     * @param email Email a analizar
     * @return True o false dependiendo de si es o no válido el email introducido.
     */
    public static boolean correctoEmail(String email) {
        boolean res = true;
        if (!email.equals("")) {
            String antesArroba;
            String[] separado;
            if (email.endsWith("@upm.es") || email.endsWith("@alumnos.upm.es")) {
                separado = email.split("@");
                antesArroba = separado[0].trim();
                if (!antesArroba.endsWith(".") && !antesArroba.startsWith(".")) {
                    for (int i = 0; i < antesArroba.length() && res; i++) {
                        char letra = antesArroba.charAt(i);
                        res = (letra >= 'a' && letra <= 'z') || (letra >= 'A' && letra <= 'Z') || letra == '.';
                    }
                } else res = false;
            } else res = false;
        }
        return res;
    }
}