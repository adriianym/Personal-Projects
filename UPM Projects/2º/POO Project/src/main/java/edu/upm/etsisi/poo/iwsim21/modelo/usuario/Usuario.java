package edu.upm.etsisi.poo.iwsim21.modelo.usuario;

public class Usuario {

    /**
     * String que designa el nombre del usuario.
     */
    private String nombre;
    /**
     * Entero que designa la edad del usuario.
     */
    private int edad;
    /**
     * Entero que designa el número de teléfono del usuario.
     */
    private int telefono;
    /**
     * String que designa la clave del usuario.
     */
    private String clave;

    /**
     * Constructor de Usuario.
     *
     * @param nombre   String que designa el nombre del usuario.
     * @param edad     Entero que designa la edad del usuario.
     * @param telefono Entero que designa el número de teléfono del usuario.
     * @param clave    String que designa la clave del usuario.
     */
    public Usuario(String nombre, int edad, int telefono, String clave) {
        this.nombre = nombre;
        this.edad = edad;
        this.telefono = telefono;
        this.clave = clave;
    }

    /**
     * Getter de nombre.
     *
     * @return Nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Getter de edad.
     *
     * @return Edad del usuario.
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Getter de teléfono.
     *
     * @return Teléfono del usuario.
     */
    public int getTelefono() {
        return telefono;
    }

    /**
     * Getter de clave.
     *
     * @return Clave del usuario.
     */
    public String getClave() {
        return clave;
    }

    /**
     * toString del usuario.
     *
     * @return Cadena de carácteres con la información del usuario.
     */
    public String toString() {
        return "nombre:" + nombre + "; edad:" + edad + "; teléfono:" + telefono + "; clave:" + clave;
    }
}