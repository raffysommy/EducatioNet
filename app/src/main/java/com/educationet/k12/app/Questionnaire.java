package com.educationet.k12.app;

/**
 * Created by K12-Dev-Team on 25/04/2015.
 */
public class Questionnaire  {

    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public Questionnaire(String id,String name) {
        this.name = name;
        this.id=id;
    }
}
