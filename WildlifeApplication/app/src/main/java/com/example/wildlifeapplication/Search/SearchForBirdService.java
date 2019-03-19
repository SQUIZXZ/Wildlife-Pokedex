package com.example.wildlifeapplication.Search;

import com.example.wildlifeapplication.Search.Animal;
import com.example.wildlifeapplication.Search.ISearchForAnimalService;

import java.util.ArrayList;
import java.util.List;

public class SearchForBirdService implements ISearchForAnimalService {
    @Override
    public List<Animal> getAll() {
        List<Animal> listOfAllBirds = new ArrayList<>();
        listOfAllBirds.add(new Animal("Bird1", "Ave1"));
        listOfAllBirds.add(new Animal("Bird2", "Ave2"));
        listOfAllBirds.add(new Animal("Bird3", "Ave3"));
        listOfAllBirds.add(new Animal("Bird4", "Ave4"));
        return listOfAllBirds;
    }
}
