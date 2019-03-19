package com.example.wildlifeapplication.Search;

import com.example.wildlifeapplication.Search.Animal;

import java.util.List;

public interface ISearchForAnimalService {

    List<Animal> getAll();

    List<Animal> filterByName(String name, List<Animal> listOfAnimalsToFilter);
}
