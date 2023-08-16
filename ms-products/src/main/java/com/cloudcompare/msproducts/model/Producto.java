package com.cloudcompare.msproducts.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

	@ManyToOne(optional = false, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Categoria categoria;
	
	@OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProductoCaracteristica> caracteristicas = new ArrayList<>();

	public Producto() {
	}

	public Producto(String nombreProducto, String descripcion) {
		this.nombreProducto = nombreProducto;
		this.descripcion = descripcion;
	}
	
	public void addCaracteristica(Caracteristica caracteristica) {
        ProductoCaracteristica productoCaracteristica = new ProductoCaracteristica(this, caracteristica);
        caracteristicas.add(productoCaracteristica);
        caracteristica.getProductos().add(productoCaracteristica);
    }
 
    public void Caracteristica(Caracteristica caracteristica) {
        for (Iterator<ProductoCaracteristica> iterator = caracteristicas.iterator();
             iterator.hasNext(); ) {
        	ProductoCaracteristica productoCaracteristica = iterator.next();
             
            if (productoCaracteristica.getProducto().equals(this) &&
                    productoCaracteristica.getCaracteristica().equals(caracteristica)) {
                iterator.remove();
                productoCaracteristica.getCaracteristica().getProductos().remove(productoCaracteristica);
                productoCaracteristica.setProducto(null);
                productoCaracteristica.setCaracteristica(null);
            }
        }
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

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<ProductoCaracteristica> getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(List<ProductoCaracteristica> caracteristicas) {
		this.caracteristicas = caracteristicas;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Producto)) return false;
        return idProducto != null && idProducto.equals(((Producto) o).getIdProducto());
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
