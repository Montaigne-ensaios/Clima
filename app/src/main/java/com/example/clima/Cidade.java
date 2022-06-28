package com.example.clima;

import android.widget.TextView;

public class Cidade {
    private final String nome;
    private final double[] coordenadas;
    private TextView tempView;

    public Cidade(String nome, double[] coordenadas) {
        this.nome = nome;
        this.coordenadas = coordenadas;
    }

    public void setTempView(TextView tempView) {
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
