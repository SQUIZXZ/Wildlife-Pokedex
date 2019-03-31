package com.example.wildlifeapplication;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.wildlifeapplication.Extras.ExtrasFragment;
import com.example.wildlifeapplication.Feed.FeedFragment;
import com.example.wildlifeapplication.Map.MapFragment;
import com.example.wildlifeapplication.Search.AnimalSearchFragment;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private AnimalSearchFragment animalSearchFragment;
    private ExtrasFragment extrasFragment;
    private FeedFragment feedFragment;
    private MapFragment mapFragment;
    FragmentManager fragmentManager = getSupportFragmentManager();
    View view;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    switchToMapFragment();
                    return true;
                case R.id.navigation_feed:
                    switchToFeedFragment();
                    return true;
                case R.id.navigation_search:
                    switchToBirdSearchFragment();
                    return true;
                case R.id.navigation_extras:
                    switchToExtrasFragment();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        SharedPreferences sp = getSharedPreferences("pref",Context.MODE_PRIVATE);
        String launchChoice = sp.getString("LaunchChoice","Map");

        switch (launchChoice){
            case "Search":
                view = navigation.findViewById(R.id.navigation_search);
                view.performClick();
                break;
            case "Feed":
                view = navigation.findViewById(R.id.navigation_feed);
                view.performClick();
                break;
            case "Map":
                view = navigation.findViewById(R.id.navigation_map);
                view.performClick();
                break;
        }
    }

    public void switchToBirdSearchFragment() {
        fragmentManager.beginTransaction().replace(R.id.fragment_container, animalSearchFragment = new AnimalSearchFragment()).commit();
    }

    public void switchToExtrasFragment() {
        fragmentManager.beginTransaction().replace(R.id.fragment_container, extrasFragment = new ExtrasFragment()).commit();
    }

    public void switchToFeedFragment() {
        fragmentManager.beginTransaction().replace(R.id.fragment_container, feedFragment = new FeedFragment()).commit();
    }

    public void switchToMapFragment() {
        fragmentManager.beginTransaction().replace(R.id.fragment_container, mapFragment = new MapFragment()).commit();
    }

    public void  switchToMainActivity() {
        getSupportFragmentManager().beginTransaction().remove(animalSearchFragment).commit();
    }
}
