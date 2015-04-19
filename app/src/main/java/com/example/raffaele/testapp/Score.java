package com.example.raffaele.testapp;

import android.os.Parcel;
import android.os.Parcelable;
/***
 * Created by Antonio P on 21/03/2015.
 * Classe realizzata per la gestione degli Scores. Comprende metodi che permettono la gestione dei risultati ottenuti
 * ed implementa la classe "Parcelable" che consente di passare i dati da una activity all'altra dell'app.
 */


public class Score implements Parcelable {
    //dichiaro un tipo privato per la mia classe
    private int value;



    //costruttore inizializzato a 0
    public Score() {
        setValue(0);
    }
    //getter
    public int getValue() {
        return value;
    }
    //seter
    private void setValue(int value) {
        this.value = value;
    }

    //incrementa di 1 il valore dell'oggetto
    public void increment() {
        setValue(getValue() + 1);
    }

    //metodo che mi ritorna il valore di tipo stringa dell'oggetto value
    public String StringValue() {
        return String.valueOf(value);
    }

    // Inizializzazione dell'oggetto Parcel
    public Score(Parcel parcel){
        this.value= parcel.readInt();
    }

    @Override
    public int describeContents(){
        return 0;
    }
    //metodo che mi permette di scrivere in un oggetto di tipo Parcel a cui è associato un flag
    @Override
    public void writeToParcel(Parcel dest, int flags) {
       dest.writeInt(value);
    }

    //costruttore del Parcel
    public final static Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }
        //Array che contiene tutti gli oggetti che devo passare tramite i metodi di Parcel
        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };

}
