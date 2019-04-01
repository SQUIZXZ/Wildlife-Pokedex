package com.example.wildlifeapplication.Store;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.wildlifeapplication.Map.MapFragment;
import com.example.wildlifeapplication.R;
import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class StoreFragment extends Fragment {

    private static final int CAMERA_PIC_REQUEST = 1337;
    private String pictureFilePath;
    private File image = null;
    private Activity activity;
    private boolean manualLocation;
    private MapFragment mapFragment;
    private LatLng latLng;
    private boolean manual = false;

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
        mapFragment = (MapFragment) getFragmentManager().findFragmentByTag("Map");
        manualLocation = false;
        activity = getActivity();
        View view = inflater.inflate(R.layout.fragment_store,container,false);
        Switch switch1 = view.findViewById(R.id.switch1);
        final Button button3 = view.findViewById(R.id.button3);
        button3.setVisibility(View.INVISIBLE);
        final Button button1 = view.findViewById(R.id.button);
        final Button button2 = view.findViewById(R.id.button2);
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    manual = true;
                    button3.setVisibility(View.VISIBLE);
                }else{
                    manual = false;
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
                if (image != null) {
                    Uri photoURI = FileProvider.getUriForFile(getContext(), "com.example.wildlifeapplication", image);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    System.out.println("act: " + activity);
                    activity.startActivityForResult(intent, CAMERA_PIC_REQUEST);
                }
            }
        });
        button3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view) {
                FragmentTransaction tr = getFragmentManager().beginTransaction();
                tr.replace(R.id.fragment_container, mapFragment).commit();
                tr.addToBackStack(null);
                mapFragment.setStoreFragManualLocation(true);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(manual);
                if (manual = true) {
                    LatLng pos = mapFragment.storeFragGetLoc(); // crashing here
                    setLatLng(pos);
                }
                System.out.println("LATLNG: " + latLng);
            }
        });
        return view;
    }


    public String getPictureFilePath(){
        return this.pictureFilePath;
    }


    public void setLatLng(LatLng latLng){
        this.latLng = latLng;
    }
}
