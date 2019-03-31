package com.example.wildlifeapplication.Search.AnimalInformation;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AnimalDao {

    @Query("SELECT * FROM Animal")
    List<Animal> getAllAnimals();

    @Query("SELECT * FROM Animal WHERE scientific_noun = :aScientificNoun")
    Animal getAnimal(String aScientificNoun);

    @Insert
    void insertAnimal(Animal animal);

    @Insert
    void insertAllAnimals(List<Animal> listOfAnimals);

    @Delete
    void clearDatabase(List<Animal> AnimalList);
}
