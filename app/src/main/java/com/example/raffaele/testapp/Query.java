package com.example.raffaele.testapp;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

/***
 * Created by K12-Dev-Team on 11/03/2015.
 */

/**
 * Classe delle Domande
 * @author K12-Dev-Team
 * @version 0.1
 */
public class Query implements Parcelable {

    private String id_domanda;
    private String Domanda;
    private String Risposta;
    private ArrayList<String> Risposteprob;
    private String topic;
    public static final Parcelable.Creator<Query> CREATOR= new Parcelable.Creator<Query>(){
        @Override
        public Query createFromParcel(Parcel in){
            return new Query(in);
        }

        @Override
        public Query[] newArray(int size) {
            return new Query[size];
        }
    };
    /**
     * Costruttore della classe Domanda
     * @param id_domanda Id della domanda
     * @param domanda   Domanda
     * @param risposta Risosta esatta
     * @param rispostaf1 Risposta errata 1
     * @param rispostaf2 Risposta errata 2
     * @param rispostaf3 Risposta errata 3
     * @param topic Argomento
     */
    public Query(final String id_domanda,final String domanda,final String risposta,final String rispostaf1,final String rispostaf2,final String rispostaf3,final String topic){
        setid_domanda(id_domanda);
        setDomanda(domanda);
        setRisposta(risposta);
        setRispostarray(rispostaf1, rispostaf2, rispostaf3, risposta);
        setTopic(topic);
    }
    /**
     *
     * @param in oggetto parceable da convertire
     */
    private Query(Parcel in ){
        readFromParcel(in);
    }
    /**
     *  Costruttore Vuoto
     */
    public Query(){
        setid_domanda("");
        setDomanda("");
        setRisposta("");
        setRispostarray("", "", "", "");
    }
    public Query(String domanda){
        setid_domanda("");
        setDomanda(domanda);
        setRisposta("");
        setRispostarray("","","","");
    }

    /**
     * Costruttore di copia
     * @param q Domanda da copiare
     */
    public Query(final Query q){
       setid_domanda(q.getid_domanda());
       setDomanda(q.getDomanda());
       setRisposta(q.getRisposta());
       setRisposteprob(q.getRisposteprob());
       setTopic(q.getTopic());
    }

    /**
     * @return Ritorna L'Id della domanda
     */
    public String getid_domanda() {
        return id_domanda;
    }

    /**
     * @param id_domanda Imposta L'Id della domanda
     */
    public void setid_domanda(final String id_domanda) {
        this.id_domanda = id_domanda;
    }

    /**
     * @return Ritorna la domanda
     */
    public String getDomanda() {
        return Domanda;
    }

    /**
     * @param domanda Imposta la domanda
     */
    private void setDomanda(final String domanda) {
        Domanda = domanda;
    }

    /**
     * @return Ritorna la risposta
     */
    public String getRisposta() {
        return Risposta;
    }

    /**
     * @param risposta Imposta la risposta
     */
    private void setRisposta(final String risposta) {
        Risposta = risposta;
    }

    /**
     * @return Ritorna un array list delle risposte probabili
     */
    public ArrayList<String> getRisposteprob() {
        return Risposteprob;
    }

    /**
     * @param risposteprob Imposta le risposte probabili
     */
    public void setRisposteprob(ArrayList<String> risposteprob) {
        this.Risposteprob =risposteprob;
    }

    /**
     * @param a Risposta 1
     * @param b Risposta 2
     * @param c Risposta 3
     * @param d Risposta 4
     */
    private void setRispostarray(final String a,final String b,final String c,final String d) {
        ArrayList<String> rispostepro = new ArrayList<>();
        rispostepro.add(a);
        rispostepro.add(b);
        rispostepro.add(c);
        rispostepro.add(d);
        setRisposteprob(rispostepro);
    }

    /**
     * @return Argomento Domanda
     */
    public String getTopic() {
        return topic;
    }

    /**
     * @param topic Argomento
     */
    private void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * Ordina casualmente le risposte
     */
    public void RandomQuery(){
        Collections.shuffle(this.Risposteprob);
    }

    /**
     * Metodo di default dell'interfaccia parcel
     * @return 0
     */

    /**
     *
     * @param in Riceve in ingresso una domanda parcellizzata e imposta i membri privati con i parametri del parcel attraverso il readString
     */
    private void readFromParcel(Parcel in) {
        setid_domanda(in.readString());
        setDomanda(in.readString());
        setRisposta(in.readString());
        setRisposteprob(in.createStringArrayList());
        //Log.d("TeacherQuestionresult", Risposteprob.get(3));
        setTopic(in.readString());
    }
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
        dest.writeString(id_domanda);
        dest.writeString(Domanda);
        dest.writeString(Risposta);
        dest.writeStringList(Risposteprob);
        dest.writeString(topic);
    }
}