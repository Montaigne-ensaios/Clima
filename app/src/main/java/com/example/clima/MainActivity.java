package com.example.clima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Cidade[] cidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cidades = new Cidade[]{
            new Cidade("Natal", new double[]{-5.79, -35.34}),
            new Cidade("Helsinki", new double[]{60.17, 24.94}),
            new Cidade("Nova Iorque", new double[]{40.72, -73.99}),
            new Cidade("Nova Delhi", new double[]{28.52,77.06})
        };

        CidadesAdapter adapter = new CidadesAdapter();
        adapter.setCidades(cidades);

        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateValues();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateValues();
    }

    private void updateValues(){
        for (Cidade cidade: cidades) {
            WeatherClient.getTemperature(cidade);
        }
    }
}