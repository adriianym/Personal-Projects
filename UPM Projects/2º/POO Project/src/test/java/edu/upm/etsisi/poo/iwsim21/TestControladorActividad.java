package edu.upm.etsisi.poo.iwsim21;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

import edu.upm.etsisi.poo.iwsim21.controlador.ControladorActividad;
import edu.upm.etsisi.poo.iwsim21.exceptions.NonExistentDataException;
import edu.upm.etsisi.poo.iwsim21.modelo.actividad.Actividad;
import org.junit.Test;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;


public class TestControladorActividad {

    private ControladorActividad controlador = new ControladorActividad();

    @Test
    public void testCrearActividad() {
        try {
            controlador.crearActividad("genérica", "Actividad Genérica", "Descripción", 60, 10.0, 50);
            List<Actividad> listaActividades = controlador.getListaActividad();
            assertEquals(1, listaActividades.size());
        } catch (NonExistentDataException e) {
            fail("No se esperaba una excepción: " + e.getMessage());
        }
    }

    @Test
    public void testConsultaActividadNombre() throws NonExistentDataException {
        controlador.crearActividad("generica", "Actividad 1", "Descripción 1", 60, 10.0, 50);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        String nombre = "Actividad 1";
        controlador.consultaActividadNombre(nombre);
        String resultadoEsperado = "   Actividades:\n" +
                "    -id:" + controlador.getListaActividad().get(0).getId() + "; nombre:Actividad 1; descripción:Descripción 1; duración:60 min; coste:10.0 €; aforo:50\n";
        String resultadoReal = outputStream.toString();

        assertEquals(resultadoEsperado, resultadoReal);

        // Restaurar System.out al flujo original después de la prueba
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }

    @Test
    public void testConsultaActividadCapacidad() throws NonExistentDataException {
        controlador.crearActividad("generica", "Actividad 2", "Descripción 2", 60, 10.0, 50);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        int capacidad = 50;
        controlador.consultaActividadCapacidad(capacidad);
        String resultadoEsperado = "   Actividades:\n" +
                "    -id:" + controlador.getListaActividad().get(0).getId() + "; nombre:Actividad 2; descripción:Descripción 2; duración:60 min; coste:10.0 €; aforo:50\n";
        String resultadoReal = outputStream.toString();

        assertEquals(resultadoEsperado, resultadoReal);

        // Restaurar System.out al flujo original después de la prueba
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }

    //Test que comprueba  si lo que se emite por pantalla(output) es lo que se deberia emitir
    @Test
    public void testConsultaActividadDuracion() throws NonExistentDataException {
        controlador.crearActividad("generica", "Actividad 3", "Descripción 3", 70, 10.0, 50);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        int duracionPrueba = 70;
        controlador.consultaActividadDuracion(duracionPrueba);
        String resultadoEsperado = "   Actividades:\n" +
                "    -id:" + controlador.getListaActividad().get(0).getId() + "; nombre:Actividad 3; descripción:Descripción 3; duración:70 min; coste:10.0 €; aforo:50\n";
        String resultadoReal = outputStream.toString();

        assertEquals(resultadoEsperado, resultadoReal);

        // Restaurar System.out al flujo original después de la prueba
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }
}