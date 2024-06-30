package org.example.vista;

public interface IVistaApp {
    void inicioApp();
    void finalApp();
    void usuarioLogout();
    void seleccionarIdioma();
    void comandoPrincipal();
    void comandoDueno();
    void comandoCuidador();
    void mostrarComandos(String rol);
}