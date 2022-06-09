package com.examenfinal.upsa.jaquino.icp.controllers;

import com.examenfinal.upsa.jaquino.icp.entities.Producto;
import com.examenfinal.upsa.jaquino.icp.entities.TraspasoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductoControllerWEB {

    //Inyectar el WebTestClient
    @Autowired
    private WebTestClient webTestClient;

    //Este se crea para probar el json completo
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    //PREGUNTA 2

    @Test
    @Order(1)
    void detalleProductoTest() {
        //P2.1 Probar el metodo detalleProducto con busqueda OK, con el body recuperando nombre_producto y stock,
        webTestClient.get().uri("/api/productos/1").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.nombre_producto").isEqualTo("Computadora S1")
                .jsonPath("$.stock").isEqualTo(1000);
    }

    @Test
    @Order(2)
    void traspasoTest() throws JsonProcessingException {
        //P2.2 Usando TraspasoDTO realizar el test para el proceo, donde se espera que contentType
        //sea una aplicacion JSON, el valor del body sea el objeto definido en traspasoDTO
        //que el traspaso sea Ok
        //que el cuerpo del body aparezca
        //i. Que el mensaje sea igual al mensaje recibido cuando el traspaso es exitoso.
        //ii. Que numProductoorigen sea igual al definido en el valor esperado
        //iii. Que numProductoDestino sea igual al definido en el valor esperado
        //iv. Que la cantidad esperada sea igual al valor que se traspasa
        //v. Que la fecha del traspaso sea igual a la fecha actual del dia


        //Contexto Given
        TraspasoDTO dto = new TraspasoDTO();

        dto.setNumProductoOrigen(1);
        dto.setNumProductoDestino(2);
        dto.setSucursal_id(1);
        dto.setCantidad(100);

        //Creamos nuestro JSON a partir de un objeto MAP
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("Fecha", LocalDate.now().toString());
        respuesta.put("Estado", "Ok");
        respuesta.put("Mensaje", "Traspaso exitoso!");
        respuesta.put("Transaccion", dto);

        //Contexto when
        webTestClient.post().uri("/api/productos/traspaso")
                .contentType(MediaType.APPLICATION_JSON) //Configuración del request
                .bodyValue(dto)
                .exchange()
                //Contexto then
                .expectStatus().isOk()
                .expectBody()
                //Primera Forma
                .consumeWith(resp -> {
                    try {
                        JsonNode json = objectMapper.readTree(resp.getResponseBody());
                        assertEquals("Traspaso exitoso!", json.path("Mensaje").asText());
                        assertEquals(1, json.path("Transaccion").path("numProductoOrigen").asInt());
                        assertEquals(LocalDate.now().toString(), json.path("Fecha").asText());
                        assertEquals("100", json.path("Transaccion").path("cantidad").asText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
                //Segunda Forma
                .jsonPath("$.Mensaje").isNotEmpty()
                .jsonPath("$.Mensaje").value(is("Traspaso exitoso!"))
                .jsonPath("$.Mensaje").value(valor -> assertEquals("Traspaso exitoso!", valor))
                .jsonPath("$.Mensaje").isEqualTo("Traspaso exitoso!")
                .jsonPath("$.Transaccion.numProductoOrigen").isEqualTo(dto.getNumProductoOrigen())
                .jsonPath("$.Fecha").isEqualTo(LocalDate.now().toString())

                //Como es una prueba de integración, primero debemos levantar el proyecto.
                .json(objectMapper.writeValueAsString(respuesta));
    }
    @Test
    @Order(3)
    void grabarProductoTest() {
        //P2.3 Crear un metodo para grabarProducto. Se espera que nos de el valor del registro creado
        //y que los valores registrados sean identicos a los valores de prueba.

        //Given
        Producto producto = new Producto(0, "Mouse", 3000);

        //When
        webTestClient.post().uri("/api/productos")
                .contentType(MediaType.APPLICATION_JSON) //Configuración del json
                .bodyValue(producto)
                .exchange()
                //Then
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(3)
                .jsonPath("$.nombre_producto").isEqualTo("Mouse")
                .jsonPath("$.stock").isEqualTo(3000);
    }
}
