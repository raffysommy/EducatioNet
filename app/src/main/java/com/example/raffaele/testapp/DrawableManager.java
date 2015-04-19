package com.example.raffaele.testapp;

/**
 * Created by Raffaele on 05/04/2015.
 * This is a rewrite of  android-drawable-manager library provided under MIT licence,so this is provided under MIT licence
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * Questa classe implementa la gestione del download delle immagini in thread indipendenti.
 * E' stata estesa per permettere di gestire non solo le imageview ma anche bottoni e imagebutton
 * E' stato inoltre sistemato l'algoritmo di caching in memoria delle immagini usante il SoftReference
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
 * Gestore delle immagini
 * @version 0.2
 * @author Raffaele
 */
public class DrawableManager {
	private static final String LOG_TAG = "DrawableManager";
    protected final Map<String, SoftReference<Drawable>> drawableMap;
    protected static DrawableManager _instance;
    public static DrawableManager getInstance(){
        if(_instance == null) _instance = new DrawableManager();
        return _instance;
    }

    /**
     * @param drawable Immagine in ingresso
     * @param view Oggetto su cui impostare l'immagine come background
     * @throws UnknownTypeException Tipo Sconosciuto
     */

    protected static void setByObject(Drawable drawable, Object view) throws UnknownTypeException { //setta il drawable al seconda del tipo e delle api.
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
     * Costruttore che crea la mappa di immagini
     */
    protected DrawableManager() {
        drawableMap = new HashMap<String, SoftReference<Drawable>>();
    }

    /**
     * @param urlString Url dell' immagine
     * @param view Vista attuale
     */
    public void setDrawable(final String urlString, final Object view){
        if(drawableMap.containsKey(urlString)){ //se l'oggetto è in cache ed è valido lo setto altrimenti rimuovo l'url
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
        final HttpClient httpClient = new DefaultHttpClient(); //instanzio httpclient
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
            public void run() { //dichiaro il thread
				Log.i(LOG_TAG, urlString);
                HttpGet request = new HttpGet(urlString); //Instanzio la richiesta di Get
                HttpParams params = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(params, 2000);
                HttpConnectionParams.setSoTimeout(params, 1000);
                request.setParams(params); //passo i parametri di timeout
                BasicManagedEntity entity = null;

                try{
                    HttpResponse response = httpClient.execute(request); //scarica l'immagine
                    entity = (BasicManagedEntity)response.getEntity();
                } catch (IOException e) {
                    handler.sendEmptyMessage(NORM_PRIORITY); //in caso di eccezione termina il thread richiamando l'handler alla priorità di default e stampa lo Stack Trace
                    e.printStackTrace();
                }
                if(entity != null){
                    Drawable drawable = null;
                    try {
                        drawable = Drawable.createFromStream(entity.getContent(), "www");
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
        thread.start(); //fa partire il thread
    }

}