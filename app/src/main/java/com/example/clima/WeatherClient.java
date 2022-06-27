package com.example.clima;

import android.os.AsyncTask;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class WeatherClient extends AsyncTask<String, Integer, String> {
    private final int dia = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    private Cidade cidade;

    public WeatherClient(Cidade cidade) {
        super();
        this.cidade = cidade;
    }

    @Override
    protected String doInBackground(String... string) {  // parâmetro obrigatório
        /*
        cria a requisição, executa assincronamente e retorna o valor tratado
         */
        String link = buildAPIRequest(this.cidade);
        String json = request(link);
        return readJSON(json);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        // passa a cidade e a temperatura (`s`) para o writeTemperatures
        // decidir para onde vai cada valor
        cidade.updateTemp(s);
    }

    public static String request(String link){
        /*
          Função de requisição http por url, retorna resposta crua
         */
        String response = null;
        try {
            URL url = new URL(link);
            HttpURLConnection urlConnection = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();

                StringBuilder buffer = new StringBuilder();
                InputStream is = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                String line = null;
                while ((line = br.readLine()) != null)
                    // monta resposta da input stream
                    buffer.append(line);
                is.close();
                response = buffer.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static String buildAPIRequest(Cidade cidade){
        /*
           Função de construir link de requisição pelos requistos da API
         */
        return "https://api.open-meteo.com/v1/forecast" +
                "?latitude=" +
                cidade.getLat() +
                "&longitude=" +
                cidade.getLong() +
                "&daily=temperature_2m_max" +
                "&timezone=America%2FSao_Paulo";
    }

    public String readJSON(String json) {
        /*
           Função que faz a requisição, trata o JSON e retorna a temperatura

           Estrutura da resposta:
           ...
           daily:
               temperature_2m_max:
                   temp dia0
                   temp dia1
                   temp dia2
                   ...
               ...
           ...
         */
        try {
            // JSONObject funciona como dicionário
            JSONObject clima = new JSONObject(json);
            for (Iterator<String> it = clima.keys(); it.hasNext(); ) {
                // é necessário iterar por todas as linhas da string
                String key = it.next();
                if (key.equals("daily")) {
                    JSONObject daily = (JSONObject) clima.get(key);
                    JSONArray temperatureSemana = daily.getJSONArray("temperature_2m_max");
                    return temperatureSemana.get(dia).toString();
                }
            }
        } catch (JSONException ignored){}
        return null;
        // todo: toast para problemas na requisição, Toasts podem requerir contexto
    }

    public static void getTemperature(Cidade cidade){
        /*
        instancia o objeto e executa para a cidade passada
         */
        WeatherClient instance = new WeatherClient(cidade);
        instance.execute();
    }
}
