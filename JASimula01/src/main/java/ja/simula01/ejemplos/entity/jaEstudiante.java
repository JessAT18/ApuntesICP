package ja.simula01.ejemplos.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class jaEstudiante {
    private String nombreEstudiante;
    private double notaParcial1;
    private double notaParcial2;
}
