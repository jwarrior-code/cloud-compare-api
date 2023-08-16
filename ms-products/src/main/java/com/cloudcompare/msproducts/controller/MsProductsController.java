package com.cloudcompare.msproducts.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloudcompare.msproducts.model.Producto;
import com.cloudcompare.msproducts.repository.ProductoRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MsProductsController {
	
	@Autowired
	ProductoRepository productoRepository;
	
	@GetMapping("/products")
	public ResponseEntity<List<Producto>> getProducts(@RequestParam(defaultValue = "") String filters) {
		try {
		      List<Producto> productos = productoRepository.findAll();
//
//		      productoRepository.findAll().forEach(productos::add);
//		      productos.forEach(producto->System.out.println(producto.toString()));
		      if (productos.isEmpty()) {
		        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		      }

		      return new ResponseEntity<>(productos, HttpStatus.OK);
		    } catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		    }
	}

}
