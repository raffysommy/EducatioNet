package com.example.raffaele.testapp;

import java.util.ArrayList;
import java.util.Collections;

/***
 * Created by Raffaele on 11/03/2015.
 */

/**
 * Classe delle Domande
 * @author Raffaele
 * @version 0.1
 */
public class Query {

    private String id_domanda;
    private String Domanda;
    private String Risposta;
    private ArrayList<String> Risposteprob;

    /**
     * Costruttore della classe Domanda
     * @param id_domanda Id della domanda
     * @param domanda   Domanda
     * @param risposta Risosta esatta
     * @param rispostaf1 Risposta errata 1
     * @param rispostaf2 Risposta errata 2
     * @param rispostaf3 Risposta errata 3
     */
    public Query(final String id_domanda,final String domanda,final String risposta,final String rispostaf1,final String rispostaf2,final String rispostaf3){
        setid_domanda(id_domanda);
        setDomanda(domanda);
        setRisposta(risposta);
        setRispostarray(rispostaf1, rispostaf2, rispostaf3, risposta);
    }

    /**
     *  Costruttore Vuoto
     */
    public Query(){
        setid_domanda("");
        setDomanda("");
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
    @SuppressWarnings({"unchecked"})
    public void setRisposteprob(final ArrayList<String> risposteprob) {
        Risposteprob = (ArrayList<String>) risposteprob.clone();
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
     * Ordina casualmente le risposte
     */
    public void RandomQuery(){
        Collections.shuffle(this.Risposteprob);
    }
}