package com.peditualmuerzo.dominio;

import java.util.ArrayList;
import java.util.List;

public class Plato {
	private int idPlato;
	private String nombrePlato;
	private boolean opcional;
	private TiempoDeComida categoria;
	private List<ItemPlatoComponente> items;
	
	public Plato() {
		super();
		this.categoria = new TiempoDeComida();
		this.items = new ArrayList<ItemPlatoComponente>();
	}

	public int getIdPlato() {
		return idPlato;
	}

	public void setIdPlato(int idPlato) {
		this.idPlato = idPlato;
	}

	public boolean isOpcional() {
		return opcional;
	}

	public void setOpcional(boolean opcional) {
		this.opcional = opcional;
	}

	public TiempoDeComida getCategoria() {
		return categoria;
	}

	public void setCategoria(TiempoDeComida categoria) {
		this.categoria = categoria;
	}

	public List<ItemPlatoComponente> getItems() {
		return items;
	}

	public void setItems(List<ItemPlatoComponente> items) {
		this.items = items;
	}

	public String getNombrePlato() {
		return nombrePlato;
	}

	public void setNombrePlato(String nombrePlato) {
		this.nombrePlato = nombrePlato;
	}
}
