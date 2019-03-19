package com.example.wildlifeapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private AnimalSearchFragment animalSearchFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_map:
                    mTextMessage.setText(R.string.title_map);
                    switchToMainActivity();
                    return true;
                case R.id.navigation_feed:
                    mTextMessage.setText(R.string.title_feed);
                    switchToMainActivity();
                    return true;
                case R.id.navigation_search:
                    mTextMessage.setText(R.string.title_search);
                    switchToBirdSearchFragment();
                    return true;
                case R.id.navigation_extras:
                    mTextMessage.setText(R.string.extras);
                    switchToMainActivity();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_map);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void switchToBirdSearchFragment() {
        mTextMessage.setText("");
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container, animalSearchFragment = new AnimalSearchFragment()).commit();
    }

    public void  switchToMainActivity() {
        getSupportFragmentManager().beginTransaction().remove(animalSearchFragment).commit();
    }
}
