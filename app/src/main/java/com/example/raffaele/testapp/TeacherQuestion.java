package com.example.raffaele.testapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class TeacherQuestion extends Activity {
    private QuestionnaireList listaquestionari = new QuestionnaireList();
    private User utente;
    private static String token;
    private AdapterCustom dataAdapter = null;
    private QueryList teacherQuestionList=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_question);
        Intent i = getIntent();
        Bundle extras=i.getExtras();
        this.utente = extras.getParcelable("utentec"); //riceve da welcome l'utente
        token=this.utente.getAccessToken();
        this.listaquestionari.getHttp(token);
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
    public void onQuizSelect(View view){
        String quiz=((TextView)view).getHint().toString();
        teacherQuestionList =new QueryList(quiz);
        try {
            teacherQuestionList.getHTTP(token);
            Toast.makeText(getApplicationContext(), "Selected: " + ((TextView)view).getText().toString(), Toast.LENGTH_LONG).show();
            Intent i=new Intent(this,Question.class);
            Bundle extras=new Bundle();
            extras.putParcelable("utentec", this.utente);
            extras.putParcelable("quiz",this.teacherQuestionList);
            extras.putString("idquiz", quiz);
            i.putExtras(extras);
            startActivity(i);
        } catch (NullPointerException e) {
            Toast.makeText(getApplicationContext(), "No question available for this quiz!", Toast.LENGTH_LONG).show();
        }
    }



    private void displayListView() {
        //Array list di Argomenti
        dataAdapter = new AdapterCustom(this,R.layout.list_item1, listaquestionari);
        ListView listView = (ListView) findViewById(R.id.listView2);
        // Assegna l'adapter alla listview
        listView.setAdapter(dataAdapter);
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
            textcheck.code.setText(listquest.getname());
            textcheck.code.setHint(listquest.getId());
            return convertView;

        }

        private QuestionnaireList listaquestionari = null;

        /**
         * @param context            Context dell'applicazione
         * @param textViewResourceId Id della textview
         * @param questList           Lista di argomenti
         */
        public AdapterCustom(Context context, int textViewResourceId, ArrayList questList) {
            super(context, textViewResourceId, questList); //costruttore della superclasse
            listaquestionari = new QuestionnaireList();
            listaquestionari.addAll(questList); //aggiunta degli elementi alla arraylist
        }

    }
}