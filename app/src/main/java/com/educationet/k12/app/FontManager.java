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
 * Font TTF Manager
 * @author K12-Dev-Team
 */
public class FontManager {
    private Typeface typeface=null;

    /*
     * @param fontname font's Name
     * @param mgr Manager asset
     */
    FontManager(String fontname,AssetManager mgr){
        typeface=Typeface.createFromAsset(mgr,"fonts/"+fontname+".ttf"); //set the font with the relative path to the folder asset
    }

    /**
     * Imposed the font to the object
     * @param obj object to be set
     * @throws UnknownTypeException Unmanaged type
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
