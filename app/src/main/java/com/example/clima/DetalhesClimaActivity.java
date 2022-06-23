package com.example.clima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class DetalhesClimaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_clima);

        // recuperando valores da Intent
        Intent originIntent = getIntent();
        String cidade = originIntent.getStringExtra("cidade");
        String temperatura = originIntent.getStringExtra("temperatura");

        ConstraintLayout fundo = findViewById(R.id.constraintLayout);
        int temp = Integer.parseInt(temperatura.replace("°C", ""));
        if(temp > 40){
            fundo.setBackgroundColor(ContextCompat.getColor(this, R.color.muito_quente));
        } else if (temp > 30){
            fundo.setBackgroundColor(ContextCompat.getColor(this, R.color.quente));
        } else if (temp > 20){
            fundo.setBackgroundColor(ContextCompat.getColor(this, R.color.agradavel));
        } else if (temp > 10){
            fundo.setBackgroundColor(ContextCompat.getColor(this, R.color.frio));
        } else if (temp < 10){
            fundo.setBackgroundColor(ContextCompat.getColor(this, R.color.muito_frio));
        }

        // atribui os valores às views
        TextView cidadeView = findViewById(R.id.cidade);
        TextView temperaturaView = findViewById(R.id.temperatura);
        cidadeView.setText(cidade);
        temperaturaView.setText(temperatura);
    }
}