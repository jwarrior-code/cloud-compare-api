package com.cloudcompare.msproject.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity(name = "Proyecto")
@Table(name = "proyecto")
public class Proyecto {
	@Id
	@Column(name = "idProyecto")
	private String idProyecto;

	@Column(name = "nombreProyecto")
	private String nombreProyecto;

	@Column(name = "usuario")
	private String usuario;

	@OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ProyectoProducto> productos = new ArrayList<>();

	public Proyecto() {
	}

	public Proyecto(String idProyecto, String nombreProyecto, String usuario) {
		this.idProyecto = idProyecto;
		this.nombreProyecto = nombreProyecto;
		this.usuario = usuario;
	}

	public String getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(String idProyecto) {
		this.idProyecto = idProyecto;
	}

	public String getNombreProyecto() {
		return nombreProyecto;
	}

	public void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	
	public List<ProyectoProducto> getProductos() {
		return productos;
	}

	public void setProductos(List<ProyectoProducto> productos) {
		this.productos = productos;
	}

	public void addProducto(Producto producto) {
        ProyectoProducto proyectoProducto = new ProyectoProducto(this, producto);
        productos.add(proyectoProducto);
        producto.getProyectos().add(proyectoProducto);
    }
 
    public void Producto(Producto producto) {
        for (Iterator<ProyectoProducto> iterator = productos.iterator();
             iterator.hasNext(); ) {
        	ProyectoProducto proyectoProducto = iterator.next();
             
            if (proyectoProducto.getProyecto().equals(this) &&
                    proyectoProducto.getProducto().equals(producto)) {
                iterator.remove();
                proyectoProducto.getProducto().getProyectos().remove(proyectoProducto);
                proyectoProducto.setProyecto(null);
                proyectoProducto.setProducto(null);
            }
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Proyecto)) return false;
        return idProyecto != null && idProyecto.equals(((Proyecto) o).getIdProyecto());
    }
 
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
	
}
