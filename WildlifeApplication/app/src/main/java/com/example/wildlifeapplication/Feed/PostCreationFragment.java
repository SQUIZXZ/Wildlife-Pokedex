package com.example.wildlifeapplication.Feed;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.wildlifeapplication.R;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

public class PostCreationFragment extends Fragment {
    private static final int CAMERA_PIC_REQUEST = 104;
    private static final int REQUEST_PERM_WRITE_STORAGE = 102;
    String imagePath;

    public PostCreationFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_post, container, false);
        Button cameraButton = v.findViewById(R.id.camera_button);
        Button submitButton = v.findViewById(R.id.submit_button);

        cameraButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //Set permission to access gallery and camera
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(getActivity().checkSelfPermission(Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, CAMERA_PIC_REQUEST);
                    }
                }
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERM_WRITE_STORAGE);
                }else{
                    takePhoto();
                }

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = ((EditText) getActivity().findViewById(R.id.username_edit_text)).getText().toString();
                String caption = ((EditText) getActivity().findViewById(R.id.caption_edit_text)).getText().toString();
                final boolean[] postInserted = {false};

                if (username.trim().equals("") | caption.trim().equals("")) {
                    Toast.makeText(getActivity(), "Username and caption cannot be empty", Toast.LENGTH_SHORT).show();
                }else{
                    final Post post = new Post(username, caption, imagePath);
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            PostDatabase postDatabase = Room.databaseBuilder(getActivity(), PostDatabase.class, "Newsfeed_Database").build();
                            postDatabase.postDA0().insertPosts(post);
                            postInserted[0] = true;
                            postDatabase.close();

                            FeedFragment feedFragment = new FeedFragment();

                            getFragmentManager().beginTransaction().replace(R.id.fragment_container, feedFragment).commit();
                        }
                    });

//                    synchronized (this) {
//                        try {
//                            while(!postInserted[0])
//                            wait(5);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                    FeedFragment feedFragment = new FeedFragment();
//
//                    getFragmentManager().beginTransaction().replace(R.id.fragment_container, feedFragment).commit();
                }
            }
        });

        return v;
    }

    public void takePhoto() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent returnIntent) {
        super.onActivityResult(requestCode, resultCode, returnIntent);

        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case CAMERA_PIC_REQUEST:
                    Bitmap capturedBitmap =(Bitmap) returnIntent.getExtras().get("data");
                    ImageView imageView = getActivity().findViewById(R.id.imageView);
                    imageView.setImageBitmap(capturedBitmap);
                    saveImageToGallery(capturedBitmap);

            }
        }
    }

    public void saveImageToGallery(Bitmap bitmap){
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root+"/image");
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        String imageName = "Image-"+n+".jpg";
        File file = new File(myDir, imageName);
        if(file.exists()) file.delete();
        try{
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            imagePath = file.getAbsolutePath();
            out.flush();
            out.close();
            System.out.println("photo saved");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
        }

    }
}
