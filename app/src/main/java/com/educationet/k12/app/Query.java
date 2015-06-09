package com.educationet.k12.app;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;

/***
 * Created by K12-Dev-Team on 11/03/2015.
 */

/**
 * Query Mapper Class
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
     * constructor of the class question
     * @param id_domanda Id of the question
     * @param domanda   question
     * @param risposta Right answer
     * @param rispostaf1 wrong answer 1
     * @param rispostaf2 wrong answer 2
     * @param rispostaf3 wrong answer 3
     * @param topic Argument
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
     * @param in object parceable to be converted
     */
    private Query(Parcel in ){
        readFromParcel(in);
    }
    /**
     *  empty constructor
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
     * copy constructor
     * @param q question to be copy
     */
    public Query(final Query q){
       setid_domanda(q.getid_domanda());
       setDomanda(q.getDomanda());
       setRisposta(q.getRisposta());
       setRisposteprob(q.getRisposteprob());
       setTopic(q.getTopic());
    }

    /**
     * @return returns Id of the question
     */
    public String getid_domanda() {
        return id_domanda;
    }

    /**
     * @param id_domanda set Id of the question
     */
    public void setid_domanda(final String id_domanda) {
        this.id_domanda = id_domanda;
    }

    /**
     * @return returns the question
     */
    public String getDomanda() {
        return Domanda;
    }

    /**
     * @param domanda set the question
     */
    private void setDomanda(final String domanda) {
        Domanda = domanda;
    }

    /**
     * @return returns the question
     */
    public String getRisposta() {
        return Risposta;
    }

    /**
     * @param risposta set the answer
     */
    private void setRisposta(final String risposta) {
        Risposta = risposta;
    }

    /**
     * @return returns an array list of probable answers
     */
    public ArrayList<String> getRisposteprob() {
        return Risposteprob;
    }

    /**
     * @param risposteprob set the probable answers
     */
    public void setRisposteprob(ArrayList<String> risposteprob) {
        this.Risposteprob =risposteprob;
    }

    /**
     * @param a answer 1
     * @param b answer 2
     * @param c answer 3
     * @param d answer 4
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
     * @return Argument question
     */
    public String getTopic() {
        return topic;
    }

    /**
     * @param topic Argument
     */
    private void setTopic(String topic) {
        this.topic = topic;
    }

    /**
     * Sort randomly answers
     */
    public void RandomQuery(){
        Collections.shuffle(this.Risposteprob);
    }

    /**
     * Default method interface parcel
     * @return 0
     */

    /**
     *
     * @param in Receives as input a question parceled and set private members with the parameters of the parcel through readString
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
     * method of writing of parcel
     * @param dest Parcel of destination
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