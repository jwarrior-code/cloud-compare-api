package com.cloudcompare.msproducts.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloudcompare.msproducts.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	

}
