package com.tzuniga.test;

import com.tzuniga.test.entities.Banco;
import com.tzuniga.test.entities.Cuenta;

import java.math.BigDecimal;
import java.util.Optional;

public class DatosDePruebas {
    //public static  final Cuenta cuenta001 = new Cuenta(1L, "Lucas", new BigDecimal("1000"));
    //public static  final Cuenta cuenta002 = new Cuenta(2L, "Ana", new BigDecimal("2000"));
    //public static  final Banco banco001 = new Banco(1L,"Banco de mi Tierra", 0);

    public static Optional<Cuenta> crearCuenta001 (){
        return Optional.of(new Cuenta(1L, "Lucas", new BigDecimal("1000")));
    }

    public static Optional<Cuenta> crearCuenta002 (){
        return Optional.of(new Cuenta(2L, "Ana", new BigDecimal("2000")));
    }

    public  static Optional<Banco> crearBanco001 (){
        return  Optional.of(new Banco(1L,"Banco de mi Tierra", 0));
    }
}

