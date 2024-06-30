package edu.upm.etsisi.poo.iwsim21.exceptions;

public class NonExistentPlanException extends NonExistentDataException {

    /**
     * Excepción emitida en caso de que un plan elegido no exista.
     */
    public NonExistentPlanException() {
        super("EL plan elegido no existe.");
    }
}