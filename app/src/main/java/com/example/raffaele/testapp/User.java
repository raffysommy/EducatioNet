package com.example.raffaele.testapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by paolo on 15/03/2015.
 * Classe per gestire l' utente. conterrà metodi e proprietà utili per velocizzare l'accesso e l'uso
 * delle funzionalità online. comunica spesso con il backend su bluemix dell' applicazione k12.
 * INPUT: username, password
 */
public class User implements Parcelable {
    //costanti della classe
    private final String url_login = "https://k12-api.mybluemix.net/oauth";
    private final String params = "grant_type=password&client_id=student-app&client_secret=student-app-pw&";
    //membri privati

    private String nome = new String();
    private String cognome = new String();
    private String scuola = new String();
    private String username = new String();
    private String password = new String();
    private String email= new String();
    private String address=new String();
    private String permessi = new String();
    private String access_token = new String();
    private String refresh_token = new String();
    public static final Creator<User> CREATOR= new Creator<User>(){
        @Override
        public User createFromParcel(Parcel in){
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };


    public User(String user, String pass) {
        this.username = user;
        this.password = pass;
    }
    public User (String user, String pass, String n, String c, String s, String e, String a){
        this.username= user;
        this.password= pass;
        this.nome= n;
        this.cognome= c;
        this.scuola=s;
        this.email= e;
        this.address=a;
    }
    private User(Parcel in){
        readFromParcel(in);
    }

    public boolean connetti() {
        //richiesta http al backend
        HTMLRequest dl = new HTMLRequest(url_login, params + "username="+this.username+"&password="+this.password);
        //richiede json di risposta
        String result = dl.getHTMLThread();
        //debug + console = <3
        //Log.i("User", "User.result =" + result);
        //estrapola dati
        JSONObject data = null;
        try {
            data = new JSONObject(result.toString());
            //TODO attendere tonino e il servizio di getCazziUser();
            setPermessi("0"/*data.getString("permissions")*/);
            //cognome e nome
            setCognome("panza"/*data.getString("cognome")*/);
            setNome("ciccio"/*data.getString("nome")*/);
            setScuola("carcere"/*data.getString("scuola")*/);
            setAccessToken(data.getString("access_token"));
            setRefreshToken(data.getString("refresh_token"));
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
    public void setScuola(String s) { this.scuola = s;  }
    public void setAccessToken(String at) { this.access_token = at;  }
    public void setRefreshToken(String rt) { this.refresh_token = rt;  }
    public void setPermessi(String p) { this.permessi = p;  }
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
    public String getAccessToken() {
        return this.access_token;
    }
    public String getRefreshToken() {
        return this.refresh_token;
    }
    public String getPermessi() {
        return this.permessi;
    }
    public void readFromParcel(Parcel in) {
        nome = in.readString();
        cognome = in.readString();
        scuola= in.readString();
        username= in.readString();
        access_token=in.readString();
        refresh_token=in.readString();
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
        dest.writeString(access_token);
        dest.writeString(refresh_token);
    }
}
