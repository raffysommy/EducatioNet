package com.example.raffaele.testapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Welcome_student extends ActionBarActivity {
    private User utente;
    private ArgumentList argumentList;
    static final int MSG_SAVE_SCORE_REQUEST = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_student);
        //prendo l'oggetto passato alla view
        Intent i = getIntent();
        Bundle extras=i.getExtras();
        this.utente = extras.getParcelable("utentec");
        //imposto valori di nome,cognome e scuola in view
        ((TextView)findViewById(R.id.first_name)).setText(utente.getFirstName());
        ((TextView)findViewById(R.id.last_name)).setText(utente.getLastName());
        ((TextView)findViewById(R.id.school)).setText(utente.getSchool());
        argumentList=null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (0) : {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle extras = data.getExtras();
                    this.argumentList = extras.getParcelable("argomenti");
                }
            }   break;
            //se premo su back dal questionario, mostro risultato del salvataggio scores
            case (1) : {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle b = data.getExtras();
                    if (b != null) {
                        String msg = (String) b.getSerializable("msg");
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                }
            } break;
        }
    }
    //richiamo view Question
    public void toQuestion(View v) {
        Intent i = new Intent(this, Question.class);
        Bundle extras=new Bundle();
        //passo l'oggetto user alla prossima view
        if(argumentList!=null){
            extras.putParcelable("argomenti",this.argumentList);
        }
        extras.putParcelable("utentec",this.utente);
        i.putExtras(extras);
        startActivityForResult(i, MSG_SAVE_SCORE_REQUEST);

    }


    public void toArgument(View v) {
        Intent i = new Intent(this, Argument_Page.class);
        Bundle extras=new Bundle();
        //passo l'oggetto user alla prossima view
        if(argumentList!=null){
            extras.putParcelable("argomenti",this.argumentList);
        }
        extras.putParcelable("utentec",this.utente);
        i.putExtras(extras);
        startActivityForResult(i, 0);

    }


}
