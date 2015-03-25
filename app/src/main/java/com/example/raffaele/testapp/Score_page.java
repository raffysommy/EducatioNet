package com.example.raffaele.testapp;

import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


public class Score_page extends ActionBarActivity {
    Score correct, wrong;
    Button x_btn;
    TextView CorrectC, WrongC, AnsweredC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_page);
        Intent i = getIntent();
        Bundle extras=i.getExtras();
        this.correct = extras.getParcelable("Correct");
        this.wrong=extras.getParcelable("Wrong");
        //imposto valori di nome,cognome e scuola in view
        ((TextView)findViewById(R.id.CorrectC)).setText(correct.StringValue());
        ((TextView)findViewById(R.id.WrongC)).setText(wrong.StringValue());
        ((TextView) findViewById(R.id.AnsweredC)).setText(correct.StringValue());

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_score_page, menu);
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
    /*
    public void X_click (){

        x_btn = (Button) findViewById(R.id.Xbtn);
        x_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                    }
                }
        );
    }
*/
}

