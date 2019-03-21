package com.example.wildlifeapplication.Map;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities = {Spotting.class}, version = 1, exportSchema = false)
@TypeConverters({DateTypeConverter.class})
public abstract class SpottingOfAnimalsDatabase extends RoomDatabase {
    public abstract SpottingDao spottingAnimalDao();
}
