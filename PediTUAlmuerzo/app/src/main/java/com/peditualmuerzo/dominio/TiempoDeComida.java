package com.peditualmuerzo.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TiempoDeComida implements Serializable{
	private String nombreTiempoComida;
	private List<Plato> platos;

	public TiempoDeComida() {
		super();
		this.platos = new ArrayList<Plato>();
	}

	public String getNombreTiempoComida() {
		return nombreTiempoComida;
	}

	public void setNombreTiempoComida(String nombreTiempoComida) {
		this.nombreTiempoComida = nombreTiempoComida;
	}

	public List<Plato> getPlatos() {
		return platos;
	}

	public void setPlatos(List<Plato> platos) {
		this.platos = platos;
	}


}
