package com.educationet.k12.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Login Main Activity
 * @author K12-Dev-Team
 * @version 0.9
 * @see android.app.Activity
 */
public class MainActivity extends ActionBarActivity {
    //subclass for login execution

    /**
     * Classe interna di gestione della login asincrona
     */
    private class LoginTask extends AsyncTask<User, Integer, User> {
        /**
         * Metood eseguito in background
         * @param utenti Utente
         * @return Utente connesso con campi compilati
         */
        protected User doInBackground(User... utenti) {
            User u=utenti[0];
            boolean connected = u.connetti();
            if(connected)
                return u;
            return null;
        }

        /**
         * Login finito
         * @param utente Utente loggato
         */
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
    private SharedPreferences loginSharedPreferences;
    private SharedPreferences.Editor EditorSharedPreference;
    private Boolean saveLogin;

    /**
     * Costruttore della vista
     * @param savedInstanceState Parametri salvati
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setloginsaved();
    }

    /**
     * Imposta le credenziali
     */
    private void setloginsaved(){
        loginSharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        EditorSharedPreference = loginSharedPreferences.edit();
        if(loginSharedPreferences.getBoolean("saveLogin", false)){
            ((EditText)findViewById(R.id.username)).setText(loginSharedPreferences.getString("username", ""));
            ((EditText)findViewById(R.id.password)).setText(loginSharedPreferences.getString("password", ""));
            ((CheckBox)findViewById(R.id.rememberme)).setChecked(true);
        }
        EditorSharedPreference.commit();
    }

    /**
     * Handler della rotazione
     * @param newConfig nuova orientazione
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_main); //al cambiamento della configurazione dello schermo refresha il layout
        setloginsaved();
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
        return super.onOptionsItemSelected(item);
    }

    /**
     * Metodo eseguito alla pressione del pulsante login
     * @param v vista attuale
     */
    public void onStartClick(View v) {

        String user = ((EditText) findViewById(R.id.username)).getText().toString();
        String pass = ((EditText) findViewById(R.id.password)).getText().toString();
        if(((CheckBox)findViewById(R.id.rememberme)).isChecked()){
            EditorSharedPreference.putBoolean("saveLogin", true);
            EditorSharedPreference.putString("username", user);
            EditorSharedPreference.putString("password", pass);
            EditorSharedPreference.commit();
        }
        else{
            EditorSharedPreference.clear();
            EditorSharedPreference.commit();
        }
        findViewById(R.id.loadBar).setVisibility(View.VISIBLE);
        User utente = new User(user, pass);
        new LoginTask().execute(utente);
    }

    /**
     * Metodo di passaggio alla register page
     * @param v vista attuale
     */
    public void Register(View v) {
        Intent i = new Intent(this,Register_form.class);
        startActivity(i);
    }
}

