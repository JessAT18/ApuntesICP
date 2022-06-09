package com.examenfinal.upsa.jaquino.icp.controllers;

import com.examenfinal.upsa.jaquino.icp.entities.Producto;
import com.examenfinal.upsa.jaquino.icp.entities.TraspasoDTO;
import com.examenfinal.upsa.jaquino.icp.services.IProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    @Autowired
    private IProductoService iProductoService;


    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public Producto detalleProducto(@PathVariable int id){
        return iProductoService.findById(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Producto guardar(@RequestBody Producto producto) {
        return iProductoService.save(producto);
    }

    @PostMapping("/traspaso")
    public ResponseEntity<?> traspaso(@RequestBody TraspasoDTO traspasoDTO){
        iProductoService.traspasos(traspasoDTO.getNumProductoOrigen(),
                traspasoDTO.getNumProductoDestino(),
                traspasoDTO.getCantidad(),
                traspasoDTO.getSucursal_id());

        //Creamos nuestro JSON a partir de un objeto map
        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("Fecha", LocalDate.now().toString());
        respuesta.put("Estado", "Ok");
        respuesta.put("Mensaje", "Traspaso exitoso!");
        respuesta.put("Transaccion", traspasoDTO);

        return ResponseEntity.ok(respuesta);
    }
}
