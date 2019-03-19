package com.example.wildlifeapplication;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SearchForBirdServiceTest {

    @Test
    public void namesAreFilteredCorrectly() {
        List<Animal> list = new ArrayList<>();
        list.add(new Animal("test", "testScientific"));
        list.add(new Animal("default", "testScientific2"));
        SearchForBirdService searchForBirdService = new SearchForBirdService();
        List<Animal> result = searchForBirdService.filterByName("test", list);

        Assert.assertEquals(1, result.size());
    }
}
