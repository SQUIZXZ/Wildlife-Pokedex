package com.example.wildlifeapplication;

import java.util.ArrayList;
import java.util.List;

public class SearchForBirdService implements ISearchForAnimalService {
    @Override
    public List<Animal> getAll() {
        List<Animal> listOfAllBirds = new ArrayList<Animal>();
        listOfAllBirds.add(new Animal("Bird1", "Ave1"));
        listOfAllBirds.add(new Animal("Bird2", "Ave2"));
        listOfAllBirds.add(new Animal("Bird3", "Ave3"));
        listOfAllBirds.add(new Animal("Bird4", "Ave4"));
        return listOfAllBirds;
    }
}
