package com.peditualmuerzo.dominio;

import java.util.ArrayList;
import java.util.List;

public class ItemPedido {
	private int cantidad;
	private String comentarios;
	private List<Plato> platos;
	
	public ItemPedido() {
		super();
		this.platos = new ArrayList<Plato>();
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public List<Plato> getPlatos() {
		return platos;
	}

	public void setPlatos(List<Plato> platos) {
		this.platos = platos;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
}
