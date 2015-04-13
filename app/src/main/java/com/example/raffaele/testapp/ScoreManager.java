package com.example.raffaele.testapp;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Raffaele trasferendo il  codice di Paolo on 08/04/2015.
 */

/**
 * Gestore degli Score
 * @Autor Paolo ft Raffaele
 * @version 0.2
 */
public class ScoreManager extends ArrayList<String[]> {

    private  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private final String url_score = "https://mysql-raffysommy-1.c9.io/api/question/score";
    private final String access_token;
    private final Application application;

    /**
     * @param access_token Token di accesso al backend
     * @param application Applicazione chiamante
     */
    public ScoreManager(String access_token, Application application){
        super();
        this.access_token = access_token;
        this.application = application;
    }

    /**
     * Aggiunge lo score all'array
     * @param id_domanda Domanda
     * @param result Risultato utente
     */
    public void addScore(String id_domanda,Boolean result){
        Date date = new Date();
        this.add(new String[]{id_domanda, String.valueOf(result), dateFormat.format(date)});
    }

    /**
     * Salva lo score
     */
    public void saveScore() {
        new ScoreSender().execute(this);
    }

    /**
     * Classe di salvataggio dello score
     */
    private class  ScoreSender extends AsyncTask<ScoreManager,Void,String>{
        /**
         * @param params Arraylist di Score
         * @return Esito operazione
         */
        @Override
        protected String doInBackground(ScoreManager... params) {
            //da ArrayList a JSON
            JSONArray jsonA = null;
            jsonA = new JSONArray(params[0]);
            Log.i("JSON SCORE=>", jsonA.toString());
            //richiesta http al backend
            HTMLRequest dl = new HTMLRequest(url_score,"access_token=" + access_token +"&scores=" + jsonA.toString());
            //invoco api
            String result = dl.getHTML();
            String msg = "";
            try {
                msg = new JSONObject(result).getString("message");
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            return msg;
        }

        /**
         * @param msg Messaggio da mostrare
         */
        protected void onPostExecute(String msg) {
            Toast.makeText(application.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
