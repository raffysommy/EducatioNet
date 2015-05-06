package com.educationet.k12.app;

import android.util.Log;
/**
 *
 * Created by K12-Dev-Team on 19/04/2015.
 */
/**
 * Class of exceptions for Drawable Manager
 * @author K12-Dev-Team
 */
public class UnknownTypeException extends Exception {
    private final String typename;

    /**
     * Constructor
     * @param typename name of the type
     */
    UnknownTypeException(String typename){
        super();
        this.typename=typename;
    }

    /**
     * Funcion of log
     */
    public void logException(){
        Log.e("Unknown Type: ",typename);
    }
}
