package ja.simula01.ejemplos.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class jaEstudianteTest {
    //Verificar que el nombre del estudiante no sea nulo

    @Test
    void jatestNombreEstudiante() {
        jaEstudiante estudiante = new jaEstudiante();
        estudiante.setNombreEstudiante("Pepe");
        Assertions.assertNotNull(estudiante.getNombreEstudiante(), "El nombre del estudiante es nulo");
    }
    //Verificar que la nota parcial1 y nota parcial 2 no sean nulos
    @Test
    void jatestNotaParcial1yNotaParcial2() {
        jaEstudiante estudiante = new jaEstudiante();
        estudiante.setNotaParcial1(87.8);
        estudiante.setNotaParcial2(90);
        Assertions.assertNotNull(estudiante.getNotaParcial1(), "La nota parcial 1 es nula");
        Assertions.assertNotNull(estudiante.getNotaParcial2(), "La nota parcial 2 es nula");
    }

    @Test
    void jatestNotasParcialesMayoresACero() {
        jaEstudiante estudiante = new jaEstudiante();
        estudiante.setNotaParcial1(87.8);
        estudiante.setNotaParcial2(90);
        Assertions.assertTrue(estudiante.getNotaParcial1() > 0, "La nota parcial 1 es menor a cero");
        Assertions.assertTrue(estudiante.getNotaParcial2() > 0, "La nota parcial 2 es menor a cero");
    }

    @Test
    void jatestNotasParcialesMenoresACien() {
        jaEstudiante estudiante = new jaEstudiante();
        estudiante.setNotaParcial1(87.8);
        estudiante.setNotaParcial2(90);
        Assertions.assertTrue(estudiante.getNotaParcial1() <= 100, "La nota parcial 1 es mayor a cien");
        Assertions.assertTrue(estudiante.getNotaParcial2() <= 100, "La nota parcial 1 es mayor a cien");
    }
}