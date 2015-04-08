package com.example.raffaele.testapp;

import java.util.ArrayList;
import java.util.Collections;

/***
 * Created by Raffaele on 11/03/2015.
 */
public class Query {

    private String id_domanda;
    private String Domanda;
    private String Risposta;
    private ArrayList<String> Risposteprob;

    public Query(String id_domanda,String domanda,String risposta,String rispostaf1,String rispostaf2,String rispostaf3){
        setid_domanda(id_domanda);
        setDomanda(domanda);
        setRisposta(risposta);
        setRispostarray(rispostaf1, rispostaf2, rispostaf3, risposta);
    }
    public Query(){
        setid_domanda("");
        setDomanda("");
        setRisposta("");
        setRispostarray("","","","");
    }
    public Query(Query q){
       setid_domanda(q.getid_domanda());
       setDomanda(q.getDomanda());
       setRisposta(q.getRisposta());
       setRisposteprob(q.getRisposteprob());
    }
    public String getid_domanda() {
        return id_domanda;
    }

    public void setid_domanda(String id_domanda) {
        this.id_domanda = id_domanda;
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
    @SuppressWarnings({"unchecked"})
    public void setRisposteprob(ArrayList<String> risposteprob) {
        Risposteprob = (ArrayList<String>) risposteprob.clone();
    }
    public void setRispostarray(String a,String b,String c,String d) {
        ArrayList<String> rispostepro = new ArrayList<>();
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