package edu.upm.etsisi.poo.iwsim21.exceptions;

public class NoPermissionException extends Exception {

    /**
     * Excepción emitida en caso de que un usuario no tenga permisos.
     */
    public NoPermissionException() {
        super("No tienes permiso para realizar esta acción.");
    }

    public NoPermissionException(String message) {
        super(message);
    }
}