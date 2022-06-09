package com.tzuniga.test.repositories;

import com.tzuniga.test.entities.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ICuentaRepository extends JpaRepository<Cuenta, Long> {
    //List<Cuenta> findAll();
    //Cuenta findById(Long id);
    //void update(Cuenta cuenta);
}
