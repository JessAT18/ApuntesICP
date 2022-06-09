package com.tzuniga.test.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tzuniga.test.entities.Cuenta;
import com.tzuniga.test.entities.TransaccionDTO;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*;

import static org.junit.jupiter.api.Assertions.*;

//Lo covertimos a static
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest(webEnvironment = RANDOM_PORT)

//Cuando se ejecutan pruebas de integración, se debe ordenar los métodos de prueba
//para que la ocurrencia de un método no cambia los valores de los otros métodos.
// Para esto se usa la anotación @TestMethodOrder
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CuentaControllerWebTestClienteTests {

    //Inyectar el WebTestClient
    @Autowired
    private WebTestClient webTestClient;

    //Este se crea para probar el json completo
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    @Order(1)
    void transferirTest() throws JsonProcessingException {
        //Contexto Given
        TransaccionDTO dto = new TransaccionDTO();

        dto.setCuentaOrigenId(1L);
        dto.setCuentaDestinoId(2L);
        dto.setBancoId(1L);
        dto.setMonto(new BigDecimal("100"));

        //Para probare el json completo (vamos al controller y copiamos esta parte)
        //Creamos nuestro JSON a partir de un objeto map
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("Fecha", LocalDate.now().toString());
        respuesta.put("Estado", "Ok");
        respuesta.put("Mensaje", "Transferencia exitosa!!!");
        respuesta.put("Transaccion", dto);

        //Contexto when
        webTestClient.post().uri("/api/cuentas/transferir")
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
                        assertEquals("Transferencia exitosa!!!", json.path("Mensaje").asText());
                        assertEquals(1L, json.path("Transaccion").path("cuentaOrigenId").asLong());
                        assertEquals(LocalDate.now().toString(), json.path("Fecha").asText());
                        assertEquals("100", json.path("Transaccion").path("monto").asText());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                })
                //Seguna Forma
                .jsonPath("$.Mensaje").isNotEmpty()
                .jsonPath("$.Mensaje").value(is("Transferencia exitosa!!!"))
                .jsonPath("$.Mensaje").value(valor -> assertEquals("Transferencia exitosa!!!", valor))
                .jsonPath("$.Mensaje").isEqualTo("Transferencia exitosa!!!")
                .jsonPath("$.Transaccion.cuentaOrigenId").isEqualTo(dto.getCuentaOrigenId())
                .jsonPath("$.Fecha").isEqualTo(LocalDate.now().toString())

                //Como es una prueba de integración, primero debemos levantar el proyecto.
                .json(objectMapper.writeValueAsString(respuesta));
    }

    @Test
    @Order(2)
    void detalleCuentaTest() { //Primera Forma

        webTestClient.get().uri("/api/cuentas/1").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.persona").isEqualTo("Lucas")
                .jsonPath("$.saldo").isEqualTo(900);
    }

    @Test
    @Order(3)
    void detalleCuentaTest2() { //Segunda Forma

        webTestClient.get().uri("/api/cuentas/2").exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Cuenta.class)
                .consumeWith(resp -> {
                    Cuenta cuenta = resp.getResponseBody();
                    assertEquals("Ana", cuenta.getPersona());
                    assertEquals("2100.00", cuenta.getSaldo().toPlainString());
                });

    }

    @Test
    @Order(4)
    void grabarCuentaTest() {

        //Given
        Cuenta cuenta = new Cuenta(null, "Maria", new BigDecimal("3000"));

        //When
        webTestClient.post().uri("/api/cuentas")
                .contentType(MediaType.APPLICATION_JSON) //Configuración del json
                .bodyValue(cuenta)
                .exchange()
                //Then
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id").isEqualTo(3)
                .jsonPath("$.persona").isEqualTo("Maria")
                .jsonPath("$.saldo").isEqualTo(3000);
    }
}