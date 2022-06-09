package com.jessat18industries.junit5app.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

//import static org.junit.jupiter.api.Assertions.*;

class CuentaCreditoDebitoTest {
    @Test
    void testNombreCuenta() {
        /*
        CuentaCreditoDebito cuentaCreditoDebito = new CuentaCreditoDebito();
        cuentaCreditoDebito.setNombre("Jessica");
        String esperado = "Jessica";
        String actual = cuentaCreditoDebito.getNombre();
        Assertions.assertEquals(esperado, actual);*/
        CuentaCreditoDebito cuentaDyC = new CuentaCreditoDebito("Pepe", new BigDecimal("2000.123"));
        Assertions.assertNotNull(cuentaDyC.getNombre(), "El nombre de la cuenta no debe ser nulo");
    }
    @Test
    void testSaldoCuentaDyC(){
        CuentaCreditoDebito cuentaDyC = new CuentaCreditoDebito("Pepe", new BigDecimal("2000.1234"));
        Assertions.assertEquals(2000.1234, cuentaDyC.getSaldo().doubleValue()); //Primera regla de negocio
        Assertions.assertFalse(cuentaDyC.getSaldo().compareTo(BigDecimal.ZERO) < 0); //Segunda regla de negocio
    }


    @Test
    void testReferenciasCuentaDyC(){
        CuentaCreditoDebito cuentaDyC1 = new CuentaCreditoDebito("Pepe", new BigDecimal("2500.123456"));
        CuentaCreditoDebito cuentaDyC2 = new CuentaCreditoDebito("Pepe", new BigDecimal("2500.123456"));
        Assertions.assertEquals(cuentaDyC1, cuentaDyC2);
    }
}