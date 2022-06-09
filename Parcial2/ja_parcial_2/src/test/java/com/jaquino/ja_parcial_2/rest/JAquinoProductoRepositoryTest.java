package com.jaquino.ja_parcial_2.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;


import static io.restassured.RestAssured.given;

import static io.restassured.RestAssured.get;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Sql(scripts = {"/schema.sql", "/datos.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class JAquinoProductoRepositoryTest {
    @Test
    void JAquinoGETbuscarTodosLosProductosTest() {
        //1. Probar que la URL de /productos tiene una propiedad “codProducto” y que además su valor es 1.
        get("http://localhost:9000/productos").then().body("codigo", hasItem(1));
    }

    @Test
    void JAquinoDELETEborrarUnProductoTest(){
        //2. Que, al eliminar un producto, retorne un código de estado 200, indicando que el registro fue eliminado con éxito.
        given().pathParam("codigo", 1).delete("http://localhost:9000/productos/{codigo}").then().statusCode(200);
    }

    @Test
    void JAquinoPOSTinsertarUnProductoTest() throws JsonProcessingException {
        //3. Que, al insertar un producto, retorne un código de estado 200, indicando que el registro fue registrado con éxito;
        //y además pruebe también que el registro agregado es el correcto, tomando como valor de prueba el nombre del producto.
        Producto producto = new Producto(6, "ESCRITORIO", 500, 650);
        ObjectMapper mapper = new ObjectMapper();

        String objetoJSON = mapper.writeValueAsString(producto);

        given()
                .header("Content-Type", "application/json")
                .and().body(objetoJSON)
                .post("http://localhost:9000/productos/")
                .then()
                .statusCode(200);

        get("http://localhost:9000/productos/6").then().body("nombre", equalTo("ESCRITORIO"));
    }
    @Test
    void JAquinoPUTactualizarUnUnProductoTest() throws JsonProcessingException {
        //4. Que, al actualizar un producto, retorne un código de estado 200, indicando que el registro fue actualizado con éxito;
        // y además pruebe también que el registro actualizado es el correcto, tomando como valor de prueba el nombre del producto.
        Producto producto = new Producto(2, "DISCO DURO ACTUALIZADO", 300, 450);
        ObjectMapper mapper = new ObjectMapper();

        String objetoJSON = mapper.writeValueAsString(producto);

        given()
                .header("Content-Type", "application/json")
                .and().body(objetoJSON)
                .pathParam("codigo", 2)
                .put("http://localhost:9000/productos/{codigo}")
                .then()
                .statusCode(200);

        get("http://localhost:9000/productos/2").then().body("nombre", equalTo("DISCO DURO ACTUALIZADO"));
    }

    @Test
    void JAquinoCostoMenorAlPrecioTest() {
        //5. Que, el costo del producto no sea mayor al precio de venta del producto.
        //Ref: https://www.toolsqa.com/rest-assured/read-json-response-body-using-rest-assured/
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("http://localhost:9000/productos/1");

        JsonPath jsonPathEvaluator = response.jsonPath();

        float precio = jsonPathEvaluator.get("precio");
        float costo = jsonPathEvaluator.get("costo");
        assertTrue(costo <= precio, "El costo del producto es mayor al precio de venta");
    }
    @Test
    void JAquinoPrecioMayorACeroTest(){
        //6. Que, el precio de venta del producto sea mayor que cero.
        get("http://localhost:9000/productos/1").then().body("precio", greaterThan(0.0F));
    }
}

