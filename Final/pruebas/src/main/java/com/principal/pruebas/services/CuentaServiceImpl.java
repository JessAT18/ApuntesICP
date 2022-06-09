package com.principal.pruebas.services;

import com.principal.pruebas.entities.Banco;
import com.principal.pruebas.entities.Cuenta;
import com.principal.pruebas.repositories.IBancoRepository;
import com.principal.pruebas.repositories.ICuentaRepository;

import java.math.BigDecimal;

public class CuentaServiceImpl implements ICuentaService{

    private ICuentaRepository iCuentaRepository;
    private IBancoRepository iBancoRepository;

    public CuentaServiceImpl(ICuentaRepository iCuentaRepository, IBancoRepository iBancoRepository) {
        this.iCuentaRepository = iCuentaRepository;
        this.iBancoRepository = iBancoRepository;
    }

    @Override
    public Cuenta findById(Long cuentaId) {
        return iCuentaRepository.findById(cuentaId);
    }

    @Override
    public int revisarTotalDeTransferencias(Long bancoId) {
        Banco banco = iBancoRepository.findById(bancoId);
        return banco.getTotalTransferencia();
    }

    @Override
    public BigDecimal revisarSaldo(Long cuentaId) {
        Cuenta cuenta = iCuentaRepository.findById(cuentaId);
        return cuenta.getSaldo();
    }

    @Override
    public void transferir(Long nroCuentaOrigen, Long nroCuentaDestino, BigDecimal monto, Long bancoId) {
        //Primero encontramos el banco
        Banco banco = iBancoRepository.findById(bancoId);

        //Segundo: Capturar el numero de transferencias que ya tiene ese banco
        int nroTransferencias = banco.getTotalTransferencia();
        banco.setTotalTransferencia(++nroTransferencias);

        //Tercero: Actualizar en banco el nuevo numero de transferencias
        iBancoRepository.update(banco);

        //--Actualizar la cuenta destino con el nuevo saldo
        //Cuarto: Realizar el debito correspondiente (De aqui sale el dinero para transferir a la cuenta destino)
        Cuenta cuentaOrigen = iCuentaRepository.findById(nroCuentaOrigen);
        cuentaOrigen.debito(monto);
        iCuentaRepository.update(cuentaOrigen);

        //Quinto: Acredita el monto a cuenta destino
        Cuenta cuentaDestino = iCuentaRepository.findById(nroCuentaDestino);
        cuentaDestino.credito(monto);
        iCuentaRepository.update(cuentaDestino);
    }
}
