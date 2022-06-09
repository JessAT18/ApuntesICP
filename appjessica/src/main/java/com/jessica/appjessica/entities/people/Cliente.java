package com.jessica.appjessica.entities.people;

import com.jessica.appjessica.entities.enums.Estados;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "petbl_Clientes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //Para cuando el nombre del atributo sea diferente a cuando esta en la tabla de la bd:
    @Column(name = "sCliente_nm")
    //@NotNull(message = "Nombre cliente requerido")
    @NotNull(message = "{cliente.scliente_nm.required}")
    private String sCliente_nm;

    @NotNull(message = "{cliente.scliente_app.required}")
    private String sCliente_app;

    private String sDireccion_desc;

    //private int iEstado_fl;

    @Enumerated(value = EnumType.ORDINAL)
    private Estados iEstado_fl;
}