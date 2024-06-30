package edu.upm.etsisi.poo.iwsim21.exceptions;

public class OutOfRangeException extends IncorrectInputException {

    /**
     * Excepción emitida en caso de que un dato no se encuentre en un rango establecido.
     */
    public OutOfRangeException() {
        super("El dato introducido está fuera del rango permitido.");
    }

    public OutOfRangeException(String message) {
        super(message);
    }
}