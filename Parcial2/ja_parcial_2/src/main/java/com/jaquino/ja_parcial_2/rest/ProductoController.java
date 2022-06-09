package com.jaquino.ja_parcial_2.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping
    public List<Producto> buscarTodosLosProductos() {
        return productoRepository.buscarTodosLosProductos();
    }

    @GetMapping("/{codigo}")
    public Producto buscarUnProducto(@PathVariable int codigo) {
        return productoRepository.buscarUnProducto(codigo);
    }

    @DeleteMapping("/{codigo}")
    public void borrarUnProducto(Producto producto) {
        productoRepository.borrarUnProducto(producto);
    }

    @PostMapping
    public void insertarUnProducto(@RequestBody Producto producto) {
        productoRepository.insertarUnProducto(producto);
    }

    @PutMapping("/{codigo}")
    public void actualizarUnProducto(@RequestBody Producto producto) {
        productoRepository.actualizarUnProducto(producto);
    }

    @PatchMapping("/{codigo}")
    public void actualizarUnProductoParcial(@PathVariable int codigo, @RequestBody ProductoParcialDTO productoParcialDTO) {
        productoRepository.actualizarUnProductoParcial(codigo, productoParcialDTO);
    }
}
