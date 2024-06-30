package org.example;

import org.example.controlador.*;
import org.example.sistema.ISistema;
import org.example.sistema.Sistema;
import org.example.modelo.Cuidado;
import org.example.modelo.Cuidador;
import org.example.modelo.EnumIdioma;
import org.example.modelo.Usuario;
import org.example.vista.IVistaApp;
import org.example.vista.VistaApp;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class App {
    ControladorCuidador controladorCuidador;
    ControladorUsuario controladorUsuario;
    ControladorMascota controladorMascota;
    ControladorCuidado controladorCuidado;
    ControladorDueno controladorDueno;
    ISistema iSistema;
    IVistaApp vistaApp;

    public App() throws IOException {
        controladorUsuario = new ControladorUsuario();
        controladorCuidador = new ControladorCuidador();
        controladorDueno = new ControladorDueno();
        controladorMascota = new ControladorMascota(controladorUsuario);
        controladorCuidado = new ControladorCuidado(controladorUsuario, controladorMascota);
        iSistema = Sistema.getInstance();
        vistaApp = new VistaApp();
    }

    public void init() {
        boolean run = true;

        vistaApp.inicioApp();

        Usuario usuario;
        String input;
        String rol;

        while (run) {
            usuario = iSistema.getUsuarioLogeado();
            rol = usuario != null ? usuario.getClass().getSimpleName() : null;

            vistaApp.mostrarComandos(rol);

            input = iSistema.entradaString().trim();

            try {
                switch (input.toUpperCase()) {
                    case "LOGIN":
                        if (usuario == null) {
                            usuario = controladorUsuario.login();
                            iSistema.setUsuarioLogeado(usuario);
                        } else throw new IllegalStateException("Ya te encuentras logeado.");
                        break;
                    case "LOGOUT":
                        if (usuario != null) {
                            iSistema.setUsuarioLogeado(null);
                            vistaApp.usuarioLogout();
                        } else throw new IllegalStateException("No estas logeado.");
                        break;
                    case "CAMBIAR IDIOMA":
                        vistaApp.seleccionarIdioma();
                        String idiomaString = iSistema.entradaString().toUpperCase();
                        idiomaString = idiomaString.replace("INGLES", "INGLÉS").replace("CATALAN", "CATALÁN");

                        try {
                            EnumIdioma idioma = EnumIdioma.valueOf(idiomaString);
                            iSistema.setIdioma(idioma);
                        } catch (IllegalArgumentException e) {
                            throw new IllegalArgumentException("Idioma introducido no válido.", e);
                        }
                        break;
                    case "SALIR":
                        run = false;
                        vistaApp.finalApp();
                        break;

                    // Comandos para CUIDADOR y DUEÑO.
                    case "LISTAR MASCOTAS":
                    case "VER MIS CUIDADOS":
                    case "ALTA MASCOTA":
                    case "ALTA MASCOTA EXOTICA":
                    case "ALTA MASCOTA EXÓTICA":
                    case "ALTA PETICION CUIDADO":
                    case "ALTA PETICIÓN CUIDADO":
                        comandosDuenoCuidador(rol, input);
                        break;
                    default:
                        throw new IllegalArgumentException("Comando desconocido.");
                }

                if (input.equalsIgnoreCase("salir")) break;
            } catch (Exception e) {
                System.out.println(" -ERROR: " + e.getMessage());
            }
        }
    }

    private void comandosDuenoCuidador(String rol, String input) throws IOException, ParseException {
        if ("Dueno".equals(rol)) {
            switch (input.toUpperCase()) {
                case "LISTAR MASCOTAS":
                    controladorDueno.listarMascotasDueno();
                    break;
                case "ALTA MASCOTA":
                    controladorMascota.altaMascota();
                    break;
                case "ALTA MASCOTA EXOTICA":
                case "ALTA MASCOTA EXÓTICA":
                    controladorMascota.altaMascotaExotica();
                    break;
                case "ALTA PETICION CUIDADO":
                case "ALTA PETICIÓN CUIDADO":
                    controladorCuidado.altaPeticionCuidado();
                    break;
            }
        } else if ("Cuidador".equals(rol)) {
            switch (input.toUpperCase()) {
                case "VER MIS CUIDADOS":
                    controladorCuidador.verCuidados();
                    break;
                case "LISTAR MASCOTAS":
                    Cuidador cuidador = controladorUsuario.getCuidadorId(iSistema.getUsuarioLogeado().getId());

                    if (cuidador == null) {
                        throw new NullPointerException("El usuario seleccionado no existe.");
                    }

                    List<Cuidado> cuidadosLibres = controladorCuidado.getCuidadosLibres();
                    Cuidado cuidado = controladorCuidador.listarMascotasCuidador(cuidadosLibres);

                    controladorCuidado.asignarCuidado(cuidado, cuidador);
                    break;
            }
        } else {
            throw new IllegalArgumentException("Comando desconocido.");
        }
    }

    public static void main(String[] args) {
        try {
            App app = new App();
            app.init();
        } catch (Exception e) {
            System.out.println(" -ERROR: " + e.getMessage() + " (" + e.getClass() + ")");
        }
    }
}
