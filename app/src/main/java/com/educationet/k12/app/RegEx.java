package com.educationet.k12.app;


import java.util.regex.Pattern;

/***
 * Created by K12-Dev-Team on 20/03/2015.
 */

/**
 * Manager of regular expressions
 * @author K12-Dev-Tea
 * @version 0.1
 */
public class RegEx {
    private Pattern patt;

    /**
     * @param pattern Imposta il pattern dell'espressione regolare Set the pattern of the regular expression
     */
    public RegEx(String pattern) {
        setPatt(Pattern.compile(pattern));
    }

    /**
     * @param Text Text to check

     * @return Compatibility with the regular expression
     */
    public boolean Match(String Text){
        return getPatt().matcher(Text).matches();
    }

    /**
     * @return Pattern set
     */
    public Pattern getPatt() {
        return patt;
    }

    /**
     * @param patt Pattern to be set
     */
    private void setPatt(Pattern patt) {
        this.patt = patt;
    }
}
