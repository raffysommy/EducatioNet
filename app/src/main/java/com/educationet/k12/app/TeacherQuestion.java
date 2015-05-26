package com.educationet.k12.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_question);
        Intent i = getIntent();
        Bundle extras=i.getExtras();
        this.utente = extras.getParcelable("utentec"); //received the user from welcome
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
            Toast.makeText(getApplicationContext(), "Downloading: " + ((TextView)view).getText().toString()+" Please Wait :)", Toast.LENGTH_LONG).show();
            listView.setEnabled(false);
            enableDisableView(listView,false);
            teacherQuestionList.getHTTP(token);
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
        finally {
            listView.setEnabled(true);
            enableDisableView(listView,true);
        }
    }
    public static void enableDisableView(View view, boolean enabled) {
        view.setEnabled(enabled);
        if ( view instanceof ViewGroup ) {
            ViewGroup group = (ViewGroup)view;

            for ( int idx = 0 ; idx < group.getChildCount() ; idx++ ) {
                enableDisableView(group.getChildAt(idx), enabled);
            }
        }
    }


    private void displayListView() {
        //Array list of Arguments
        dataAdapter = new AdapterCustom(this,R.layout.list_item1, listaquestionari);
        listView = (ListView) findViewById(R.id.listView2);
        // Assing the adapter to the listview
        listView.setAdapter(dataAdapter);
    }

    /**
     * Internal private class that manages the list Arguments
     */
    private class AdapterCustom extends ArrayAdapter<Questionnaire> {
        /**
         * Private class for the management of the tuple text checkbox
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
                convertView = vi.inflate(R.layout.list_item1, null); // insert tuples checkbox text as lines

                textcheck = new TextCheck();
                textcheck.code = (TextView) convertView.findViewById(R.id.textView1);
                convertView.setTag(textcheck);
            } else {
                textcheck = (TextCheck) convertView.getTag();
            }
            Questionnaire listquest = listaquestionari.get(position); // returns the position of the selected item
            textcheck.code.setText(listquest.getname());
            textcheck.code.setHint(listquest.getId());
            return convertView;

        }

        private QuestionnaireList listaquestionari = null;

        /**
         * @param context            Context of the application
         * @param textViewResourceId Id of the textview
         * @param questList           List of arguments
         */
        public AdapterCustom(Context context, int textViewResourceId, ArrayList questList) {
            super(context, textViewResourceId, questList); //superclass constructor
            listaquestionari = new QuestionnaireList();
            listaquestionari.addAll(questList); //Adding elements to the arraylist
        }

    }
}