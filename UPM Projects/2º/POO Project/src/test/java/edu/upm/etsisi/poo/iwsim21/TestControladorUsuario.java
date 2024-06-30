package edu.upm.etsisi.poo.iwsim21;

import edu.upm.etsisi.poo.iwsim21.controlador.ControladorUsuario;
import edu.upm.etsisi.poo.iwsim21.exceptions.IncorrectInputException;
import edu.upm.etsisi.poo.iwsim21.modelo.usuario.Usuario;
import org.junit.Assert;
import org.junit.Test;

public class TestControladorUsuario {

    ControladorUsuario controladorUsuario = new ControladorUsuario();
    Usuario usuario1 = new Usuario("adrian", 17, 123, "321");

    @Test
    public void testRegistro() throws IncorrectInputException {
        controladorUsuario.registro("adrian", 17, 123, "321");
        Assert.assertEquals(123, controladorUsuario.getListaUsuario().get(0).getTelefono());
    }

    @Test
    public void testLogin() throws IncorrectInputException {
        controladorUsuario.registro("adrian", 17, 123, "321");
        Assert.assertTrue(usuario1.getNombre().equals(controladorUsuario.loginUsuario("adrian", "321").getNombre()) && usuario1.getEdad() == controladorUsuario.loginUsuario("adrian", "321").getEdad() && usuario1.getTelefono() == controladorUsuario.loginUsuario("adrian", "321").getTelefono() && usuario1.getClave().equals(controladorUsuario.loginUsuario("adrian", "321").getClave()));
    }

    @Test
    public void testUsuarioCorrecto() throws IncorrectInputException {
        try {
            controladorUsuario.registro("adrian", 17, 123, "321");
            Assert.assertFalse(controladorUsuario.usuarioCorrecto(usuario1));
        } catch (IncorrectInputException ex) {
            System.out.println(ex.getMessage());
        }
    }
}