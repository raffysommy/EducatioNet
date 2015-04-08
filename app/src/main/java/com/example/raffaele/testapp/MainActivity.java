package com.example.raffaele.testapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    //subclass for login execution
    private class LoginTask extends AsyncTask<User, Integer, User> {
        protected User doInBackground(User... utenti) {
            User u=utenti[0];
            boolean connected = u.connetti();
            if(connected)
                return u;
            return null;
        }

        protected void onPostExecute(User utente) {

            if(utente!=null){
                Intent i = new Intent(getApplicationContext(), Welcome_student.class);
                Bundle extras=new Bundle();
                //passo l'oggetto user alla prossima view
                extras.putParcelable("utentec", utente);
                i.putExtras(extras);
                findViewById(R.id.loadBar).setVisibility(View.INVISIBLE);
                startActivity(i);
            }
            else {
                findViewById(R.id.loadBar).setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(), "Credenziali non valide", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            ((TextView)findViewById(R.id.passwordLabel)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/FunnyKid.ttf"));
            ((TextView)findViewById(R.id.passwordLabel)).setTypeface(Typeface.createFromAsset(getAssets(), "fonts/FunnyKid.ttf"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main); //al cambiamento della configurazione dello schermo refresha il layout
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

    public void onStartClick(View v) {

        String user = ((EditText) findViewById(R.id.username)).getText().toString();
        String pass = ((EditText) findViewById(R.id.password)).getText().toString();
        findViewById(R.id.loadBar).setVisibility(View.VISIBLE);
        User utente = new User(user, pass);
        new LoginTask().execute(utente);
        /*
        boolean connected = utente.connetti();
        if (!connected) {//user non presente
            Toast.makeText(getApplicationContext(), "Credenziali non valide", Toast.LENGTH_SHORT).show();
        } else {//login effettuato, schermata successiva
            Intent i = new Intent(this, Welcome_student.class);
            Bundle extras=new Bundle();
            //passo l'oggetto user alla prossima view
            extras.putParcelable("utentec",utente);
            i.putExtras(extras);
            startActivity(i);
        }*/

    }

    public void Register(View v) {
        Intent i = new Intent(this,Register_form.class);
        startActivity(i);
    }
}

