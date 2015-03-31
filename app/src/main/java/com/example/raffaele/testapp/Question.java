package com.example.raffaele.testapp;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

import static android.view.View.OnClickListener;

public class Question extends ActionBarActivity {
    private String token = "";
    private final String api = "https://mysql-raffysommy-1.c9.io/api/question/random";
    private User utente;
    private ArgumentList argumentList=new ArgumentList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* prendo token di accesso passato */
        Intent i = getIntent();
        Bundle extras=i.getExtras();
        this.utente = extras.getParcelable("utentec");
        if(extras.getParcelable("argomenti")!=null)
            this.argumentList=extras.getParcelable("argomenti");
        this.token = this.utente.getAccessToken();
        setContentView(R.layout.activity_question);
        cambiatestobottoni();
        //cliccando sulla textbox di aiuto, si riporta al link per la spiegazione dell' argomento
        findViewById(R.id.textView3).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://k12-api.mybluemix.net/php/learnTopic.php?topic=math"));
                startActivity(browserIntent);


            }
        });
    }
    private Query Domanda;
    //variabili per contatori Score
    private Score correct = new Score();
    private Score wrong = new Score();
    private TextView score;
    public Query request_data() {
        Query Domand = new Query();
        String result;
        JSONObject jo;
        Log.d("append", this.argumentList.toString());
        HTMLRequest htmlRequest = new HTMLRequest(this.api, "access_token=" + this.token +"&Topics="+this.argumentList.toString());
        try {
            result = htmlRequest.getHTMLThread();
            jo = new JSONObject(result);
            Domand = new Query(jo.getString("body"), jo.getString("answer"), jo.getString("fakeAnswer1"), jo.getString("fakeAnswer2"), jo.getString("fakeAnswer3"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Domand;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_question, menu);
        return super.onCreateOptionsMenu(menu);
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
        if(id==R.id.Correct||id==R.id.CorrectIMG||id==R.id.Wrong||id==R.id.WrongImg){
            this.Score_click(this.getCurrentFocus());
        }
        return super.onOptionsItemSelected(item);
    }

    public void cambiatestobottoni() {
        this.Domanda = new Query(request_data());
        TextView view = (TextView) findViewById(R.id.domanda);
        view.setText(this.Domanda.getDomanda());
        this.Domanda.RandomQuery();
        ArrayList<Integer> index = new ArrayList<>(); //TODO:Ma l'array list integer serve davvero?
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
            score=(TextView) findViewById(R.id.Correct);
            correct.increment();
            score.setText("Correct: "+ correct.toString());

        } else {//risposta sbagliata
            Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT).show();
            //ha bisogno di suggerimenti
            findViewById(R.id.textView3).setVisibility(View.VISIBLE);
            score=(TextView) findViewById(R.id.Wrong);
            wrong.increment();
            score.setText("Wrong: "+ wrong.toString());

        }

    }

    public void Score_click(View v){
       Intent i = new Intent("com.example.raffaele.testapp.Score_page");
        Bundle extras= new Bundle();
        extras.putParcelable("Correct", this.correct );
        extras.putParcelable("Wrong", this.wrong);
        i.putExtras(extras);
       startActivity(i);
      }




}
//Todo: Va aggiunta una gestione degli argomenti per domanda in modo da poter incrementare anche delle variabili score definite per argomento  (forse è il caso di inserire un oggetto Score in arguments)