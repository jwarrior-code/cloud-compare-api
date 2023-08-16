package com.cloudcompare.msproducts.model;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "ProductoCaracteristica")
@Table(name = "producto_caracteristica")
@ToString
public class ProductoCaracteristica {
	@EmbeddedId
	@JsonIgnore
    private ProductoCaracteristicaId id;
 
	@JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idProducto")
    private Producto producto;
 
	//@JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("idCaracteristica")
    private Caracteristica caracteristica;
 
    @Column(name = "valor")
    private String valor;
 
    private ProductoCaracteristica() {}
 
    public ProductoCaracteristica(Producto producto, Caracteristica caracteristica) {
        this.producto = producto;
        this.caracteristica = caracteristica;
        this.id = new ProductoCaracteristicaId(producto.getIdProducto(), caracteristica.getIdCaracteristica());
    }
 
    public ProductoCaracteristicaId getId() {
		return id;
	}

	public void setId(ProductoCaracteristicaId id) {
		this.id = id;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Caracteristica getCaracteristica() {
		return caracteristica;
	}

	public void setCaracteristica(Caracteristica caracteristica) {
		this.caracteristica = caracteristica;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
 
        if (o == null || getClass() != o.getClass())
            return false;
 
        ProductoCaracteristica that = (ProductoCaracteristica) o;
        return Objects.equals(producto, that.producto) &&
               Objects.equals(caracteristica, that.caracteristica);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(producto, caracteristica);
    }
}
