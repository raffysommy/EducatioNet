package com.example.raffaele.testapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.net.Uri;
import android.os.Build;
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

import static android.view.View.OnClickListener;

public class Question extends ActionBarActivity {
    private String token = new String();
    private final String api = new String("https://k12-api.mybluemix.net/api/question/random");
    private User utente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* prendo token di accesso passato */
        Intent i = getIntent();
        Bundle extras=i.getExtras();
        this.utente = extras.getParcelable("utentec");
        this.token = this.utente.getAccessToken();

        setContentView(R.layout.activity_question);
        cambiatestobottoni();
        Score_click();

        //cliccando sulla textbox di aiuto, si riporta al link per la spiegazione dell' argomento
        findViewById(R.id.textView3).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://k12-api.mybluemix.net/php/learnTopic.php?topic=math"));
                startActivity(browserIntent);


            }
        });
    }

    private Button buttona;
    private Button buttonb;
    private Button buttonc;
    private Button buttond;
    private Query Domanda;
    //variabili per contatori Score
    private Score correct = new Score();
    private Score wrong = new Score();
    private static Button Score_btn;


    public Query request_data() {
        Query Domand = new Query();
        String result = "";
        JSONObject jo;
        JSONArray ja;
        HTMLRequest htmlRequest = new HTMLRequest(this.api, "access_token=" + this.token);
        try {
            result = htmlRequest.getHTMLThread();
            ja = new JSONArray(result.toString());
            jo = (JSONObject)ja.get(0);

            Domand = new Query(jo.getString("Domanda"), jo.getString("Risposta"), jo.getString("Risposte_Falsa1"), jo.getString("Risposte_Falsa2"), jo.getString("Risposte_Falsa3"));
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

    public void cambiatestobottoni() {
        this.Domanda = new Query(request_data());
        TextView view = (TextView) findViewById(R.id.domanda);
        view.setText(this.Domanda.getDomanda());
        this.Domanda.RandomQuery();
        ArrayList<Integer> index = new ArrayList<>();
        index.add(0);
        index.add(1);
        index.add(2);
        index.add(3);
        CambiaBottone(R.id.Risposta1, Domanda.getRisposteprob().get(0));
        CambiaBottone(R.id.Risposta2, Domanda.getRisposteprob().get(1));
        CambiaBottone(R.id.Risposta3, Domanda.getRisposteprob().get(2));
        CambiaBottone(R.id.Risposta4, Domanda.getRisposteprob().get(3));
    }

    public void CambiaBottone(int buttonid, String risp) {
        Button button = (Button) findViewById(buttonid);
        String regtex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        RegEx regex = new RegEx(regtex);
        if (regex.Match(risp)) {
            HTMLDrawable htmlimg = new HTMLDrawable(risp);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                button.setBackground(htmlimg.getimg());
            } else {
                button.setBackgroundDrawable(htmlimg.getimg());
            }
            button.setText(" ");
            button.setHint(risp);
        } else {
            button.setBackgroundResource(R.drawable.abc_btn_check_to_on_mtrl_000);
            button.setText(risp);
        }
    }

    public boolean checkrisposta(int buttonid) {
        Button buttonpressed = (Button) findViewById(buttonid);
        if (buttonpressed.getText().equals(this.Domanda.getRisposta())) {
            return true;
        } else {
            if (!(buttonpressed.getHint() == null)) {
                return buttonpressed.getHint().equals(this.Domanda.getRisposta());
            }
        }
        return false;
    }

    public void onClick1(View v) {


        if (checkrisposta(v.getId())) {
            Toast.makeText(getApplicationContext(), "Right :)", Toast.LENGTH_SHORT).show();
            cambiatestobottoni();//cambia il testo dei bottoni con una nuova domanda
            findViewById(R.id.textView3).setVisibility(View.INVISIBLE);
            correct.increment();


        } else {//risposta sbagliata
            Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT).show();
            //ha bisogno di suggerimenti
            findViewById(R.id.textView3).setVisibility(View.VISIBLE);

            wrong.increment();

        }
    }
    public void Score_click(){
        Score_btn= (Button) findViewById(R.id.Score_button);
        Score_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v){
                        Intent i=new Intent("com.example.raffaele.testapp.Score_page");
                        startActivity(i);
                    }
                }
        );

    }


}
    /*
    public void addListenerOnButton(final String risposta) {
        final Intent i=new Intent(this,Question.class);
        radiogroup = (RadioGroup) findViewById(R.id.radioGroup);
        btnDisplay = (Button) findViewById(R.id.button2);//commentopush
        btnDisplay = (Button) findViewById(R.id.button2);
        //Dichiarazione variabili contatori e li imposto uguali a 0
        final TextView RightCTxt=(TextView) findViewById(R.id.RightC);
        TextView WrongCTxt=(TextView) findViewById(R.id.WrongC);
        RightCTxt.setText("0");
        WrongCTxt.setText("0");
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
                        RightCTxt.setText(+1);
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
*/
