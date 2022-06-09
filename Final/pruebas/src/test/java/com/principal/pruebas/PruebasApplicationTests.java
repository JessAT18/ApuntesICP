package com.principal.pruebas;

import com.principal.pruebas.entities.Banco;
import com.principal.pruebas.entities.Cuenta;
import com.principal.pruebas.repositories.IBancoRepository;
import com.principal.pruebas.repositories.ICuentaRepository;
import com.principal.pruebas.services.CuentaServiceImpl;
import com.principal.pruebas.services.ICuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*; //Static
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
class PruebasApplicationTests {

	private IBancoRepository iBancoRepository;
	private ICuentaRepository iCuentaRepository;
	private ICuentaService iCuentaService;

	//Por cada metodo, vamos a definir un Mock (repositorio) y la instancia del service.
	@BeforeEach
	void setUp() {
		iCuentaRepository = mock(ICuentaRepository.class);
		iBancoRepository = mock(IBancoRepository.class);

		iCuentaService = new CuentaServiceImpl(iCuentaRepository, iBancoRepository);
	}

	@Test
	void contextLoads() {
		//Determinar los valores con los cuales vamos a trabajar las pruebas
		 when(iCuentaRepository.findById(1L)).thenReturn(DatosDePruebas.cuenta_001);
		 when(iCuentaRepository.findById(2L)).thenReturn(DatosDePruebas.cuenta_002);

		 when(iBancoRepository.findById(1L)).thenReturn(DatosDePruebas.banco_001);

		 //Primera prueba: Verificar el saldo antes de la transferencia
		BigDecimal saldoCuentaOrigen = iCuentaService.revisarSaldo(1L);
		BigDecimal saldoCuentaDestino = iCuentaService.revisarSaldo(2L);
		assertEquals("1000", saldoCuentaOrigen.toPlainString());
		assertEquals("2000", saldoCuentaDestino.toPlainString());

		//Revisar las transferencias. Realizar las pruebas de transferir entre una cuenta
		//origen a una cuenta destino.
		iCuentaService.transferir(1L, 2L, new BigDecimal("100"), 1L);

		//Vamos a volver a revisar los saldos
		saldoCuentaOrigen = iCuentaService.revisarSaldo(1L);
		saldoCuentaDestino = iCuentaService.revisarSaldo(2L);
		assertEquals("900", saldoCuentaOrigen.toPlainString());
		assertEquals("2100", saldoCuentaDestino.toPlainString());

		//Vamos a verificar cuantas veces se invocaron los métodos de cada Mock (Métodos de cada repositorio)
		//Verificar para la Cuenta
		verify(iCuentaRepository, times(3)).findById(1L);
		verify(iCuentaRepository, times(3)).findById(2L);
		verify(iCuentaRepository, times(2)).update(any(Cuenta.class));

		//Verificar el banco
		verify(iBancoRepository).findById(1L); //Por defecto el time es 1
		verify(iBancoRepository).update(any(Banco.class));

		//Revisar el total de transferencias. Probar la actualización del número de transferencias
		//para verificar si cumple con el requisito.
		int totalTransferencia = iCuentaService.revisarTotalDeTransferencias(1L);
		assertEquals(1, totalTransferencia);

	}

}
