package com.educationet.k12.app;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/***
 * Created by K12-Dev-Team on 25/03/2015.
 * * This class extends the argument arraylist of implementing the interface parceable
   * And a method to return the data in string with the format json
   * It also implements a method of request arguments to the database
 */

/**
 * Array of arguments
 * @version 0.1
 * @author K12-Dev-Team
 */
public class ArgumentList extends ArrayList<Argument> implements Parcelable{
    private final String url = "https://k12-api.mybluemix.net/api/topic/list"; //Url of connection to backend
    public static final Creator<ArgumentList> CREATOR= new Creator<ArgumentList>(){ //creator of the argumentlist
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
     * Constructor empty
     */
    public ArgumentList(){
        super();
    }

    /**
     * @param in Parcel of input
     */
    private ArgumentList(Parcel in ){
        readFromParcel(in);
    } //Constructor of parceable

    /**
     * @param in Parcel of input
     */
    public void readFromParcel(Parcel in){
        this.clear(); //clean the list for security
        in.readList(this,ArgumentList.class.getClassLoader()); //fills the list with the elements from parceable
    }

    /**
     * Request list arguments to backend
     * @param token Token of authentication to backend
     */
    public void getHttp(String token){ //method of request to backend
        this.clear(); //clean the list for security
        HTMLRequest htmlRequest =new HTMLRequest(url, "access_token=" + token); //request with token
        String result=htmlRequest.getHTMLThread();
        Log.d("token",token);
        try{
            JSONArray ja=new JSONArray(result); //elaborate json array and add the elements to the list
            JSONObject jo;
            for(int i=0;i<ja.length();i++){
                jo=ja.getJSONObject(i);
                this.add(new Argument(jo.getString("name"), jo.getString("description")));
            }
        } catch (JSONException e) {
            e.printStackTrace(); /// in case of exceptions print the call list (The exceptions json are not recoverable but enables the continuous execution)
        }
    }

    /**
     * Method toString
     * @return Json encoded string output
     */
    public String toString(){ //method toString with output style JSON
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
     * @return return ArrayList of only selected elements
     */
    public ArrayList<String> toArrayString(){ // method to return an arraylist of strings of the selected items
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
     * @param dest Parcel of destionation
     * @param flags (optional)
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this);
    } //write the list in Parceable
}
