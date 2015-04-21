package com.example.raffaele.testapp;

import android.os.Parcel;
import android.os.Parcelable;
/***
 * Classe degli Score
 * @author K12-Dev-Team
 * Created by K12-Dev-Team on 21/03/2015.
 * @version 0.1
 */


public class Score implements Parcelable {
     private int value;

    /**
     * Costruttore inzializzato a zerpo
     */
    public Score() {
        setValue(0);
    }

    /**
     *
     * @return Ritorna il valore
     */
    public int getValue() {
        return value;
    }

    /**
     *
     * @param value imposta il valore
     */
    private void setValue(int value) {
        this.value = value;
    }

    /**
     * incrementa il valore dello Score di uno
     */
    public void increment() {
        setValue(getValue() + 1);
    }

    /**
     *
     * @return Ritorna il tipo stringa del tipo intero
     */
    public String StringValue() {
        return String.valueOf(value);
    }

    /**
     *
     * @param parcel viene inizializzato con il valore value
     */
    private Score(Parcel parcel){
        this.value= parcel.readInt();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    /**
     *
     * @param dest viene scritto il valore value di tipo Int
     * @param flags descrizione del valore
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
       dest.writeInt(value);
    }


    public final static Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        /**
         *
         * @param size cardinalità dell'array
         * @return ritorna l'array di tipo Score e di cardinalità size
         */
        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };

}
