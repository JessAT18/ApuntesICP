package com.examenfinal.upsa.jaquino.icp.repositories;

import com.examenfinal.upsa.jaquino.icp.entities.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductoRepository extends JpaRepository<Producto, Integer> {
}
