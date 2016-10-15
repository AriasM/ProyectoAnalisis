package com.peditualmuerzo.dominio;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
	private int idPedido;
	private float precio;
	private List<ItemPedido> items;
	
	public Pedido() {
		super();
		this.items = new ArrayList<ItemPedido>();
	}
	
	public int getIdPedido() {
		return idPedido;
	}
	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}
	public float getPrecio() {
		return precio;
	}
	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public List<ItemPedido> getItems() {
		return items;
	}

	public void setItems(List<ItemPedido> items) {
		this.items = items;
	}
	
	
}
