package com.educationet.k12.app;

import android.content.res.Configuration;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager of the registration page
 * @author K12-Dev-Team
 * @version 0.1
 */

public class Register_form extends ActionBarActivity {
    private final String url_register = "https://k12-api.mybluemix.net/api/user/register";
    private EditText nomeTxt;
    private EditText cognomeTxt;
    private EditText emailTxt;
    private EditText indirizzoTxt;
    private EditText userTxt;
    private EditText passTxt;
    private EditText schoolTxt;
    private User x = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_form);
        // Declare TextView from which data can be picked
        nomeTxt =(EditText) findViewById(R.id.Reg_name);
        cognomeTxt =(EditText) findViewById(R.id.Reg_surname);
        emailTxt=(EditText) findViewById(R.id.Reg_email);
        indirizzoTxt=(EditText) findViewById(R.id.Reg_address);
        userTxt=(EditText) findViewById(R.id.Reg_username);
        passTxt=(EditText) findViewById(R.id.Reg_pass);
        schoolTxt =(EditText) findViewById(R.id.Reg_school);

    }
    @Override
    public void onConfigurationChanged(Configuration newConfig){
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_register_form);  //change the configuration of the screen update the layout
    }

    /**
     *
     * @param v View where you go after you press the button "Submit"
     */
    public void Registrazione(View v) {
        // check that the required fields are filled
        String regtex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        RegEx regex = new RegEx(regtex);
        if (nomeTxt.getText().toString().equals("") || cognomeTxt.getText().toString().equals("") || emailTxt.getText().toString().equals("") || userTxt.getText().toString().equals("") ||
                passTxt.getText().toString().equals("")|| schoolTxt.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Ops! Some mandatory fields are empty!", Toast.LENGTH_LONG).show();
        }
        else {
            // check the email with a regex
            if(!regex.Match(emailTxt.getText().toString())){
                Toast.makeText(getApplicationContext(), "Email not valid!", Toast.LENGTH_LONG).show();
            }
            else{
                // create a new user x, with data entered by keyboard
            x = new User(userTxt.getText().toString(), passTxt.getText().toString(), nomeTxt.getText().toString(), cognomeTxt.getText().toString(), schoolTxt.getText().toString(),
                    emailTxt.getText().toString(), indirizzoTxt.getText().toString());
            //user registration
            HTMLRequest htmlRequest=new HTMLRequest(url_register,"User="+registerUser(x).toString());
            if(htmlRequest.getHTMLThread()!=null) {
                Toast.makeText(getApplicationContext(), "You have been registered! Well done!", Toast.LENGTH_LONG).show();
                userTxt.setText("");
                cognomeTxt.setText("");
                nomeTxt.setText("");
                passTxt.setText("");
                schoolTxt.setText("");
                indirizzoTxt.setText("");
                emailTxt.setText("");

                 }
            else {
                Toast.makeText(getApplicationContext(), "Error! You cannot register now!", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    /**
     *
     * @param x Generic User inside which it will be passed parameters entered by keyboard
     * @return Returns a JSON array that inside it will had user's data to be recorded
     */
    public JSONArray registerUser(User x){
        // Si riempie l'array con gli input da tastiera
        List<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("name", x.getFirstName()));
        params.add(new BasicNameValuePair("surname", x.getLastName()));
        params.add(new BasicNameValuePair("school", x.getSchool()));
        params.add(new BasicNameValuePair("user", x.getUsername()));
        params.add(new BasicNameValuePair("email", x.getEmail()));
        params.add(new BasicNameValuePair("password", x.getPassword()));
        // creation JSONArray
        JSONArray json = new JSONArray(params);
        // Check that the array has been filled by print of log
        Log.d("append", params.toString());
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
        return super.onOptionsItemSelected(item);
    }
}
