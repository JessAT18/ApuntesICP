package com.restapp.rest.restytest;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class FacturaRepository {

    @PersistenceContext
    private EntityManager em;

    public List<Factura> buscarTodas(){
        return em.createQuery("SELECT f FROM Factura f", Factura.class).getResultList();
    }

    public Factura buscarUnaFactura(int numero){
        return em.find(Factura.class, numero);
    }

    @Transactional
    public void borrarUnaFactura(Factura factura){
        em.remove(em.merge(factura));
    }

    @Transactional
    public void insertarFactura(Factura factura){
        em.persist(factura);
    }

    @Transactional
    public void actualizarFactura(Factura factura){
        em.merge(factura);
    }
}
