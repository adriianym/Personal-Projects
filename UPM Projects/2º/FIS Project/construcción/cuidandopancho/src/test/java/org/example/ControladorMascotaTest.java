package org.example;

import org.example.controlador.ControladorMascota;
import org.example.controlador.ControladorUsuario;
import org.example.modelo.Dueno;
import org.example.modelo.EnumProveedor;
import org.example.modelo.Mascota;
import org.example.persistencia.IPersistenciaMascota;
import org.example.persistencia.PersistenciaMascota;
import org.example.sistema.ISistema;
import org.example.sistema.Sistema;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.*;

public class ControladorMascotaTest {
    IPersistenciaMascota persistenciaMascota;
    ControladorUsuario controladorUsuario;
    ControladorMascota controladorMascota;
    ISistema iSistema;
    List<Mascota> listaMascotas;
    Dueno dueno;

    @Before
    public void ControladorCuidadoTest() throws IOException {
        persistenciaMascota = new PersistenciaMascota("csv/mascotas.csv");
        this.controladorUsuario = new ControladorUsuario();
        this.controladorMascota = new ControladorMascota(controladorUsuario);
        iSistema = Sistema.getInstance();
        dueno = new Dueno("73a96e34b738bae548e575c824e1edf4", "PruebaControladorMascota", EnumProveedor.GOOGLE);
        iSistema.setUsuarioLogeado(dueno);
        listaMascotas = controladorMascota.getListaMascota();
    }

    @After
    public void tearDown() throws IOException {
        if (!listaMascotas.isEmpty()) {
            Mascota mascota = listaMascotas.get(listaMascotas.size() - 1);
            listaMascotas.remove(mascota);
            persistenciaMascota.borrar(mascota.getRiac());
        }
    }

    @Test
    public void testAltaMascotaOk() throws IOException {
        String input = "1234567890,12345,Firulais,Madrid,Gato\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        iSistema.setScanner(new Scanner(System.in));
        controladorMascota.altaMascota();

        assertEquals(1, listaMascotas.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltaMascotaRiacNulo() throws IOException {
        String input = ",12345,Firulais,Madrid,Gato\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        iSistema.setScanner(new Scanner(System.in));
        controladorMascota.altaMascota();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltaMascotaRiacInvalido() throws IOException {
        String input = "12345,12345,Firulais,Madrid,Gato\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        iSistema.setScanner(new Scanner(System.in));
        controladorMascota.altaMascota();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltaMascotaRiacRepetido() throws IOException {
        Mascota mascota = new Mascota("1234567890", 12345, "Firulais", "Madrid", "Gato", dueno);
        listaMascotas.add(mascota);

        String input = "1234567890,12345,Firulais,Madrid,Gato\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        iSistema.setScanner(new Scanner(System.in));
        controladorMascota.altaMascota();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltaMascotaPolizaNula() throws IOException {
        String input = "1234567890,,Firulais,Madrid,Gato\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        iSistema.setScanner(new Scanner(System.in));
        controladorMascota.altaMascota();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltaMascotaPolizaNegativa() throws IOException {
        String input = "1234567890,-5,Firulais,Madrid,Gato\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        iSistema.setScanner(new Scanner(System.in));
        controladorMascota.altaMascota();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltaMascotaPolizaRepetida() throws IOException {
        Mascota mascota = new Mascota("1234567890", 12345, "Firulais", "Madrid", "Gato", dueno);
        listaMascotas.add(mascota);

        String input = "1234567890,12345,Firulais,Madrid,Gato\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        iSistema.setScanner(new Scanner(System.in));
        controladorMascota.altaMascota();
    }

    @Test
    public void testAltaMascotaExoticaOk() throws IOException {
        String input = "1234567890,12345,Firulais,Madrid,Gato\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        iSistema.setScanner(new Scanner(System.in));
        controladorMascota.altaMascotaExotica();

        assertEquals(1, listaMascotas.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltaMascotaExoticaRiacNulo() throws IOException {
        String input = ",12345,Firulais,Madrid,Gato\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        iSistema.setScanner(new Scanner(System.in));
        controladorMascota.altaMascotaExotica();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltaMascotaExoticaRiacInvalido() throws IOException {
        String input = "12345,12345,Firulais,Madrid,Gato\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        iSistema.setScanner(new Scanner(System.in));
        controladorMascota.altaMascotaExotica();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltaMascotaExoticaRiacRepetido() throws IOException {
        Mascota mascota = new Mascota("1234567890", 12345, "Firulais", "Madrid", "Gato", dueno);
        listaMascotas.add(mascota);

        String input = "1234567890,12345,Firulais,Madrid,Gato\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        iSistema.setScanner(new Scanner(System.in));
        controladorMascota.altaMascotaExotica();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltaMascotaExoticaPolizaNula() throws IOException {
        String input = "1234567890,,Firulais,Madrid,Gato\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        iSistema.setScanner(new Scanner(System.in));
        controladorMascota.altaMascotaExotica();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltaMascotaExoticaPolizaNegativa() throws IOException {
        String input = "1234567890,-5,Firulais,Madrid,Gato\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        iSistema.setScanner(new Scanner(System.in));
        controladorMascota.altaMascotaExotica();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAltaMascotaExoticaPolizaRepetida() throws IOException {
        Mascota mascota = new Mascota("1234567890", 12345, "Firulais", "Madrid", "Gato", dueno);
        listaMascotas.add(mascota);

        String input = "1234567890,12345,Firulais,Madrid,Gato\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        iSistema.setScanner(new Scanner(System.in));
        controladorMascota.altaMascotaExotica();
    }

    @Test
    public void testGetMascotaRiacOk() {
        Mascota mascotaRequerida = new Mascota("1234567890", 12345, "Firulais", "Madrid", "Gato", dueno);
        listaMascotas.add(mascotaRequerida);

        Mascota mascotaReal = controladorMascota.getMascotaRiac("1234567890");

        assertEquals(mascotaReal, mascotaRequerida);
    }

    @Test
    public void testGetMascotaRiacMal() {
        Mascota mascotaReal = controladorMascota.getMascotaRiac("1234567890");

        assertNull(mascotaReal);
    }
}