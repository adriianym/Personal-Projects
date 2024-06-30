package edu.upm.etsisi.poo.iwsim21.exceptions;

public class NoLogException extends Exception {

    /**
     * Excepción emitida en caso de que un usuario necesite estar logueado.
     */
    public NoLogException() {
        super("Necesitas estar logueado.");
    }
}