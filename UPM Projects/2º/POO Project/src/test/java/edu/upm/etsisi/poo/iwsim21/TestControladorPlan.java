package edu.upm.etsisi.poo.iwsim21;

import edu.upm.etsisi.poo.iwsim21.controlador.ControladorPlan;
import edu.upm.etsisi.poo.iwsim21.exceptions.*;
import edu.upm.etsisi.poo.iwsim21.modelo.actividad.Actividad;
import edu.upm.etsisi.poo.iwsim21.modelo.plan.Plan;
import edu.upm.etsisi.poo.iwsim21.modelo.usuario.Usuario;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestControladorPlan {

    int id = 0;
    ControladorPlan controladorPlan;
    Usuario propietario;
    Actividad actividad;
    Usuario participante;
    GregorianCalendar fecha;

    @Before
    public void setUp() {
        id = 0;
        controladorPlan = new ControladorPlan();
        propietario = new Usuario("adrian", 17, 123, "321");
        actividad = new Actividad("Fútbol", "Partido de fútbol", 90, 20.0, 22);
        participante = new Usuario("participante", 20, 456, "789");
        fecha = new GregorianCalendar();
    }

    @AfterEach
    public void tearDown() {
        // Limpiar o restablecer el estado después de cada test si es necesario.
        controladorPlan.getListaPlan().get(0).getActividades().clear();
        controladorPlan.getListaPlan().clear();
        // Limpiar la lista de planes
    }

    @Test
    public void testCrearPlan() throws IncorrectInputException {
        controladorPlan.crearPlan(propietario, "Fiesta en la playa", "01/01/2023-12:00", "Playa", 50);
        Assert.assertEquals(1, controladorPlan.getListaPlan().size());
    }


    @Test(expected = NonExistentPlanException.class)
    public void testEditarPlanNoExistente() throws NonExistentDataException, NoPermissionException {
        controladorPlan.editarPlan(0, propietario, "Fiesta en la montaña", "01/01/2023-12:00", "Montaña", 30);
    }


    @Test(expected = NonExistentPlanException.class)
    public void testBorrarPlanNoExistente() throws NonExistentDataException, NoPermissionException {
        controladorPlan.borrarPlan(propietario, 0);
    }

    @Test
    public void testJoinPlan() throws NonExistentDataException, NoPermissionException {
        // Crear un plan en el futuro
        controladorPlan.crearPlan(propietario, "Fiesta en la playa", "01/02/2024-12:00", "Playa", 50);
        // Intentar unirse al plan
        try {
            controladorPlan.joinPlan(participante, controladorPlan.getListaPlan().get(0).getId(), fecha);
            // Verificar que el participante está en el plan después de unirse
            Assert.assertTrue(controladorPlan.contieneUser(controladorPlan.getListaPlan().get(0).getId(), participante));
        } catch (NoPermissionException e) {
            // Si ocurre alguna excepción, imprimir el mensaje
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testAnadirActividadPlan() throws IncorrectInputException, NonExistentDataException, NoPermissionException, NoCapacityException {
        controladorPlan.crearPlan(propietario, "Fiesta en la playa", "01/01/2023-12:00", "Playa", 50);
        controladorPlan.anadirActividadPlan(controladorPlan.getListaPlan().get(0).getId(), actividad, propietario);
        Assert.assertEquals(1, controladorPlan.getListaPlan().get(0).getActividades().size());
    }

    @Test
    public void testBorrarPlan() throws IncorrectInputException, NonExistentDataException, NoPermissionException {
        controladorPlan.crearPlan(propietario, "Fiesta en la playa", "01/01/2023-12:00", "Playa", 50);
        Assert.assertEquals(1, controladorPlan.getListaPlan().size());
        controladorPlan.borrarPlan(propietario, controladorPlan.getListaPlan().get(0).getId());
        Assert.assertEquals(0, controladorPlan.getListaPlan().size());
    }

    @Test
    public void testEditarPlan() throws IncorrectInputException, NonExistentDataException, NoPermissionException {
        controladorPlan.crearPlan(propietario, "Fiesta en la playa", "01/01/2023-12:00", "Playa", 50);
        controladorPlan.editarPlan(controladorPlan.getListaPlan().get(0).getId(), propietario, "Fiesta en la montaña", "01/01/2023-12:00", "Montaña", 30);
        Assert.assertEquals("Fiesta en la montaña", controladorPlan.getListaPlan().get(0).getNombrePlan());
    }

    @Test
    public void testValorarPlan() throws NonExistentDataException, NoPermissionException {
        // Crear un plan en el pasado
        controladorPlan.crearPlan(propietario, "Fiesta en la playa", "01/01/2020-12:00", "Playa", 50);
        // Valorar el plan
        try {
            // Valorar con una puntuación
            controladorPlan.valorarPlan(controladorPlan.getListaPlan().get(0).getId(), 4.5, fecha, propietario);
            // Verificar que la valoración del plan se haya actualizado correctamente
            Assert.assertEquals(4.5, controladorPlan.getListaPlan().get(0).getValoraciones().get(0), 0.001);
        } catch (NoPermissionException | NonExistentDataException e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void testConsultaPlanCoste() throws NonExistentDataException {
        controladorPlan.crearPlan(propietario, "Fiesta en la playa", "01/01/2020-12:00", "Playa", 50);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        int coste = 0; // Al plan no le he metido ninguna actividad por lo que su coste sera 0.
        controladorPlan.consultaPlanesCoste(coste);
        String resultadoEsperado = "   Planes:\n" +
                "    -id:" + controladorPlan.getListaPlan().get(0).getId() + "; propietario:" + propietario.getNombre() + "; nombre:Fiesta en la playa; fecha:01/01/2020-12:00; lugar:Playa\n";
        String resultadoReal = outputStream.toString();

        assertEquals(resultadoEsperado, resultadoReal);

        // Restaurar System.out al flujo original después de la prueba
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }

    @Test
    public void testConsultaPlanLugar() throws NonExistentDataException {
        controladorPlan.crearPlan(propietario, "Fiesta en la playa", "01/01/2020-12:00", "Playa", 50);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        String lugar = "Playa";
        controladorPlan.consultaPlanesLugar(lugar);
        String resultadoEsperado = "   Planes:\n" +
                "    -id:" + controladorPlan.getListaPlan().get(0).getId() + "; propietario:" + propietario.getNombre() + "; nombre:Fiesta en la playa; fecha:01/01/2020-12:00; lugar:Playa\n";
        String resultadoReal = outputStream.toString();

        assertEquals(resultadoEsperado, resultadoReal);

        // Restaurar System.out al flujo original después de la prueba
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }

    @Test
    public void testConsultaPlanFecha() throws NonExistentDataException {
        controladorPlan.crearPlan(propietario, "Fiesta en la playa", "01/01/2020-12:00", "Playa", 50);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        String fecha = "01/01/2020-12:00";
        controladorPlan.consultaPlanesFecha(fecha);
        String resultadoEsperado = "   Planes:\n" +
                "    -id:" + controladorPlan.getListaPlan().get(0).getId() + "; propietario:" + propietario.getNombre() + "; nombre:Fiesta en la playa; fecha:01/01/2020-12:00; lugar:Playa\n";
        String resultadoReal = outputStream.toString();

        assertEquals(resultadoEsperado, resultadoReal);

        // Restaurar System.out al flujo original después de la prueba
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
    }
}