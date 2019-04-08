package com.example.wildlifeapplication.Extras;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.wildlifeapplication.R;

import java.util.ArrayList;

public class SettingsFragment extends ListFragment {

    private ArrayList<String> Settings_List = new ArrayList<>();
    private ArrayAdapter<String> mAdapter;
    ListView listView;

    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onListItemClick(ListView listView, View v, int position, long id) {
        SharedPreferences sp = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String item = listView.getAdapter().getItem(position).toString();
        switch (item) {
            case "Change Launch Area":
                createPopUpMenu(listView.getChildAt(0));
                break;
            case "Online/Offline Mode":
                System.out.println(sp.getAll());
                if (sp.getString("OnlineStatus", "Online").equals("Online")) {
                    Toast.makeText(getContext(), "You've gone offline", Toast.LENGTH_SHORT).show();
                    editor.putString("OnlineStatus", "Offline");
                } else {
                    Toast.makeText(getContext(), "You've gone online", Toast.LENGTH_SHORT).show();
                    editor.putString("OnlineStatus", "Online");
                }
                editor.apply();
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
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        listView = v.findViewById(android.R.id.list);
        getOptions();
        mAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, Settings_List);
        listView.setAdapter(mAdapter);
        return v;
    }


    public void getOptions() {
        Settings_List.add("Change Launch Area");
        Settings_List.add("Online/Offline Mode");
    }

    public void createPopUpMenu(View View) {
        PopupMenu popup = new PopupMenu(getContext(), View);
        popup.getMenuInflater().inflate(R.menu.launch_setting_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                changeLaunchSettings(item.getTitle().toString());
                return true;
            }
        });
        popup.show();


    }

    public void changeLaunchSettings(String selected) {
        SharedPreferences sp = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        switch (selected) {
            case "Feed":
                editor.putString("LaunchChoice", "Feed");
                break;
            case "Search":
                editor.putString("LaunchChoice", "Search");
                break;
            case "Map":
                editor.putString("LaunchChoice", "Map");
        }
        editor.apply();
    }
}