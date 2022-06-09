package com.jessica.examen1.pruebasunitarias;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EstudiantePago {
    private String nombreEstudiante;
    private String semestreEstudiante;
    private BigDecimal totalAPagar;
    private BigDecimal montoPagado;
    private BigDecimal saldoPorPagar;
}
