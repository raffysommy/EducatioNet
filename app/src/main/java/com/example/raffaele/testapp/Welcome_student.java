package com.example.raffaele.testapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Classe di Benvenuto Utente
 * @author K12-Dev-Team
 * @see android.app.Activity
 * @version 0.4
 */
public class Welcome_student extends ActionBarActivity {
    private User utente;
    private ArgumentList argumentList;
    private static final int MSG_SAVE_SCORE_REQUEST = 1;

    /**
     * Creatore della vista
     * @param savedInstanceState Parametri salvati
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_student);
        //prendo l'oggetto passato alla view
        Intent i = getIntent();
        Bundle extras=i.getExtras();
        this.utente = extras.getParcelable("utentec");
        impostacampiutente();
        argumentList=null;
    }

    /**
     * Handler rotazione
     * @param newConfig nuova orientazione
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_welcome_student); //al cambiamento della configurazione dello schermo refresha il layout
        impostacampiutente();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome_student, menu);
        return true;
    }

    /**
     * Metodo che imposta i campi nome cognome e scuola
     */
    private void impostacampiutente(){
        //imposto valori di nome,cognome e scuola in view
        ((TextView)findViewById(R.id.first_name)).setText(utente.getFirstName());
        ((TextView)findViewById(R.id.last_name)).setText(utente.getLastName());
        ((TextView)findViewById(R.id.school)).setText(utente.getSchool());
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==R.id.chat){ //Open score page
            this.toChat(this.getCurrentFocus());
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("resultCode=>", Integer.toString(resultCode));
        Log.i("requestCode=>", Integer.toString(requestCode));
        switch(requestCode) {
            case (0) : {
                if (resultCode == Activity.RESULT_OK) {
                    Bundle extras = data.getExtras();
                    this.argumentList = extras.getParcelable("argomenti");
                }
            }   break;
        }
    }
    /**
     * Pulsante di Help Bambino
     * @param view Vista attuale
     */
    public void opendialog(View view){
        LayoutInflater linf = LayoutInflater.from(this);
        final View inflator =linf.inflate(R.layout.dialog_question_select,null);
        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Select the type of Question");
        alertDialogBuilder.setView(inflator);
        /*alertDialogBuilder
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
        */
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    //richiamo view Question
    public void toQuestion(View v) {/*
        Intent i = new Intent(this, Question.class);
        Bundle extras=new Bundle();
        //passo l'oggetto user alla prossima view
        if(argumentList!=null){
            extras.putParcelable("argomenti",this.argumentList);
        }
        extras.putParcelable("utentec",this.utente);
        i.putExtras(extras);
        startActivityForResult(i, MSG_SAVE_SCORE_REQUEST);

    */
    opendialog(this.getCurrentFocus());
    }

    //richiamo view Question
    public void toChat(View v) {
        Intent i = new Intent(this, ChatPage.class);
        Bundle extras=new Bundle();
        extras.putParcelable("utentec",this.utente);
        i.putExtras(extras);
        startActivity(i);
    }


    public void toArgument(View v) {
        Intent i = new Intent(this, Argument_Page.class);
        Bundle extras=new Bundle();
        //passo l'oggetto user alla prossima view
        /*if(argumentList!=null){
            extras.putParcelable("argomenti",this.argumentList);
        }
        */
        extras.putParcelable("utentec",this.utente);
        i.putExtras(extras);
        startActivity(i);

    }
    public void TeacherQuestion (View v) {
        Intent i = new Intent(this, TeacherQuestion.class);
        Bundle extras=new Bundle();
        extras.putParcelable("utentec",this.utente);
        i.putExtras(extras);
        startActivity(i);
    }

    public void onScore(View v ) {
        Intent i = new Intent(this, Score_page.class);
        Bundle extras=new Bundle();
        extras.putParcelable("utentec",this.utente);
        i.putExtras(extras);
        startActivity(i);
    }
}
