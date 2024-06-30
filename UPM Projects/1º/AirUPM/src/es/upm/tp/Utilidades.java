package es.upm.tp;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Utilidades es una clase que encapsula métodos
 * para la obtención de valores introducidos por teclado.
 *
 * @author Adrián Yepes Mora
 * @author Pedro Salvo Saa
 * @version 1.0
 */
public class Utilidades {

    /**
     * Método que pide por teclado un número de tipo entero hasta que se introduzca uno correcto.
     *
     * @param teclado Se encarga de solicitar por teclado el número.
     * @param mensaje Mensaje que se muestra por pantalla en la ejecución de este método.
     * @param minimo  Valor mínimo que puede tomar el entero.
     * @param maximo  Valor máximo que puede tomar el entero.
     * @return Devuelve el número de tipo entero.
     */
    public static int leerNumero(Scanner teclado, String mensaje, int minimo, int maximo) {
        int res = -999;
        do {
            try {
                System.out.print(mensaje);
                res = teclado.nextInt();
                teclado.nextLine();
            } catch (InputMismatchException error) {
                teclado.nextLine();
            }
        } while (res > maximo || res < minimo);
        return res;
    }

    /**
     * Método que pide por teclado un número de tipo long hasta que se introduzca uno correcto.
     *
     * @param teclado Se encarga de solicitar por teclado el número.
     * @param mensaje Mensaje que se muestra por pantalla en la ejecución de este método.
     * @param minimo  Valor mínimo que puede tomar el número de tipo long.
     * @param maximo  Valor máximo que puede tomar el número de tipo long.
     * @return Devuelve el número de tipo long.
     */
    public static long leerNumero(Scanner teclado, String mensaje, long minimo, long maximo) {
        long res = -999;
        do {
            try {
                System.out.print(mensaje);
                res = teclado.nextLong();
                teclado.nextLine();
            } catch (InputMismatchException ex) {
                teclado.nextLine();
            }
        } while (res > maximo || res < minimo);
        return res;
    }

    /**
     * Método que pide por teclado un número de tipo double hasta que se introduzca uno correcto.
     *
     * @param teclado Se encarga de solicitar por teclado el número.
     * @param mensaje Mensaje que se muestra por pantalla en la ejecución de este método.
     * @param minimo  Valor mínimo que puede tomar el número de tipo double.
     * @param maximo  Valor máximo que puede tomar el número de tipo double.
     * @return Devuelve el número de tipo double.
     */
    public static double leerNumero(Scanner teclado, String mensaje, double minimo, double maximo) {
        double res = -999;
        do {
            try {
                System.out.print(mensaje);
                res = teclado.nextDouble();
                teclado.nextLine();
            } catch (InputMismatchException ex) {
                teclado.nextLine();
            }
        } while (res > maximo || res < minimo);
        return res;
    }

    /**
     * Método que pide por teclado un carácter de tipo char hasta que se introduzca uno correcto.
     *
     * @param teclado Se encarga de solicitar por teclado el carácter.
     * @param mensaje Mensaje que se muestra por pantalla en la ejecución de este método.
     * @param minimo  Valor mínimo que puede tomar el carácter.
     * @param maximo  Valor máximo que puede tomar el carácter.
     * @return Devuelve el carácter introducido.
     */
    public static char leerLetra(Scanner teclado, String mensaje, char minimo, char maximo) {
        char res = 0;
        do {
            try {
                System.out.print(mensaje);
                res = teclado.next().charAt(0);
                teclado.nextLine();
            } catch (InputMismatchException ex) {
                teclado.nextLine();
            }
        } while (res > maximo || res < minimo);
        return res;
    }

    /**
     * Método que pide una fecha por teclado y que, si no es válida, la pide hasta que sea válida.
     *
     * @param teclado Se encarga de solicitar por teclado la fecha.
     * @param mensaje Mensaje que se muestra por pantalla en la ejecución de este método.
     * @return Devuelve un objeto Fecha con los atributos dia, mes y año.
     */
    public static Fecha leerFecha(Scanner teclado, String mensaje) {
        int dia, mes, ano;
        System.out.println(mensaje);
        do {
            dia = leerNumero(teclado, "Ingrese día:", 1, 31);
            mes = leerNumero(teclado, "Ingrese mes:", 1, 12);
            ano = leerNumero(teclado, "Ingrese año:", 2000, 3000);
            if (!Fecha.comprobarFecha(dia, mes, ano))
                System.out.println("Fecha introducida incorrecta.");
        } while (!Fecha.comprobarFecha(dia, mes, ano));
        return new Fecha(dia, mes, ano);
    }

    /**
     * Método que pide una fecha y una hora por teclado y que, si no son válidas, las pide hasta que sean válidas.
     *
     * @param teclado Se encarga de solicitar por teclado la fecha y la hora.
     * @param mensaje Mensaje que se muestra por pantalla en la ejecución del método.
     * @return Devuelve un objeto fecha con los atributos dia, mes, año, hora, minuto y segundo.
     */
    public static Fecha leerFechaHora(Scanner teclado, String mensaje) {
        int dia, mes, ano, hora, minuto, segundo;
        System.out.println(mensaje);
        do {
            dia = leerNumero(teclado, "Ingrese día:", 1, 31);
            mes = leerNumero(teclado, "Ingrese mes:", 1, 12);
            ano = leerNumero(teclado, "Ingrese año:", 2000, 3000);
            hora = leerNumero(teclado, "Ingrese hora:", 0, 23);
            minuto = leerNumero(teclado, "Ingrese minuto:", 0, 60);
            segundo = leerNumero(teclado, "Ingrese segundo:", 0, 60);
            if (!Fecha.comprobarFecha(dia, mes, ano) || !Fecha.comprobarHora(hora, minuto, segundo))
                System.out.println("Fecha u hora introducida incorrecta.");
        } while (!Fecha.comprobarFecha(dia, mes, ano) || !Fecha.comprobarHora(hora, minuto, segundo));
        return new Fecha(dia, mes, ano, hora, minuto, segundo);
    }
}
