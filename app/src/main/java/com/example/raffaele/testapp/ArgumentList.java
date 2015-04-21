package com.example.raffaele.testapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/***
 * Created by K12-Dev-Team on 25/03/2015.
 * Questa classe estende l'arraylist di argument implementando l'interfaccia parceable
 * e un metodo per ritornare i dati in stringa in formato json
 * Implementa inoltre un metodo di richiesta argomenti al database
 */

/**
 * Array di Argomenti
 * @version 0.1
 * @author K12-Dev-Team
 */
public class ArgumentList extends ArrayList<Argument> implements Parcelable{
    private final String url = "https://k12-api.mybluemix.net/api/topic/list"; //Url di connessione al backend
    public static final Creator<ArgumentList> CREATOR= new Creator<ArgumentList>(){ //creatore dell'argumentlist
        @Override
        public ArgumentList createFromParcel(Parcel in){
            return new ArgumentList(in);
        }
        @Override
        public ArgumentList[] newArray(int size) {
            return new ArgumentList[0];
        }
    };

    /**
     * Costruttore vuoto
     */
    public ArgumentList(){
        super();
    } //costruttore vuoto

    /**
     * @param in Parcel di ingresso
     */
    private ArgumentList(Parcel in ){
        readFromParcel(in);
    } //Costruttore della parceable

    /**
     * @param in Parcel di ingresso
     */
    public void readFromParcel(Parcel in){
        this.clear(); //pulisce la lista per sicurezza
        in.readList(this,ArgumentList.class.getClassLoader()); //riempie la lista con gli elementi dal parceable
    }

    /**
     * Richiesta lista argomenti al backend
     * @param token Token di autenticazione al backend
     */
    public void getHttp(String token){ //metodo di richiesta al backend
        this.clear(); //pulisce la lista per sicurezza
        HTMLRequest htmlRequest =new HTMLRequest(url, "access_token=" + token); //richiesta con token
        String result=htmlRequest.getHTMLThread();
        Log.d("token",token); //loggo il token per scopi di debug
        try{
            JSONArray ja=new JSONArray(result); //elaboro l'array di json e aggiungo gli elementi alla lista
            JSONObject jo;
            for(int i=0;i<ja.length();i++){
                jo=ja.getJSONObject(i);
                this.add(new Argument(jo.getString("name"), jo.getString("description")));
            }
        } catch (JSONException e) {
            e.printStackTrace(); //in caso di eccezzioni stampo la lista chiamate (Le eccezzioni json non sono recuperabili ma non impediscono il continuo dell'esecuzione)
        }
    }

    /**
     * Metodo toString
     * @return Json encoded string output
     */
    public String toString(){ //metodo toString con output stile JSON
      StringBuilder responseText = new StringBuilder();
      responseText.append("[");
      if(this.size()==0){
            return responseText.append("]").toString();
        }

      for(int i=0;i<this.size();i++) {
          if(this.get(i).isCheck())
                responseText.append("\"").append(this.get(i).getArg()).append("\",");
      }
      return responseText.deleteCharAt(responseText.length()-1).append("]").toString();
    }

    /**
     * @return Torna un ArrayList di soli elementi selezionati
     */
    public ArrayList<String> toArrayString(){ //metodo per restituire un arraylist di stringhe degli elementi selezionati
        ArrayList<String> arrayList=new ArrayList<>();
        if(this.size()==0){
            return null;
        }
        for(int i=0;i<this.size();i++) {
            if(this.get(i).isCheck())
                arrayList.add(this.get(i).getArg());
        }
        return arrayList;
    }

    /**
     * @return 0
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * @param dest Parcel di destinazione
     * @param flags (optional)
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this);
    } //Scrive la lista nel Parceable
}
