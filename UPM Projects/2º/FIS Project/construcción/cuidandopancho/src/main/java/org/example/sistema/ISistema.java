package org.example.sistema;

import org.example.modelo.EnumIdioma;
import org.example.modelo.Usuario;

import java.util.Scanner;

public interface ISistema {
    String entradaString();

    EnumIdioma getIdioma();

    void setIdioma(EnumIdioma idioma);

    Usuario getUsuarioLogeado();

    void setUsuarioLogeado(Usuario usuario);

    void setScanner(Scanner scanner);
}