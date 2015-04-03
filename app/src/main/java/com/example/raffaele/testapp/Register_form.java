package com.example.raffaele.testapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Register_form extends ActionBarActivity {
    EditText nomeTxt,cognomeTxt,emailTxt,indirizzoTxt,userTxt,passTxt,schoolTxt;
    Button Rbtn;
    User x = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);
        nomeTxt =(EditText) findViewById(R.id.Reg_name);
        cognomeTxt =(EditText) findViewById(R.id.Reg_surname);
        emailTxt=(EditText) findViewById(R.id.Reg_email);
        indirizzoTxt=(EditText) findViewById(R.id.Reg_address);
        userTxt=(EditText) findViewById(R.id.Reg_username);
        passTxt=(EditText) findViewById(R.id.Reg_pass);
        schoolTxt =(EditText) findViewById(R.id.Reg_school);
        Rbtn =(Button) findViewById(R.id.Reg_button);

    }
    public void Registrazione(View v) {
        if (nomeTxt.getText().toString().equals("") || cognomeTxt.getText().toString().equals("") || emailTxt.getText().toString().equals("") || userTxt.getText().toString().equals("") ||
                passTxt.getText().toString().equals("")|| schoolTxt.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Ops! Some mandatory fields are empty!", Toast.LENGTH_LONG).show();
        }
        else {
            //creazione di un nuovo utente x, con dati inseriti da tastiera
            x = new User(userTxt.getText().toString(), passTxt.getText().toString(), nomeTxt.getText().toString(), cognomeTxt.getText().toString(), schoolTxt.getText().toString(),
                    emailTxt.getText().toString(), indirizzoTxt.getText().toString());
            //registrazione utente
            registerUser(x);

            Toast.makeText(getApplicationContext(), "You have been registered! Well done!", Toast.LENGTH_LONG).show();
            userTxt.setText("");
            cognomeTxt.setText("");
            nomeTxt.setText("");
            passTxt.setText("");
            schoolTxt.setText("");
            indirizzoTxt.setText("");
            emailTxt.setText("");
        }
    }

    public JSONArray registerUser(User x){
        // Si crea array con input da tastiera
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("name", x.getFirstName()));
        params.add(new BasicNameValuePair("surname", x.getLastName()));
        params.add(new BasicNameValuePair("school", x.getSchool()));
        params.add(new BasicNameValuePair("user", x.getUsername()));
        params.add(new BasicNameValuePair("email", x.getEmail()));
        params.add(new BasicNameValuePair("password", x.getPassword()));


        // creazione JSONArray
        JSONArray json = new JSONArray(params);
        // controllo che tutto vada bene
        Log.d("append", params.toString());
        //return json
        return json;

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_form, menu);
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
}
