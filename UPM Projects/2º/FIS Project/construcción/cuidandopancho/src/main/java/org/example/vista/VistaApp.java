package org.example.vista;

public class VistaApp implements IVistaApp {
    public VistaApp() {
    }

    public void inicioApp() {
        System.out.println("    Bienvenido a Cuidando a Pancho");
        System.out.println("--------------------------------------\n");
    }

    public void finalApp() {
        System.out.println(" -¡Adios!");
    }

    public void usuarioLogout() {
        System.out.println(" -Has hecho logout correctamente.");
    }

    public void seleccionarIdioma() {
        System.out.println(" -Selecciona el idioma (Castellano, Inglés, Catalán, Euskera, Gallego).");
    }

    public void comandoPrincipal() {
        System.out.println("¿Qué quieres hacer?");
        System.out.println(" -Login");
        System.out.println(" -Cambiar idioma");
        System.out.println(" -Salir");
    }

    public void comandoDueno() {
        System.out.println("¿Qué quieres hacer?");
        System.out.println(" -Listar mascotas");
        System.out.println(" -Alta mascota");
        System.out.println(" -Alta mascota exótica");
        System.out.println(" -Alta petición cuidado");
        System.out.println(" -Logout");
        System.out.println(" -Cambiar idioma");
        System.out.println(" -Salir");
    }

    public void comandoCuidador() {
        System.out.println("¿Qué quieres hacer?");
        System.out.println(" -Ver mis cuidados");
        System.out.println(" -Listar mascotas");
        System.out.println(" -Logout");
        System.out.println(" -Cambiar idioma");
        System.out.println(" -Salir");
    }

    public void mostrarComandos(String rol) {
        if (rol != null) {
            if ("Dueno".equals(rol)) {
                comandoDueno();
            } else comandoCuidador();
        } else comandoPrincipal();
    }
}