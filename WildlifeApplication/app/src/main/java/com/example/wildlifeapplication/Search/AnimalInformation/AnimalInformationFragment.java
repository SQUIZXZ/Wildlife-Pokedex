package com.example.wildlifeapplication.Search.AnimalInformation;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.wildlifeapplication.R;
import com.example.wildlifeapplication.Search.ISearchForAnimalService;
import com.example.wildlifeapplication.Search.SearchForAnimalService;
import com.example.wildlifeapplication.Store.StoreFragment;

public class AnimalInformationFragment extends Fragment {
    private volatile static Animal animal;
    private StoreFragment storeFragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final String[] sNoun = new String[1];
        sNoun[0] = getArguments().getString("Scientific noun");
        if(sNoun[0] == null){
            final String noun = getArguments().getString("Noun");
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    AnimalDatabase animalDatabase = Room.databaseBuilder(getContext(), AnimalDatabase.class, "animal database").build();
                    animalDatabase.clearAllTables();
                    ISearchForAnimalService searchForAnimalService = new SearchForAnimalService();
                    animalDatabase.animalDao().insertAllAnimals(searchForAnimalService.getAllAnimals());
                    Animal animal = animalDatabase.animalDao().getAnimalWithNoun(noun);
                    sNoun[0] = animal.getScientificNoun();
                    animalDatabase.close();
                }
            });

            synchronized (this) {
                while (sNoun[0] == null) {
                    try {
                        wait(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        final AnimalDatabase animalDB = Room.databaseBuilder(getContext(), AnimalDatabase.class, "animal database").build();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                animal = animalDB.animalDao().getAnimalWithScientificNoun(sNoun[0]);
                animalDB.close();
            }
        });
        View v = inflater.inflate(R.layout.fragment_animal_info, container,false);
        synchronized (this ) {
            while (animal == null || !animal.getScientificNoun().equals(sNoun[0])) {
                try {
                    AnimalInformationFragment.this.wait(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            //Setting text views with animal's information
            ((ImageView) v.findViewById(R.id.animal_image)).setImageResource(animal.getImgId());
            ((TextView) v.findViewById(R.id.animal_identification)).setText(animal.getIdentification());
            ((TextView) v.findViewById(R.id.animal_noun)).setText(animal.getNoun());
            ((TextView) v.findViewById(R.id.animal_scientific_noun)).setText(animal.getScientificNoun());
            ((TextView) v.findViewById(R.id.animal_body_length)).setText(animal.getBodyLengthRange());
            ((TextView) v.findViewById(R.id.animal_habitat)).setText(animal.getHabitat());
            ((TextView) v.findViewById(R.id.animal_time)).setText(animal.getBestTimeToSee());
            ((TextView) v.findViewById(R.id.animal_food_source)).setText(animal.getFoodSource());
        }

        ((Button) v.findViewById(R.id.store_animal_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                storeFragment = new StoreFragment();
                Bundle args = new Bundle();
                String noun = animal.getNoun();
                String scientificNoun = animal.getScientificNoun();
                args.putString("noun",noun);
                args.putString("scientific_noun",scientificNoun);
                storeFragment.setArguments(args);
                fragmentManager.beginTransaction().replace(R.id.fragment_container, storeFragment,"Store").commit();
            }
        });

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
