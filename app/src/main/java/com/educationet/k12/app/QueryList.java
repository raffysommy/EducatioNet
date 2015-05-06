package com.educationet.k12.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

/**
 * Created by K12-Dev-Team on 29/04/2015.
 */
public class QueryList extends LinkedList<Query> implements Parcelable {
    private final String url = "https://k12-api.mybluemix.net/api/questionnaire/view"; //Url di connessione al backend
    public static final Parcelable.Creator<QueryList> CREATOR= new Parcelable.Creator<QueryList>(){ //creatore dell'argumentlist
        @Override
        public QueryList createFromParcel(Parcel in){
            return new QueryList(in);
        }

        @Override
        public QueryList[] newArray(int size) {return new QueryList[0];
        }
    };
    private String idquiz;

    public QueryList(String idquiz){
        this.idquiz=idquiz;
    }
    public void getHTTP(String token) throws NullPointerException {
        this.clear(); //pulisce la lista per sicurezza
        HTMLRequest htmlRequest =new HTMLRequest(url, "access_token=" + token+"&id="+idquiz); //richiesta con token
        String result=htmlRequest.getHTMLThread();
        Log.d("QueryList", idquiz);  //loggo per scopi di debug
        Log.d("TeacherQuestionresult", result);
        try{
            JSONObject jo= new JSONObject(result); //elaboro l'array di json e aggiungo gli elementi alla lista
            if(jo.has("success")&&!jo.getBoolean("success")){
                throw new NullPointerException("QuizNull");
            }
            JSONArray ja=jo.getJSONArray("questions");
            for(int i=0;i<ja.length();i++){
                jo=ja.getJSONObject(i);
                this.add(new Query(jo.getString("id"), jo.getString("body"), jo.getString("answer"), jo.getString("fakeAnswer1"), jo.getString("fakeAnswer2"), jo.getString("fakeAnswer3"), jo.getString("topic")));
            }
        } catch (JSONException e) {
            throw new NullPointerException("QuizNull");
        }
    }
    /**
     * @param in Parcel di ingresso
     */
    private QueryList(Parcel in){
        readFromParcel(in);
    } //Costruttore della parceable

    /**
     * @param in Parcel di ingresso
     */
    public void readFromParcel(Parcel in){
        this.clear(); //pulisce la lista per sicurezza
        in.readList(this,QueryList.class.getClassLoader()); //riempie la lista con gli elementi dal parceable
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this);
    }
}
