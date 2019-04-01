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

    @Query("SELECT * FROM Animal WHERE scientific_noun = :scientificNoun")
    Animal getAnimalWithScientificNoun(String scientificNoun);

    @Query("SELECT * FROM Animal WHERE  type= :type")
    List<Animal> getAnimalOfType(String type);

    @Query("SELECT * FROM Animal WHERE  min_body_length= :minBodyLength")
    List<Animal> getAnimalWithMinLength(int minBodyLength);

    @Query("SELECT * FROM Animal WHERE  max_body_length= :maxBodyLength")
    List<Animal> getAnimalOfWithMaxLength(int maxBodyLength);

    @Query("SELECT * FROM Animal WHERE  colours= :colours")
    List<Animal> getAnimalWithColours(String colours);

    @Insert
    void insertAnimal(Animal animal);

    @Insert
    void insertAllAnimals(List<Animal> listOfAnimals);

    @Delete
    void clearDatabase(List<Animal> AnimalList);
}
