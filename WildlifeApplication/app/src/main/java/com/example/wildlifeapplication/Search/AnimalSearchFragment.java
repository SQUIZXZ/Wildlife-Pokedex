package com.example.wildlifeapplication.Search;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.wildlifeapplication.R;

import java.util.ArrayList;

public class AnimalSearchFragment extends ListFragment {

    private ArrayList<Animal> mAllValues;
    private ArrayAdapter<Animal> mAdapter;
    ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setHasOptionsMenu(true);
         setAllValues();
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
        listView = v.findViewById(android.R.id.list);
        v.findViewById(R.id.empty).setVisibility(View.INVISIBLE);
        mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mAllValues);
        listView.setAdapter(mAdapter);
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search by name or scientific name");

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
        mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, mAllValues);
        setListAdapter(mAdapter);
    }

    private void setAllValues() {
        mAllValues = new ArrayList<>();

        SearchForBirdService birdSearchService = new SearchForBirdService();
        mAllValues.addAll(birdSearchService.getAll());
    }

    public interface OnItem1SelectedListener {
        void OnItem1SelectedListener(String item);
    }
}
