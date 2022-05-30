package com.example.clima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private CardView[] cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        cards = new CardView[]{
                findViewById(R.id.natalCard),
                findViewById(R.id.helsinkiCard),
                findViewById(R.id.novaioroqueCard),
                findViewById(R.id.delhiCard),
        };

        for (CardView card: cards) {
            card.setOnClickListener(v -> {
                // updateValues()
                // pondo valroes extras na intent (cidade e temperatura)
                Intent intent = new Intent(MainActivity.this, detalhesClima.class);
                ConstraintLayout layout = (ConstraintLayout) card.getChildAt(0);
                TextView nomeView = (TextView) layout.getChildAt(0);
                TextView tempView = (TextView) layout.getChildAt(1);
                intent.putExtra("cidade", nomeView.getText());
                intent.putExtra("temperatura", tempView.getText());
                startActivity(intent);
            });
        }
    }

    public void updateValues(){
        WeatherClient.getTemperature( this);
    }

    public void writeTemperatures(String cidade, String temp){
        for (CardView card: cards) {
            TextView cidadeView = (TextView) ((ConstraintLayout) card.getChildAt(0))
                    .getChildAt(0);
            if(cidadeView.getText().equals(cidade)){
                TextView tempView = (TextView) ((ConstraintLayout) card.getChildAt(0))
                        .getChildAt(1);
                tempView.setText(temp + "°C");
            }
        }
    }
}