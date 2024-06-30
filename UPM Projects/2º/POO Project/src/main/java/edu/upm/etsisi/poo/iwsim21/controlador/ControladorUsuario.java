package edu.upm.etsisi.poo.iwsim21.controlador;

import edu.upm.etsisi.poo.iwsim21.exceptions.IncorrectInputException;
import edu.upm.etsisi.poo.iwsim21.exceptions.IncorrectLengthException;
import edu.upm.etsisi.poo.iwsim21.exceptions.OutOfRangeException;
import edu.upm.etsisi.poo.iwsim21.modelo.usuario.Usuario;
import edu.upm.etsisi.poo.iwsim21.vista.CLI;

import java.util.ArrayList;
import java.util.List;

public class ControladorUsuario {

    /**
     * Lista Usuario de usuarios creados.
     */
    private List<Usuario> listaUsuario;
    /**
     * CLI del proyecto.
     */
    private CLI cli;

    /**
     * Constructor de ControladorUsuario.
     */
    public ControladorUsuario() {
        listaUsuario = new ArrayList<>();
        cli = CLI.getInstance();
    }

    /**
     * Método que se encarga de registrar un usuario comprobando sus datos.
     *
     * @param nombre   Nombre del usuario.
     * @param edad     Edad del usuario (14-100 años).
     * @param telefono Número de teléfono del usuario.
     * @param clave    Clave del usuario.
     */
    public void registro(String nombre, int edad, int telefono, String clave) throws IncorrectInputException {
        Usuario usuario = new Usuario(nombre, edad, telefono, clave);
        if (usuarioCorrecto(usuario)) {
            listaUsuario.add(usuario);
            cli.emitirMensaje("Usuario registrado correctamente.");
        }
    }

    /**
     * Método que loguea al usuario en la app.
     *
     * @param nombre Nombre del usuario.
     * @param clave  Clave del usuario.
     * @return Devuelve el usuario si ha sido logeado.
     * @throws IncorrectInputException
     */
    public Usuario loginUsuario(String nombre, String clave) throws IncorrectInputException {
        Usuario usuario = null;
        for (int i = 0; i < listaUsuario.size() && usuario == null; i++) {
            Usuario user = listaUsuario.get(i);
            if (user.getNombre().equals(nombre) && user.getClave().equals(clave)) usuario = user;
        }
        if (usuario == null) throw new IncorrectInputException("Usuario/contraseña incorrectos.");
        return usuario;
    }

    //MÉTODOS ADICIONALES

    /**
     * Método que comprueba si un usuario es correcto.
     *
     * @param usuario Usuario a comprobar.
     * @return True si es correcto y false si no.
     * @throws IncorrectInputException
     */
    public boolean usuarioCorrecto(Usuario usuario) throws IncorrectInputException {
        boolean correcto = true;
        for (int i = 0; i < listaUsuario.size(); i++) {
            Usuario user = listaUsuario.get(i);
            if (user.getNombre().equals(usuario.getNombre()) || user.getTelefono() == usuario.getTelefono()) {
                if (user.getNombre().equals(usuario.getNombre()))
                    throw new IncorrectInputException("Nombre ya existente.");
                else throw new IncorrectInputException("Teléfono ya existente.");
            }
        }
        if ((!(usuario.getEdad() >= 14 && usuario.getEdad() <= 100) || usuario.getClave().length() < 3)) {
            if (usuario.getClave().length() < 3)
                throw new IncorrectLengthException("La longitud de la clave debe ser igual o mayor a 3.");
            else throw new OutOfRangeException("La edad debe estar entre 14 y 100 años.");
        }
        return correcto;
    }

    /**
     * Getter de listaUsuario.
     *
     * @return Lista con los usuarios registrados.
     */
    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }
}