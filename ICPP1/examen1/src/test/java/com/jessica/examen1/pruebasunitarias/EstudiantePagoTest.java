package com.jessica.examen1.pruebasunitarias;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class EstudiantePagoTest {

    //Verificar que el nombre del estudiante no sea nulo
    @Test
    void testNombreEstudiante() {
        EstudiantePago estudiante = new EstudiantePago();
        estudiante.setNombreEstudiante("Pepe");
        Assertions.assertNotNull(estudiante.getNombreEstudiante(), "El nombre del estudiante es nulo");
    }

    //Verificar que el semestre no sea nulo
    @Test
    void testSemestreEstudiante() {
        EstudiantePago estudiante = new EstudiantePago();
        estudiante.setSemestreEstudiante("Semestre II/2021");
        Assertions.assertNotNull(estudiante.getSemestreEstudiante(), "El semestre del estudiante es nulo");
    }

    //Verificar que el total a pagar no sea nulo y sea mayor que cero.
    @Test
    void testTotalAPagarNotNullGreaterThanZero() {
        EstudiantePago estudiante = new EstudiantePago();
        estudiante.setTotalAPagar(new BigDecimal("15000"));
        Assertions.assertNotNull(estudiante.getTotalAPagar(), "El total a pagar es nulo");
        Assertions.assertFalse(estudiante.getTotalAPagar().compareTo(BigDecimal.ZERO) <= 0, "El total a pagar es menor o igual que cero");
    }

    //Verificar que el Monto Pagado no sea mayor al total a pagar.
    @Test
    void testMontoPagadoLessThanTotalAPagar() {
        EstudiantePago estudiante = new EstudiantePago();
        estudiante.setMontoPagado(new BigDecimal("10000"));
        estudiante.setTotalAPagar(new BigDecimal("15000"));
        Assertions.assertFalse(estudiante.getTotalAPagar().compareTo(estudiante.getMontoPagado()) < 0, "El monto pagado es mayor al total a pagar");
    }

    //Verificar que el Saldo por Pagar sea menor o igual al monto pagado.
    @Test
    void testSaldoPorPagarLessOrEqualThanMontoPagado() {
        EstudiantePago estudiante = new EstudiantePago();
        estudiante.setSaldoPorPagar(new BigDecimal("10000.87"));
        estudiante.setMontoPagado(new BigDecimal("15000.87"));
        Assertions.assertFalse(estudiante.getMontoPagado().compareTo(estudiante.getSaldoPorPagar()) < 0, "El saldo por pagar es mayor al monto pagado");
    }

    //Verificar que el Saldo por Pagar sea menor o igual al Total a Pagar.
    @Test
    void testSaldoPorPagarLessOrEqualThanTotalAPagar() {
        EstudiantePago estudiante = new EstudiantePago();
        estudiante.setSaldoPorPagar(new BigDecimal("10000.87"));
        estudiante.setTotalAPagar(new BigDecimal("15000.87"));
        Assertions.assertFalse(estudiante.getTotalAPagar().compareTo(estudiante.getSaldoPorPagar()) < 0, "El saldo por pagar es mayor al total a pagar");
    }
}