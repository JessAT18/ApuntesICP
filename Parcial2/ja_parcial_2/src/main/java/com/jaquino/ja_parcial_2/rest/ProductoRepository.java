package com.jaquino.ja_parcial_2.rest;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ProductoRepository {
    @PersistenceContext
    private EntityManager em;

    public List<Producto> buscarTodosLosProductos(){
        return em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
    }

    public Producto buscarUnProducto(int codigo){
        return em.find(Producto.class, codigo);
    }

    @Transactional
    public void borrarUnProducto(Producto producto){
        em.remove(em.merge(producto));
    }

    @Transactional
    public void insertarUnProducto(Producto producto){
        em.persist(producto);
    }

    @Transactional
    public void actualizarUnProducto(Producto producto){
        em.merge(producto);
    }

    @Transactional
    public void actualizarUnProductoParcial(int codigo, ProductoParcialDTO productoParcialDTO) {
        //Obteniendo producto desde la BD
        Producto producto = em.find(Producto.class, codigo);

        //Setteando nuevos valores
        producto.setNombre(productoParcialDTO.getNombre());
        producto.setCosto(productoParcialDTO.getCosto());
        producto.setPrecio(productoParcialDTO.getPrecio());

        //Insertando producto
        actualizarUnProducto(producto);
    }
}
