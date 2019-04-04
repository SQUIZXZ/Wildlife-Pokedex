package com.example.wildlifeapplication.Store;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.google.android.gms.maps.model.LatLng;

@Database(entities = {AnimalSighting.class},version =1,exportSchema = false)
public abstract class AnimalSightingDatabase extends RoomDatabase {
    public abstract AnimalSightingDao animalSightingDao();
}
