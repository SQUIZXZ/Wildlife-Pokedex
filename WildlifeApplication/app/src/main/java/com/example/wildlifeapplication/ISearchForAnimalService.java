package com.example.wildlifeapplication;

import java.util.List;

public interface ISearchForAnimalService {

    Animal filterByName(List<Animal> listOfAnimalsToFilter);
}
