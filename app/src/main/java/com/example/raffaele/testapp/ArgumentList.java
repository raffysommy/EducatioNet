package com.example.raffaele.testapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/***
 * Created by Raffaele on 25/03/2015.
 * Questa classe estende l'arraylist di argument implementando l'interfaccia parceable
 * e un metodo per ritornare i dati in stringa in formato json
 * Implementa inoltre un metodo di richiesta argomenti al database
 */
public class ArgumentList extends ArrayList<Argument> implements Parcelable{
    private final String url = "http://mysql-raffysommy-1.c9.io/api/topic/list"; //Url di connessione al backend
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
    public ArgumentList(){
        super();
    } //costruttore vuoto
    private ArgumentList(Parcel in ){
        readFromParcel(in);
    } //Costruttore della parceable
    public void readFromParcel(Parcel in){
        this.clear(); //pulisce la lista per sicurezza
        in.readList(this,ArgumentList.class.getClassLoader()); //riempie la lista con gli elementi dal parceable
    }
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
    public String toString(){ //metodo toString con output stile JSON
      StringBuilder responseText = new StringBuilder();
      responseText.append("[");
      if(this.size()==0){
            return responseText.append("]").toString();
        }

      for(int i=0;i<this.size();i++) {
          if(this.get(i).isCheck())
                responseText.append("\""+this.get(i).getArg()).append("\",");
      }
      return responseText.deleteCharAt(responseText.length()-1).append("]").toString();
    }
    public ArrayList<String> toArrayString(){ //metodo per restituire un arraylist di stringhe degli elementi selezionati
        ArrayList<String> arrayList=new ArrayList<String>();
        if(this.size()==0){
            return null;
        }
        for(int i=0;i<this.size();i++) {
            if(this.get(i).isCheck())
                arrayList.add(this.get(i).getArg());
        }
        return arrayList;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this);
    } //Scrive la lista nel Parceable
}
