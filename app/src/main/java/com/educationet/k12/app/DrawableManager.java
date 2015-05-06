package com.educationet.k12.app;

/**
 * Created by K12-Dev-Team on 05/04/2015.
 * This Class implements the management of download of image in independent thread
 * It handle button imageview and image button.
 * it was implemented also the caching algorithms thought SoftReference
 */


import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.BasicManagedEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
/**
 * Drawable Manager
 * @version 0.2
 * @author K12-Dev-Team
 */
@SuppressWarnings("deprecation")
public class DrawableManager {
	private static final String LOG_TAG = "DrawableManager";
    private final Map<String, SoftReference<Drawable>> drawableMap;
    private static DrawableManager _instance;
    public static DrawableManager getInstance(){
        if(_instance == null) _instance = new DrawableManager();
        return _instance;
    }

    /**
     * @param drawable In Image
     * @param view Object that need image
     * @throws UnknownTypeException Unknown Type
     */

    private static void setByObject(Drawable drawable, Object view) throws UnknownTypeException { //set drawable by obj-type and by api type.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            if (view instanceof Button) {
                ((Button) view).setBackground(drawable);
                return;
            }
            if (view instanceof ImageButton) {
                ((ImageButton) view).setBackground(drawable);
                return;
            }
        }
        else {
            if (view instanceof Button) {
                ((Button) view).setBackgroundDrawable(drawable);
                return;
            }
            if (view instanceof ImageButton) {
                ((ImageButton) view).setBackgroundDrawable(drawable);
                return;
            }
        }
        if(view instanceof ImageView){
            ((ImageView)view).setImageDrawable(drawable);
            return;
        }
        throw new UnknownTypeException(view.getClass().getName());
    }

    /**
     * Constructor of HashMap of Drawable for caching purpose
     */
    protected DrawableManager() {
        drawableMap = new HashMap<>();
    }

    /**
     * @param urlString Url of image
     * @param view Current View
     */
    public void setDrawable(final String urlString, final Object view){
        if(drawableMap.containsKey(urlString)){ //if object is in cache return it else delete it
            Drawable drawable = drawableMap.get(urlString).get();
            if(drawable != null){
                try {
                    setByObject(drawable,view);
                } catch (UnknownTypeException e) {
                    e.logException();
                }
                Log.i(LOG_TAG,"Image Cached");
                return;
            }
            else drawableMap.remove(urlString);
        }
        final HttpClient httpClient = new DefaultHttpClient(); //instance httpclient
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                if(msg.obj != null) try {
                     setByObject((Drawable) msg.obj,view);
                } catch (UnknownTypeException e) {
                    e.logException();
                }
            }
        }; //Handler di settaggio

        final Thread thread = new Thread(){
            /**
             * Metodo di esecuzione richiesta http,conversione ad immagine e inserimento in cache map
             */
            @Override
            public void run() { //declare thread
				Log.i(LOG_TAG, urlString);
                HttpGet request = new HttpGet(urlString); //Instance Get Request
                HttpParams params = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(params, 2000);
                HttpConnectionParams.setSoTimeout(params, 1000);
                request.setParams(params); //set timeout
                BasicManagedEntity entity = null;

                try{
                    HttpResponse response = httpClient.execute(request); //download image
                    entity = (BasicManagedEntity)response.getEntity();
                } catch (IOException e) {
                    handler.sendEmptyMessage(NORM_PRIORITY); //If there is an exception return handler with default priority then print stack trace
                    e.printStackTrace();
                }
                if(entity != null){
                    Drawable drawable = null;
                    try {
                        drawable = Drawable.createFromStream(entity.getContent(), "www");
                        //noinspection unchecked
                        drawableMap.put(urlString,new SoftReference(drawable));
                    } catch (IOException e) {
                        handler.sendEmptyMessage(NORM_PRIORITY);
                        e.printStackTrace();
                    }
                    if(drawable == null){
                        handler.sendEmptyMessage(NORM_PRIORITY);
                    }
                    else{
                        handler.sendMessage(handler.obtainMessage(NORM_PRIORITY,drawable));
                    }
                }
                else{
                    handler.sendEmptyMessage(NORM_PRIORITY);
                }
            }
        };
        thread.start(); //start thread
    }

}