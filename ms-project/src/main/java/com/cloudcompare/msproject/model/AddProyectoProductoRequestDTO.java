package com.cloudcompare.msproject.model;

public class AddProyectoProductoRequestDTO {
	private Integer idProducto;
	private Integer cantidad;
	
	public Integer getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
}
