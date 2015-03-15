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
        //policy per http al thread
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HTMLRequest dl = new HTMLRequest(url_login, "username="+this.username+"&password="+this.password);
        String result = dl.getHTML();
        JSONArray ja = null;
        JSONObject jo = null;
        try {
            ja = new JSONArray(result.toString());
            jo = (JSONObject) ja.get(0);
            this.permessi = jo.getString("permissions");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (jo == null) //connessione fallita
            return false;
        else return true;//connessione riuscita
    }
}
