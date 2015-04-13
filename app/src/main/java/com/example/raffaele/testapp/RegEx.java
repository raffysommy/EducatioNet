package com.example.raffaele.testapp;


import java.util.regex.Pattern;

/***
 * Created by Raffaele on 20/03/2015.
 */

/**
 * Gestore di espressioni regolari
 * @author Raffaele
 * @version 0.1
 */
public class RegEx {
    private Pattern patt;

    /**
     * @param pattern Imposta il pattern dell'espressione regolare
     */
    public RegEx(String pattern) {
        setPatt(Pattern.compile(pattern));
    }

    /**
     * @param Text Testo da controllare
     * @return Compatibilità con l'espressione regolare
     */
    public boolean Match(String Text){
        return getPatt().matcher(Text).matches();
    }

    /**
     * @return Pattern impostato
     */
    public Pattern getPatt() {
        return patt;
    }

    /**
     * @param patt Pattern da impostare
     */
    private void setPatt(Pattern patt) {
        this.patt = patt;
    }
}
