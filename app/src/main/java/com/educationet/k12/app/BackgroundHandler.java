package com.educationet.k12.app;

import android.widget.Button;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * Created by K12-Dev-Team on 19/04/2015.
 */

/**
 * Classe di gestione del background dei bottoni di question
 *@author K12-Dev-Team
 */
public class BackgroundHandler extends ArrayList<Integer> {
    private Integer count = 0;

    /**
     * Costruttore della classe
     * @param a Sfondo 1
     * @param b Sfondo 2
     * @param c Sfondo 3
     * @param d Sfondo 4
     */
    BackgroundHandler(int a, int b, int c, int d) {
        this.add(a);
        this.add(b);
        this.add(c);
        this.add(d);
    }

    /**
     *  Setter dei bottoni
     * @param b Bottone
     */
    public void setbg(Button b) {
        if (count >= this.size()) { //resetta il counter e riodina casualmente i bottoni quando li ha assegnati tutti
            Collections.shuffle(this);
            count = 0;
        }
        b.setBackgroundResource(this.get(count++)); //assegna il background
    }
}
