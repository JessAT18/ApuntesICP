package com.examenfinal.upsa.jaquino.icp.controllers;


import com.examenfinal.upsa.jaquino.icp.entities.Producto;
import com.examenfinal.upsa.jaquino.icp.entities.TraspasoDTO;
import com.examenfinal.upsa.jaquino.icp.services.IProductoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.print.attribute.standard.Media;
import java.time.LocalDate;

import static com.examenfinal.upsa.jaquino.icp.DatosDePruebas.crearProducto001;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class) //Implementar un MVC TEST para CuentaController
class ProductoControllerTest {

    //Inyectar el WebTestClient
    @Autowired
    private MockMvc mvc;

    @MockBean
    private IProductoService service;

    //Este se crea para probar el json completo
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    //PREGUNTA 2

    @Test
    void detalleProductoTest() throws Exception {
        //P2.1 Probar el metodo detalleProducto con busqueda OK, con el body recuperando nombre_producto y stock,

        //Este es nuestro contexto - nuestro given.
        when(service.findById(1)).thenReturn(crearProducto001().orElseThrow());

        //Nuestro when
        mvc.perform(MockMvcRequestBuilders.get("/api/productos/1").contentType(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre_producto").value("Computadora S1"))
                .andExpect(jsonPath("$.stock").value(1000));

        verify(service).findById(1);
    }

    @Test
    void traspasoTest() throws Exception {
        //P2.2 Usando TraspasoDTO realizar el test para el proceo, donde se espera que contentType
        //sea una aplicacion JSON, el valor del body sea el objeto definido en traspasoDTO
        //que el traspaso sea Ok
        //que el cuerpo del body aparezca
        //i. Que el mensaje sea igual al mensaje recibido cuando el traspaso es exitoso.
        //ii. Que numProductoorigen sea igual al definido en el valor esperado
        //iii. Que numProductoDestino sea igual al definido en el valor esperado
        //iv. Que la cantidad esperada sea igual al valor que se traspasa
        //v. Que la fecha del traspaso sea igual a la fecha actual del dia

        //Nuestro contexto Given
        TraspasoDTO dto = new TraspasoDTO();
        dto.setNumProductoOrigen(1);
        dto.setNumProductoDestino(2);
        dto.setCantidad(100);
        dto.setSucursal_id(1);

        //Nuestro contexto when
        mvc.perform(MockMvcRequestBuilders.post("/api/productos/traspaso")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                // Nuestro contexto then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.Fecha").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.Mensaje").value("Traspaso exitoso!"))
                .andExpect(jsonPath("$.Transaccion.numProductoOrigen").value(dto.getNumProductoOrigen()))
                .andExpect(jsonPath("$.Transaccion.numProductoDestino").value(dto.getNumProductoDestino()))
                .andExpect(jsonPath("$.Transaccion.cantidad").value(dto.getCantidad()))
                .andExpect(jsonPath("$.Transaccion.sucursal_id").value(dto.getSucursal_id()));
        //nuestro contexto verify

    }
}