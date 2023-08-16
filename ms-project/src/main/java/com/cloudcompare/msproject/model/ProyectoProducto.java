package com.cloudcompare.msproject.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.ToString;

@Entity(name = "ProyectoProducto")
@Table(name = "proyecto_producto")
@ToString
public class ProyectoProducto {
	@EmbeddedId
	@JsonIgnore
    private ProyectoProductoId id;
 
	@JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idProyecto")
    private Proyecto proyecto;
 
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idProducto")
    private Producto producto;
 
    @Column(name = "unidades")
    private String unidades;
 
    private ProyectoProducto() {}
 
    public ProyectoProducto(Proyecto proyecto, Producto producto) {
        this.proyecto = proyecto;
        this.producto = producto;
        this.id = new ProyectoProductoId(proyecto.getIdProyecto(), producto.getIdProducto());
    }
    public ProyectoProducto(Proyecto proyecto, Producto producto,String unidades) {
    	this.proyecto = proyecto;
    	this.producto = producto;
    	this.unidades = unidades;
    	this.id = new ProyectoProductoId(proyecto.getIdProyecto(), producto.getIdProducto());
    }
 
    public ProyectoProductoId getId() {
		return id;
	}

	public void setId(ProyectoProductoId id) {
		this.id = id;
	}

	

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public String getUnidades() {
		return unidades;
	}

	public void setUnidades(String unidades) {
		this.unidades = unidades;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        ProyectoProducto that = (ProyectoProducto) o;
        return Objects.equals(proyecto, that.proyecto) &&
               Objects.equals(producto, that.producto);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(proyecto, producto);
    }
}
