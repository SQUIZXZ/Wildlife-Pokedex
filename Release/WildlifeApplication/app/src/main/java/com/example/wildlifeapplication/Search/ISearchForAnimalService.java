package com.example.wildlifeapplication.Search;

import com.example.wildlifeapplication.Search.AnimalInformation.Animal;

import java.util.ArrayList;
import java.util.List;

public interface ISearchForAnimalService {

    ArrayList<Animal> getAllAnimals();

    List<Animal> filterByColours(List<Animal> animalsToBeFiltered, String[] coloursSelected);

}
