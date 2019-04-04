package com.example.wildlifeapplication.Search;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.wildlifeapplication.R;
import com.example.wildlifeapplication.Search.AnimalInformation.Animal;
import com.example.wildlifeapplication.Search.AnimalInformation.AnimalDatabase;
import com.example.wildlifeapplication.Search.AnimalInformation.AnimalInformationFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimalSearchFragment extends ListFragment {

    private List<Animal> mAllAnimals;
    ArrayList<Map<String, String>> data = new ArrayList<>();
    private SimpleAdapter mAdapter;

    AppCompatButton mButton;
    String[] colourList;
    String[] selectedColoursAsStringArray;
    boolean[] checkedItems;
    ArrayList<Integer> mSelectedColoursPositions = new ArrayList<>();
    volatile static List<Animal> animalsWithSelectedType;
    volatile static List<Animal> animalsWithSelectedMinLength;
    volatile static List<Animal> animalsWithSelectedMaxLength;
    volatile static List<Animal> animalsWithSelectedColours;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Override
    public void onListItemClick(ListView listView, View v, int position, long id) {
        String item = listView.getAdapter().getItem(position).toString();
        if (getActivity() instanceof OnItem1SelectedListener) {
            ((OnItem1SelectedListener) getActivity()).OnItem1SelectedListener(item);
        }
        getFragmentManager().popBackStack();

        //Sending the scientific noun of the animal selected from the list to the
        //AnimalInformationFragment as an argument
        AnimalInformationFragment animalInformationFragment = new AnimalInformationFragment();
        Bundle args = new Bundle();
        HashMap hashMap = (HashMap) listView.getItemAtPosition(position);
        String[] scientificNounValueAsStringArray = hashMap.entrySet().toArray()[0].toString().split("=");
        args.putString(scientificNounValueAsStringArray[0], scientificNounValueAsStringArray[1]);
        animalInformationFragment.setArguments(args);
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, animalInformationFragment).commit();

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_animal_search, container, false);
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

            //Adding all the animals from the database to the list view
            HashMap<String, String> hashMap;
            for (Animal animal : mAllAnimals) {
                hashMap = new HashMap<>();
                hashMap.put("Noun", animal.getNoun());
                hashMap.put("Scientific noun", animal.getScientificNoun());

                if (animal.getMinBodyLength() > 0) {
                    hashMap.put("Body length", "Body length: " + animal.getMinBodyLength() + "-" + animal.getMaxBodyLength() + " cm");
                } else {
                    hashMap.put("Body length", "");
                }
                hashMap.put("Image", Integer.toString(animal.getImgId()));
                data.add(hashMap);
            }

            String[] from = {"Noun", "Scientific noun", "Body length", "Image"};
            int[] to = {R.id.listview_heading, R.id.listview_subheading, R.id.listview_description, R.id.listview_image};
            mAdapter = new SimpleAdapter(getActivity(), data, R.layout.custom_list_view_image_and_text, from, to);
            setListAdapter(mAdapter);
        }

        //Button in order to be able to select multiple colours in a dialog box

        mButton = v.findViewById(R.id.colour_button);
        colourList = getResources().getStringArray(R.array.colours);
        checkedItems = new boolean[colourList.length];

        //Adapted code from https://github.com/codingdemos/MultichoiceTutorial
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                mBuilder.setTitle("Colours of animal");
                mBuilder.setMultiChoiceItems(colourList, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position, boolean isChecked) {
                        if (isChecked) {
                            if (!mSelectedColoursPositions.contains(position)) {
                                mSelectedColoursPositions.add(position);
                            }
                        }else if(mSelectedColoursPositions.contains(position)) {
                            mSelectedColoursPositions.remove(mSelectedColoursPositions.indexOf(position));
                        }


                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String item = "";
                        for (int i = 0; i < mSelectedColoursPositions.size(); i++) {
                            item = item + colourList[mSelectedColoursPositions.get(i)];
                            if(i != mSelectedColoursPositions.size() - 1){
                                item = item +", ";
                            }
                        }

                        selectedColoursAsStringArray = item.split(", ");

                        ///getting animals with specified colours
                        SearchForAnimalService animalSearchService = new SearchForAnimalService();
                        animalsWithSelectedColours = animalSearchService.filterByColours(mAllAnimals, selectedColoursAsStringArray);
                        updateListView();

                        //removes all tags currently displaying
                        if(((ViewGroup) getActivity().findViewById(R.id.colour_filter)).getChildCount() > 1){
                            ((ViewGroup) getActivity().findViewById(R.id.colour_filter)).removeViews(1, ((ViewGroup) getActivity().findViewById(R.id.colour_filter)).getChildCount()-1);
                        }

                        //adds tags for each colour
                        for(String colour: selectedColoursAsStringArray) {
                            if(!colour.equals("")) {
                                addFilterTag(inflater, container, R.id.colour_filter, "Colour", colour.trim());
                            }
                        }


                    }
                });
                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                mBuilder.setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mSelectedColoursPositions.clear();
                        }
                        animalsWithSelectedColours = null;
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
        //End of adaptation

        //Adapted code from: https://android--code.blogspot.com/2015/08/android-spinner-hint.html
        //setting type filter spinner options
        ArrayAdapter<String> typeSpinnerAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.types));
        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) v.findViewById(R.id.type_spinner)).setAdapter(typeSpinnerAdapter);

        //setting a listener for the type spinner
        ((Spinner) v.findViewById(R.id.type_spinner)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String selectedItemText = (String) parent.getItemAtPosition(position);
                animalsWithSelectedType = null;
                if (position > 0) {
                    // Notify the selected item text
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            RoomDatabase animalDB = Room.databaseBuilder(getContext(), AnimalDatabase.class, "animal database").build();
                            animalsWithSelectedType = ((AnimalDatabase) animalDB).animalDao().getAnimalOfType(selectedItemText);
                            animalDB.close();
                        }
                    });


                    synchronized (this) {
                        while (animalsWithSelectedType == null) {
                            try {
                                wait(5);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        if (((ViewGroup) v.findViewById(R.id.type_filter)).getChildCount() == 1) {
                            //adding filter tag
                            addFilterTag(inflater, container, R.id.type_filter, "Type", selectedItemText);
                            updateListView();
                        }else{
                            updateFilterTag(R.id.type_filter, selectedItemText);
                            updateListView();
                        }

                    }
                } else {
                    if(selectedItemText.equals("Select a type") && ((ViewGroup) v.findViewById(R.id.type_filter)).getChildCount() == 2 ) {
                        View filterTagView = ((ViewGroup) v.findViewById(R.id.type_filter)).getChildAt(1);
                        ((ViewGroup) v.findViewById(R.id.type_filter)).removeView(filterTagView);
                    }
                    updateListView();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });


        //setting minimum size filter spinner options
        ArrayAdapter<String> minSizeSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.min_length));
        minSizeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) v.findViewById(R.id.min_size_spinner)).setAdapter(minSizeSpinnerAdapter);

        //setting a listener for the minimum size spinner
        ((Spinner) v.findViewById(R.id.min_size_spinner)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String selectedItemText = (String) parent.getItemAtPosition(position);
                animalsWithSelectedMinLength = null;
                if (position > 0) {
                    // Notify the selected item text
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            RoomDatabase animalDB = Room.databaseBuilder(getContext(), AnimalDatabase.class, "animal database").build();
                            animalsWithSelectedMinLength = ((AnimalDatabase) animalDB).animalDao().getAnimalWithMinLength(Integer.parseInt(selectedItemText));
                            animalDB.close();
                        }
                    });


                    synchronized (this) {
                        while (animalsWithSelectedMinLength == null) {
                            try {
                                wait(5);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                        updateListView();
                    if (((ViewGroup) v.findViewById(R.id.min_length_filter)).getChildCount() != 2) {
                        //adding filter tag
                        addFilterTag(inflater, container, R.id.min_length_filter, "Min Length", selectedItemText);
                    }else {
                        updateFilterTag( R.id.min_length_filter, selectedItemText);

                    }
                } else {
                    if(selectedItemText.equals("Select a minimum size(cm)") && ((ViewGroup) v.findViewById(R.id.min_length_filter)).getChildCount() == 2 ) {
                        View filterTagView = ((ViewGroup) v.findViewById(R.id.min_length_filter)).getChildAt(1);
                        ((ViewGroup) v.findViewById(R.id.min_length_filter)).removeView(filterTagView);
                    }
                    updateListView();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //setting maximum size filter spinner options
        ArrayAdapter<String> maxSizeSpinnerAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.max_length));
        maxSizeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ((Spinner) v.findViewById(R.id.max_size_spinner)).setAdapter(maxSizeSpinnerAdapter);

        //setting a listener for the maximum size spinner
        ((Spinner) v.findViewById(R.id.max_size_spinner)).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                final String selectedItemText = (String) parent.getItemAtPosition(position);
                animalsWithSelectedMaxLength = null;
                if (position > 0) {
                    // Notify the selected item text
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            RoomDatabase animalDB = Room.databaseBuilder(getContext(), AnimalDatabase.class, "animal database").build();
                            animalsWithSelectedMaxLength = ((AnimalDatabase) animalDB).animalDao().getAnimalWithMaxLength(Integer.parseInt(selectedItemText));
                            animalDB.close();
                        }
                    });


                    synchronized (this) {
                        while (animalsWithSelectedMaxLength == null) {
                            try {
                                wait(5);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    if (((ViewGroup) v.findViewById(R.id.max_length_filter)).getChildCount() != 2) {
                        //adding filter tag
                        addFilterTag(inflater, container, R.id.max_length_filter,"Max Length", selectedItemText);
                    }else {
                        updateFilterTag( R.id.max_length_filter, selectedItemText);
                    }
                    updateListView();
                } else {
                    if(selectedItemText.equals("Select a maximum size(cm)") && ((ViewGroup) v.findViewById(R.id.max_length_filter)).getChildCount() == 2 ) {
                        View filterTagView = ((ViewGroup) v.findViewById(R.id.max_length_filter)).getChildAt(1);
                        ((ViewGroup) v.findViewById(R.id.max_length_filter)).removeView(filterTagView);
                    }
                    updateListView();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //End of adaptation


        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        //Expanding the search view
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

    public void initialiseDatabase() {
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
                db.close();
            }
        });


    }

    public List<Animal> determineListOfAnimalsToDisplay(List<Animal> allAnimals, List<Animal> ... args){
        List<List<Animal>> listsOfAnimals = new ArrayList<>();
        for(List<Animal> list: args) {
            if(list != null) {
                listsOfAnimals.add(list);
            }
        }

        List<Animal> copyMAllAnimals = new ArrayList<>(allAnimals);


        //Checks which animals all the lists have in common and removes the ones that are not
        //present in all of the lists
        for(List<Animal> listOfAnimals: listsOfAnimals) {
                for (Animal animalInAllAnimals : allAnimals) {
                    boolean animalInList = false;
                    for(Animal animal: listOfAnimals){
                        if(animal.getScientificNoun().equalsIgnoreCase(animalInAllAnimals.getScientificNoun())){
                            animalInList = true;
                        }
                    }

                    if (!animalInList && copyMAllAnimals.contains(animalInAllAnimals)) {
                        copyMAllAnimals.remove(animalInAllAnimals);
                    }
                }
        }
        return copyMAllAnimals;
    }

    public void updateListView() {
        List<Animal> listOfAnimalsToDisplay = determineListOfAnimalsToDisplay(mAllAnimals, animalsWithSelectedMinLength, animalsWithSelectedType, animalsWithSelectedMaxLength, animalsWithSelectedColours);

        if (listOfAnimalsToDisplay.size() == 0) {
            //Display no results text and hide list view
            getActivity().findViewById(R.id.empty).setVisibility(View.VISIBLE);
            getActivity().findViewById(android.R.id.list).setVisibility(View.INVISIBLE);
        } else {
            //Display the list view with animals and hide the no results text
            getActivity().findViewById(android.R.id.list).setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.empty).setVisibility(View.INVISIBLE);
            HashMap<String, String> hashMap;
            data = new ArrayList<>();
            for (Animal animal : listOfAnimalsToDisplay) {
                hashMap = new HashMap<>();
                hashMap.put("Noun", animal.getNoun());
                hashMap.put("Scientific noun", animal.getScientificNoun());

                if (animal.getMinBodyLength() > 0) {
                    hashMap.put("Body length", "Body length: " + animal.getMinBodyLength() + "-" + animal.getMaxBodyLength() + " cm");
                } else {
                    hashMap.put("Body length", "");
                }
                hashMap.put("Image", Integer.toString(animal.getImgId()));
                data.add(hashMap);
            }

            String[] from = {"Noun", "Scientific noun", "Body length", "Image"};
            int[] to = {R.id.listview_heading, R.id.listview_subheading, R.id.listview_description, R.id.listview_image};
            mAdapter = new SimpleAdapter(getActivity(), data, R.layout.custom_list_view_image_and_text, from, to);
            setListAdapter(mAdapter);

        }
    }

    public void addFilterTag(LayoutInflater inflater, ViewGroup container, final int idOfViewWhichToInsertInto, final String typeOfFilter, final String filterSelected) {
        final View filterTagView = inflater.inflate(R.layout.filter_tag, container, false);
        ((LinearLayout) getActivity().findViewById(idOfViewWhichToInsertInto)).addView(filterTagView);
        //Setting text of filter tag
        ((TextView) filterTagView.findViewById(R.id.filter_tag_title)).setText(filterSelected);

        if(typeOfFilter == "Colour") {
            ((ViewGroup) filterTagView).removeView(filterTagView.findViewById(R.id.x_button));
        } else {

            //Setting listener for filter tag
            ((ViewGroup) filterTagView).getChildAt(1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((LinearLayout) getActivity().findViewById(idOfViewWhichToInsertInto)).removeView(filterTagView);
                    switch (typeOfFilter) {
                        case "Type":
                            animalsWithSelectedType = null;
                        case "Min Length":
                            animalsWithSelectedMinLength = null;
                        case "Max Length":
                            animalsWithSelectedMaxLength = null;
                    }
                    updateListView();
                }
            });
        }
    }

    public void updateFilterTag(final int idOfViewFilterTagIsIn, String filter) {
        ((TextView) getActivity().findViewById(idOfViewFilterTagIsIn).findViewById(R.id.filter_tag_title)).setText(filter);
    }
}
