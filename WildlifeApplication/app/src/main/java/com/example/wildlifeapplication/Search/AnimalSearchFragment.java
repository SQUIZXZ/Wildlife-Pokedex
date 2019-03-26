package com.example.wildlifeapplication.Search;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import com.example.wildlifeapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnimalSearchFragment extends ListFragment {

    private List<Animal> mAllAnimals;
    ArrayList<HashMap<String, String>> data = new ArrayList<>();
    private SimpleAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setHasOptionsMenu(true);
    }

    @Override
    public void onListItemClick(ListView listView, View v, int position, long id) {
        String item = listView.getAdapter().getItem(position).toString();
        if(getActivity() instanceof  OnItem1SelectedListener) {
            ((OnItem1SelectedListener) getActivity()).OnItem1SelectedListener(item);
        }
        getFragmentManager().popBackStack();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bird_search, container,false);
        v.findViewById(R.id.empty).setVisibility(View.INVISIBLE);

        synchronized (this) {
            initialiseDatabase();
            try {
                while (mAllAnimals == null) {
                    wait(5);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            HashMap<String, String> hashMap;
            for(Animal animal: mAllAnimals) {
                hashMap = new HashMap<>();
                hashMap.put("Noun", animal.getNoun());
                hashMap.put("Scientific noun", animal.getScientificNoun());

                if (animal.getMinBodyLength() > 0) {
                    hashMap.put("Body length", "Body length: "+animal.getMinBodyLength()+"-"+animal.getMaxBodyLength()+" cm");
                } else {
                    hashMap.put("Body length", "");
                }
                hashMap.put("Image", Integer.toString(animal.getImgURL()));
                data.add(hashMap);
            }

            String[] from = {"Noun","Scientific noun", "Body length", "Image"};
            int[] to = {R.id.listview_heading, R.id.listview_subheading, R.id.listview_description, R.id.listview_image};
            mAdapter = new SimpleAdapter(getActivity(), data, R.layout.custom_list_view_image_and_text, from, to);
            setListAdapter(mAdapter);
            return v;
        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search by name");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText == null || newText.trim().isEmpty()) {
                    resetSearch();
                } else {
                    mAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });
    }

    public void resetSearch() {
        setListAdapter(mAdapter);
    }

    public interface OnItem1SelectedListener {
        void OnItem1SelectedListener(String item);
    }

    private void initialiseDatabase(){
        final SearchForAnimalService animalSearchService = new SearchForAnimalService();
        final AnimalDatabase db = Room.databaseBuilder(getContext(), AnimalDatabase.class, "animal database").build();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                if(db.animalDao().getAllAnimals() != null) {
                    db.clearAllTables();
                }
                db.animalDao().insertAllAnimals(animalSearchService.getAllAnimals());
                mAllAnimals = db.animalDao().getAllAnimals();
            }
        });


    }
}
