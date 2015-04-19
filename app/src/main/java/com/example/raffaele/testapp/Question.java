package com.example.raffaele.testapp;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import static android.view.View.OnClickListener;

public class Question extends ActionBarActivity {
    private String token = "";
    private final String api = "https://mysql-raffysommy-1.c9.io/api/question/random";
    private final String apiDoc="https://mysql-raffysommy-1.c9.io/api/teacher/help";
    private User utente;
    private ArgumentList argumentList=new ArgumentList();
    private ScoreManager scoreManager=null;
    private DrawableManager draw=new DrawableManager();
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
        ((TextView) findViewById(R.id.domanda)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/FunnyKid.ttf"));
        cambiadomanda();
        //cliccando sulla textbox di aiuto, si riporta al link per la spiegazione dell' argomento
        findViewById(R.id.textView3).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://k12-api.mybluemix.net/php/learnTopic.php?topic=math"));
                startActivity(browserIntent);
            }
        });
        //svuoto precedente lista di scores
        scoreManager=new ScoreManager(token,this.getApplication());
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_question); //al cambiamento della configurazione dello schermo refresha il layout
        ((TextView) findViewById(R.id.domanda)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/FunnyKid.ttf"));
        impostabottoni();
    }

    public void opendialog(View view, final String id_question){
        LayoutInflater linf = LayoutInflater.from(this);
        final View inflator =linf.inflate(R.layout.dialog_help_wanted,null);
        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Send question to Teacher");
        alertDialogBuilder.setView(inflator);
        final EditText editText= (EditText) inflator.findViewById(R.id.textquest);
        alertDialogBuilder
                .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String question = editText.getText().toString().trim();
                        HTMLRequest htmlRequest = new HTMLRequest(apiDoc, "idquestion=" + id_question + "&question=" + question + "&user" + utente.getUsername() + "&access_token" + utente.getAccessToken());
                        if (Boolean.valueOf(htmlRequest.getHTMLThread())) {
                            Toast.makeText(getApplicationContext(), "Question Sent", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error: Question not Sent", Toast.LENGTH_SHORT).show();
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
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
        HTMLRequest htmlRequest = new HTMLRequest(this.api, "access_token=" + this.token +"&topics="+this.argumentList.toString());
        try {
            result = htmlRequest.getHTMLThread();
            jo = new JSONObject(result);
            Domand = new Query(jo.getString("id"),jo.getString("body"), jo.getString("answer"), jo.getString("fakeAnswer1"), jo.getString("fakeAnswer2"), jo.getString("fakeAnswer3"));
            Log.d("id",Domanda.getid_domanda());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Domand;
    }

    //premo back? mostro risultato salvataggio nell'activity chiamata
    public void onBackPressed() {
        this.scoreManager.saveScore();
        setResult(Activity.RESULT_OK);
        finish();
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
        if(id==R.id.CorrectCnt||id==R.id.CorrectImg||id==R.id.WrongCnt||id==R.id.WrongImg){
            this.Score_click(this.getCurrentFocus());
        }
        if (id==R.id.help_wanted){
            opendialog(this.getCurrentFocus(),Domanda.getid_domanda());
        }
        return super.onOptionsItemSelected(item);
    }
    public void cambiadomanda(){
        this.Domanda = new Query(request_data());
        this.Domanda.RandomQuery();
        impostabottoni();
    }
    public void impostabottoni() {
        TextView view = (TextView) findViewById(R.id.domanda);
        view.setText(this.Domanda.getDomanda());
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
            /*HTMLDrawable htmlimg = new HTMLDrawable(risp);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                button.setBackground(htmlimg.getimg());
            } else {
                button.setBackgroundDrawable(htmlimg.getimg());
            }*/

            draw.setDrawable(risp,findViewById(buttonid));
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
        Boolean esito=checkrisposta(v.getId());
        scoreManager.addScore(Domanda.getid_domanda(),esito);
        if (esito) {
            Toast.makeText(getApplicationContext(), "Right :)", Toast.LENGTH_SHORT).show();
            cambiadomanda();//cambia il testo dei bottoni con una nuova domanda
            findViewById(R.id.textView3).setVisibility(View.INVISIBLE);
            score=(TextView) findViewById(R.id.CorrectCnt);
            correct.increment();
            score.setText(correct.StringValue());

        } else {//risposta sbagliata
            Toast.makeText(getApplicationContext(), "Wrong!", Toast.LENGTH_SHORT).show();
            //ha bisogno di suggerimenti
            findViewById(R.id.textView3).setVisibility(View.VISIBLE);
            score=(TextView) findViewById(R.id.WrongCnt);
            wrong.increment();
            score.setText(wrong.StringValue());

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
