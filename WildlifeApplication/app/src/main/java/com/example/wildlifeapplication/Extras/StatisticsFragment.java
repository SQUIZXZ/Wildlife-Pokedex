package com.example.wildlifeapplication.Extras;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wildlifeapplication.R;
import com.example.wildlifeapplication.Search.AnimalInformation.Animal;
import com.example.wildlifeapplication.Search.AnimalInformation.AnimalDatabase;
import com.example.wildlifeapplication.Search.SearchForAnimalService;

import java.util.List;


public class StatisticsFragment extends Fragment {

    private List<Animal> mAllAnimals;

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
        View view = inflater.inflate(R.layout.fragment_statistics,container,false);
        TextView animal_count_view = view.findViewById(R.id.number1);

        final SearchForAnimalService animalSearchService = new SearchForAnimalService();
        final AnimalDatabase db = Room.databaseBuilder(getContext(), AnimalDatabase.class, "animal database").build();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                setmAllAnimals(db.animalDao().getAllAnimals());
                db.close();
            }
        });

        int count = 0;
        for(int i=0; i<getmAllAnimals().size();i++)
            count += 1;

        animal_count_view.setText(count);
        return view;
    }

    public List<Animal> getmAllAnimals() {
        return mAllAnimals;
    }

    public void setmAllAnimals(List<Animal> mAllAnimals) {
        this.mAllAnimals = mAllAnimals;
    }
}
