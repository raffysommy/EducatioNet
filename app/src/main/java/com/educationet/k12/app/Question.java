package com.educationet.k12.app;


import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.NoSuchElementException;
import java.util.Queue;

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
    private Queue<Query> queries;
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
     * Builder of interface
     * @param savedInstanceState Instanza salvata
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* Take the info from previous activity */
        Intent i = getIntent();
        Bundle extras=i.getExtras();
        this.utente = extras.getParcelable("utentec");
        if(i.hasExtra("argomenti"))
            this.argumentList=extras.getParcelable("argomenti");
        if(i.hasExtra("quiz")){
            queries= extras.getParcelable("quiz");
        }
        this.token = this.utente.getAccessToken();
        setContentView(R.layout.activity_question);
        toastview=getLayoutInflater().inflate(R.layout.toastlayout, (ViewGroup)findViewById(R.id.toastlayout));
        KGPrimary=new FontManager("KGPrimaryItalics",getAssets());
        Funnykid=new FontManager("FunnyKid",getAssets());
        impostafont();
        cambiadomanda();
        //empty score list
        scoreManager=new ScoreManager(token,this.getApplication());
    }

    /**
     * Handler of rotate
     * @param newConfig new orientation
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
     * On Application close remove all floating toast
     */
    @Override
    public void onDestroy(){
        super.onDestroy();
        if(t!=null){t.cancel();}
        System.gc();
    }
    /**
     * On Activity close remove all floating toast
     */
    public void onDetachedFromWindow(){
        super.onDetachedFromWindow();
        if(t!=null){t.cancel();}
    }
    /**
     * Set Font to text
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
     * Take the Question from backend
     * @return Question from backend
     * @throws NullPointerException Null Query
     * @see  NullPointerException
     * @see HTMLRequest
     */
    public Query request_data() {
        Query Domand = new Query();
        String result;
        JSONObject jo;
        HTMLRequest htmlRequest = new HTMLRequest(this.api, "access_token=" + this.token +"&topics="+this.argumentList.toString());
        try {
            result = htmlRequest.getHTMLThread();
            jo = new JSONObject(result);
            Domand = new Query(jo.getString("id"), jo.getString("body"), jo.getString("answer"), jo.getString("fakeAnswer1"), jo.getString("fakeAnswer2"), jo.getString("fakeAnswer3"), jo.getString("topic"));
            Log.d("id", jo.getString("id"));
            Log.d("topic", jo.getString("topic"));
        } catch (Exception e) {
            throw new NullPointerException("NullQuery");
        }
        return Domand;
    }

    /**
     * On Back Press show the result of save score in a separate Thread
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
     * Builder of Menu
     * @param menu Menu of activity
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
     * Handler of ActionBar
     * @param item Object of Menu
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
     * Procedure for change question
     */
    public void cambiadomanda(){
        try {
            if (queries != null) {
                this.Domanda = queries.remove();
            }
            else {
                this.Domanda = new Query(request_data());
            }
            this.Domanda.RandomQuery();
        }
        catch (NoSuchElementException e) {
            this.Domanda=new Query("Quiz Completed! Good Job :)");
            Log.d("QuestionHandler","QuizCompleted");
        }
        catch (NullPointerException e){
            this.Domanda=new Query("There are not more question");
            Log.d("QuestionHandler","NoMoreQuestion");
        }
        finally {
            impostabottoni();
        }
    }

    /**
     * Procedure for set button e question text
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
     * Set single Button
     * @param buttonid Id Button
     * @param risp  Answer
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
     * Check of Answer
     * @param buttonid Button Pressed
     * @return Answer Boolean result
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
     * Handler of click on answer button
     * @param v CurrentView
     */
    public void onClick1(View v) {
        Boolean esito=checkrisposta(v.getId());
        scoreManager.addScore(Domanda.getid_domanda(),esito);
        if (esito) { //if answer is correct set the toast with correct
            ((ImageView)toastview.findViewById(R.id.imagetoast)).setImageResource(R.drawable.toastright);
            cambiadomanda();//change the text of button with a new question
            findViewById(R.id.textView3).setVisibility(View.INVISIBLE); //set invisible help
            score=(TextView) findViewById(R.id.CorrectCnt); //re set score in actionbar
            correct.increment(); //inc the score
            score.setText(correct.StringValue());

        } else {//if answer is wrong set the toast with wrong
            ((ImageView)toastview.findViewById(R.id.imagetoast)).setImageResource(R.drawable.toastwrong);
            //need help show the help message
            findViewById(R.id.textView3).setVisibility(View.VISIBLE);
            score=(TextView) findViewById(R.id.WrongCnt); //inc e set action score
            wrong.increment();
            score.setText(wrong.StringValue());

        }

        if(t!=null){t.cancel();}
        t = new Toast(this);
        t.setDuration(Toast.LENGTH_SHORT);
        t.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        t.setView(toastview);
        t.show(); //set and show toast
    }

    /**
     * Handler of score click
     * @param v vista v
     */
    public void Score_click(View v){
        Intent i = new Intent("com.example.raffaele.testapp.Score_page");
        Bundle extras= new Bundle();
        extras.putParcelable("Correct", this.correct );
        extras.putParcelable("Wrong", this.wrong);
        i.putExtras(extras); //send score to activity
        startActivity(i);
    }

    /**
     * Handler help
     * @param v Current View
    */
    public void Help_click(View v){
        //click here bring to explain of arguments
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://k12-api.mybluemix.net/php/learnTopic.php?topic="+Domanda.getTopic()));
        startActivity(browserIntent);
    }
}
