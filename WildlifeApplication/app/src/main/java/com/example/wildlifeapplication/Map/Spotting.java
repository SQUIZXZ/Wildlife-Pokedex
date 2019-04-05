package com.example.wildlifeapplication.Map;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity
public class Spotting {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int spottingID;

    @ColumnInfo(name = "noun")
    private String noun;

    @ColumnInfo(name = "scientific_noun")
    private String scientificNoun;

    @ColumnInfo(name = "latitude_of_spotting")
    private Float latitudeOfSpotting;

    @ColumnInfo(name = "longtitude_of_spotting")
    private Float longitudeOfSpotting;

    @ColumnInfo(name = "datetime_of_spotting")
    @TypeConverters({DateTypeConverter.class})
    private Date datetimeOfSpotting;


    @NonNull
    public int getSpottingID() {
        return spottingID;
    }

    public void setSpottingID(@NonNull int spottingID) {
        this.spottingID = spottingID;
    }

    public String getNoun() {
        return noun;
    }

    public void setNoun(String noun) {
        this.noun = noun;
    }

    public String getScientificNoun() {
        return scientificNoun;
    }

    public void setScientificNoun(String scientificNoun) {
        this.scientificNoun = scientificNoun;
    }

    public Float getLatitudeOfSpotting() {
        return latitudeOfSpotting;
    }

    public void setLatitudeOfSpotting(Float latitudeOfSpotting) {
        this.latitudeOfSpotting = latitudeOfSpotting;
    }

    public Float getLongitudeOfSpotting() {
        return longitudeOfSpotting;
    }

    public void setLongitudeOfSpotting(Float longitudeOfSpotting) {
        this.longitudeOfSpotting = longitudeOfSpotting;
    }

    public Date getDatetimeOfSpotting() {
        return datetimeOfSpotting;
    }

    public void setDatetimeOfSpotting(Date datetimeOfSpotting) {
        this.datetimeOfSpotting = datetimeOfSpotting;
    }

    public Spotting(){}


    public Spotting(String aNoun, String aScientficNoun, Float aLatitudeOfSpotting, Float aLongitudeOfSpotting, Date aDatetimeOfSpotting ){
        this.noun = aNoun;
        this.scientificNoun = aScientficNoun;
        this.latitudeOfSpotting = aLatitudeOfSpotting;
        this.longitudeOfSpotting = aLongitudeOfSpotting;
        this.datetimeOfSpotting = aDatetimeOfSpotting;
    }
}

