package com.tzuniga.test.controllers;

import static  org.springframework.http.HttpStatus.*;
import com.tzuniga.test.entities.Cuenta;
import com.tzuniga.test.entities.TransaccionDTO;
import com.tzuniga.test.services.ICuentaService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {
    @Autowired
    private ICuentaService iCuentaService;

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Cuenta detalleCuenta(@PathVariable Long id){
        return iCuentaService.findById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Cuenta guardar(@RequestBody Cuenta cuenta) {
        return iCuentaService.save(cuenta);
    }

    @PostMapping("/transferir")
    public ResponseEntity<?> transferir(@RequestBody TransaccionDTO transaccionDTO){
        iCuentaService.transferir(transaccionDTO.getCuentaOrigenId(),
                transaccionDTO.getCuentaDestinoId(),
                transaccionDTO.getMonto(),
                transaccionDTO.getBancoId());

        //Creamos nuestro JSON a partir de un objeto map
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("Fecha", LocalDate.now().toString());
        respuesta.put("Estado", "Ok");
        respuesta.put("Mensaje", "Transferencia exitosa!!!");
        respuesta.put("Transaccion", transaccionDTO);

        return ResponseEntity.ok(respuesta);
    }
}
