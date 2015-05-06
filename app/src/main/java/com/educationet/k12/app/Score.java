package com.educationet.k12.app;

import android.os.Parcel;
import android.os.Parcelable;
/***
 * Score's class
 * @author K12-Dev-Team
 * Created by K12-Dev-Team on 21/03/2015.
 * @version 0.1
 */


public class Score implements Parcelable {
     private int value;

    /**
     * Constructor initialized to zero
     */
    public Score() {
        setValue(0);
    }

    /**
     *
     * @return return the value
     */
    public int getValue() {
        return value;
    }

    /**
     *
     * @param value impose the value
     */
    private void setValue(int value) {
        this.value = value;
    }

    /**
     * increments the value of Score of one
     */
    public void increment() {
        setValue(getValue() + 1);
    }

    /**
     *
     * @return Returns the string type of the  integer type
     */
    public String StringValue() {
        return String.valueOf(value);
    }

    /**
     *
     * @param parcel it is initialized with the value "value"
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
     * @param dest is written the value "value" of Int type
     * @param flags value's description
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
         * @param size array's size
         * @return returns array of Score type and of size "size"
         */
        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };

}
