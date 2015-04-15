package com.example.raffaele.testapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.TwoStatePreference;
import android.support.v7.app.ActionBarActivity;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Score_page extends ActionBarActivity {
    private Score correct, wrong;
    private final String api = "https://mysql-raffysommy-1.c9.io/api/score/total";
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_page);
        Intent i = getIntent();
        Bundle extras=null;
        if(i.hasExtra("Correct")&&i.hasExtra("Wrong")) {
            extras=i.getExtras();
            this.correct = extras.getParcelable("Correct");
            this.wrong = extras.getParcelable("Wrong");
            //imposto valori di nome,cognome e scuola in view
            ((TextView) findViewById(R.id.CorrectC)).setText(correct.StringValue());
            ((TextView) findViewById(R.id.WrongC)).setText(wrong.StringValue());
            ((TextView) findViewById(R.id.AnsweredC)).setText(correct.StringValue());
        }
        else if(i.hasExtra("utentec")){
            extras=i.getExtras();
            //((TextView) findViewById(R.id.ScoreName)).setText("Global Score");
            user=extras.getParcelable("utentec");
            String result="";
            JSONObject jo;
            HTMLRequest htmlRequest = new HTMLRequest(this.api, "access_token=" + this.user.getAccessToken() +"&user="+this.user.getUsername());
            result=htmlRequest.getHTMLThread();
            if(result==null)
                   return;
            try {
                jo=new JSONObject(result);
                ((TextView)findViewById(R.id.CorrectC)).setText(jo.getString("correct"));
                ((TextView)findViewById(R.id.WrongC)).setText(jo.getString("wrong"));
                ((TextView)findViewById(R.id.AnsweredC)).setText(jo.getString("answered"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_argument__page); //al cambiamento della configurazione dello schermo refresha il layout

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

