package com.jessica.appjessica.daos;

import com.jessica.appjessica.entities.people.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClienteDao extends JpaRepository<Cliente, Integer> {

}
