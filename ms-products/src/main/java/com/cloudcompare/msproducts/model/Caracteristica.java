package com.cloudcompare.msproducts.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.ToString;

@Entity(name="Caracteristica")
@Table(name = "caracteristica")
@NaturalIdCache
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@ToString
public class Caracteristica {
	@Id
	@Column(name = "idCaracteristica")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idCaracteristica;

	@Column(name = "nombreCaracteristica")
	@NaturalId
	private String nombreCaracteristica;
	
	@JsonIgnore
	@OneToMany(mappedBy = "caracteristica", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductoCaracteristica> productos = new ArrayList<>();

	public Caracteristica() {
	    }

	public Caracteristica(String nombreCaracteristica) {
	        this.nombreCaracteristica = nombreCaracteristica;
	    }
	
	public Integer getIdCaracteristica() {
		return idCaracteristica;
	}

	public void setIdCaracteristica(Integer idCaracteristica) {
		this.idCaracteristica = idCaracteristica;
	}

	public String getNombreCaracteristica() {
		return nombreCaracteristica;
	}

	public void setNombreCaracteristica(String nombreCaracteristica) {
		this.nombreCaracteristica = nombreCaracteristica;
	}

	public List<ProductoCaracteristica> getProductos() {
		return productos;
	}

	public void setProductos(List<ProductoCaracteristica> productos) {
		this.productos = productos;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Caracteristica caracteristica = (Caracteristica) o;
		return Objects.equals(nombreCaracteristica, caracteristica.nombreCaracteristica);
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombreCaracteristica);
	}
}
