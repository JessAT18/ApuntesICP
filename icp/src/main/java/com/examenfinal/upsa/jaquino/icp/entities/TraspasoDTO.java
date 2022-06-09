package com.examenfinal.upsa.jaquino.icp.entities;

public class TraspasoDTO {
    private int numProductoOrigen;
    private int numProductoDestino;
    private int cantidad;
    private int sucursal_id;

    public int getNumProductoOrigen() {
        return numProductoOrigen;
    }

    public void setNumProductoOrigen(int numProductoOrigen) {
        this.numProductoOrigen = numProductoOrigen;
    }

    public int getNumProductoDestino() {
        return numProductoDestino;
    }

    public void setNumProductoDestino(int numProductoDestino) {
        this.numProductoDestino = numProductoDestino;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getSucursal_id() {
        return sucursal_id;
    }

    public void setSucursal_id(int sucursal_id) {
        this.sucursal_id = sucursal_id;
    }
}
