package com.peditualmuerzo.dominio;

import java.io.Serializable;

/**
 * Created by victo on 5/11/2016.
 */

public class Dia implements Serializable {
    private String nombreDia;

    public Dia(){

    }

    public String getNombreDia() {
        return nombreDia;
    }

    public void setNombreDia(String nombreDia) {
        this.nombreDia = nombreDia;
    }
}