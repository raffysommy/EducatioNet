package com.example.raffaele.testapp;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Raffaele on 11/03/2015.
 */
public class Query {
    private String Domanda;
    private String Risposta;
    private ArrayList<String> Risposteprob;

    public Query(String domanda,String risposta,String rispostaf1,String rispostaf2,String rispostaf3){
        setDomanda(domanda);
        setRisposta(risposta);
        setRispostarray(rispostaf1, rispostaf2, rispostaf3, risposta);
    }
    public Query(){
        setDomanda("");
        setRisposta("");
        setRispostarray("","","","");
    }
    public Query(Query q){
       setDomanda(q.getDomanda());
       setRisposta(q.getRisposta());
       setRisposteprob(q.getRisposteprob());
    }

    public String getDomanda() {
        return Domanda;
    }

    public void setDomanda(String domanda) {
        Domanda = domanda;
    }

    public String getRisposta() {
        return Risposta;
    }
    public void setRisposta(String risposta) {
        Risposta = risposta;
    }


    public ArrayList<String> getRisposteprob() {
        return Risposteprob;
    }

    public void setRisposteprob(ArrayList<String> risposteprob) {
        Risposteprob = (ArrayList<String>)risposteprob.clone();
    }
    public void setRispostarray(String a,String b,String c,String d) {
        ArrayList<String> rispostepro = new ArrayList<String>();
        rispostepro.add(a);
        rispostepro.add(b);
        rispostepro.add(c);
        rispostepro.add(d);
        setRisposteprob(rispostepro);
    }
    public void RandomQuery(){

        Collections.shuffle(this.Risposteprob);
    }
}