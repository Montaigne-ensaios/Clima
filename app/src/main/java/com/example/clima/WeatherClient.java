package com.example.clima;

import android.os.AsyncTask;

import com.example.clima.requests.WheatherRequests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Iterator;

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
        String link = WheatherRequests.buildAPIRequest(this.cidade);
        String json = WheatherRequests.request(link);
        return readJSON(json);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        // passa a cidade e a temperatura (`s`) para o writeTemperatures
        // decidir para onde vai cada valor
        cidade.updateTemp(s);
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
        } catch (JSONException | NullPointerException e) {
            e.printStackTrace();
        }
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
