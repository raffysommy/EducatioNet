package com.example.raffaele.testapp;

//filippo
import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void onStartClick(View v){

        String user = ((EditText)findViewById(R.id.editText2)).getText().toString();
        String pass = ((EditText)findViewById(R.id.editText)).getText().toString();
        /*//retrieve information about user login through POST request
        String result = "";
        //permessi http per il thread
        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        HTMLRequest dl = new HTMLRequest("http://mysql-raffysommy-1.c9.io/k12api/login.php", "username="+user+"&password="+pass);
        result = dl.getHTML();
        JSONArray ja = null;
        JSONObject jo = null;
        try {
            ja = new JSONArray(result.toString());
            jo = (JSONObject) ja.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        user utente = new user(user, pass);
        boolean connected = utente.connetti();
        if (!connected) {//user non presente
            ((EditText) findViewById(R.id.editText2)).setText("User inesistente");
            ((EditText) findViewById(R.id.editText)).setText("");
        }
        else {//login effettuato, schermata successiva
            Intent i = new Intent(this, Question.class);
            startActivity(i);
        }
    }
}
