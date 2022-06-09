package com.examenfinal.upsa.jaquino.icp.entities;

import com.examenfinal.upsa.jaquino.icp.exceptions.StockInsuficienteException;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre_producto;
    private int stock;

    public Producto() {
    }

    public Producto(int id, String nombre_producto, int stock) {
        this.id = id;
        this.nombre_producto = nombre_producto;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void salida(int cantidad){
        int nuevoStock = this.stock - cantidad;
        if (nuevoStock < 0){
            throw new StockInsuficienteException("Stock Insuficiente!");
        }
        this.stock = nuevoStock;
    }

    public void entrada(int cantidad){
        this.stock += cantidad;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Producto producto = (Producto) o;

        if (id != producto.id) return false;
        if (stock != producto.stock) return false;
        return Objects.equals(nombre_producto, producto.nombre_producto);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nombre_producto != null ? nombre_producto.hashCode() : 0);
        result = 31 * result + stock;
        return result;
    }
}
