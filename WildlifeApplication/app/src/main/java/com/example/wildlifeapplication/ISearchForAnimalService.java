package com.example.wildlifeapplication;

import java.util.List;

public interface ISearchForAnimalService {

    List<Animal> getAll();

    List<Animal> filterByName(String name, List<Animal> listOfAnimalsToFilter);
}
