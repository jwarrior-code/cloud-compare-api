package com.cloudcompare.msproject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cloudcompare.msproject.model.Proyecto;

public interface ProyectoRepository extends JpaRepository<Proyecto, String> {
	
	List<Proyecto> findByUsuario(String usuario);
}
