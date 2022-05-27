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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class WeatherClient {
    private static volatile String response = "";
    private static final HashMap<String, double[]> coordenadas = new HashMap<String, double[]>() {{
        put("Natal", new double[]{-5.79, -35.34});
        put("Helsinki", new double[]{60.17, 24.94});
        put("Nova Iorque", new double[]{40.72, -73.99});
        put("Bova Delhi", new double[]{28.52,77.06});
    }};

    public static void request(String link){
        AsyncTask.execute(() -> {
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
                        buffer.append(line);
                    is.close();
                    urlConnection.disconnect();
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
    }

    public static String getResponse(String link) {
        request(link);
        return response;
    }

    public static String getCityResponse(String cidade){
        return getResponse(buildLink(cidade));
    }

    public static String buildLink(String cidade){
        return "https://api.open-meteo.com/v1/forecast?latitude=" +
                Objects.requireNonNull(coordenadas.get(cidade))[0] +
                "&longitude=" +
                Objects.requireNonNull(coordenadas.get(cidade))[0] +
                "&daily=temperature_2m_max&timezone=America%2FSao_Paulo";
    }

    public static String getTemperature(String cidade) {
        try {
            String json = WeatherClient.getCityResponse(cidade);
            JSONObject clima = new JSONObject(json);
            for (Iterator<String> it = clima.keys(); it.hasNext(); ) {
                String key = it.next();
                boolean teste=key.equals("daily");
                if (teste) {
                    JSONObject daily=(JSONObject) clima.get(key);
                    JSONArray temperatureSemana=daily.getJSONArray("temperature_2m_max");
                    return temperatureSemana.get(dia).toString();
                }
            }
        } catch (JSONException ignored){}
        return null;
    }
}
