package com.example.raffaele.testapp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Raffaele on 20/03/2015.
 */
public class RegEx {
    private Pattern patt;

    public RegEx(String pattern) {
        setPatt(Pattern.compile(pattern));
    }
    public boolean Match(String Text){
        return patt.matcher(Text).matches();
    }
    public Pattern getPatt() {
        return patt;
    }

    public void setPatt(Pattern patt) {
        this.patt = patt;
    }
}
