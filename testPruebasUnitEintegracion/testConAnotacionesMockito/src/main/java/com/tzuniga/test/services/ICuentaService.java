package com.tzuniga.test.services;

import com.tzuniga.test.entities.Cuenta;

import java.math.BigDecimal;

public interface ICuentaService {
    Cuenta save(Cuenta cuenta);
    Cuenta findById(Long cuentaId);
    int revisarTotalTransferencias(Long bancoId);
    BigDecimal revisarSaldo(Long cuentaId);
    void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId);
}

