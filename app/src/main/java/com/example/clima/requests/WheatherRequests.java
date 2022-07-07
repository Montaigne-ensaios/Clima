package com.example.clima.requests;

import com.example.clima.Cidade;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class WheatherRequests {
    public static String buildAPIRequest(Cidade cidade) {
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
}