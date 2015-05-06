package com.educationet.k12.app;
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
 * Page Topics Controller
 * @author K12-Dev-Team
 * @version 0.1
 */

public class Argument_Page extends Activity {
    private ArgumentList argumentList=new ArgumentList();
    private AdapterCustom dataAdapter = null;
    private User utente;
    private String token = "";

    /**
     * Builder of Interface
     * @param savedInstanceState previous instance
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_argument__page); //set layout
        Intent i = getIntent();
        Bundle extras=i.getExtras();
        this.utente = extras.getParcelable("utentec"); //take from welcome_student the user
        this.token = this.utente.getAccessToken();
        if(extras.getParcelable("argomenti")!=null){ //if welcome_student not pass the argument ask to backend
            this.argumentList=extras.getParcelable("argomenti");
        }
        else{
            this.argumentList.getHttp(token);
        }
        displayListView(); //generate list
    }

    /**
     * @param newConfig Config screen
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_argument__page); //at change of orientation refresh the screen
        displayListView();
    }

    /**
     * Show the list of Argument
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
     * Private Class that manage the argument list
     */
    private class AdapterCustom extends ArrayAdapter<Argument> {
        private ArgumentList argumentList = null;

        /**
         * @param context Context of Application
         * @param textViewResourceId Id of textview
         * @param argsList List of argument
         */
        public AdapterCustom(Context context, int textViewResourceId, ArrayList<Argument> argsList) {
            super(context, textViewResourceId, argsList); //Constructor  superclass
            argumentList = new ArgumentList();
            argumentList.addAll(argsList); //set of elements of arraylist
        }

        /**
         * Private Class for the managing of tuple (text,checkbox)
         */
        private class TextCheck {
            TextView code;
            CheckBox name;
        }

        /**
         *
         * @param position Position of selected argument
         * @param convertView Converted View
         * @param parent Original view
         * @return return view
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            TextCheck textcheck;
            Log.v("Converter View", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.row, null); //insert the tuple as row

                textcheck = new TextCheck();
                textcheck.code = (TextView) convertView.findViewById(R.id.textView1);
                textcheck.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(textcheck);

                textcheck.name.setOnClickListener(new OnClickListener() { //controlla la selezione dell'argomento
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Argument arg = (Argument) cb.getTag();
                        arg.setCheck(cb.isChecked());  //set the checked argument
                    }
                });
            } else {
                textcheck = (TextCheck) convertView.getTag();
            }
            Argument arg = argumentList.get(position); //return the position of selected argument
            textcheck.code.setText(" (" + arg.getArg() + ")");
            textcheck.name.setText(arg.getDescr());
            textcheck.name.setChecked(arg.isCheck());
            textcheck.name.setTag(arg);

            return convertView;

        }

    }

    /**
     * Go to question and pass Argument List
     * @param v Current View
     */
    public void toWelcome(View v) {
        argumentList = dataAdapter.argumentList;
        Intent intent = new Intent(this, Question.class);
        Bundle extras=new Bundle();
        //pass the object to the next view
        extras.putParcelable("utentec",this.utente);
        extras.putParcelable("argomenti",this.argumentList);
        intent.putExtras(extras);
        startActivity(intent);
    }
}
