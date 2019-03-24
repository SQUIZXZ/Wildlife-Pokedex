package com.example.wildlifeapplication.Extras;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.wildlifeapplication.Map.MapFragment;
import com.example.wildlifeapplication.R;

import java.util.ArrayList;


public class ExtrasFragment extends ListFragment {


    private SettingsFragment settingsFragment;
    private StatisticsFragment statisticsFragment;

    private ArrayList<String> Extras_List = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    ListView listView;


    public static ExtrasFragment newInstance(String param1, String param2) {
        ExtrasFragment fragment = new ExtrasFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onListItemClick(ListView listView, View v, int position, long id) {
        FragmentManager fragmentManager = getFragmentManager();
        String item = listView.getAdapter().getItem(position).toString();

        switch (item){
            case "Settings":
                fragmentManager.beginTransaction().replace(R.id.fragment_container, settingsFragment = new SettingsFragment()).commit();
                break;
            case "Statistics":
                fragmentManager.beginTransaction().replace(R.id.fragment_container, statisticsFragment = new StatisticsFragment()).commit();
                break;
            case "Log Out":
                Toast.makeText(getContext(), item, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_extras, container,false);
        listView = v.findViewById(android.R.id.list);
        getOptions();
        mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,Extras_List );
        listView.setAdapter(mAdapter);
        return v;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void getOptions(){
        Extras_List.add("Settings");
        Extras_List.add("Statistics");
        Extras_List.add("Log Out");
    }


}
