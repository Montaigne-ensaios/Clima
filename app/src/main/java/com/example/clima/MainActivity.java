package com.example.clima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private CardView natalCard;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        natalCard = findViewById(R.id.natalCard);

        textView = findViewById(R.id.natalNome);

        natalCard.setOnClickListener(v -> textView.setText(request(
                "https://api.open-meteo.com/v1/forecast?latitude=-5.79&longitude=-35.34&daily=temperature_2m_max&timezone=America%2FSao_Paulo"))
//                startActivity(
//                new Intent(MainActivity.this, detalhesClima.class)
        );


    }
}