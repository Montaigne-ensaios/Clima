package com.example.clima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    private CardView natalCard;
    private TextView textView;
    private final int dia = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

    private final String url="https://api.open-meteo.com/v1/forecast?latitude=-5.79&longitude=-35.34&daily=temperature_2m_max&timezone=America%2FSao_Paulo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        natalCard = findViewById(R.id.natalCard);
        textView = findViewById(R.id.natalNome);
        textView.setText(WeatherClient.getTemperature("Natal"));

        natalCard.setOnClickListener(v -> startActivity(
                new Intent(MainActivity.this, detalhesClima.class)
        ));

    }
}