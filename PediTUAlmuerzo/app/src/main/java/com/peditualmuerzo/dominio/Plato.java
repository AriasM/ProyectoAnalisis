package com.peditualmuerzo.dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Plato implements Serializable{

	private String idPlatillo;
	private String nombrePlatillo;
	private boolean opcional;
	private float precioPlatillo;
	private TiempoDeComida categoria;
	private Dia dia;
	private Semana semana;
	private List<ItemPlatoComponente> items;

	public Plato() {

		categoria = new TiempoDeComida();
		dia = new Dia();
		semana = new Semana();
		items = new ArrayList<ItemPlatoComponente>();
	}

	public String getIdPlatillo() {
		return idPlatillo;
	}

	public void setIdPlatillo(String idPlatillo) {
		this.idPlatillo = idPlatillo;
	}

	public boolean isOpcional() {
		return opcional;
	}

	public void setOpcional(boolean opcional) {
		this.opcional = opcional;
	}

	public String getNombrePlatillo() {
		return nombrePlatillo;
	}

	public void setNombrePlatillo(String nombrePlatillo) {
		this.nombrePlatillo = nombrePlatillo;
	}

	public float getPrecioPlatillo() {
		return precioPlatillo;
	}

	public void setPrecioPlatillo(float precioPlatillo) {
		this.precioPlatillo = precioPlatillo;
	}

	public List<ItemPlatoComponente> getItems() {
		return items;
	}

	public void setItems(List<ItemPlatoComponente> items) {
		this.items = items;
	}

	public TiempoDeComida getCategoria() {
		return categoria;
	}

	public void setCategoria(TiempoDeComida categoria) {
		this.categoria = categoria;
	}

	public Dia getDia() {
		return dia;
	}

	public void setDia(Dia dia) {
		this.dia = dia;
	}

	public Semana getSemana() {
		return semana;
	}

	public void setSemana(Semana semana) {
		this.semana = semana;
	}
}
