package com.example.raffaele.testapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by Raffaele on 20/03/2015.
 */
public class HTMLDrawable extends AsyncTask<Void,Void,Drawable> {
    private String site = new String();
    public HTMLDrawable(String site) {
        this.site=site;
    }
    private Bitmap fetchImage()
    {
        try
        {
            URL url;
            url = new URL( this.site );
            HttpURLConnection c = ( HttpURLConnection ) url.openConnection();
            c.setDoInput( true );
            c.connect();
            InputStream is = c.getInputStream();
            Bitmap img;
            img = BitmapFactory.decodeStream(is);
            return img;
        }
        catch ( MalformedURLException e )
        {
            e.printStackTrace();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        return null;
    }
    public Drawable getimg(){
        try {
            return this.execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected Drawable doInBackground(Void... params) {//Il metodo doinbackground specifica le azioni da eseguire in un Thread separato
        return new BitmapDrawable(fetchImage());
    }
}
