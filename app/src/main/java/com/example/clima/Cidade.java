package com.example.clima;

import android.widget.TextView;

public class Cidade {
    private final String nome;
    private final double[] coordenadas;
    private final TextView tempView;

    public Cidade(String nome, double[] coordenadas, TextView tempView) {
        this.nome = nome;
        this.coordenadas = coordenadas;
        this.tempView = tempView;
    }

    public void updateTemp(String temp){
        tempView.setText(temp + "°C");
    }

    public String getNome() {
        return nome;
    }

    public double getLat() {
        return coordenadas[0];
    }

    public double getLong() {
        return coordenadas[1];
    }
}
