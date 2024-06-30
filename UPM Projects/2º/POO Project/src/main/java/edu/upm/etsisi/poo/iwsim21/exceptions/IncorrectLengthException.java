package edu.upm.etsisi.poo.iwsim21.exceptions;

public class IncorrectLengthException extends OutOfRangeException {

    /**
     * Excepción emitida en caso de la longitud de una cedena no sea la correcta.
     */
    public IncorrectLengthException() {
        super("Longitud de la cadena incorrecta.");
    }

    public IncorrectLengthException(String message) {
        super(message);
    }
}