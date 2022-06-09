package com.postman.rest.demopostman;

public class PersonaDTO {
    private String nombre;
    private String apellidos;
    private int edad;

    //DTO Solo debe tener propiedades, getters and setters.

    public PersonaDTO() {
    }

    public PersonaDTO(Persona persona){
        this.setNombre(persona.getNombre());
        this.setApellidos(persona.getApellidos());
        this.setEdad(persona.getEdad());
    }

    public PersonaDTO(String nombre, String apellido, int edad) {
        this.nombre = nombre;
        this.apellidos = apellido;
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
