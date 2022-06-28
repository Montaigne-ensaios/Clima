package com.example.clima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

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

        // inicializa valores
        initializeViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateValues();
    }

    private void initializeViews(){
        // inicaliza cards numa lista, os TextViews ficam
        // acessíveis pelo getChildAt(index) do ConstraintLayout,
        // que por sua vez é acessível pelo getChildAt(0) do
        // ConstraintLayout. Os valroes de index são:
        // 0: textView do nome
        // 1: textView da temperatura
        CardView[] cards = new CardView[]{
                findViewById(R.id.natalCard),
                findViewById(R.id.helsinkiCard),
                findViewById(R.id.novaioroqueCard),
                findViewById(R.id.delhiCard),
        };

        for (CardView card: cards) {
            card.setOnClickListener(v -> {
                // updateValues()
                // pondo valroes extras na intent (cidade e temperatura)
                Intent intent = new Intent(MainActivity.this, DetalhesClimaActivity.class);
                ConstraintLayout layout = (ConstraintLayout) card.getChildAt(0);
                TextView nomeView = (TextView) layout.getChildAt(0);
                TextView tempView = (TextView) layout.getChildAt(1);
                intent.putExtra("cidade", nomeView.getText());
                intent.putExtra("temperatura", tempView.getText());
                startActivity(intent);
            });
        }
    }

    private void updateValues(){
        for (Cidade cidade: cidades) {
            WeatherClient.getTemperature(cidade);
        }
    }
}