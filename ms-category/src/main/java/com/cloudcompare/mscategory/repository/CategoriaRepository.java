package com.cloudcompare.mscategory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloudcompare.mscategory.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
	

}
