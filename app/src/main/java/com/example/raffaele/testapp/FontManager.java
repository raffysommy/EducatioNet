package com.example.raffaele.testapp;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Ilaria on 19/04/2015.
 */

/**
 * Classe per la gestione dei font
 * @author Ilaria
 */
public class FontManager {
    Typeface typeface=null;

    /**
     * Costruttore della classe
     * @param fontname Nome del font
     * @param mgr Gestore degli asset
     */
    FontManager(String fontname,AssetManager mgr){
        typeface=Typeface.createFromAsset(mgr,"fonts/"+fontname+".ttf"); //imposta il font con il path relativo alla cartella asset
    }

    /**
     * Imposta il font sull'oggetto
     * @param obj Oggetto da impostare
     * @throws UnknownTypeException Tipo non gestito
     */
    public void setFont(Object obj) throws UnknownTypeException {
        if (obj instanceof TextView) {
            ((TextView) obj).setTypeface(typeface);
        } else
        if (obj instanceof Button) {
            ((Button) obj).setTypeface(typeface);
        } else
        if (obj instanceof EditText){
            ((EditText) obj).setTypeface(typeface);
        } else
            throw new UnknownTypeException(obj.getClass().getName());
    }
}
