package com.example.raffaele.testapp;

import android.os.Parcel;
import android.os.Parcelable;

/***
 * Created by Raffaele on 25/03/2015.
 */
public class Argument implements Parcelable {
    private String Arg;
    private String Descr;
    private boolean Check;
    public static final Creator<Argument> CREATOR= new Creator<Argument>(){
        @Override
        public Argument createFromParcel(Parcel in){
            return new Argument(in);
        }

        @Override
        public Argument[] newArray(int size) {
            return new Argument[size];
        }
    };
    private Argument(Parcel in ){
        readFromParcel(in);
    }
    public Argument(String Arg,String Descr){
        setArg(Arg);
        setDescr(Descr);
        setCheck(false);
    }
    public Argument(){
        setArg("");
        setDescr("");
        setCheck(false);
    }
    public Argument(Argument argument){
        setArg(argument.getArg());
        setDescr(argument.getDescr());
        setCheck(argument.isCheck());
    }
    public String getArg() {
        return Arg;
    }

    public void setArg(String arg) {
        Arg = arg;
    }

    public String getDescr() {
        return Descr;
    }

    public void setDescr(String descr) {
        Descr = descr;
    }

    public boolean isCheck() {
        return Check;
    }

    public void setCheck(boolean check) {
        Check = check;
    }
    public void readFromParcel(Parcel in) {
        setArg(in.readString());
        setDescr(in.readString());
        setCheck(in.readByte() != 0);
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Arg);
        dest.writeString(Descr);
        dest.writeInt((byte) (isCheck() ? 1 : 0));
    }
}

