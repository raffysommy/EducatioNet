package com.educationet.k12.app;

import android.util.Log;
/**
 *
 * Created by K12-Dev-Team on 19/04/2015.
 */
/**
 * Classe delle eccezzioni per Drawable Manager
 * @author K12-Dev-Team
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
