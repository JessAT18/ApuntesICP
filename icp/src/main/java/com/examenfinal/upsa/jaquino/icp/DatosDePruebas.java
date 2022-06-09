package com.examenfinal.upsa.jaquino.icp;

import com.examenfinal.upsa.jaquino.icp.entities.Producto;
import com.examenfinal.upsa.jaquino.icp.entities.Sucursal;

import java.math.BigDecimal;
import java.util.Optional;

public class DatosDePruebas {
    /*
    INSERT INTO productos (nombre_producto, stock) VALUES ('Computadora S1', 1000);
    INSERT INTO productos (nombre_producto, stock) VALUES ('Computadora S2', 2000);
    INSERT INTO sucursales (nombre, nro_traspasos) VALUES ('Aquino Torrez', 0);*/

    public static Optional<Producto> crearProducto001 (){
        return Optional.of(new Producto(11, "Computadora S1", 1000));
    }

    public static Optional<Producto> crearProducto002 (){
        return Optional.of(new Producto(2, "Computadora S2", 2000));
    }

    public  static Optional<Sucursal> crearSucursal001 (){
        return  Optional.of(new Sucursal(1,"Aquino Torrez", 0));
    }
}