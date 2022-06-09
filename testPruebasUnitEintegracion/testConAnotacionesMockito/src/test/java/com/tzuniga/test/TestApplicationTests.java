package com.tzuniga.test;

import  static com.tzuniga.test.DatosDePruebas.*;
import com.tzuniga.test.entities.Banco;
import com.tzuniga.test.entities.Cuenta;
import com.tzuniga.test.exceptions.DineroInsuficienteException;
import com.tzuniga.test.repositories.IBancoRepository;
import com.tzuniga.test.repositories.ICuentaRepository;
import com.tzuniga.test.services.CuentaServiceImpl;
import com.tzuniga.test.services.ICuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static  org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

@SpringBootTest
class TestApplicationTests {

	//Para usar TEST con anotaciones, debemos hacer los siguientes cambios primeramente. Esta es la primera forma.
	//PRIMERA FORMA, inclusive podemos quitar void SetUp().
	/*
	@Mock
	IBancoRepository bancoRepository;

	@Mock
	ICuentaRepository cuentaRepository;

	@InjectMocks //Es importante aquí que el inject sea del cuentaServiceImp ya que contiene un constructor
	CuentaServiceImpl iCuentaService;

	 */

	//SEGUNDA FORMA: Es usando SPRINGBOOT. Vamos a comentar lo anterior. Tambien debemos colocar en la claseCuentaServoceImpl
	//La anotación @Service para indicarle al contenedor de Spring que de ahí vamos a injectar usandand @Autwired.
	@MockBean
	IBancoRepository bancoRepository;

	@MockBean
	ICuentaRepository cuentaRepository;

	@Autowired //Es importante aquí que el inject sea del cuentaServiceImp ya que contiene un constructor
	//CuentaServiceImpl iCuentaService; //Usando test con spring, lo puedo comentar, ya que spring detecta o escanea en el conenedor quin implementa
	//es interfaz.
	ICuentaService iCuentaService;


	//Por cada método vamos a definir nuestro mock(repositorio) y la instancia del service
	@BeforeEach //Recordar que este método, se ejecuta siempre antes de ejecutar cualquier método test.
	void setUp() {
		//cuentaRepository = mock(ICuentaRepository.class);
		//bancoRepository = mock(IBancoRepository.class);
		//iCuentaService = new CuentaServiceImpl(cuentaRepository, bancoRepository);

		//primera solución
		//DatosDePruebas.cuenta001.setSaldo(new BigDecimal("1000"));
		//DatosDePruebas.cuenta002.setSaldo(new BigDecimal("2000"));
		//DatosDePruebas.banco001.setTotalTransferencia(0);

	}

	@Test
	void contextLoads() {

		when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());
		when(cuentaRepository.findById(2L)).thenReturn(crearCuenta002());

		when(bancoRepository.findById(1L)).thenReturn(crearBanco001());

		//Verificar el saldo antes de realizar la transferencia.
		BigDecimal saldoCuentaOrigen = iCuentaService.revisarSaldo(1L);
		BigDecimal saldoCuentaDestino = iCuentaService.revisarSaldo(2L);
		assertEquals("1000", saldoCuentaOrigen.toPlainString());
		assertEquals("2000", saldoCuentaDestino.toPlainString());

		//Revisar las transferencias
		iCuentaService.transferir(1L, 2L, new BigDecimal("100"), 1L);
		//Ahora debemos volver a revisar los saldos
		saldoCuentaOrigen = iCuentaService.revisarSaldo(1L);
		saldoCuentaDestino = iCuentaService.revisarSaldo(2L);
		assertEquals("900", saldoCuentaOrigen.toPlainString());
		assertEquals("2100", saldoCuentaDestino.toPlainString());

		//Vamos a verificar cuantas veces se invocaron los métodos de cada Mock(quiere decir de cada Repositorio)
		//Verificamos para el Repositorio de Cuentas
		verify(cuentaRepository, times(3)).findById(1L);
		verify(cuentaRepository, times(3)).findById(2L);
		verify(cuentaRepository, times(2)).save(any(Cuenta.class));

		//Verificamos para el Repositorio de Banco
		verify(bancoRepository).findById(1L); //Lo dejamos así, porque times por defecto es una vez
		verify(bancoRepository).save(any(Banco.class));

		//Revisar total de transferencias
		int totalTransferecia = iCuentaService.revisarTotalTransferencias(1L);

		//Cambio 1: Al correr los dos métodos de pruebas unitarias, se cuenta el total de transferencias. En este caso
		//contando con el otro método se hacen dos transferencias. Por lo tanto, el esperado aquí es de dos (2). De esta
		//manera pasará la prueba.
		//Como ya pasó la prueba, ahora vamos a mejorar un poco el código. Vamos a la clase:CuentaServiceImpl y hagamos el cambio.
		//Movemos todo el código que implica actualizar la transferencia al final de todo.
		//Una vez hecho este cambio, volvemos a las pruebas y debemos modificar los verify en contextLoads2, porque el
		//findById del banco se ejecuta una solo vez y ya no dos; y el update se coloca en never porque falló.
		//también cambiar el numero de transferencias, porque al fallar, no se realiza la transferencia.
		assertEquals(1, totalTransferecia);

