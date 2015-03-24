package com.example.raffaele.testapp;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Antonio on 21/03/2015.
 */


public class Score implements Parcelable{
    private int value;



    public static final Creator<Score> CREATOR= new Creator<Score>(){
        @Override
        public Score createFromParcel(Parcel in){
            return new Score(in);
        }

        @Override
        public Score[] newArray(int size) {return new Score[size];}
    };
    private Score(Parcel in){readFromParcel(in);}
    public Score(int x) {
        setValue(x);
    }
    public Score () {
        setValue(0);
    }

    public int getValue() {
        return value;
    }

    private void setValue(int value) {
        this.value = value;
    }
    //incrementa il contatatore
    public void increment (){
        setValue(getValue()+1);
    }

    //restituisce il tipo stringa da int
    public String StringValue ()
    {
       return String.valueOf(value);
    }
       //parcelable
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(value);
    }
    public void readFromParcel (Parcel in){
        value= in.readInt();

    }

    @Override
    public int describeContents() {return 0;}
}

/*
public class Score {
    private int Correct;
    private int Wrong;

    public Score(int correct, int wrong) {
        setCorrect(correct);
        setWrong(wrong);
    }
    public Score(){
        setCorrect(0);
        setWrong(0);
    }
    public void inccorrect() {
        setCorrect(getCorrect()+1);
    }
    public void incwrong() {
        setWrong(getWrong()+1);
    }

    public int getCorrect() {
        return Correct;
    }
    public String getCorrectString(){return String.valueOf(Correct);}
    public String getWrongString(){return String.valueOf(Wrong);}
    public void setCorrect(int correct) {
        Correct = correct;
    }

    public int getWrong() {
        return Wrong;
    }

    public void setWrong(int wrong) {
        Wrong = wrong;
    }
};


*/