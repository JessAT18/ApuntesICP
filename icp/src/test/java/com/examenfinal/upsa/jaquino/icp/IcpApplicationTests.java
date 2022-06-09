package com.examenfinal.upsa.jaquino.icp;

import com.examenfinal.upsa.jaquino.icp.entities.Producto;
import com.examenfinal.upsa.jaquino.icp.entities.Sucursal;
import com.examenfinal.upsa.jaquino.icp.exceptions.StockInsuficienteException;
import com.examenfinal.upsa.jaquino.icp.repositories.IProductoRepository;
import com.examenfinal.upsa.jaquino.icp.repositories.ISucursalRepository;
import com.examenfinal.upsa.jaquino.icp.services.IProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;

import static com.examenfinal.upsa.jaquino.icp.DatosDePruebas.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class IcpApplicationTests {
	@MockBean
	IProductoRepository ProductoRepository;

	@MockBean
	ISucursalRepository SucursalRepository;

	@Autowired
	IProductoService iProductoService;

	@Test
	void contextLoads() {
		//PREGUNTA 1
		//P1.1 Verificar stock origen antes de realizar el traspaso

		when(ProductoRepository.findById(1)).thenReturn(crearProducto001());
		when(ProductoRepository.findById(2)).thenReturn(crearProducto002());

		when(SucursalRepository.findById(1)).thenReturn(crearSucursal001());

		int stockProductoOrigen = iProductoService.revisarStock(1);
		int stockProductoDestino = iProductoService.revisarStock(2);
		assertEquals(1000, stockProductoOrigen);
		assertEquals(2000, stockProductoDestino);

		//P1.2 Verificar que el traspaso se haya realizado correctamente,
		//considerando que existe una salida de stock que incrementara al
		//mismo producto que se encuentra en una sucursal especifica

		iProductoService.traspasos(1, 2, 100, 1);

		stockProductoOrigen = iProductoService.revisarStock(1);
		stockProductoDestino = iProductoService.revisarStock(2);
		assertEquals(900, stockProductoOrigen);
		assertEquals(2100, stockProductoDestino);

		//P1.3 Verificar el numero de veces que se invoca a un metodo considerando
		//P1.1 y P1.2
		//Repositorio de Productos
		verify(ProductoRepository, times(3)).findById(1);
		verify(ProductoRepository, times(3)).findById(2);
		verify(ProductoRepository, times(2)).save(any(Producto.class));

		//Repositorio de Banco
		verify(SucursalRepository).findById(1);
		verify(SucursalRepository).save(any(Sucursal.class));

		//P1.4 Verificar que el total de traspasos se haya actualizado correctamente
		//en la sucursal
		int totalTraspasos = iProductoService.revisarTotalTraspasos(1);
		assertEquals(1, totalTraspasos);

		//P1.5 Verificar que se lanza la excepción cuando se quiere realizar una salida
		//del stock de un producto mayor al existente

		assertThrows(StockInsuficienteException.class, ()->{
			iProductoService.traspasos(1, 2, 1200, 1);
		});
		assertEquals(900, stockProductoOrigen);
		assertEquals(2100, stockProductoDestino);
	}
}
