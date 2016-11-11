package com.peditualmuerzo.dominio;

/**
 * Created by Manuel on 08/11/2016.
 */

public class Control {

    private String dia;
    private int semana;

    public Control() {

    }

    public Control(String dia, int semana) {
        this.dia = dia;
        this.semana = semana;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public int getSemana() {
        return semana;
    }

    public void setSemana(int semana) {
        this.semana = semana;
    }
}
