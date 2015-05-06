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
    private final String url = "https://k12-api.mybluemix.net/api/questionnaire/view"; //Url of connection to backend
    public static final Parcelable.Creator<QueryList> CREATOR= new Parcelable.Creator<QueryList>(){ //creator of argumentlist
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
        this.clear(); //clean the list for security
        HTMLRequest htmlRequest =new HTMLRequest(url, "access_token=" + token+"&id="+idquiz); //request with token
        String result=htmlRequest.getHTMLThread();
        Log.d("QueryList", idquiz);
        Log.d("TeacherQuestionresult", result);
        try{
            JSONObject jo= new JSONObject(result); //elaborate json array and add elements to the list
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
     * @param in Parcel of input
     */
    private QueryList(Parcel in){
        readFromParcel(in);
    } //Constructor of parceable

    /**
     * @param in Parcel of input
     */
    public void readFromParcel(Parcel in){
        this.clear(); //clean the list for security
        in.readList(this,QueryList.class.getClassLoader()); //fills the list with the elements from parceable
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
