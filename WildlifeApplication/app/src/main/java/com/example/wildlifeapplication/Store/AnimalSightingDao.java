package com.example.wildlifeapplication.Store;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;

@Dao
public interface AnimalSightingDao {
    @Insert
    void insertSighting(AnimalSighting animalSighting);

}
