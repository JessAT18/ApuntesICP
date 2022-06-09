package com.principal.pruebas.repositories;

import com.principal.pruebas.entities.Banco;

import java.util.List;

public interface IBancoRepository {
    List<Banco> findAll();
    Banco findById(Long id);
    void update(Banco cuenta);
}
