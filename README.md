To launch this application for yourself you will need a google maps API key and will need to put it into the manifest.
Insert the key into the android:value = "" setting in the meta-data tag with name "com.google.android.geo.API_KEY"

The application will require the location setting to be turned on even if the user does not give permission for it to be used.

The application has no specific admin rights so any user can register and have the same access level as anyone else.

The application can be launched through android studio on an emulator, or additionally, launched on an android phone using android 
studio.

Wildlife-Pokedex/Release/WildlifeApplication/app/src/debug/res/values/google_maps_api.xml
