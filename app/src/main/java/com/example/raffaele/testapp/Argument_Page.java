package com.example.raffaele.testapp;
import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class Argument_Page extends Activity {
    private ArgumentList argumentList = new ArgumentList();
    private MyCustomAdapter dataAdapter = null;
    private User utente;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_argument__page);
        Intent i = getIntent();
        Bundle extras=i.getExtras();
        this.utente = extras.getParcelable("utentec");
        displayListView(); //genera lista
    }

    private void displayListView() {
        //Array list di Argomenti
        argumentList.getHttp();
        dataAdapter = new MyCustomAdapter(this, R.layout.row, argumentList);
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

    private class MyCustomAdapter extends ArrayAdapter<Argument> {
        private ArgumentList argumentList = null;

        public MyCustomAdapter(Context context, int textViewResourceId, ArrayList<Argument> argsList) {
            super(context, textViewResourceId, argsList);
            argumentList = new ArgumentList();
            argumentList.addAll(argsList);
        }

        private class ViewHolder {
            TextView code;
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            Log.v("ConvertView", String.valueOf(position));

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.row, null);

                holder = new ViewHolder();
                holder.code = (TextView) convertView.findViewById(R.id.textView1);
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        Argument arg = (Argument) cb.getTag();
                        arg.setCheck(cb.isChecked());  //imposta l'argomento selezionato
                    }
                });
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Argument arg = argumentList.get(position);
            holder.code.setText(" (" + arg.getArg() + ")");
            holder.name.setText(arg.getDescr());
            holder.name.setChecked(arg.isCheck());
            holder.name.setTag(arg);

            return convertView;

        }

    }

    public void toWelcome(View v) {
        argumentList = dataAdapter.argumentList;
        Intent resultintent = new Intent();
        Bundle extras=new Bundle();
        //passo l'oggetto user alla prossima view
        extras.putParcelable("argomenti",this.argumentList);
        extras.putParcelable("utentec",this.utente);
        resultintent.putExtras(extras);
        setResult(Activity.RESULT_OK,resultintent);
        finish();
    }
}