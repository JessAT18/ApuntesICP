package com.principal.pruebas.repositories;

import com.principal.pruebas.entities.Cuenta;

import java.util.List;

public interface ICuentaRepository {
    List<Cuenta> findAll();
    Cuenta findById(Long id);
    void update(Cuenta cuenta);
}
