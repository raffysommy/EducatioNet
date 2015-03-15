package com.example.raffaele.testapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;

public class Question extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        cambiatestobottoni();
        //cliccando sulla textbox di aiuto, si riporta al link per la spiegazione dell' argomento
        findViewById(R.id.textView3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://k12-api.mybluemix.net/php/learnTopic.php?topic=math"));
                startActivity(browserIntent);

            }
        });
  }
    private RadioGroup radiogroup;
    private RadioButton radioselected;
    private Button btnDisplay;
    public Query request_data() {
        Query Domand=new Query();
        String result = "";
        String url = "http://mysql-raffysommy-1.c9.io/k12api/questions.php";
        HTMLRequest htmlRequest=new HTMLRequest(url);
        try {
            result =htmlRequest.getHTMLTread();
            JSONArray ja = new JSONArray(result.toString());
            JSONObject jo = (JSONObject) ja.get(0);

            Domand = new Query(jo.getString("Domanda"),jo.getString("Risposta"),jo.getString("Risposte_Falsa1"),jo.getString("Risposte_Falsa2"),jo.getString("Risposte_Falsa3"));
        } catch (Exception e) {
               e.printStackTrace();
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
    public void cambiatestobottoni(){
        Query domand=new Query(request_data());
        TextView view = (TextView) findViewById(R.id.domanda);
        view.setText(domand.getDomanda());
        domand.RandomQuery();
        ArrayList<Integer> index = new ArrayList<>();
        index.add(0);index.add(1);index.add(2);index.add(3);
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
    public void addListenerOnButton(final String risposta) {
        final Intent i=new Intent(this,Question.class);
        radiogroup = (RadioGroup) findViewById(R.id.radioGroup);
        btnDisplay = (Button) findViewById(R.id.button2);//commentopush
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
                    if (radioselected.getText().equals(risposta)) {//risposta esatta
                        Toast.makeText(getApplicationContext(), "Right :)", Toast.LENGTH_SHORT).show();
                        //startActivity(i);
                        radioselected.setChecked(false);//reimposta il bottone premuto
                        cambiatestobottoni();//cambia il testo dei bottoni con una nuova domanda
                        //non ha bisogno di suggerimenti
                        findViewById(R.id.textView3).setVisibility(View.INVISIBLE);
                    } else {//risposta sbagliata
                        Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT).show();
                        //ha bisogno di suggerimenti
                        findViewById(R.id.textView3).setVisibility(View.VISIBLE);
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
