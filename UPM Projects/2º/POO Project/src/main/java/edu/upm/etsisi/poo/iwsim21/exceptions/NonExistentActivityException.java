package edu.upm.etsisi.poo.iwsim21.exceptions;

public class NonExistentActivityException extends NonExistentDataException {

    /**
     * Excepción emitida en caso de que una actividad no exista.
     */
    public NonExistentActivityException() {
        super("La actividad elegida no existe.");
    }

    public NonExistentActivityException(String message) {
        super(message);
    }
}