package com.peditualmuerzo.dominio;

import java.io.Serializable;

/**
 * Created by victo on 5/11/2016.
 */

public class Semana implements Serializable {

    private String numeroSemana;

    public Semana() {
    }

    public String getNumeroSemana() {
        return numeroSemana;
    }

    public void setNumeroSemana(String numeroSemana) {
        this.numeroSemana = numeroSemana;
    }
}

