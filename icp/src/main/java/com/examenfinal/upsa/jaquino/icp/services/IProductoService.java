package com.examenfinal.upsa.jaquino.icp.services;

import com.examenfinal.upsa.jaquino.icp.entities.Producto;

public interface IProductoService {
    Producto save(Producto producto);
    Producto findById(int productoId);
    int revisarTotalTraspasos(int sucursalId);
    int revisarStock(int productoId);
    void traspasos(int numProductoOrigen, int numProductoDestino, int cantidad, int sucursalId);
}
