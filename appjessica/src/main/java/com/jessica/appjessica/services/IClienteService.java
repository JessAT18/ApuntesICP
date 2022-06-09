package com.jessica.appjessica.services;

import com.jessica.appjessica.entities.people.Cliente;

import java.util.List;

public interface IClienteService {
    void grabarCliente(Cliente cliente);
    List<Cliente> listarTodosLosClientes();
    void borrarClienteById(int id);
    Cliente encontrarUnCliente(int id);
}
