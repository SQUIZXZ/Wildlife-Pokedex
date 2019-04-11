package com.example.wildlifeapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.wildlifeapplication.Extras.ExtrasFragment;
import com.example.wildlifeapplication.Feed.FeedFragment;
import com.example.wildlifeapplication.Map.MapFragment;
import com.example.wildlifeapplication.Search.AnimalSearchFragment;
import com.example.wildlifeapplication.Store.StoreFragment;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private StoreFragment storeFragment;
    FragmentManager fragmentManager = getSupportFragmentManager();
    View view;
    private static final int CAMERA_PIC_REQUEST = 1337;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    switchToMapFragment();
                    return true;
                case R.id.navigation_feed:
                    SharedPreferences sp = getSharedPreferences("pref", Context.MODE_PRIVATE);
                    if (sp.getString("OnlineStatus", "Online").equals("Online")) {
                        switchToFeedFragment();
                        return true;
                    } else {
                        Toast.makeText(getApplicationContext(), "Sorry, you need to be on online mode to access the newsfeed", Toast.LENGTH_LONG).show();
                        return false;
                    }
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

        SharedPreferences sp = getSharedPreferences("pref", Context.MODE_PRIVATE);
        String launchChoice = sp.getString("LaunchChoice", "Map");

        switch (launchChoice) {
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
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new AnimalSearchFragment()).addToBackStack(null).commit();
        fragmentManager.executePendingTransactions();
    }

    public void switchToExtrasFragment() {
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new ExtrasFragment()).addToBackStack(null).commit();
        fragmentManager.executePendingTransactions();
    }

    public void switchToFeedFragment() {
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new FeedFragment()).addToBackStack(null).commit();
        fragmentManager.executePendingTransactions();
    }

    public void switchToMapFragment() {
        fragmentManager.beginTransaction().replace(R.id.fragment_container, new MapFragment(), "Map").addToBackStack(null).commit();
        fragmentManager.executePendingTransactions();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST) {
            storeFragment = (StoreFragment) fragmentManager.findFragmentByTag("Store");
            File imgFile = new File(storeFragment.getPictureFilePath());
            if (imgFile.exists()) {
                ImageView imageview = (ImageView) findViewById(R.id.imageView);
                imageview.setImageURI(Uri.fromFile(imgFile));
            }
        }
    }

}
