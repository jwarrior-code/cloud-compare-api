package com.cloudcompare.msproject.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloudcompare.msproject.model.AddProyectoProductoRequestDTO;
import com.cloudcompare.msproject.model.Producto;
import com.cloudcompare.msproject.model.Proyecto;
import com.cloudcompare.msproject.model.ProyectoProducto;
import com.cloudcompare.msproject.repository.ProductoRepository;
import com.cloudcompare.msproject.repository.ProyectoRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MsProjectsController {

	@Autowired
	ProyectoRepository proyectoRepository;

	@Autowired
	ProductoRepository productoRepository;

	@GetMapping("/projects")
	public ResponseEntity<List<Proyecto>> getProjects(@RequestParam(defaultValue = "") String usuario) {
		try {
			List<Proyecto> proyectos = proyectoRepository.findByUsuario(usuario);
			if (proyectos.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(proyectos, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/projects")
	public ResponseEntity<Proyecto> createProject(@RequestBody Proyecto proyecto) {
		try {
			proyecto.setIdProyecto(UUID.randomUUID().toString());
			Proyecto proyectoCreado = proyectoRepository.save(proyecto);
			return new ResponseEntity<>(proyectoCreado, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/projects/{idProyecto}/products")
	public ResponseEntity<Proyecto> addProductsToProject(
			@RequestBody List<AddProyectoProductoRequestDTO> productosAgregar, @PathVariable String idProyecto) {
		try {
			Proyecto proyecto = proyectoRepository.findById(idProyecto).orElseThrow(Exception::new);
			List<ProyectoProducto> productosProyecto = proyecto.getProductos();
			productosAgregar.forEach(productoAgregar -> {
				try {
					Producto producto = productoRepository.findById(productoAgregar.getIdProducto())
							.orElseThrow(Exception::new);
					productosProyecto.add(
							new ProyectoProducto(proyecto, producto, String.valueOf(productoAgregar.getCantidad())));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			proyecto.setProductos(productosProyecto);

			proyectoRepository.save(proyecto);

			return new ResponseEntity<>(proyecto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping("/projects/{idProyecto}/products")
	public ResponseEntity<Proyecto> updateProductsOfProject(
			@RequestBody List<AddProyectoProductoRequestDTO> productosModificar, @PathVariable String idProyecto) {
		try {
			Proyecto proyecto = proyectoRepository.findById(idProyecto).orElseThrow(Exception::new);
			List<ProyectoProducto> productosProyecto = proyecto.getProductos();
			productosModificar.forEach(productoModificar -> {
				try {
					Producto producto = productoRepository.findById(productoModificar.getIdProducto())
							.orElseThrow(Exception::new);
					productosProyecto.stream()
							.filter(productoProyecto -> productoProyecto.getProducto().getIdProducto() == producto
									.getIdProducto())
							.findFirst().ifPresent(productoProyecto -> productoProyecto
									.setUnidades(String.valueOf(productoModificar.getCantidad())));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			proyecto.setProductos(productosProyecto);

			proyectoRepository.save(proyecto);

			return new ResponseEntity<>(proyecto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/projects/{idProyecto}/products")
	public ResponseEntity<Proyecto> removeProductsFromProject(
			@RequestBody List<AddProyectoProductoRequestDTO> productosRemover, @PathVariable String idProyecto) {
		try {
			Proyecto proyecto = proyectoRepository.findById(idProyecto).orElseThrow(Exception::new);
			List<ProyectoProducto> productosProyecto = proyecto.getProductos();
			productosRemover.forEach(productoAgregar -> {
				try {
					Producto producto = productoRepository.findById(productoAgregar.getIdProducto())
							.orElseThrow(Exception::new);
					productosProyecto.remove(new ProyectoProducto(proyecto, producto));
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
			proyecto.setProductos(productosProyecto);

			proyectoRepository.save(proyecto);

			return new ResponseEntity<>(proyecto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/projects/{idProyecto}")
	public ResponseEntity<Proyecto> removeProject(@PathVariable String idProyecto) {
		try {
			Proyecto proyecto = proyectoRepository.findById(idProyecto).orElseThrow(Exception::new);

			proyectoRepository.delete(proyecto);

			return new ResponseEntity<>(proyecto, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
