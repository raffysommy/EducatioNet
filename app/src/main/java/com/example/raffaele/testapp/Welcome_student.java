package com.example.raffaele.testapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Welcome_student extends ActionBarActivity {
    User utente;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_student);
        //prendo l'oggetto passato alla view
        Intent i = getIntent();
        Bundle extras=i.getExtras();
        this.utente = extras.getParcelable("utentec");
        //imposto valori di nome,cognome e scuola in view
        ((TextView)findViewById(R.id.first_name)).setText(utente.getNome());
        ((TextView)findViewById(R.id.last_name)).setText(utente.getCognome());
        ((TextView)findViewById(R.id.school)).setText(utente.getScuola());
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
    //richiamo view Question
    public void toQuestion(View v) {
        Intent i = new Intent(this, Question.class);
        Bundle extras=new Bundle();
        //passo l'oggetto user alla prossima view
        extras.putParcelable("utentec",this.utente);
        i.putExtras(extras);
        startActivity(i);

    }


}
