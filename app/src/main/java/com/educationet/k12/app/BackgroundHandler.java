package com.educationet.k12.app;

import android.widget.Button;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * Created by K12-Dev-Team on 19/04/2015.
 */

/**
 * this class manages the background of question's buttons
 *@author K12-Dev-Team
 */
public class BackgroundHandler extends ArrayList<Integer> {
    private Integer count = 0;

    /**
     * Constructor of the class
     * @param a background 1
     * @param b background 2
     * @param c background 3
     * @param d background 4
     */
    BackgroundHandler(int a, int b, int c, int d) {
        this.add(a);
        this.add(b);
        this.add(c);
        this.add(d);
    }

    /**
     *  Setter of the buttons
     * @param b Button
     */
    public void setbg(Button b) {
        if (count >= this.size()) { // reset the counter and randomly order the buttons when all them are assigned
            Collections.shuffle(this);
            count = 0;
        }
        b.setBackgroundResource(this.get(count++)); //assign the background
    }
}
