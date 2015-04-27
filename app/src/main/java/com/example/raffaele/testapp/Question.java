package com.example.raffaele.testapp;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

/**
 * @author K12-Dev-Team
 * @version 0.4
 * @see android.app.Activity
 */

public class Question extends ActionBarActivity {
    private String token = "";
    private final String api = "https://k12-api.mybluemix.net/api/question/random";
    private final String apiDoc="https://k12-api.mybluemix.net/api/teacher/help";
    private User utente;
    private ArgumentList argumentList=new ArgumentList();
    private ScoreManager scoreManager=null;
    private final DrawableManager draw=new DrawableManager();
    private final BackgroundHandler backgroundHandler=new BackgroundHandler(R.drawable.risposta1bg,R.drawable.risposta2bg,R.drawable.risposta3bg, R.drawable.risposta4bg);
    private View toastview=null;
    private Query Domanda;
    //variabili per contatori Score
    private final Score correct = new Score();
    private final Score wrong = new Score();
    private TextView score;
    private FontManager KGPrimary=null;
    private FontManager Funnykid;
    private Toast t;
    private Menu _menu = null;
    /**
     * Costruttore dell'interfaccia
     * @param savedInstanceState Instanza salvata
     */
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
        toastview=getLayoutInflater().inflate(R.layout.toastlayout, (ViewGroup)findViewById(R.id.toastlayout));
        KGPrimary=new FontManager("KGPrimaryItalics",getAssets());
        Funnykid=new FontManager("FunnyKid",getAssets());
        impostafont();
        cambiadomanda();
        //svuoto precedente lista di scores
        scoreManager=new ScoreManager(token,this.getApplication());
    }

    /**
     * Handler del rotate
     * @param newConfig nuova orientazione
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_question); //al cambiamento della configurazione dello schermo refresha il layout
        if (_menu!=null)
            this.onCreateOptionsMenu(_menu);
        impostafont();
        impostabottoni();
    }

    /**
     * Metodo di destroy per annullare le toast dopo che l'application è stata chiusa
     */
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(t!=null){t.cancel();}
        System.gc();
    }
    /**
     * Metodo di destroy per annullare le toast dopo che l'activity è stata chiusa
     */
    public void onDetachedFromWindow(){
        super.onDetachedFromWindow();
        if(t!=null){t.cancel();}
    }
    /**
     * Imposta i font al testo
     */
    public void impostafont(){
        try {
            KGPrimary.setFont(findViewById(R.id.domanda));
            KGPrimary.setFont(findViewById(R.id.textView3));
            Funnykid.setFont(findViewById(R.id.Risposta1));
            Funnykid.setFont(findViewById(R.id.Risposta2));
            Funnykid.setFont(findViewById(R.id.Risposta3));
            Funnykid.setFont(findViewById(R.id.Risposta4));
        } catch (UnknownTypeException e) {
            e.logException();
            e.printStackTrace();
        }
    }


    /**
     * Recupera la domanda dal backend
     * @return Domanda dal backend
     */
    public Query request_data() {
        Query Domand = new Query();
        String result;
        JSONObject jo;
        HTMLRequest htmlRequest = new HTMLRequest(this.api, "access_token=" + this.token +"&topics="+this.argumentList.toString());
        try {
            result = htmlRequest.getHTMLThread();
            if(result!=null) {
                jo = new JSONObject(result);
                Domand = new Query(jo.getString("id"), jo.getString("body"), jo.getString("answer"), jo.getString("fakeAnswer1"), jo.getString("fakeAnswer2"), jo.getString("fakeAnswer3"), jo.getString("topic"));
                Log.d("id", jo.getString("id"));
                Log.d("topic", jo.getString("topic"));
            } else{
                Domand=new Query("There are not more question");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Domand;
    }

    /**
     * Alla pressione di back mostro risultato salvataggio nell'activity chiamata
     * attraverso un Thread separato
     */
    public void onBackPressed() {
        this.scoreManager.saveScore();
        Intent i=new Intent(this, Welcome_student.class);
        Bundle extras=new Bundle();
        extras.putParcelable("utentec",this.utente);
        i.putExtras(extras);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    /**
     * Costruttore del menu
     * @param menu Menu dell'activity
     * @return Status
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if(menu!=null)
            menu.clear();
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_question, menu);
        _menu=menu;
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Gestore della ActionBar
     * @param item Oggetto del menù
     * @return Status
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if(id==R.id.CorrectCnt||id==R.id.CorrectImg||id==R.id.WrongCnt||id==R.id.WrongImg){ //Open score page
            this.Score_click(this.getCurrentFocus());
        }
        if (id==R.id.help_wanted){ //Open Help dialog
            //opendialog(this.getCurrentFocus(),Domanda.getid_domanda());
            Intent i = new Intent(this, ChatPage.class);
            Bundle extras=new Bundle();
            extras.putParcelable("utentec",this.utente);
            i.putExtras(extras);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Procedura di cambio domanda
     */
    public void cambiadomanda(){
        this.Domanda = new Query(request_data());
        this.Domanda.RandomQuery();
        impostabottoni();
    }

    /**
     * Procedura di impostazione dei bottoni e della domanda
     */
    public void impostabottoni() {
        TextView view = (TextView) findViewById(R.id.domanda);
        view.setText(this.Domanda.getDomanda());
        CambiaBottone(R.id.Risposta1, Domanda.getRisposteprob().get(0));
        CambiaBottone(R.id.Risposta2, Domanda.getRisposteprob().get(1));
        CambiaBottone(R.id.Risposta3, Domanda.getRisposteprob().get(2));
        CambiaBottone(R.id.Risposta4, Domanda.getRisposteprob().get(3));
    }

    /**
     * Impostazione singolo bottone
     * @param buttonid Id Bottone
     * @param risp  risposta
     */
    public void CambiaBottone(int buttonid, String risp) {
        Button button = (Button) findViewById(buttonid);
        if(risp.isEmpty()){
            button.setEnabled(false);
        }
        String regtex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
        RegEx regex = new RegEx(regtex);
        if (regex.Match(risp)) { //se è un immagine la imposto come background attraverso il Drawablemanager
            draw.setDrawable(risp,findViewById(buttonid));
            button.setText(" ");
            button.setHint(risp);
        } else { //altrimenti imposto il testo e i bottoni di default
            backgroundHandler.setbg(button);
            button.setText(risp);
        }
    }

    /**
     * Controllo della riposta
     * @param buttonid Bottone Premuto
     * @return Esattezza risposta
     */
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

    /**
     * Handler del click sulle risposte
     * @param v vista attuale
     */
    public void onClick1(View v) {
        Boolean esito=checkrisposta(v.getId());
        scoreManager.addScore(Domanda.getid_domanda(),esito);
        if (esito) { //se l'esito è positivo imposto la toast con l'immagine correct
            ((ImageView)toastview.findViewById(R.id.imagetoast)).setImageResource(R.drawable.toastright);
            cambiadomanda();//cambia il testo dei bottoni con una nuova domanda
            findViewById(R.id.textView3).setVisibility(View.INVISIBLE); //imposto l'help invisibile
            score=(TextView) findViewById(R.id.CorrectCnt); //re-imposto lo score nella actionbar
            correct.increment(); //incremento lo score
            score.setText(correct.StringValue());

        } else {//se l'esito è negativo imposto la toast con l'immagine wrong
            ((ImageView)toastview.findViewById(R.id.imagetoast)).setImageResource(R.drawable.toastwrong);
            //ha bisogno di suggerimenti imposto l'help visibile
            findViewById(R.id.textView3).setVisibility(View.VISIBLE);
            score=(TextView) findViewById(R.id.WrongCnt); //incremento e imposto il contatore nella actionbar
            wrong.increment();
            score.setText(wrong.StringValue());

        }

        if(t!=null){t.cancel();}
        t = new Toast(this);
        t.setDuration(Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        t.setView(toastview);
        t.show(); //imposto e mostro la toast
    }

    /**
     * Handler dello score click
     * @param v vista v
     */
    public void Score_click(View v){
        Intent i = new Intent("com.example.raffaele.testapp.Score_page");
        Bundle extras= new Bundle();
        extras.putParcelable("Correct", this.correct );
        extras.putParcelable("Wrong", this.wrong);
        i.putExtras(extras); //passo lo score all'activity
        startActivity(i);
    }

    /**
     * Handler help
     * @param v Vista attuale
     */
    public void Help_click(View v){
        //cliccando sulla textbox di aiuto, si riporta al link per la spiegazione dell' argomento
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://k12-api.mybluemix.net/php/learnTopic.php?topic="+Domanda.getTopic()));
        startActivity(browserIntent);
    }
}
