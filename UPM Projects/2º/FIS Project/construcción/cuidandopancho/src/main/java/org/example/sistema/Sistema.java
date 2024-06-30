package org.example.sistema;

import org.example.modelo.EnumIdioma;
import org.example.modelo.Usuario;

import java.util.Scanner;

public class Sistema implements ISistema {
    private static Sistema instance;
    private Scanner scanner;
    private EnumIdioma idioma;
    private Usuario usuarioLogeado;

    private Sistema() {
        scanner = new Scanner(System.in);
        idioma = EnumIdioma.CASTELLANO;
        usuarioLogeado = null;
    }

    public static Sistema getInstance() {
        if (instance == null) instance = new Sistema();
        return instance;
    }

    public String entradaString() {
        if (usuarioLogeado == null) System.out.print("cp> ");
        else System.out.print("cp-" + usuarioLogeado.getNombre() + "> ");
        return scanner.nextLine();
    }

    public Usuario getUsuarioLogeado() {
        return usuarioLogeado;
    }

    public void setUsuarioLogeado(Usuario usuarioLogeado) {
        this.usuarioLogeado = usuarioLogeado;
    }

    public EnumIdioma getIdioma() {
        return idioma;
    }

    public void setIdioma(EnumIdioma idioma) {
        this.idioma = idioma;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }
}