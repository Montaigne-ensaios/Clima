package com.example.clima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private CardView natalCard;
    private TextView natalTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inicializa valores e atualiza temperatura
        inicializarViews();
        atulizarValores();

        natalCard.setOnClickListener(v -> startActivity(
                new Intent(MainActivity.this, detalhesClima.class)
        )); // todo: extra para Activity
    }

    @Override
    protected void onResume() {
        super.onResume();
        atulizarValores();
    }

    private void inicializarViews(){
        natalCard = findViewById(R.id.natalCard);
        natalTemp = findViewById(R.id.natalTemp);
    }

    public void atulizarValores(){
        natalTemp.setText(WeatherClient.getTemperature("Natal"));
    }
}