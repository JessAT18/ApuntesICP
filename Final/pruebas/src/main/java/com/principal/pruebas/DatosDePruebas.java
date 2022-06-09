package com.principal.pruebas;

import com.principal.pruebas.entities.Banco;
import com.principal.pruebas.entities.Cuenta;

import java.math.BigDecimal;

public class DatosDePruebas {
    public static final Cuenta cuenta_001 = new Cuenta(1L, "Lucas", new BigDecimal("1000"));
    public static final Cuenta cuenta_002 = new Cuenta(2L, "Ana", new BigDecimal("2000"));

    public static final Banco banco_001 = new Banco(1L, "El Banco de mi Tierra", 0);

}
