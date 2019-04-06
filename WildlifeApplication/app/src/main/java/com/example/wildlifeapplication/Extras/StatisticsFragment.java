package com.example.wildlifeapplication.Extras;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wildlifeapplication.Map.Spotting;
import com.example.wildlifeapplication.Map.SpottingOfAnimalsDatabase;
import com.example.wildlifeapplication.R;
import com.example.wildlifeapplication.Search.AnimalInformation.Animal;
import com.example.wildlifeapplication.Search.AnimalInformation.AnimalDatabase;
import com.example.wildlifeapplication.Search.SearchForAnimalService;

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class StatisticsFragment extends Fragment {

    private List<Animal> mAllAnimals;
    private List<Spotting> mAllSpottings;
    int animalCount = 0;
    int spotCount = 0 ;

    public static StatisticsFragment newInstance() {
        StatisticsFragment fragment = new StatisticsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        final TextView total_Sightings_Stored = view.findViewById(R.id.number1);
        final TextView types_of_animal_count = view.findViewById(R.id.number2);

        //        final SpottingOfAnimalsDatabase spottingOfAnimalsDB = Room.databaseBuilder(getContext(),SpottingOfAnimalsDatabase.class,"animal sighting database").build();


        final SearchForAnimalService animalSearchService = new SearchForAnimalService();
        final AnimalDatabase db = Room.databaseBuilder(getContext(), AnimalDatabase.class, "animal database").build();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if (db.animalDao().getAllAnimals() != null) {
                    db.clearAllTables();
                }
                db.animalDao().insertAllAnimals(animalSearchService.getAllAnimals());
                mAllAnimals = db.animalDao().getAllAnimals();

                for (int i = 0; i < mAllAnimals.size(); i++) {
                    animalCount++;
                }
                db.close();
            }
        });

        final SpottingOfAnimalsDatabase spottingOfAnimalsDB = Room.databaseBuilder(getContext(),SpottingOfAnimalsDatabase.class,"spotting database").build();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                mAllSpottings = spottingOfAnimalsDB.spottingAnimalDao().getAllSpottingOfAnimals();

                for(int i = 0; i <mAllSpottings.size();i++){
                    spotCount++;
                }
                spottingOfAnimalsDB.close();
            }
        });

        try {
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (java.lang.InterruptedException e) {
            e.printStackTrace();
        }

        types_of_animal_count.setText(Integer.toString(animalCount));
        total_Sightings_Stored.setText(Integer.toString(spotCount));
        return view;
    }


    public List<Animal> getmAllAnimals() {
        return mAllAnimals;
    }

    public void setmAllAnimals(List<Animal> mAllAnimals) {
        this.mAllAnimals = mAllAnimals;
    }

    public int getAnimalCount() {
        return animalCount;
    }

    public void setAnimalCount(int animalCount) {
        this.animalCount = animalCount;
    }



}
