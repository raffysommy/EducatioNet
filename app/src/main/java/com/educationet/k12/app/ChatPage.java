package com.educationet.k12.app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * chat's class
 * @author  K12-Dev-Team
 * Created by K12-Dev-Team on 12/04/2015.
 */

public class ChatPage extends ActionBarActivity {

    private User user;

    /**
     * Constructor of the page
     * @param savedInstanceState saved instance
     */
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_activity);
        Intent i = getIntent();
        Bundle extras;
        extras=i.getExtras();
        user=extras.getParcelable("utentec");
        loadpage();
    }
    private void loadpage(){
        WebView myWebView = (WebView) findViewById(R.id.chatView);
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebChromeClient(new WebChromeClient());
        myWebView.loadUrl("http://chat.bluemix.educationet.tk/?id="+user.getFirstName()+user.getLastName());
    }
    /**
     * Handler of rotation
     * @param newConfig new orientation
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.chat_activity); //when changes the screen's configuration the layout is updated
        loadpage();
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
        return super.onOptionsItemSelected(item);
    }
}
