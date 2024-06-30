package edu.upm.etsisi.poo.iwsim21.exceptions;

public class IncorrectInputException extends Exception {

    /**
     * Excepción emitida en caso de que el formato de los datos introducidos no es el correcto.
     */
    public IncorrectInputException() {
        super("Datos erróneos.");
    }

    public IncorrectInputException(String message) {
        super(message);
    }
}
