package com.jonerysantos.carros.domain;

import android.content.Context;
import android.util.Log;
import com.jonerysantos.carros.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import livroandroid.lib.utils.FileUtils;
import livroandroid.lib.utils.HttpHelper;

import static com.jonerysantos.carros.R.string.carros;

/**
 * Created by Jonery on 22/10/2016.
 */

public class CarroService {
    private static final boolean LOG_ON = false;
    private static final String TAG = "CarroService";
    private static final String URL = "http://www.livroandroid.com.br/livro/carros/carros_{tipo}.json";

    public static List<Carro> getCarros(Context context, int tipo) throws IOException {
        List<Carro> carros = null;
        if(tipo == R.string.favoritos){
            //Consulta no banco de dados
            CarrosDB db = new CarrosDB(context);
            carros = db.findAll();
        } else {
            //Consulta no web service
            String tipoString = getTipo(tipo);
            String url = URL.replace("{tipo}", tipoString);
            //Faz a requisição HTTP no servidor e retorna a string com o conteúdo
            HttpHelper http = new HttpHelper();
            String json = http.doGet(url);
            carros = parserJSON(context, json);
        }
        return carros;
    }

    private static String getTipo(int tipo) {
        if (tipo == R.string.classicos){
            return "classicos";
        } else if (tipo == R.string.esportivos){
            return "esportivos";
        }
        return "luxo";
    }

    //faz o parser do XML e cria a lista de carros
    private static List<Carro> parserJSON(Context context, String json) throws IOException {
        List<Carro> carros = new ArrayList<Carro>();
        try{
            JSONObject root = new JSONObject(json);
            JSONObject obj = root.getJSONObject("carros");
            JSONArray jsonCarros = obj.getJSONArray("carro");
            //Insere cada carro na lista
            for(int i =0; i<jsonCarros.length();i++){
                JSONObject jsonCarro = jsonCarros.getJSONObject(i);
                Carro c = new Carro();
                //Lê as informações de cada carro
                c.nome = jsonCarro.optString("nome");
                c.desc = jsonCarro.optString("desc");
                c.urlFoto = jsonCarro.optString("url_foto");
                c.urlInfo = jsonCarro.optString("url_info");
                c.urlVideo = jsonCarro.optString("url_video");
                c.latitude = jsonCarro.optString("latitude");
                c.longitude = jsonCarro.optString("longitude");
                if(LOG_ON){
                    Log.d(TAG, "Carro " + c.nome + " > " + c.urlFoto);
                }
                carros.add(c);
            }
            if(LOG_ON){
                Log.d(TAG, carros.size() + " encontrados");
            }
        } catch (JSONException e){
            throw new IOException(e.getMessage(), e);
        }
        return carros;
    }

    //faz a leitura do arquivo que esta na pasta
    //O FileUtils pega o InputStream do xml e converte para String
    //Leitura dos arquivos não está sendo usado, pois está utilizando webservice
//    private static String readFile(Context context, int tipo) throws IOException {
//        if(tipo == R.string.classicos){
//            return FileUtils.readRawFileString(context, R.raw.carros_classicos, "UTF-8");
//        } else if (tipo == R.string.esportivos){
//            return FileUtils.readRawFileString(context, R.raw.carros_esportivos, "UTF-8");
//        }
//        return FileUtils.readRawFileString(context, R.raw.carros_luxo, "UTF-8");
//    }
}
