package com.example.wildlifeapplication;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wildlifeapplication.Extras.ExtrasFragment;
import com.example.wildlifeapplication.Feed.FeedFragment;
import com.example.wildlifeapplication.Map.MapFragment;
import com.example.wildlifeapplication.Search.AnimalSearchFragment;
import com.example.wildlifeapplication.Store.StoreFragment;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private AnimalSearchFragment animalSearchFragment;
    private ExtrasFragment extrasFragment;
    private FeedFragment feedFragment;
    private MapFragment mapFragment;
    private StoreFragment storeFragment;
    FragmentManager fragmentManager = getSupportFragmentManager();
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
                    switchToFeedFragment();
                    return true;
                case R.id.navigation_search:
                    switchToBirdSearchFragment();
                    return true;
                case R.id.navigation_extras:
                    switchToExtrasFragment();
                    return true;
                case R.id.navigation_temp:
                    switchToTempFragment();
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
                switchToBirdSearchFragment();
//                navigation.setSelectedItemId(R.id.navigation_search);
                break;
            case "Feed":
                switchToFeedFragment();
//                navigation.setSelectedItemId(R.id.navigation_feed);
                break;
            case "Map":
                switchToMapFragment();
//                navigation.setSelectedItemId(R.id.navigation_map);
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
        fragmentManager.beginTransaction().replace(R.id.fragment_container, mapFragment = new MapFragment(),"Map").commit();
    }

    public void  switchToTempFragment() {
        fragmentManager.beginTransaction().replace(R.id.fragment_container, storeFragment = new StoreFragment(),"Store").commit();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == CAMERA_PIC_REQUEST) {
            File imgFile = new File(storeFragment.getPictureFilePath());
            if (imgFile.exists()) {
                ImageView imageview = (ImageView) findViewById(R.id.imageView);
                imageview.setImageURI(Uri.fromFile(imgFile));
            }
        }
    }
}
