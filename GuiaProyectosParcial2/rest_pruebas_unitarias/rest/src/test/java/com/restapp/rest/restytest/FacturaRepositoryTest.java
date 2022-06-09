package com.restapp.rest.restytest;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.jupiter.api.Assertions.*;

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
    void buscarTodasRESTTest() {
        //Probar que la url de /facturas tiene una propiedad numero y ademas su valor es 1.
        get("http://localhost:9000/facturas").then().body("numero", hasItem(1));
    }

    @Test
    void buscarUnaRESTTest() {
        //Probar que la url de /facturas tiene una propiedad numero y ademas su valor es 1.
        get("http://localhost:9000/facturas/1").then().body("numero", equalTo(1));
    }

    @Test
    void borrarUnaRESTTest() {
        given().pathParam("numero", 1).delete("http://localhost:9000/facturas/{numero}").then().statusCode(200);
    }




}