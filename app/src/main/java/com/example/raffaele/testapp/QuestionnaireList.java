package com.example.raffaele.testapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 *
 * Created by K12-Dev-Team on 02/05/2015.
 */
public class QuestionnaireList extends ArrayList<Questionnaire> {
    private final String url = "http://k12-api.mybluemix.net/api/questionnaire/list";
    public void getHttp(String token) { //metodo di richiesta al backend
        this.clear(); //pulisce la lista per sicurezza
        HTMLRequest htmlRequest = new HTMLRequest(url, "access_token=" + token); //richiesta con token
        String result = htmlRequest.getHTMLThread();
        Log.d("token", token); //loggo il token per scopi di debug
        try {
            JSONArray ja = new JSONArray(result); //elaboro l'array di json e aggiungo gli elementi alla lista
            JSONObject jo;
            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                this.add(new Questionnaire(jo.getString("id"), jo.getString("name")));
            }
        } catch (JSONException e) {
            e.printStackTrace(); //in caso di eccezzioni stampo la lista chiamate (Le eccezzioni json non sono recuperabili ma non impediscono il continuo dell'esecuzione)
        }
    }
}
