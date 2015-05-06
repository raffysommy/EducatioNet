package com.educationet.k12.app;

import android.os.Parcel;
import android.os.Parcelable;

/***
 * Class of Argument
 * @author K12-Dev-Team
 * Created by K12-Dev-Team on 25/03/2015.
 * @version 0.1
 * @see android.os.Parcelable
 * Implements Parceable
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

    /**
     *
     * @param in parcel object to convert
     */
    private Argument(Parcel in ){
        readFromParcel(in);
    }

    /**
     *
     * @param Arg Argument
     * @param Descr Description of Argument
     */
    public Argument(String Arg,String Descr){
        setArg(Arg);
        setDescr(Descr);
        setCheck(false);
    }

    /**
     * Null Constructor
     */
    public Argument(){
        setArg("");
        setDescr("");
        setCheck(false);
    }

    /**
     *
     * @param argument Copy Constructor
     */
    public Argument(Argument argument){
        setArg(argument.getArg());
        setDescr(argument.getDescr());
        setCheck(argument.isCheck());
    }

    /**
     *
     * @return Return the Argument
     */
    public String getArg() {
        return Arg;
    }

    /**
     *
     * @param arg Set the Argument
     */
    private void setArg(String arg) {
        Arg = arg;
    }

    /**
     *
     * @return  Return description of Argument
     */
    public String getDescr() {
        return Descr;
    }

    /**
     *
     * @param descr Set description
     */
    private void setDescr(String descr) {
        Descr = descr;
    }

    /**
     *
     * @return Return check-status of Argument
     */
    public boolean isCheck() {
        return Check;
    }

    /**
     *
     * @param check Set checked Argument
     */
    public void setCheck(boolean check) {
        Check = check;
    }

    /**
     *
     * @param in Read a parcel element and set the private members with readString
     */
    void readFromParcel(Parcel in) {
        setArg(in.readString());
        setDescr(in.readString());
        setCheck(in.readByte() != 0);

    }

    /**
     * Default Methods of Parcel Interface
     * @return 0
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Write Object to Parcel
     * @param dest Destination Parcel
     * @param flags (optional)
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Arg);
        dest.writeString(Descr);
        dest.writeInt((byte) (isCheck() ? 1 : 0));

    }
}

