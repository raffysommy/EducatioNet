package com.example.raffaele.testapp;

import android.util.Log;
/**
 * Created by Raffaele on 19/04/2015.
 */
/**
 * Classe delle eccezzioni per Drawable Manager
 * @author Raffaele
 */
public class UnknownTypeException extends Exception {
    private final String typename;

    /**
     * Costruttore
     * @param typename nome del tipo
     */
    UnknownTypeException(String typename){
        super();
        this.typename=typename;
    }

    /**
     * Funzione di log
     */
    public void logException(){
        Log.e("Unknown Type: ",typename);
    }
}
