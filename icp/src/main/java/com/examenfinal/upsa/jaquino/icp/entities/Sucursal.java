package com.examenfinal.upsa.jaquino.icp.entities;

import javax.persistence.*;

@Entity
@Table(name = "sucursales")
public class Sucursal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private int nro_traspasos;

    public Sucursal() {
    }

    public Sucursal(int id, String nombre, int nro_traspasos) {
        this.id = id;
        this.nombre = nombre;
        this.nro_traspasos = nro_traspasos;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNro_traspasos() {
        return nro_traspasos;
    }

    public void setNro_traspasos(int nro_traspasos) {
        this.nro_traspasos = nro_traspasos;
    }
}
