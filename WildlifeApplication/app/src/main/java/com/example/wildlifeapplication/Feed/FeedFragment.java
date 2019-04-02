package com.example.wildlifeapplication.Feed;

import android.arch.persistence.room.Room;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wildlifeapplication.Extras.ExtrasFragment;
import com.example.wildlifeapplication.R;

import java.util.List;

public class FeedFragment extends Fragment {

    PostDatabase db;
    List<Post> posts;

        public static FeedFragment newInstance() {
            FeedFragment fragment = new FeedFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_feed, container, false);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                db = Room.databaseBuilder(getContext(),
                        PostDatabase.class, "Newsfeed_Database").build();

                db.postDA0().insertPosts(
                        new Post("Joey", "I love this app!!!")
                );

                posts = db.postDA0().getAllPosts();
                db.close();

//                Toast.makeText(getApplicationContext(),username,Toast.LENGTH_SHORT).show();

            }
        });

        synchronized (this) {
            while(posts == null) {
                try {
                    wait(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        Log.d("BOO_TEST", String.format("Number of Posts: %d", posts.size()));

        String username = posts.get(posts.size()-1).getUsername();

        String caption = posts.get(posts.size()-1).getCaption();

        TextView textViewUsername = (TextView) v.findViewById(R.id.username);

        textViewUsername.setText(username);

        TextView textViewCaption = (TextView) v.findViewById(R.id.caption);
        textViewCaption.setText(caption);

        return v;
    }
}
