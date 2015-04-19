package com.example.raffaele.testapp;

/**
 * Created by paolo on 14/03/2015.
 * INPUT: URL website + parameters
 * OUTPUT: getHTML() method retrieve url html
 * In case of POST data, use the second parameter in the constructor, otherwise use URL to use GET
 */

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpVersion;
import org.apache.http.params.CoreProtocolPNames;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;

public class HTMLRequest extends AsyncTask<Void,Void,String> {
    private String site = "";
    private String cookie = "";
    private String parameters = "";


    public HTMLRequest(String u, String par) {
        site = u;
        parameters = par;
    }
    public HTMLRequest(String u){//Costruttuore per il solo passaggio di Url
        site=u;
    }
    void setCookie(String str) {
        cookie = str;
    }
    //request html to url
    @SuppressLint("NewApi")
    String getHTML() {
        URL url;
        try {
            url = new URL(site);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

        String urlParameters  = this.parameters;
        byte[] postData       = urlParameters.getBytes( Charset.forName( "UTF-8" ));
        int    postDataLength = postData.length;
        HttpURLConnection cox = null;
        try {
            cox = (HttpURLConnection) url.openConnection();
        } catch (IOException e2) {
            Log.e("HTMLRequest","Error on openConnection");
            e2.printStackTrace();
        }
        assert cox != null;
        cox.addRequestProperty("Cookie", cookie);
        cox.setDoOutput( true );
        cox.setDoInput ( true );
        cox.setInstanceFollowRedirects( false );

        try {
            cox.setRequestMethod( "POST" );
        } catch (ProtocolException e1) {
            Log.e("HTMLRequest","Error on setRequestMethod");
            e1.printStackTrace();
        }
        cox.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
        cox.setRequestProperty( "charset", "utf-8");
        cox.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
        cox.setUseCaches( false );
        try( DataOutputStream wr = new DataOutputStream( cox.getOutputStream())) {
            wr.write( postData );
        } catch (IOException e1) {
            Log.e("HTMLRequest","Error on writeData");
            e1.printStackTrace();
        }

        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(cox.getInputStream()));
        } catch (IOException e) {
            Log.e("HTMLRequest","Error on BufferedReader");
            e.printStackTrace();
            return null;
        }
        String htmlfile = "";
        String temp;
        try {
            while ((temp = br.readLine()) != null) {
                htmlfile+= temp;
            }
        } catch (IOException e) {
            Log.e("HTMLRequest","Error on readLine");
            e.printStackTrace();
        }

        return htmlfile;

    }
    String getHTMLThread(){//metodo che usa i thread
        String rit= "";
        try {
            rit= this.execute().get();
        } catch (InterruptedException e) {
            Log.e("HTMLRequest","Execution Interrupted");
            e.printStackTrace();
        } catch (ExecutionException e) {
            Log.e("HTMLRequest","Execution Exception");
            e.printStackTrace();
        }
        return rit;
    }
    @Override
    protected String doInBackground(Void... params) {//Il metodo doinbackground specifica le azioni da eseguire in un Thread separato
        return getHTML();

    }

}
