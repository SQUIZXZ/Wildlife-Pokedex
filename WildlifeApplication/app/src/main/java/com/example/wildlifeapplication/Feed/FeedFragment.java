package com.example.wildlifeapplication.Feed;

import android.arch.persistence.room.Room;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.mbms.FileInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wildlifeapplication.Extras.ExtrasFragment;
import com.example.wildlifeapplication.R;

import java.io.File;
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

                posts = db.postDA0().getAllPosts();

                if(posts.size() == 0) {
                    db.postDA0().insertPosts(
                            new Post("Joey", "I love this app!!!", "mipmap-hdpi/blue_tit.JPG"
                            ));

                    posts = db.postDA0().getAllPosts();
                }
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

        int i = 1;
        if(posts.size()<1) {
            i = 0;
        }
        String username = posts.get(posts.size() - i).getUsername();

        String caption = posts.get(posts.size() - i).getCaption();

        TextView textViewUsername = (TextView) v.findViewById(R.id.username);

        textViewUsername.setText(username);

        TextView textViewCaption = (TextView) v.findViewById(R.id.caption);
        textViewCaption.setText(caption);

        ImageView birdImage = v.findViewById(R.id.imageView2);
        birdImage.setImageBitmap(BitmapFactory.decodeFile(posts.get(posts.size() - i).getImagePath()));

        Button createPostButton = v.findViewById(R.id.create_post_button);
        createPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new PostCreationFragment()).commit();
            }
        });

        return v;
    }
}
