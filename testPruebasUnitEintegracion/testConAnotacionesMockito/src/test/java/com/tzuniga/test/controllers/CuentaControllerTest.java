package com.tzuniga.test.controllers;

import static  com.tzuniga.test.DatosDePruebas.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tzuniga.test.entities.TransaccionDTO;
import com.tzuniga.test.services.ICuentaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CuentaController.class) //Implementar un MVC TEST para CuentaController
class CuentaControllerTest {

    //Inyectar componentes
    //Recordar que todo lo que vamos a probar es completamente falso
    //Todo es simulado
    //Es un contexto MVC, pero falso.

    @Autowired
    private MockMvc mvc;

    //Recuerden que el controldor depende de cuenta service.
    @MockBean
    private ICuentaService service;


    ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void testDetalleCuenta() throws Exception {
        //Este es nuestro contexto - nuestro given.
        when(service.findById(1L)).thenReturn(crearCuenta001().orElseThrow());

        //Nuestro when
        mvc.perform(MockMvcRequestBuilders.get("/api/cuentas/1").contentType(MediaType.APPLICATION_JSON))
                //then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.persona").value("Lucas"))
                .andExpect(jsonPath("$.saldo").value("1000"));

        verify(service).findById(1L);
    }

    @Test
    void testTransferir() throws Exception {

        //Nuestro contexto Given
        TransaccionDTO dto = new TransaccionDTO();
        dto.setCuentaOrigenId(1L);
        dto.setCuentaDestinoId(2L);
        dto.setMonto(new BigDecimal("100"));
        dto.setBancoId(1L);

        //Nuestro contexto when
        mvc.perform(MockMvcRequestBuilders.post("/api/cuentas/transferir")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))

        // Nuestro conexto then
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.Fecha").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$.Mensaje").value("Transferencia exitosa!!!"))
                .andExpect(jsonPath("$.Transaccion.cuentaOrigenId").value(dto.getCuentaOrigenId()));
        //nuestro contexto verify
    }
}