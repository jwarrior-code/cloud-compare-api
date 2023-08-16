package com.cloudcompare.msproducts.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
public class ProductoCaracteristicaId implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1467683067848308750L;

	@Column(name = "producto_idProducto")
	@Getter
	@Setter
	private Integer idProducto;

	@Column(name = "caracteristica_idCaracteristica")
	@Getter
	@Setter
	private Integer idCaracteristica;

	private ProductoCaracteristicaId() {
	}

	public ProductoCaracteristicaId(int idProducto, int idCaracteristica) {
		this.idProducto = idProducto;
		this.idCaracteristica = idCaracteristica;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        ProductoCaracteristicaId that = (ProductoCaracteristicaId) o;
        return Objects.equals(idProducto, that.idProducto) &&
               Objects.equals(idCaracteristica, that.idCaracteristica);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(idProducto, idCaracteristica);
    }
}
