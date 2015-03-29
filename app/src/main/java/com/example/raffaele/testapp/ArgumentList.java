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
 */
public class ArgumentList extends ArrayList<Argument> implements Parcelable{
    private final String url = "http://mysql-raffysommy-1.c9.io/api/topic/list";
    public static final Creator<ArgumentList> CREATOR= new Creator<ArgumentList>(){
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
    }
    private ArgumentList(Parcel in ){
        readFromParcel(in);
    }
    public void readFromParcel(Parcel in){
        this.clear();
        in.readList(this,ArgumentList.class.getClassLoader());
    }
    public void getHttp(String token){
        this.clear();
        HTMLRequest htmlRequest =new HTMLRequest(url, "access_token=" + token);
        String result=htmlRequest.getHTMLThread();
        Log.d("token",token);
        try{
            JSONArray ja=new JSONArray(result);
            JSONObject jo;
            for(int i=0;i<ja.length();i++){
                jo=ja.getJSONObject(i);
                this.add(new Argument(jo.getString("name"), jo.getString("description")));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String toString(){
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
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this);
    }
}
