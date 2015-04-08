package com.example.raffaele.testapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by paolo on 15/03/2015.
 * Classe per gestire l' utente. conterrà metodi e proprietà utili per velocizzare l'accesso e l'uso
 * delle funzionalità online. comunica spesso con il backend su bluemix dell' applicazione k12.
 * INPUT: username, password
 */
public class User implements Parcelable {
    //costanti della classe
    private final String url_login = "https://k12-api.mybluemix.net/oauth";
    private final String url_info = "https://k12-api.mybluemix.net/api/user/info";
    private final String url_score = "https://mysql-raffysommy-1.c9.io/api/question/score";
    private final String params = "grant_type=password&client_id=student-app&client_secret=student-app-pw&";
    //membri privati

    private String firstName = "";
    private String lastName = "";
    private String school = "";
    private String username = "";
    private String password = "";
    private String email="";
    private String address="";
    private String role = "";
    private String access_token = "";
    private String refresh_token = "";
    private String ID = "";

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
        this.firstName= n;
        this.lastName= c;
        this.school=s;
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
        String result = dl.getHTML();
        //debug + console = <3
        //Log.i("User", "User.result =" + result);
        //estrapola dati
        JSONObject data = null;
        try {
            data = new JSONObject(result);
            setAccessToken(data.getString("access_token"));
            setRefreshToken(data.getString("refresh_token"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //chiamo API per informazioni utente
        //downloadInfoUser();

        //richiesta http al backend
        dl = new HTMLRequest(url_info, "&access_token="+this.getAccessToken());
        //richiede json di risposta
        result = dl.getHTML();
        //estrapola dati
        try {
            data = new JSONObject(result);
            setID("id");
            setRole("role");
            setLastName(data.getString("lastName"));
            setFirstName(data.getString("firstName"));
            setSchool("Scuola");
            setEmail(data.getString("email"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //prova
        return data != null; //Stato connessione
    }
    //retrieve info about user from online API
    /*public void downloadInfoUser() {
        //richiesta http al backend
        HTMLRequest dl = new HTMLRequest(url_info, "&access_token="+this.getAccessToken());
        //richiede json di risposta
        String result = dl.getHTML();
        //estrapola dati
        JSONObject data;
        try {
            data = new JSONObject(result);
            setID("id");
            setRole("role");
            setLastName(data.getString("lastName"));
            setFirstName(data.getString("firstName"));
            setSchool("Scuola");
            setEmail(data.getString("email"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    //permette il salvataggio online del risultato di una domanda. Ritorna il campo message JSON
    public String saveScore(ArrayList<String[]> scores) {
        //da ArrayList a JSON
        JSONArray jsonA = new JSONArray(scores);
        Log.i("JSON SCORE=>", jsonA.toString());
        //richiesta http al backend
        HTMLRequest dl = new HTMLRequest(url_score, params +
                "access_token=" + this.access_token +
                "&scores=" + jsonA.toString()
        );
        //invoco api
        String result = dl.getHTMLThread();
        String msg = "";
        try {
            msg = new JSONObject(result).getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return msg;
    }
    //setters
    public void setFirstName(String n) {
        this.firstName = n;
    }
    public void setEmail(String e) {
        this.email = e;
    }
    public void setLastName(String c) {
        this.lastName = c;
    }
    public void setSchool(String s) { this.school = s;  }
    public void setID(String i) { this.ID = i;  }
    public void setAccessToken(String at) { this.access_token = at;  }
    public void setRefreshToken(String rt) { this.refresh_token = rt;  }
    public void setRole(String p) { this.role = p;  }
    //getters
    public String getFirstName() {
        return this.firstName;
    }
    public String getLastName() {
        return this.lastName;
    }
    public String getEmail() {
        return this.email;
    }
    public String getSchool() {
        return this.school;
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
    public String getRole() {
        return this.role;
    }
    public String getID() {
        return this.ID;
    }

    public void readFromParcel(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        school = in.readString();
        role = in.readString();
        address = in.readString();
        username = in.readString();
        password = in.readString();
        access_token = in.readString();
        refresh_token = in.readString();
        ID = in.readString();
        email = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(school);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(address);
        dest.writeString(role);
        dest.writeString(access_token);
        dest.writeString(refresh_token);
        dest.writeString(ID);
        dest.writeString(email);
    }
}
