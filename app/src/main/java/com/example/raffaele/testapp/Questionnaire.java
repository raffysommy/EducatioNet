package com.example.raffaele.testapp;

/**
 * Created by Muscetti on 25/04/2015.
 */
public class Questionnaire  {
    private String Name;
    private String Description;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public Questionnaire(String name) {
        Name = name;
    }
}
