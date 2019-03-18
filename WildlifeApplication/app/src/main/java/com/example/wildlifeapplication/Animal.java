package com.example.wildlifeapplication;

public class Animal {
    private String noun;
    private String scientificNoun;

    public Animal(String aNoun, String aScientificNoun) {
        this.noun = aNoun;
        this.scientificNoun = aScientificNoun;
    }

    public String getNoun() {
        return noun;
    }

    public String getScientificNoun() {
        return scientificNoun;
    }
}
