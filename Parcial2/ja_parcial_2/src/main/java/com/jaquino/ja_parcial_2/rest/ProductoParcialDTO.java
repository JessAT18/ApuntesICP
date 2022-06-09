package com.jaquino.ja_parcial_2.rest;

public class ProductoParcialDTO {
    //ProductoParcialDTO no contiene el codigo del producto

    private String nombre;
    private double costo;
    private double precio;

    public ProductoParcialDTO() {
    }

    public ProductoParcialDTO(Producto producto) {
        this.setNombre(producto.getNombre());
        this.setCosto(producto.getCosto());
        this.setPrecio(producto.getPrecio());
    }

    public ProductoParcialDTO(String nombre, double costo, double precio) {
        this.nombre = nombre;
        this.costo = costo;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
