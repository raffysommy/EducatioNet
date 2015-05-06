package com.educationet.k12.app;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 *
 * Created by K12-Dev-Team on 19/04/2015.
 */

/**
 * Classe per la gestione dei font
 * @author K12-Dev-Team
 */
public class FontManager {
    private Typeface typeface=null;

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
