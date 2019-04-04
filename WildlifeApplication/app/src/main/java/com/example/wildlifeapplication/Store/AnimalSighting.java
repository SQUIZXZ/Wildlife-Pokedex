package com.example.wildlifeapplication.Store;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;

@Entity
public class AnimalSighting {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "noun")
    private String noun;

    @NonNull
    @ColumnInfo(name = "scientific_noun")
    private String scientificNoun;

    @ColumnInfo(name = "body_length")
    private int bodyLength;

    @ColumnInfo(name = "wingspan")
    private int wingspan;

    @ColumnInfo(name = "colour")
    private String colour;

    @ColumnInfo(name = "habitat")
    private String habitat;

    @ColumnInfo(name = "time_of_year")
    private String timeOfYear;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "longitude")
    private double longitude;

    public AnimalSighting() {}

    @Ignore
    public AnimalSighting(@NonNull String aNoun,@NonNull String aScientficNoun, int aBodyLength,
                          int aWingspan, String aColour, String aHabitat, String aTimeOfYear, double aLatitude, double aLongitude){
        this.noun = aNoun;
        this.scientificNoun = aScientficNoun;
        this.bodyLength = aBodyLength;
        this.wingspan = aWingspan;
        this.colour = aColour;
        this.habitat = aHabitat;
        this.timeOfYear = aTimeOfYear;
        this.latitude = aLatitude;
        this.longitude = aLongitude;
    }

    public void setNoun(@NonNull String noun) {
        this.noun = noun;
    }

    public void setScientificNoun(@NonNull String scientificNoun) {
        this.scientificNoun = scientificNoun;
    }

    public void setBodyLength(int bodyLength) {
        this.bodyLength = bodyLength;
    }

    public void setWingspan(int wingspan) {
        this.wingspan = wingspan;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public void setHabitat(String habitat) {
        this.habitat = habitat;
    }

    public void setTimeOfYear(String timeOfYear) {
        this.timeOfYear = timeOfYear;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @NonNull
    public String getNoun() {
        return noun;
    }

    @NonNull
    public String getScientificNoun() {
        return scientificNoun;
    }

    public int getBodyLength() {
        return bodyLength;
    }

    public int getWingspan() {
        return wingspan;
    }

    public String getColour() {
        return colour;
    }

    public String getHabitat() {
        return habitat;
    }

    public String getTimeOfYear() {
        return timeOfYear;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
