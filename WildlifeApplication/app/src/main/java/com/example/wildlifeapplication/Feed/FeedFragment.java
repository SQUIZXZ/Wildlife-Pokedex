package com.example.wildlifeapplication.Feed;

import android.arch.persistence.room.Room;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

                posts = db.postDA0().getAllPosts();

                db.close();


            }
        });

        synchronized (this) {
            while (posts == null) {
                try {
                    wait(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }


        Log.d("BOO_TEST", String.format("Number of Posts: %d", posts.size()));

        ListView list = (ListView) v.findViewById(R.id.listview);

        CustomAdapter customAdapter = new CustomAdapter();

        list.setAdapter(customAdapter);

        Button createPostButton = v.findViewById(R.id.create_post_button);
        createPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, new PostCreationFragment()).commit();
            }
        });
        createPostButton.bringToFront();

        return v;
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return posts.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.postlayout, null);

            ImageView pp = (ImageView) view.findViewById(R.id.pp);
            TextView textView_name = (TextView) view.findViewById(R.id.textView_name);
            TextView textView_description = (TextView) view.findViewById(R.id.textView_description);
            ImageView image = (ImageView) view.findViewById(R.id.image);

            pp.setImageResource(R.drawable.fox_avatar);
            textView_name.setText(posts.get(posts.size() - i - 1).getUsername());
            textView_description.setText(posts.get(posts.size() - i - 1).getCaption());
            image.setImageBitmap(BitmapFactory.decodeFile(posts.get(posts.size() - i - 1).getImagePath()));


            return view;
        }
    }
}
