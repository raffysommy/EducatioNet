package com.example.raffaele.testapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by paolo on 15/03/2015.
 * Classe per gestire l' utente. conterrà metodi e proprietà utili per velocizzare l'accesso e l'uso
 * delle funzionalità online. comunica spesso con il backend su bluemix dell' applicazione k12.
 * INPUT: username, password
 */
public class user implements Parcelable {
    //costanti della classe
    private final String url_login = "http://mysql-raffysommy-1.c9.io/k12api/login.php";
    //membri privati
    private String nome = new String();
    private String cognome = new String();
    private String scuola = new String();
    private String username = new String();
    private String password = new String();
    private String permessi = new String();
    private String token = new String();
    public static final Creator<user> CREATOR= new Creator<user>(){
        @Override
        public user createFromParcel(Parcel in){
            return new user(in);
        }

        @Override
        public user[] newArray(int size) {
            return new user[size];
        }
    };


    public user(String user, String pass) {
        this.username = user;
        this.password = pass;
    }
    private user(Parcel in){
        readFromParcel(in);
    }

    public boolean connetti() {
        //richiesta http al backend
        HTMLRequest dl = new HTMLRequest(url_login, "username="+this.username+"&password="+this.password);
        //richiede json di risposta
        String result = dl.getHTMLTread();
        //estrapola dati
        JSONArray resultArray = null;
        JSONObject data = null;
        try {
            resultArray = new JSONArray(result.toString());
            data = (JSONObject) resultArray.get(0);
            this.permessi = data.getString("permissions");
            //cognome e nome
            setCognome(data.getString("cognome"));
            setNome(data.getString("nome"));
            setScuola(data.getString("scuola"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (data == null) //connessione fallita
            return false;
        else return true;//connessione riuscita
    }
    //setters
    public void setNome(String n) {
        this.nome = n;
    }
    public void setCognome(String c) {
        this.cognome = c;
    }
    public void setScuola(String s) {
        this.scuola = s;
    }
    //getters
    public String getNome() {
        return this.nome;
    }
    public String getCognome() {
        return this.cognome;
    }
    public String getScuola() {
        return this.scuola;
    }
    public String getUsername() {
        return this.username;
    }
    public String getPassword() {
        return this.password;
    }
    public void readFromParcel(Parcel in) {
        nome = in.readString();
        cognome = in.readString();
        scuola= in.readString();
        username= in.readString();
        token=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nome);
        dest.writeString(cognome);
        dest.writeString(scuola);
        dest.writeString(username);
        dest.writeString(token);
    }
}
