package com.example.wildlifeapplication.Map;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface SpottingDao {

    @Query("SELECT * FROM Spotting")
    List<Spotting> getAllSpottingOfAnimals();

    @Query("SELECT * FROM Spotting ORDER BY datetime_of_spotting DESC LIMIT 10")
    List<Spotting> getRecentSpottings();

    @Insert
    void insertAll(List<Spotting> listOfSpottings);

    @Insert
    void insertSpotting(Spotting spotting);

    @Delete
    void clearDatabase(List<Spotting> spottingList);
}
