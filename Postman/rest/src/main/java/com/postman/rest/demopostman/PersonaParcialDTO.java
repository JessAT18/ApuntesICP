package com.postman.rest.demopostman;

public class PersonaParcialDTO {
    private String apellidos;
    private int edad;

    public PersonaParcialDTO() {
    }

    public PersonaParcialDTO(String apellidos, int edad) {
        this.apellidos = apellidos;
        this.edad = edad;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }
}
