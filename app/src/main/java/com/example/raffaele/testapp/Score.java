package com.example.raffaele.testapp;

import android.os.Parcel;
import android.os.Parcelable;
/***
 * Created by Antonio on 21/03/2015.
 */


public class Score implements Parcelable {
    private int value;

    @Override
    public String toString() {
        return String.valueOf(this.value);
    }

    public Score() {
        setValue(0);
    }

    public int getValue() {
        return value;
    }

    private void setValue(int value) {
        this.value = value;
    }

    //incrementa il contatatore
    public void increment() {
        setValue(getValue() + 1);
    }

    //restituisce il tipo stringa da int
    public String StringValue() {
        return String.valueOf(value);
    }

    // Parcelling part
    public Score(Parcel parcel){
        this.value= parcel.readInt();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
       dest.writeInt(value);
    }


    public final static Parcelable.Creator CREATOR = new Parcelable.Creator() {
        @Override
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };

}
