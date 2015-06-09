package com.educationet.k12.app;

/**
 * Qestionnaire Mapper Class
 * @author K12-Dev-Team
 * @version 0.1
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

    /**
     * Costructor
     * @param id Id
     * @param name Name of Questionnaire
     */
    public Questionnaire(String id,String name) {
        this.name = name;
        this.id=id;
    }
}
