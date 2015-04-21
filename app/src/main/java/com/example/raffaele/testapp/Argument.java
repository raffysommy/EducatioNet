package com.example.raffaele.testapp;

import android.os.Parcel;
import android.os.Parcelable;

/***
 * Classe degli Argomenti
 * @author K12-Dev-Team
 * Created by K12-Dev-Team on 25/03/2015.
 * @version 0.1
 */
public class Argument implements Parcelable { //Classe argomento che implementa l'estensione parceable
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
     * @param in oggetto parceable da convertire
     */
    private Argument(Parcel in ){
        readFromParcel(in);
    }

    /**
     *
     * @param Arg Argomento
     * @param Descr Descrizione dell'argomento
     */
    public Argument(String Arg,String Descr){
        setArg(Arg);
        setDescr(Descr);
        setCheck(false);
    }

    /**
     * Costruttore nullo
     */
    public Argument(){
        setArg("");
        setDescr("");
        setCheck(false);
    }

    /**
     *
     * @param argument Costruttore di copia
     */
    public Argument(Argument argument){
        setArg(argument.getArg());
        setDescr(argument.getDescr());
        setCheck(argument.isCheck());
    }

    /**
     *
     * @return Ritorna l'argomento
     */
    public String getArg() {
        return Arg;
    }

    /**
     *
     * @param arg Imposta l'argomento
     */
    private void setArg(String arg) {
        Arg = arg;
    }

    /**
     *
     * @return  Ritorna la descrizione dell'argomento
     */
    public String getDescr() {
        return Descr;
    }

    /**
     *
     * @param descr Imposta la descrizione
     */
    private void setDescr(String descr) {
        Descr = descr;
    }

    /**
     *
     * @return Ritorna se l'argomento è selezionato
     */
    public boolean isCheck() {
        return Check;
    }

    /**
     *
     * @param check Imposta la selezione sull'argomento
     */
    public void setCheck(boolean check) {
        Check = check;
    }

    /**
     *
     * @param in Riceve in ingresso un argomento parcellizzato e imposta i membri privati con i parametri del parcel attraverso il readString
     */
    void readFromParcel(Parcel in) {
        setArg(in.readString());
        setDescr(in.readString());
        setCheck(in.readByte() != 0);

    }

    /**
     * Metodo di default dell'interfaccia parcel
     * @return 0
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Metodo di scrittura del parcel
     * @param dest Parcel di destinazione
     * @param flags (optional)
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Arg);
        dest.writeString(Descr);
        dest.writeInt((byte) (isCheck() ? 1 : 0));

    }
}

