package com.example.clima;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private CardView[] cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // inicializa valores e atualiza temperatura
        initializeViews();
        updateValues();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateValues();
    }

    private void initializeViews(){
        // inicaliza cards numa lista, os TextViews ficam
        // acessíveis pelo getChild(index) sendo os índices
        // 0: textView do nome
        // 1: textView da temperatura
        cards = new CardView[]{
                findViewById(R.id.natalCard)
        };

        for (CardView card: cards) {
            card.setOnClickListener(v -> {
                // pondo valroes extras na intent (cidade e temperatura)
                Intent intent = new Intent(MainActivity.this, detalhesClima.class);
                TextView nomeView = (TextView) card.getChildAt(0);
                TextView tempView = (TextView) card.getChildAt(1);
                intent.putExtra("cidade", nomeView.getText());
                intent.putExtra("temperatura", tempView.getText());
                startActivity(intent);
            });
            // todo: extra para Activity
        }
    }

    public void updateValues(){
        WeatherClient.getTemperature( this);
    }

    public void writeTemperatures(String cidade, String temp){
        for (CardView card: cards) {
            TextView cidadeView = (TextView) card.getChildAt(0);
            if(cidadeView.getText().equals(cidade)){
                TextView tempView = (TextView) card.getChildAt(1);
                tempView.setText(temp + "°C");
            }
        }
    }
}