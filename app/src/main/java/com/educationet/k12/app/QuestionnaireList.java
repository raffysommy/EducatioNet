package com.educationet.k12.app;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * List of Questionnaire
 * @author K12-Dev-Team
 * @version 0.1
 * Created by K12-Dev-Team on 02/05/2015.
 */
public class QuestionnaireList extends ArrayList<Questionnaire> {
    private final String url = "http://k12-api.mybluemix.net/api/questionnaire/list";

    /**
     * Get list from backend
     * @param token Auth Token
     */
    public void getHttp(String token) { //method of request to backend
        this.clear(); //cleans  the list for  security
        HTMLRequest htmlRequest = new HTMLRequest(url, "access_token=" + token); //request with token
        String result = htmlRequest.getHTMLThread();
        Log.d("token", token);
        try {
            JSONArray ja = new JSONArray(result); //elaborate the array of json and add elements to list
            JSONObject jo;
            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                this.add(new Questionnaire(jo.getString("id"), jo.getString("name")));
            }
        } catch (JSONException e) {
            e.printStackTrace(); // in case of exceptions,prints the call list (The json exceptions are not recoverable but enables the rest of the execution)
        }
    }
}
