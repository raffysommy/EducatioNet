package com.example.raffaele.testapp;

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
    private SharedPreferences loginSharedPreferences;
    private SharedPreferences.Editor EditorSharedPreference;
    private Boolean saveLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setloginsaved();
    }
    private void setloginsaved(){
        loginSharedPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        EditorSharedPreference = loginSharedPreferences.edit();
        if(loginSharedPreferences.getBoolean("saveLogin",false)==true){
            ((EditText)findViewById(R.id.username)).setText(loginSharedPreferences.getString("username", ""));
            ((EditText)findViewById(R.id.password)).setText(loginSharedPreferences.getString("password", ""));
            ((CheckBox)findViewById(R.id.rememberme)).setChecked(true);
        }
    }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

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

