package com.peditualmuerzo.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemPedido implements Serializable{
	private int cantidad;
	private String comentarios;
	private Plato plato;
	private float precioPlatillo;

	public float getPrecioPlatillo() {
		return precioPlatillo;
	}

	public void setPrecioPlatillo(float precioPlatillo) {
		this.precioPlatillo = precioPlatillo;
	}

	public ItemPedido() {
		this.plato = new Plato();
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public Plato getPlato() {
		return plato;
	}

	public void setPlato(Plato plato) {
		this.plato = plato;
	}
}