		//Comienza aquí la SEGUNDA PRUEBA donde iniciamos a usar assertSame
		verify(cuentaRepository, never()).findAll();
		//Estos dos verify, son del mismo findById(), por lo tanto se puede hacer en uno solo
		//verify(cuentaRepository, times(3)).findById(1L);
		//verify(cuentaRepository, times(3)).findById(2L);
		verify(cuentaRepository, times(6)).findById(anyLong()); //Los dos anteriores verify suman 6 llamadas.
		//Este verify vamos a mover a hora al final del contextLoads2, pero y con cinco(5) llamadas. Tambien debemos mover findAll()
	}

	//Clase lunes 22 de noviembre
	@Test
	void contextLoads2() {

		//Cuarto cambio: Como ahora estamos trabajando con dos métodos, los cambios en los valores que se hacen
		//en el primer método afectarán al segundo método. Por lo tanto necesitamos independizar los valores,
		//ya que cada prueba unitaria por esencia debería ser independiente de otros métodos. Por lo tanto, en vez
		// de que sean constantes, debería ser un método estático de la clase. Por ejemplo, el lugar de invocar
		//DatosPruebas.cuenta001; debería invocar un método que cree una nueva instancia.

		when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());
		when(cuentaRepository.findById(2L)).thenReturn(crearCuenta002());
		when(bancoRepository.findById(1L)).thenReturn(crearBanco001());

		//Verificar el saldo antes de realizar la transferencia.
		BigDecimal saldoCuentaOrigen = iCuentaService.revisarSaldo(1L);
		BigDecimal saldoCuentaDestino = iCuentaService.revisarSaldo(2L);
		assertEquals("1000", saldoCuentaOrigen.toPlainString());
		assertEquals("2000", saldoCuentaDestino.toPlainString());

		//Primer cambio, vamos a lanzar la excepción DineroInsuficienteException
		//Revisar las transferencias
		assertThrows(DineroInsuficienteException.class, ()->{
			iCuentaService.transferir(1L, 2L, new BigDecimal("1200"), 1L);
		});

		//Ahora debemos volver a revisar los saldos
		saldoCuentaOrigen = iCuentaService.revisarSaldo(1L);
		saldoCuentaDestino = iCuentaService.revisarSaldo(2L);

		//Segundo Cambio: Si lanza la excepción, no debería cambiar nada, no hace el commit..estaban antes en 900 y 2100, lo ponemos como deben estar, sin cambios
		assertEquals("1000", saldoCuentaOrigen.toPlainString());
		assertEquals("2000", saldoCuentaDestino.toPlainString());

		//Vamos a verificar cuantas veces se invocaron los métodos de cada Mock(quiere decir de cada Repositorio)
		//Verificamos para el Repositorio de Cuentas

		//Tercer Cambio: Al verificar la Excepción, cambian el verify de ID 2l, porque no se ejecutan los UPDATE
		verify(cuentaRepository, times(3)).findById(1L);
		verify(cuentaRepository, times(2)).findById(2L); //Lo cambiamos a dos veces (2), antes estaba 3
		verify(cuentaRepository, never()).save(any(Cuenta.class)); //Los update nunca se ejecutan, asi que les colocamos never en el lugar de times.



		//Revisar total de transferencias
		int totalTransferecia = iCuentaService.revisarTotalTransferencias(1L);
		assertEquals(0, totalTransferecia);

		//Verificamos para el Repositorio de Banco
		verify(bancoRepository, times(1)).findById(1L); //Lo dejamos así, porque times por defecto es una vez
		verify(bancoRepository, never()).save(any(Banco.class));

		//CORRESPONDE A LA PRUEBA (assertSame) Lo movimos del otro método
		verify(cuentaRepository, times(5)).findById(anyLong());
		verify(cuentaRepository, never()).findAll();
	}

	//SEXTO CAMBIO: Qué pasa si cambiamos el orden en el cual se ejecutan los métodos. Por ejemplo,
	//al contextLoads2() le nombramos contextLoads() y al contextLoads() le nombramos contextLoads2().
	//Falla el test, porque el otro método está modificando el saldo que el otro método está utilizando
	//en el test. Esto no debería ser así, el dato que se modifica en un método, no debería afectar al otro método.
	//Por eso los datos no deben ser estáticos (cuanta001, cuenta001, banco001).
	//Hay dos soluciones, dejemos como estaban los métodos (con sus mismos nombres).
	//Primera solución: Hacer algo (inicializar saldos de las cuentas y también
	// el total transferencia a cero(0), dentro el método setUp--ver ejemplo. Ejecutemos la prueba.
	//Segunda Solución: En lugar de usar constantes, usamos métodos estáticos. Vamos a la clase DatosDePruebas.
	//

	//Escribiendo Test con assertSame (PRUEBA NUMERO DOS DE ESTA CLASE 22 DE NOVIEMBRE 2021
	//Trabajemos con findAll(). Ahora vamos contextLoads()


	//PRUEBA NRO TRES: USO DEL assertSame (probar que sean los mismos objetos
	@Test
	void contextLoads3() {
		when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());

		Cuenta cuenta1 = iCuentaService.findById(1L);
		Cuenta cuenta2 = iCuentaService.findById(1L);

		assertSame(cuenta1, cuenta2); //Esta es la mejor opción
		assertTrue(cuenta1 == cuenta2);

		assertEquals("Lucas", cuenta1.getPersona());
		assertEquals("Lucas", cuenta2.getPersona());

		verify(cuentaRepository, times(2)).findById(1L);
	}

	
}
