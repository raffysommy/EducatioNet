package com.example.raffaele.testapp;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Question extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        Query domand=new Query(request_data());
        TextView view = (TextView) findViewById(R.id.domanda);
        view.setText(domand.getDomanda());
        domand.RandomQuery();
        RadioButton buttona = (RadioButton) findViewById(R.id.radioButton);
        buttona.setText(domand.getRisposteprob().get(0));
        RadioButton buttonb = (RadioButton) findViewById(R.id.radioButton2);
        buttonb.setText(domand.getRisposteprob().get(1));
        RadioButton buttonc = (RadioButton) findViewById(R.id.radioButton3);
        buttonc.setText(domand.getRisposteprob().get(2));
        RadioButton buttond = (RadioButton) findViewById(R.id.radioButton4);
        buttond.setText(domand.getRisposteprob().get(3));
        addListenerOnButton(domand.getRisposta());

  }
    private RadioGroup radiogroup;
    private RadioButton radioselected;
    private Button btnDisplay;
    public Query request_data() {
        Query Domand=new Query();
        InputStream is = null;
        String result = "";
        String url = "http://k12-api.mybluemix.net/php/questions.php";
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);
        ResponseHandler<String> handler = new BasicResponseHandler();
        try {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            result = httpclient.execute(request, handler);
            httpclient.getConnectionManager().shutdown();
            JSONArray ja = new JSONArray(result.toString());
            JSONObject jo = (JSONObject) ja.get(0);
            //Domand.setDomanda(jo.getString("Domanda"));
            //Domand.setRisposta(jo.getString("Risposta"));
            //Domand.setRispostarray(jo.getString("Risposta"),jo.getString("Risposte_Falsa1"),jo.getString("Risposte_Falsa2"),jo.getString("Risposte_Falsa3"));
            //String[] rispostepro = {jo.getString("Risposte_Falsa1"), jo.getString("Risposte_Falsa2"), jo.getString("Risposte_Falsa3"), jo.getString("Risposta")};
            //Domand.setRisposteprob(rispostepro);
            Domand = new Query(jo.getString("Domanda"),jo.getString("Risposta"),jo.getString("Risposte_Falsa1"),jo.getString("Risposte_Falsa2"),jo.getString("Risposte_Falsa3"));
        } catch (Exception e) {
            //Domand.setDomanda(e.toString());
        }
        return Domand;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question, menu);
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

    public void addListenerOnButton(final String risposta) {
        final Intent i=new Intent(this,Question.class);
        radiogroup = (RadioGroup) findViewById(R.id.radioGroup);
        btnDisplay = (Button) findViewById(R.id.button2);
        btnDisplay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // get selected radio button from radioGroup
                int selectedId = radiogroup.getCheckedRadioButtonId();
                // check if an answer has given
                if (selectedId != -1) {
                    // find the radiobutton by returned id
                    radioselected = (RadioButton) findViewById(selectedId);
                    if (radioselected.getText().equals(risposta)) {
                        Toast.makeText(getApplicationContext(), "Right :)", Toast.LENGTH_SHORT).show();
                        startActivity(i);
                    } else {
                        Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please, choose an answer!", Toast.LENGTH_SHORT).show();
                }


            }

        });

    }

}
