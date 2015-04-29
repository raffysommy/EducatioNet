package com.example.raffaele.testapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Filippo on 29/04/2015.
 */
public class TeacherQuestionList extends ArrayList<Query> implements Parcelable {
    public static final Parcelable.Creator<TeacherQuestionList> CREATOR= new Parcelable.Creator<TeacherQuestionList>(){ //creatore dell'argumentlist
        @Override
        public TeacherQuestionList createFromParcel(Parcel in){
            return new TeacherQuestionList(in);
        }

        @Override
        public TeacherQuestionList[] newArray(int size) {return new TeacherQuestionList[0];
        }
    };
    public TeacherQuestionList(){
        super();
    } //costruttore vuoto

    /**
     * @param in Parcel di ingresso
     */
    private TeacherQuestionList(Parcel in ){
        readFromParcel(in);
    } //Costruttore della parceable

    /**
     * @param in Parcel di ingresso
     */
    public void readFromParcel(Parcel in){
        this.clear(); //pulisce la lista per sicurezza
        in.readList(this,TeacherQuestionList.class.getClassLoader()); //riempie la lista con gli elementi dal parceable
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this);
    }
}
