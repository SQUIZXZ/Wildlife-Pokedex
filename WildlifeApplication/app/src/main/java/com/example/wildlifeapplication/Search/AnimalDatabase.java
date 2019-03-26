package com.example.wildlifeapplication.Search;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.example.wildlifeapplication.Map.DateTypeConverter;

@Database(entities = {Animal.class}, version = 1, exportSchema = false)
@TypeConverters({DateTypeConverter.class})
public abstract class AnimalDatabase extends RoomDatabase {
    public abstract AnimalDao animalDao();
}
