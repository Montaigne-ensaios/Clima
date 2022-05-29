package com.example.clima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class detalhesClima extends AppCompatActivity {
    String cidade, temperatura;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_clima);

        // recuperando valores da Intent
        Intent originIntent = getIntent();
        cidade = originIntent.getStringExtra("cidade");
        temperatura = originIntent.getStringExtra("temperatura");

        // atribui os valores às views
        TextView cidadeView = findViewById(R.id.cidade);
        TextView temperaturaView = findViewById(R.id.temperatura);
        cidadeView.setText(cidade);
        temperaturaView.setText(temperatura);
    }
}