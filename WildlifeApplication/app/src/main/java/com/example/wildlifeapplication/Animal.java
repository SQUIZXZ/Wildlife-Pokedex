package com.example.wildlifeapplication;

public class Animal {
    private String noun;
    private String scientificNoun;

    public Animal(String aNoun, String aScientificNoun) {
        this.noun = aNoun;
        this.scientificNoun = aScientificNoun;
    }

    @Override
    public String toString() {
        return "Noun: "+noun+"\nScientific Noun: "+scientificNoun;
    }

    public String getNoun() {
        return noun;
    }

    public String getScientificNoun() {
        return scientificNoun;
    }


}
