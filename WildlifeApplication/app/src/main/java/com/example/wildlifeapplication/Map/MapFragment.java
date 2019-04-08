package com.example.wildlifeapplication.Map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wildlifeapplication.R;
import com.example.wildlifeapplication.Search.AnimalInformation.Animal;
import com.example.wildlifeapplication.Search.AnimalInformation.AnimalDatabase;
import com.example.wildlifeapplication.Search.AnimalInformation.AnimalInformationFragment;
import com.example.wildlifeapplication.Store.StoreFragment;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;


import static android.support.constraint.Constraints.TAG;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mGoogleMap;
    private MapView mapView;
    private View v;
    private boolean mLocationPermissionGranted = false;
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    volatile static List<Spotting> recentSpottings;
    private List<Spotting> mAllSpottings;
    private boolean storeFragManualLocation = false;
    private StoreFragment storeFragment;
    private LatLng position;



    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_map, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getLocationPermission();

        mapView = (MapView) v.findViewById(R.id.map);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        if(storeFragManualLocation){
            storeFragLocationSelect();
        }



        String[] seenAnimals = new String[]{"51.493514, -3.194885, Common Kingfisher, 19/03/19," +
                " 10:30", "51.490448, -3.193393, European Blue Tit, 15/03/19, 15:00", "51.488832, " +
                "-3.186988, Southern Hawker, 20/03/19, 13:00", "51.490993, -3.194303," +
                " Jay, 20/03/19, 13:00", "51.507242, -3.176287, Mute Swan, 18/03/19, 12:53",
                "51.515108, -3.176015, Lesser Spotted Woodpeckers, 17/03/19, 11:48", "51.513500," +
                " -3.173833, Cormorant, 16/03/19, 13:16", "51.500444, -3.182972, Green Woodpecker," +
                " 17/03/19, 15:44", "51.503224, -3.180453, Large Yellow Underwing, 18/03/19, 18:03",
                "51.532150, -3.166498, Long-tailed Tit, 16/03/19, 10:15", "51.528373, -3.168607," +
                " Robin, 19/03/19, 09:02", "51.459524, -3.169187, Common Moorhens, 20/03/19, " +
                "16/03/19, 11:54", "51.461700, -3.175436, European Reed Warbler, 17/03/19, 14:00",
                "51.460692, -3.168486, Whitethroat, 15/03/19, 17:20", "51.523946, -3.245422, " +
                "Great Spotted Woodpecker, 16/03/19, 16:41", "51.519453, -3.252643, Blue Tit," +
                " 19/03/19, 11:36", "51.517328, -3.247728, Kingfisher, 18/03/19, 15:55"};
        final List<Spotting> listOfSpottingsGenerated = generateSpottings(seenAnimals);

        final SpottingOfAnimalsDatabase db;
        db = Room.databaseBuilder(getContext(), SpottingOfAnimalsDatabase.class, "animal sighting database").build();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                if (db.spottingAnimalDao().getAllSpottingOfAnimals().size() < 10){
                    db.spottingAnimalDao().insertAll(listOfSpottingsGenerated);
                }

                recentSpottings = db.spottingAnimalDao().getRecentSpottings();


                db.close();
            }

        });

        getLocationPermission();
        if (mLocationPermissionGranted) {
            mGoogleMap.setMyLocationEnabled(true);
            getDeviceLocation();
        } else {
            CameraPosition cardiffCentre = CameraPosition.builder().target(new LatLng(51.481580, -3.179089)).zoom(13).bearing(5).tilt(2).build();
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cardiffCentre));
            setPosition(new LatLng(51.481580, -3.179089));
        }

        synchronized (this ) {
            while (recentSpottings == null) {
                try {
                    MapFragment.this.wait(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        addMarkersForSpottedAnimals(recentSpottings, googleMap);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }


    public void addMarkersForSpottedAnimals(List<Spotting> spottings, GoogleMap googleMap) {

        for (Spotting spotting : spottings) {
            googleMap.addMarker(new MarkerOptions().position(
                    new LatLng(spotting.getLatitudeOfSpotting(),
                            spotting.getLongitudeOfSpotting())).
                    title(spotting.getNoun()).snippet("Seen on " +
                    spotting.getDatetimeOfSpotting()).
                    icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        }

        googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                final String animalNoun = marker.getTitle();
                AnimalInformationFragment animalInformationFragment = new AnimalInformationFragment();
                Bundle args = new Bundle();
                args.putString("Noun", animalNoun);
                animalInformationFragment.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, animalInformationFragment).commit();
            }
        });

    }

    public List<Spotting> generateSpottings(String[] spottingsAsStringArray) {
        List<Spotting> spottings = new ArrayList<>();
        for (String spottingDetails : spottingsAsStringArray) {
            String[] spottingDetailsSplit = spottingDetails.split(", ");
            Spotting newSpotting = new Spotting();
            newSpotting.setLatitudeOfSpotting(Float.parseFloat(spottingDetailsSplit[0]));
            newSpotting.setLongitudeOfSpotting(Float.parseFloat(spottingDetailsSplit[1]));
            newSpotting.setNoun(spottingDetailsSplit[2]);
            try {
                Date date = new SimpleDateFormat("dd/MM/yy").parse(spottingDetailsSplit[3]);
                newSpotting.setDatetimeOfSpotting(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            spottings.add(newSpotting);
        }
        return spottings;

    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void updateLocationUI() {
        if (mGoogleMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mGoogleMap.setMyLocationEnabled(false);
                mGoogleMap.getUiSettings().setMyLocationButtonEnabled(false);
                mGoogleMap = null;
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        Log.d(TAG,"getting devices location");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getContext());
        try{
            if (mLocationPermissionGranted){
                Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG,"found device's location");
                            Location currentLocation = (Location) task.getResult();
                            CameraPosition devicePosition = CameraPosition.builder().target(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude())).zoom(13).bearing(5).tilt(2).build();
                            mGoogleMap.moveCamera(CameraUpdateFactory.newCameraPosition(devicePosition));
                            setPosition(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()));
                        } else {
                            Log.d(TAG,"could not find device's location");

                        }
                    }
                });
            }

        }catch(SecurityException e){
            Log.d(TAG,"getDeviceLocation: SecurityException: " + e.getMessage());

        }
    }

    public void storeFragLocationSelect(){
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                storeFragment = (StoreFragment) getFragmentManager().findFragmentByTag("Store");
                storeFragment.setLatLng(latLng);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, storeFragment).commit();
            }
        });
    }

    public void setStoreFragManualLocation(boolean value){
        this.storeFragManualLocation = value;
    }

    public LatLng storeFragGetLoc(){
        return position;
    }

    public void setPosition(LatLng aPosition){
        this.position = aPosition;
    }
}
