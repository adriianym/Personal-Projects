package edu.upm.etsisi.poo.iwsim21.exceptions;

public class NoCapacityException extends NoPermissionException {

    /**
     * Excepción emitida en caso de que el aforo de un plan/actividad se encuentre completo.
     */
    public NoCapacityException() {
        super("Aforo completo.");
    }

    public NoCapacityException(String message) {
        super(message);
    }
}