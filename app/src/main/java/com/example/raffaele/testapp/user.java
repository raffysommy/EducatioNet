package com.example.raffaele.testapp;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by paolo on 15/03/2015.
 * Classe per gestire l' utente. conterrà metodi e proprietà utili per velocizzare l'accesso e l'uso
 * delle funzionalità online. comunica spesso con il backend su bluemix dell' applicazione k12.
 * INPUT: username, password
 */
public class user {
    //costanti della classe
    private final String url_login = "http://mysql-raffysommy-1.c9.io/k12api/login.php";
    //membri privati
    private String nome = new String();
    private String cognome = new String();
    private String username = new String();
    private String password = new String();
    private String permessi = new String();
    private String token = new String();
    public user(String user, String pass) {
        this.username = user;
        this.password = pass;
    }
    public boolean connetti() {
        //richiesta http al backend
        HTMLRequest dl = new HTMLRequest(url_login, "username="+this.username+"&password="+this.password);
        //richiede json di risposta
        String result = dl.getHTML();
        //estrapola dati
        JSONArray resultArray = null;
        JSONObject data = null;
        try {
            resultArray = new JSONArray(result.toString());
            data = (JSONObject) resultArray.get(0);
            this.permessi = data.getString("permissions");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data == null) //connessione fallita
            return false;
        else return true;//connessione riuscita
    }
}
