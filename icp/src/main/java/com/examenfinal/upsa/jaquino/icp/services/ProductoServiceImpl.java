package com.examenfinal.upsa.jaquino.icp.services;

import com.examenfinal.upsa.jaquino.icp.entities.Producto;
import com.examenfinal.upsa.jaquino.icp.entities.Sucursal;
import com.examenfinal.upsa.jaquino.icp.repositories.IProductoRepository;
import com.examenfinal.upsa.jaquino.icp.repositories.ISucursalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductoServiceImpl implements IProductoService{
    private final IProductoRepository iProductoRepository;
    private final ISucursalRepository iSucursalRepository;

    public ProductoServiceImpl(IProductoRepository iProductoRepository, ISucursalRepository iSucursalRepository) {

        this.iProductoRepository = iProductoRepository;
        this.iSucursalRepository = iSucursalRepository;
    }

    @Override
    public Producto save(Producto producto) { return iProductoRepository.save(producto);}

    @Override
    @Transactional(readOnly = true)
    public Producto findById(int productoId) {
        return iProductoRepository.findById(productoId).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public int revisarTotalTraspasos(int sucursalId) {
        Sucursal sucursal = iSucursalRepository.findById(sucursalId).orElseThrow();
        return sucursal.getNro_traspasos();
    }

    @Override
    @Transactional(readOnly = true)
    public int revisarStock(int productoId) {
        Producto producto = iProductoRepository.findById(productoId).orElseThrow();
        return producto.getStock();
    }

    @Override
    public void traspasos(int numProductoOrigen, int numProductoDestino, int cantidad, int sucursalId) {

        //-------- Actualizar num producto destino con la nueva cantidad ---------
        //Primero: Salida de la sucursal origen (de aquí salen los productos para traspasar a la sucursal destino).
        Producto productoOrigen = iProductoRepository.findById(numProductoOrigen).orElseThrow();
        productoOrigen.salida(cantidad);
        iProductoRepository.save(productoOrigen);

        //Segundo: Entrada de los productos destino
        Producto productoDestino = iProductoRepository.findById(numProductoDestino).orElseThrow();
        productoDestino.entrada(cantidad);
        iProductoRepository.save(productoDestino);


        //Encontrar la sucursal de manera estática (hay varias maneras: Sesión de usuario, otros contextos.
        Sucursal sucursal = iSucursalRepository.findById(sucursalId).orElseThrow();

        //Capturar el total de traspasos que ya tiene esa sucursal
        int totalTraspasos = sucursal.getNro_traspasos();

        //Incrementar en uno(1) ese total de traspasos
        sucursal.setNro_traspasos(++totalTraspasos);

        //Actualizar la sucursal con el total de traspasos incrementado en uno(1)
        iSucursalRepository.save(sucursal);
    }
}
