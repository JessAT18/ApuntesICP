package com.principal.pruebas.services;

import com.principal.pruebas.entities.Cuenta;

import java.math.BigDecimal;

public interface ICuentaService {
    Cuenta findById(Long cuentaId);
    int revisarTotalDeTransferencias(Long bancoId);
    BigDecimal revisarSaldo(Long cuentaId);
    void transferir(Long nroCuentaOrigen, Long nroCuentaDestino, BigDecimal monto, Long bancoId);
}
