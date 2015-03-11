package com.example.raffaele.testapp;

/**
 * Created by Raffaele on 11/03/2015.
 */
public class Query {
    public Query(String domanda,String risposta,String rispostafalsa1,String rispostafalsa2,String rispostafalsa3){
        setDomanda(domanda);
        setRisposta(risposta);
        setRispostafalsa1(rispostafalsa1);
        setRispostafalsa2(rispostafalsa2);
        setRispostafalsa3(rispostafalsa3);
    }
    public Query(Query q){
       setDomanda(q.getDomanda());
       setRisposta(q.getRisposta());
       setRispostafalsa1(q.getRispostafalsa1());
       setRispostafalsa2(q.getRispostafalsa2());
       setRispostafalsa3(q.getRispostafalsa3());
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

    public String getRispostafalsa2() {
        return Rispostafalsa2;
    }

    public void setRispostafalsa2(String rispostafalsa2) {
        Rispostafalsa2 = rispostafalsa2;
    }

    public String getRispostafalsa1() {
        return Rispostafalsa1;
    }

    public void setRispostafalsa1(String rispostafalsa1) {
        Rispostafalsa1 = rispostafalsa1;
    }

    public String getRispostafalsa3() {
        return Rispostafalsa3;
    }

    public void setRispostafalsa3(String rispostafalsa3) {
        Rispostafalsa3 = rispostafalsa3;
    }

    private String Domanda;
    private String Risposta;
    private String Rispostafalsa1;
    private String Rispostafalsa2;
    private String Rispostafalsa3;

}