package com.example.wildlifeapplication.Store;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wildlifeapplication.R;
import com.google.android.gms.maps.model.LatLng;

public class StoreAnimalSighting extends Fragment {

    private volatile static AnimalSighting animalSighting;
    private View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_map, container, false);
        return v;
    }

    public void storeAnimal(
            String noun,
            String scientificNoun,
            int bodyLength,
            int wingspan,
            String colour,
            String habitat,
            String timeOfYear,
            double latitude,
            double longitude){

        final AnimalSighting animalSighting = new AnimalSighting(noun,scientificNoun,bodyLength,
                wingspan,colour,habitat,timeOfYear,latitude,longitude);
        final AnimalSightingDatabase animalSightingDB = Room.databaseBuilder(getContext(),AnimalSightingDatabase.class,"animal sighting database").build();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                animalSightingDB.animalSightingDao().insertSighting(animalSighting);
            }
        });
    }

}
