package com.example.raffaele.testapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
            x = new User(userTxt.getText().toString(), passTxt.getText().toString(), nomeTxt.getText().toString(), cognomeTxt.getText().toString(), schoolTxt.getText().toString(),
                    emailTxt.getText().toString(), indirizzoTxt.getText().toString());
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
