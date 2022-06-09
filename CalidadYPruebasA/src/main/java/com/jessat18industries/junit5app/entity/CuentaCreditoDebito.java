package com.jessat18industries.junit5app.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class CuentaCreditoDebito {
    private String nombre;
    private BigDecimal saldo; //Mayor precision para los calculos
}