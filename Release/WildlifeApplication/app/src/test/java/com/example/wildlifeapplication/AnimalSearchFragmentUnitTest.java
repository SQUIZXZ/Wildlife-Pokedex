package com.example.wildlifeapplication;

import com.example.wildlifeapplication.Search.AnimalInformation.Animal;
import com.example.wildlifeapplication.Search.AnimalSearchFragment;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class AnimalSearchFragmentUnitTest {
    AnimalSearchFragment animalSearchFragment = new AnimalSearchFragment();
    List<Animal> mAllAnimals = new ArrayList<>();
    List<Animal> animalsWithSelectedType = new ArrayList<>();
    List<Animal> animalsWithSelectedMinLength = new ArrayList<>();


    @Before
    public void setUp() {
        //Create animals
        Animal animal1 = new Animal("animal1", "scientificAnimal1", "Bird", 10, 15, "identification", "blue", "habitat1", "anytime", "a food source", R.mipmap.blue_tit);
        Animal animal2 = new Animal("animal2", "scientificAnimal2", "Bird", R.mipmap.whitethroat);
        Animal animal3 = new Animal("animal3", "scientificAnimal3", "Invertebrate", R.mipmap.great_spotted_woodpecker);
        Animal animal4 = new Animal("animal4", "scientificAnimal4", "Bird", 16, 21, "identification", "blue", "habitat1", "anytime", "a food source", R.mipmap.great_spotted_woodpecker);

        mAllAnimals.add(animal1);
        mAllAnimals.add(animal2);
        mAllAnimals.add(animal3);
        mAllAnimals.add(animal4);

        animalsWithSelectedType.add(animal1);
        animalsWithSelectedType.add(animal2);
        animalsWithSelectedType.add(animal3);

        animalsWithSelectedMinLength.add(animal3);
        animalsWithSelectedMinLength.add(animal2);


    }

    @Test
    public void returnsAnimal2() {
        List<Animal> result =  animalSearchFragment.determineListOfAnimalsToDisplay(mAllAnimals, animalsWithSelectedType, animalsWithSelectedMinLength);
        assertEquals(2, result.size());
    }
}
