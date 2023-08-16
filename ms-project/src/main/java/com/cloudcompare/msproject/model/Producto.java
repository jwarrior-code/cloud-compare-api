package com.cloudcompare.msproject.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "Producto")
@Table(name = "producto")
public class Producto {
	@Id
	@Column(name = "idProducto")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idProducto;

	@Column(name = "nombreProducto")
	private String nombreProducto;

	@Column(name = "descripcion")
	private String descripcion;

	@JsonIgnore
	@OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProyectoProducto> proyectos = new ArrayList<>();

	public Producto() {
	}

	public Producto(String nombreProducto, String descripcion) {
		this.nombreProducto = nombreProducto;
		this.descripcion = descripcion;
	}
	
	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public String getNombreProducto() {
		return nombreProducto;
	}

	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<ProyectoProducto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(List<ProyectoProducto> proyectos) {
		this.proyectos = proyectos;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Producto producto = (Producto) o;
		return Objects.equals(nombreProducto, producto.nombreProducto);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombreProducto);
	}
}
