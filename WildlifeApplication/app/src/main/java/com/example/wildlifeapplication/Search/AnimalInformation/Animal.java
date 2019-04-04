package com.example.wildlifeapplication.Search.AnimalInformation;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Animal {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "noun")
    private String noun;

    @NonNull
    @ColumnInfo(name = "scientific_noun")
    private String scientificNoun;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "max_body_length")
    private int maxBodyLength;

    @ColumnInfo(name = "min_body_length")
    private int minBodyLength;

    @ColumnInfo(name = "max_wingspan")
    private int maxWingspan;

    @ColumnInfo(name = "min_wingspan")
    private int minWingspan;

    @ColumnInfo(name = "identification")
    private String identification;

    @ColumnInfo(name = "colours")
    private String colours;

    @ColumnInfo(name = "habitat")
    private String habitat;

    @ColumnInfo(name = "best_time_to_see")
    private String bestTimeToSee;

    @ColumnInfo
    private String foodSource;

    @ColumnInfo
    private Integer imgId;

    public Animal() {}

    @Ignore
    public Animal(@NonNull String aNoun, @NonNull String aScientificNoun, String aType, Integer anImgId ) {
        if(aNoun.equalsIgnoreCase(aScientificNoun)){
            throw new IllegalArgumentException("Noun and scientific noun cannot be the same");
        }
        this.noun = aNoun;
        this.scientificNoun = aScientificNoun;
        this.type = aType;
        this.imgId = anImgId;
    }

    @Ignore
    public Animal(@NonNull String aNoun, @NonNull String aScientificNoun, String aType, int aMinBodyLength, int aMaxBodyLength,
                  String anIdentification, String someColours, String aHabitat, String aBestTimeToSee, String aFoodSource, Integer anImgId) {
        if(aNoun.equalsIgnoreCase(aScientificNoun)){
            throw new IllegalArgumentException("Noun and scientific noun cannot be the same");
        }
        this.noun = aNoun;
        this.scientificNoun = aScientificNoun;
        this.type = aType;
        this.minBodyLength = aMinBodyLength;
        this.maxBodyLength = aMaxBodyLength;
        this.identification = anIdentification;
        this.colours = someColours;
        this.habitat = aHabitat;
        this.bestTimeToSee = aBestTimeToSee;
        this.foodSource = aFoodSource;
        this.imgId = anImgId;
    }

    public Animal(String aNoun, String aScientificNoun, String aType, int aMinBodyLength, int aMaxBodyLength,
                  int aMinWingspan, int aMaxWingspan, String anIdentification, String beakAndFeatherColours, String aHabitat,
                  String aBestTimeToSee, String aFoodSource, Integer anImgId) {
        if(aNoun.equalsIgnoreCase(aScientificNoun)){
            throw new IllegalArgumentException("Noun and scientific noun cannot be the same");
        }
        this.noun = aNoun;
        this.scientificNoun = aScientificNoun;
        this.type = aType;
        this.minBodyLength = aMinBodyLength;
        this.maxBodyLength = aMaxBodyLength;
        this.minWingspan = aMinWingspan;
        this.maxWingspan = aMaxWingspan;
        this.identification = anIdentification;
        this.colours = beakAndFeatherColours;
        this.habitat = aHabitat;
        this.bestTimeToSee = aBestTimeToSee;
        this.foodSource = aFoodSource;
        this.imgId = anImgId;
    }

    @Override
    public String toString() {
        return noun+"\nScientific Noun: "+scientificNoun;
    }

    @NonNull
    public String getNoun() {
        return noun;
    }

    public void setNoun(@NonNull String noun) {
        if(noun.equalsIgnoreCase(this.scientificNoun)){
            throw new IllegalArgumentException("Noun and scientific noun cannot be the same");
        }
        this.noun = noun;
    }

    @NonNull
    public String getScientificNoun() {
        return scientificNoun;
    }

    public void setScientificNoun(@NonNull String scientificNoun) {
        if(scientificNoun.equalsIgnoreCase(this.noun)){
            throw new IllegalArgumentException("Noun and scientific noun cannot be the same");
        }
        this.scientificNoun = scientificNoun;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMaxBodyLength() {
        return maxBodyLength;
    }

    public void setMaxBodyLength(int maxBodyLength) {
        if(maxBodyLength < 0 || maxBodyLength < this.minBodyLength ){
            throw new IllegalArgumentException("maximum body length must be greater than 0 and greater than its minimum body length");
        }
        this.maxBodyLength = maxBodyLength;
    }

    public int getMinBodyLength() {
        return minBodyLength;
    }

    public void setMinBodyLength(int minBodyLength) {
        if(minBodyLength < 0 || minBodyLength > this.maxBodyLength ){
            throw new IllegalArgumentException("minimum body length must be greater than 0 and smaller than its maximum body length");
        }
        this.minBodyLength = minBodyLength;
    }

    public String getBodyLengthRange() {
        return Integer.toString(this.minBodyLength) + " - " + Integer.toString(this.maxBodyLength);
    }

    public int getMaxWingspan() {
        return maxWingspan;
    }

    public void setMaxWingspan(int maxWingspan) {
        if(maxBodyLength < 0 || maxBodyLength < this.minBodyLength ){
            throw new IllegalArgumentException("maximum body length must be greater than 0 and greater than its minimum body length");
        }
        this.maxWingspan = maxWingspan;
    }

    public int getMinWingspan() {
        return minWingspan;
    }

    public void setMinWingspan(int minWingspan) {
        if(minWingspan < 0 || minWingspan > this.maxWingspan ){
            throw new IllegalArgumentException("minimum wingspan must be greater than 0 and smaller than its maximum wingspan");
        }
        this.minWingspan = minWingspan;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String[] getColoursAsStringArray() {
        if(colours!= null) {
            return colours.split(", ");
        }else {
            return new String[]{};
        }
    }

    public String getColours() {
        return colours;
    }

    public void setColours(String colours) {
        this.colours = colours;
    }

    public String getHabitat() {
        return habitat;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public String getBestTimeToSee() {
        return bestTimeToSee;
    }

    public void setBestTimeToSee(String bestTimeToSee) {
        this.bestTimeToSee = bestTimeToSee;
    }

    public String getFoodSource() {
        return foodSource;
    }

    public void setFoodSource(String foodSource) {
        this.foodSource = foodSource;
    }

    public Integer getImgId() {
        return imgId;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }
}
