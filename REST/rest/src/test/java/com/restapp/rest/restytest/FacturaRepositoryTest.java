package com.restapp.rest.restytest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.specification.Argument;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;

import static io.restassured.RestAssured.get;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest
//@Sql({"/schema.sql", "/datos.sql"})
@Sql(scripts = {"/schema.sql", "/datos.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class FacturaRepositoryTest {
    @Autowired
    private FacturaRepository facturaRepository;

    @Test
    void buscarTodasTest() {
        List<Factura> lista = facturaRepository.buscarTodas();
        assertThat(lista, hasItem(new Factura(1)));
    }
    @Test
    void buscarTodasRestTest() {
        //Probar que la URL de /facturas tiene una propiedad numero y ademas su valor es 1.
        get("http://localhost:9000/facturas").then().body("numero", hasItem(1));
    }
    @Test
    void buscarUnaFacturaRestTest() {
        //Probar que la URL de /facturas tiene una propiedad numero y ademas su valor es 1.
        get("http://localhost:9000/facturas/1").then().body("numero", equalTo(1));
    }

    @Test
    void borrarUnaFacturaRestTest() {
        //Cuando hacemos una peticion http por defecto, retornan 200 si todo esta bien
        given().pathParam("numero", 1).delete("http://localhost:9000/facturas/{numero}").then().statusCode(200);
    }
    @Test
    void insertarUnaFacturaRestTest() throws JsonProcessingException {
        Factura factura = new Factura(3, "MONITOR", 400);
        ObjectMapper mapper = new ObjectMapper();

        String objetoJSON = mapper.writeValueAsString(factura);

        given()
                .header("Content-Type", "application/json")
                .and().body(objetoJSON)
                .post("http://localhost:9000/facturas")
                .then()
                .statusCode(200);

        get("http://localhost:9000/facturas/3").then().body("concepto", equalTo("MONITOR"));
    }
    @Test
    void actualizarUnaFacturaRestTest() throws JsonProcessingException {
        Factura factura = new Factura(2, "TECLADO", 200);
        ObjectMapper mapper = new ObjectMapper();

        String objetoJSON = mapper.writeValueAsString(factura);

        given()
                .header("Content-Type", "application/json")
                .and().body(objetoJSON)
                .pathParam("numero", 2)
                .put("http://localhost:9000/facturas/{numero}")
                .then()
                .statusCode(200);

        get("http://localhost:9000/facturas/2").then().body("concepto", equalTo("TECLADO"));
    }
}