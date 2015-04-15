package com.example.raffaele.testapp;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Gestore della pagina Topics
 * @author Raffaele
 * @version 0.1
 */

public class Argument_Page extends Activity {
    private ArgumentList argumentList=new ArgumentList();
    private AdapterCustom dataAdapter = null;
    private User utente;
    private String token = "";

    /**
     * Creatore dell'interfaccia
     * @param savedInstanceState Istanza precedente
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_argument__page); //imposta il layout
        Intent i = getIntent();
        Bundle extras=i.getExtras();
        this.utente = extras.getParcelable("utentec"); //riceve da welcome l'utente
        this.token = this.utente.getAccessToken();
        if(extras.getParcelable("argomenti")!=null){ //se welcome non passa gli argomenti li chiede via http al backend
            this.argumentList=extras.getParcelable("argomenti");
        }
        else{
            this.argumentList.getHttp(token);
        }
        displayListView(); //genera lista
    }

    /**
     * @param newConfig Configurazione schermo
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        // TODO Auto-generated method stub
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_argument__page); //al cambiamento della configurazione dello schermo refresha il layout
        displayListView();
    }

    /**
     * Classe che mostra la lista di argomenti
     */
    private void displayListView() {
        //Array list di Argomenti
        dataAdapter = new AdapterCustom(this, R.layout.row, argumentList);
        ListView listView = (ListView) findViewById(R.id.listView1);
        // Assegna l'adapter alla listview
        listView.setAdapter(dataAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                // Mostra una toast quando checka
                Argument arg = (Argument) parent.getItemAtPosition(pos);
                Toast.makeText(getApplicationContext(), "Selected: " + arg.getArg(), Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * Classe privata interna che gestisce la lista arggomenti
     */
    private class AdapterCustom extends ArrayAdapter<Argument> {
        private ArgumentList argumentList = null;

        /**
         * @param context Context dell'applicazione
         * @param textViewResourceId Id della textview
         * @param argsList Lista di argomenti
         */
        public AdapterCustom(Context context, int textViewResourceId, ArrayList<Argument> argsList) {
            super(context, textViewResourceId, argsList); //costruttore della superclasse
            argumentList = new ArgumentList();
            argumentList.addAll(argsList); //aggiunta degli elementi alla arraylist
        }

        /**
         * Classe privata per la gestione della tupla testo checkbox
         */
        private class TextCheck {
            TextView code;
            CheckBox name;
        }

        /**
         *
         * @param position Posizione attuale dell'elemento selezionato
         * @param convertView Vista convertita
         * @param parent Vista originaria
         * @return Vista di ritorno
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextCheck textcheck;
            Log.v("Converter View", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.row, null); //inserisce le tuple checkbox testo come righe

                textcheck = new TextCheck();
                textcheck.code = (TextView) convertView.findViewById(R.id.textView1);
                textcheck.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(textcheck);

                textcheck.name.setOnClickListener(new OnClickListener() { //controlla la selezione dell'argomento
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Argument arg = (Argument) cb.getTag();
                        arg.setCheck(cb.isChecked());  //imposta l'argomento selezionato
                    }
                });
            } else {
                textcheck = (TextCheck) convertView.getTag();
            }
            Argument arg = argumentList.get(position); //ritorna la posizione dell' elemento selezionato
            textcheck.code.setText(" (" + arg.getArg() + ")");
            textcheck.name.setText(arg.getDescr());
            textcheck.name.setChecked(arg.isCheck());
            textcheck.name.setTag(arg);

            return convertView;

        }

    }

    /**
     * Ritorna all'activity welcome ripassando la lista di argomenti
     * @param v Vista attuale
     */
    public void toWelcome(View v) {
        argumentList = dataAdapter.argumentList;
        Intent resultintent = new Intent();
        Bundle extras=new Bundle();
        //passo l'oggetto user alla prossima view
        extras.putParcelable("argomenti",this.argumentList);
        resultintent.putExtras(extras);
        setResult(Activity.RESULT_OK,resultintent);
        finish();
    }
}