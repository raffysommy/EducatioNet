package com.example.raffaele.testapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
    private static Button register_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Register();
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

        User utente = new User(user, pass);
        boolean connected = utente.connetti();
        if (!connected) {//user non presente
            Toast.makeText(getApplicationContext(), "Credenziali non valide", Toast.LENGTH_SHORT).show();
        } else {//login effettuato, schermata successiva
            Intent i = new Intent(this, Welcome_student.class);
            Bundle extras=new Bundle();
            //passo l'oggetto user alla prossima view
            // TODO: Oggetto passato.Bisogna testare se la soluzione adottata è ok
            extras.putParcelable("utentec",utente);
            i.putExtras(extras);
            startActivity(i);
        }

    }

    public void Register() {
        register_btn = (Button) findViewById(R.id.RegisterButton);
        register_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent("com.example.raffaele.testapp.Register_form");
                        startActivity(i);
                    }
                }

        );

    }

}