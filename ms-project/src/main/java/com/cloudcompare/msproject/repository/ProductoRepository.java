package com.cloudcompare.msproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloudcompare.msproject.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	

}
