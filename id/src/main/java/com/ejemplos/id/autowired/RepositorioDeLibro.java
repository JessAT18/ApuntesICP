package com.ejemplos.id.autowired;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RepositorioDeLibro {
    public List<Libro> buscarTodosLosLibros(){
        List<Libro> listaLibros = new ArrayList<Libro>();
        listaLibros.add(new Libro("100", "Java", "Pepe"));
        listaLibros.add(new Libro("200", "C++", "Maria"));
        listaLibros.add(new Libro("300", "Oracle DBA", "Luisa"));

        return listaLibros;
    }
}
