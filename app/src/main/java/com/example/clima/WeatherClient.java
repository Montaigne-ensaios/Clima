package com.example.clima;

import android.os.AsyncTask;

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

public class WeatherClient {
    private static volatile String response = "";
    // volatil para passar entre threads
    private static final int dia = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
    // coordenadas das cidades listadas

    private static final HashMap<String, double[]> coordenadas = new HashMap<String, double[]>() {{
        put("Natal", new double[]{-5.79, -35.34});
        put("Helsinki", new double[]{60.17, 24.94});
        put("Nova Iorque", new double[]{40.72, -73.99});
        put("Nova Delhi", new double[]{28.52,77.06});
    }};

    public static String request(String link){
        /*
          Função de requisição http por url, retorna resposta crua
         */

        AsyncTask.execute(() -> {
            // Android requer que funções de rede sejam executadas sem
            // bloquear thread principal
            try {
                URL url = new URL(link);
                HttpURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpURLConnection) url.openConnection();

                    StringBuffer buffer = new StringBuffer();
                    InputStream is = urlConnection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is));

                    String line = null;
                    while ((line = br.readLine()) != null)
                        // monta resposta da input stream
                        buffer.append(line);
                    is.close();
                    // passa valor obtido como variável volatil
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
        });
        return response;
    }

    public static String buildAPIRequest(String cidade){
        /*
           Função de construir link de requisição pelos requistos da API
         */
        return "https://api.open-meteo.com/v1/forecast" +
                "?latitude=" +
                Objects.requireNonNull(coordenadas.get(cidade))[0] +
                "&longitude=" +
                Objects.requireNonNull(coordenadas.get(cidade))[1] +
                "&daily=temperature_2m_max" +
                "&timezone=America%2FSao_Paulo";
    }

    public static String getTemperature(String cidade) {
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
            // recupera resposta JSON da API
            String json = WeatherClient.request(buildAPIRequest(cidade));
            // JSONObject funciona como dicionário
            JSONObject clima = new JSONObject(json);
            for (Iterator<String> it = clima.keys(); it.hasNext(); ) {
                // é necessário iterar por todas as linhas da string
                String key = it.next();
                boolean teste = key.equals("daily"); // todo: revisar isso aqui
                if (teste) {
                    JSONObject daily = (JSONObject) clima.get(key);
                    JSONArray temperatureSemana = daily.getJSONArray("temperature_2m_max");
                    return temperatureSemana.get(dia).toString();
                }
            }
        } catch (JSONException ignored){}
        return null;
        // todo: toast para problemas na requisição, Toasts podem requerir contexto
    }
}
