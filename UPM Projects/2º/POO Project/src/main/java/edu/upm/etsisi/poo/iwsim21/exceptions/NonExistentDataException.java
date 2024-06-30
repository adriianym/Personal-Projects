package edu.upm.etsisi.poo.iwsim21.exceptions;

public class NonExistentDataException extends Exception {

    /**
     * Excepción emitida en caso de que los datos introducidos no existan.
     */
    public NonExistentDataException() {
        super("Datos inexistentes.");
    }

    public NonExistentDataException(String message) {
        super(message);
    }
}