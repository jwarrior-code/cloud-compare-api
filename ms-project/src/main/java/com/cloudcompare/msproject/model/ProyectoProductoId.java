package com.cloudcompare.msproject.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
public class ProyectoProductoId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1141574663282070036L;

	@Column(name = "proyecto_idProyecto")
	@Getter
	@Setter
	private String idProyecto;

	@Column(name = "producto_idProducto")
	@Getter
	@Setter
	private Integer idProducto;

	private ProyectoProductoId() {
	}

	public ProyectoProductoId(String idProyecto, int idProducto) {
		this.idProyecto = idProyecto;
		this.idProducto = idProducto;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        ProyectoProductoId that = (ProyectoProductoId) o;
        return Objects.equals(idProyecto, that.idProyecto) &&
               Objects.equals(idProducto, that.idProducto);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(idProyecto, idProducto);
    }
}
