package com.jessat18.app.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EstudianteTest {
    @Test
    void testNombre() {
        Estudiante estudiante = new Estudiante();
        estudiante.setNombre("Jessica");
        Assertions.assertNotNull(estudiante.getNombre());
    }
}