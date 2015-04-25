package com.example.raffaele.testapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class TeacherQuestion extends Activity {
    private final String url = "http://k12-api.mybluemix.net/api/questionnaire/list";
    private ArrayList<Questionnaire> listaquestionari = new ArrayList<Questionnaire>();
    private User utente;
    private String token = "";
    private AdapterCustom dataAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_question);
        Intent i = getIntent();
        Bundle extras=i.getExtras();
        this.utente = extras.getParcelable("utentec"); //riceve da welcome l'utente
        this.getHttp(this.utente.getAccessToken());
        displayListView();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_teacher_question, menu);
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

    public void getHttp(String token) { //metodo di richiesta al backend
        listaquestionari.clear(); //pulisce la lista per sicurezza
        HTMLRequest htmlRequest = new HTMLRequest(url, "access_token=" + token); //richiesta con token
        String result = htmlRequest.getHTMLThread();
        Log.d("token", token); //loggo il token per scopi di debug
        try {
            JSONArray ja = new JSONArray(result); //elaboro l'array di json e aggiungo gli elementi alla lista
            JSONObject jo;
            for (int i = 0; i < ja.length(); i++) {
                jo = ja.getJSONObject(i);
                listaquestionari.add(new Questionnaire(jo.getString("name")));
            }
        } catch (JSONException e) {
            e.printStackTrace(); //in caso di eccezzioni stampo la lista chiamate (Le eccezzioni json non sono recuperabili ma non impediscono il continuo dell'esecuzione)
        }
    }

    private void displayListView() {
        //Array list di Argomenti
        dataAdapter = new AdapterCustom(this,R.layout.list_item1, listaquestionari);
        ListView listView = (ListView) findViewById(R.id.listView2);
        // Assegna l'adapter alla listview
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                // Mostra una toast quando checka
                Questionnaire questlist = (Questionnaire) parent.getItemAtPosition(pos);
                Toast.makeText(getApplicationContext(), "Selected: " + questlist.getName(), Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * Classe privata interna che gestisce la lista arggomenti
     */
    private class AdapterCustom extends ArrayAdapter<Questionnaire> {
        /**
         * Classe privata per la gestione della tupla testo checkbox
         */
        private class TextCheck {
            TextView code;
        }
        public View getView(int position, View convertView, ViewGroup parent) {

            TextCheck textcheck;
            Log.v("Converter View", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.list_item1, null); //inserisce le tuple checkbox testo come righe

                textcheck = new TextCheck();
                textcheck.code = (TextView) convertView.findViewById(R.id.textView1);
                convertView.setTag(textcheck);
            } else {
                textcheck = (TextCheck) convertView.getTag();
            }
            Questionnaire listquest = listaquestionari.get(position); //ritorna la posizione dell' elemento selezionato
            textcheck.code.setText(listquest.getName());
            return convertView;

        }

        private ArrayList<Questionnaire> listaquestionari = null;

        /**
         * @param context            Context dell'applicazione
         * @param textViewResourceId Id della textview
         * @param questList           Lista di argomenti
         */
        public AdapterCustom(Context context, int textViewResourceId, ArrayList questList) {
            super(context, textViewResourceId, questList); //costruttore della superclasse
            listaquestionari = new ArrayList<Questionnaire>();
            listaquestionari.addAll(questList); //aggiunta degli elementi alla arraylist
        }

    }
}