package com.example.wildlifeapplication.Store;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.wildlifeapplication.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StoreFragment extends Fragment {

    private static final int CAMERA_PIC_REQUEST = 1337;
    private String pictureFilePath;
    private File image = null;
    private Activity activity;

    public static StoreFragment newInstance() {
        StoreFragment fragment = new StoreFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_store,container,false);
        Switch switch1 = view.findViewById(R.id.switch1);
        final Button button3 = view.findViewById(R.id.button3);
        button3.setVisibility(View.INVISIBLE);
        final Button button1 = view.findViewById(R.id.button);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    button3.setVisibility(View.VISIBLE);
                }else{
                    button3.setVisibility(View.INVISIBLE);
                }
            }
        });
        button1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                try {
                    String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
                    String pictureFile = "Wildlife_" + timeStamp;
                    File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    image = File.createTempFile(pictureFile, ".jpg", storageDir); // crashing here
                    pictureFilePath = image.getAbsolutePath();
                }catch (java.io.IOException e){
                    e.printStackTrace();
                    Toast.makeText(getContext(),"Photo file could not be created, please try again",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (image != null){
                    Uri photoURI = FileProvider.getUriForFile(getContext(),"com.example.wildlifeapplication",image);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,photoURI);
                    System.out.println("act: " + activity);
                    activity.startActivityForResult(intent, CAMERA_PIC_REQUEST);
                }


            }
        });

        return view;
    }


    public String getPictureFilePath(){
        return this.pictureFilePath;
    }
}
