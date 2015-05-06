package com.educationet.k12.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * Created by K12-Dev-Team on 08/04/2015.
 */

/**
 * Manager Score
 * @author K12-Dev-Team
 * @version 0.2
 */
public class ScoreManager extends ArrayList<String[]> {

    @SuppressLint("SimpleDateFormat")
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private final String url_score = "https://k12-api.mybluemix.net/api/question/score";
    private final String access_token;
    private final Application application;

    /**
     * @param access_token access Token to backend
     * @param application Application caller
     */
    public ScoreManager(String access_token, Application application){
        super();
        this.access_token = access_token;
        this.application = application;
    }

    /**
     * adds the score to the array
     * @param id_domanda Question
     * @param result user result
     */
    public void addScore(String id_domanda,Boolean result){
        Date date = new Date();
        int val = result? 1 : 0;
        this.add(new String[]{id_domanda, String.valueOf(val), dateFormat.format(date)});
    }

    /**
     * Score saved
     */
    public void saveScore() {
        new ScoreSender().execute(this);
    }

    /**
     * Class that save the score
     */
    private class  ScoreSender extends AsyncTask<ScoreManager,Void,String>{
        /**
         * @param params Arraylist of Score
         * @return result of the operation
         * */
        @Override
        protected String doInBackground(ScoreManager... params) {
            //from ArrayList to JSON
            JSONArray jsonA;
            jsonA = new JSONArray(params[0]);
            Log.i("JSON SCORE=>", jsonA.toString());
            //request http to backend
            HTMLRequest dl = new HTMLRequest(url_score,"access_token=" + access_token +"&scores=" + jsonA.toString());
            //invoke api
            String result = dl.getHTML();
            String msg;
            try {
                msg = new JSONObject(result).getString("message");
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
            return msg;
        }

        /**
         * @param msg Message to display
         */
        protected void onPostExecute(String msg) {
            Toast.makeText(application.getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        }
    }
}
