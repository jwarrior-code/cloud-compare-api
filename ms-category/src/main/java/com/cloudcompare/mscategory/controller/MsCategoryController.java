package com.cloudcompare.mscategory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloudcompare.mscategory.model.Categoria;
import com.cloudcompare.mscategory.repository.CategoriaRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MsCategoryController {
	
	@Autowired
	CategoriaRepository categoriaRepository;
	
	@GetMapping("/categories")
	public ResponseEntity<List<Categoria>> getCategories() {
		try {
		      List<Categoria> categorias = categoriaRepository.findAll();
		      
		      if (categorias.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		      }

		      return new ResponseEntity<>(categorias, HttpStatus.OK);
		    } catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}

}
