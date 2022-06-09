package com.tzuniga.test.services;

import com.tzuniga.test.entities.Banco;
import com.tzuniga.test.entities.Cuenta;
import com.tzuniga.test.repositories.IBancoRepository;
import com.tzuniga.test.repositories.ICuentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
@Service //Cuando se quiere realizar pruebas usando anotaciones de spring boot
public class CuentaServiceImpl implements ICuentaService{
    private final ICuentaRepository iCuentaRepository;
    private final IBancoRepository iBancoRepository;

    public CuentaServiceImpl(ICuentaRepository iCuentaRepository, IBancoRepository iBancoRepository) {
        this.iCuentaRepository = iCuentaRepository;
        this.iBancoRepository = iBancoRepository;
    }



    @Override
    public Cuenta save(Cuenta cuenta) {
        return iCuentaRepository.save(cuenta);
    }

    @Override
    @Transactional(readOnly = true)
    public Cuenta findById(Long cuentaId) {
        return iCuentaRepository.findById(cuentaId).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public int revisarTotalTransferencias(Long bancoId) {
        Banco banco = iBancoRepository.findById(bancoId).orElseThrow();
        return banco.getTotalTransferencia();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal revisarSaldo(Long cuentaId) {
        Cuenta cuenta = iCuentaRepository.findById(cuentaId).orElseThrow();
        return cuenta.getSaldo();
    }

    @Override
    @Transactional
    public void transferir(Long numCuentaOrigen, Long numCuentaDestino, BigDecimal monto, Long bancoId) {


        //-------- Actualizar cuenta destino con el nuevo saldo ---------
        //Primero: Debitar de la cuenta origen (de aquí sale el dinero para transferir a la cuenta destino).
        Cuenta cuentaOrigen = iCuentaRepository.findById(numCuentaOrigen).orElseThrow();
        cuentaOrigen.debito(monto);
        iCuentaRepository.save(cuentaOrigen);

        //Segundo: Acreditar la cuenta destino
        Cuenta cuentaDestino = iCuentaRepository.findById(numCuentaDestino).orElseThrow();
        cuentaDestino.credito(monto);
        iCuentaRepository.save(cuentaDestino);


        //Encontrar el banco de manera estática (hay varias maneras: Sesión de usuario, otros contextos.
        Banco banco = iBancoRepository.findById(bancoId).orElseThrow();

        //Capturar el total de transferencias que ya tiene ese banco
        int totalTransferencias = banco.getTotalTransferencia();

        //Incrementar en uno(1) ese total de transferencias
        banco.setTotalTransferencia(++totalTransferencias);

        //Actualizar el banco con el total de transferencias incrementado en uno(1)
        iBancoRepository.save(banco);

    }
}
