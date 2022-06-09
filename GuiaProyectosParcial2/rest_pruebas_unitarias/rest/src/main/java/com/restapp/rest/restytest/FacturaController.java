package com.restapp.rest.restytest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private FacturaRepository facturaRepository;

    @GetMapping
    public List<Factura> buscarTodas() {
        return facturaRepository.buscarTodas();
    }

    @GetMapping("/{numero}")
    public Factura buscarUnaFactura(@PathVariable("numero") int numero) {
        return facturaRepository.buscarUnaFactura(numero);
    }

    @DeleteMapping("/{numero}")
    public void borrarFactura(Factura factura) {
        facturaRepository.borrarFactura(factura);
    }



}

