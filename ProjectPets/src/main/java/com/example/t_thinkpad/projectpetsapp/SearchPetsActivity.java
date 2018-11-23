package com.example.t_thinkpad.projectpetsapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//TODO: Beim Klick auf den button sortiert nach Entfernung die zutreffenden Tiere anzeigen
public class SearchPetsActivity extends AppCompatActivity {
    public TextView generalSearchTextView, fABText;
    public SearchView generalSearchView, nameSeachView, familySearchView, raceSearchView, ageSearchView,
            sexSearchView, locationSearchView, currentOwnerSearchView, sizeSearchView,
            numberOfPreviousOwnersSearchView, descriptionSearchView,
            chipIdSearchView, disordersSearchView;

    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION=123;
    private boolean locationPermissionGranted;
    public LinearLayout animateThis;
    //    public Button showMoreButton;
    public FloatingActionButton searchButton;
    boolean check = false;
    ArrayList arrayList = new ArrayList();

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pets);

        // show FAB above Keyboard:
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        findViews();
        initiateDatabase();
        setListeners();

        //obsolete in a bit
        checkPermission();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            checkPermission();
        }
        /*
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            locationPermissionGranted = true;
        }*/



    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    locationPermissionGranted = true;
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    locationPermissionGranted = false;
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    private void initiateDatabase() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("pets");
    }


    public void findViews() {
        fABText = findViewById(R.id.fABTextView);
        searchButton = findViewById(R.id.fab);
        generalSearchView = findViewById(R.id.generalSearchSearchView);

    }

    public void setListeners() {
        //TODO: Realtime aktualisierung der ergebnisse einfügen
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchPetsActivity.this, "suche geklickt", Toast.LENGTH_SHORT).show();
                searchDatabase();
            }
        });
    }


    //TODO: evtl. zu viel unnötige Abfragen für den kleinen Mist.
    private void updateFABText() {
        fABText.setX(searchButton.getX()-7);
        fABText.setY(searchButton.getY()-7);

    }

    private void showData(DataSnapshot dataSnapshot) throws JSONException {
        String lookupString = generalSearchView.getQuery().toString();


        for (DataSnapshot ds : dataSnapshot.getChildren()) {

            String replace = ds.getValue().toString().replace("=", ":");
            JSONObject jobj = new JSONObject();
            String value = ds.getValue().toString();
            String s = value.substring(value.indexOf("\"image\"") + 8);
            s = s.substring(0, s.indexOf(","));
            System.out.println("SOUTTEST: " + s);
            jobj.put("image", s);
            //currentOwner:
//            s = backUpString.substring(backUpString.indexOf(" currentOwner=") + 14);
//            s = s.substring(0, s.indexOf(", name="));
//            newPet.setCurrentOwner(s);

            jobj.put("age", "VALUE");
            jobj.put("chipId", "VALUE");
            jobj.put("currentOwner", "VALUE");
            jobj.put("description", "VALUE");
            jobj.put("disorders", "VALUE");
            jobj.put("family", "VALUE");
            jobj.put("location", "VALUE");
            //jobj.put("latitude","VALUE");
            //jobj.put("longitude","VALUE");
            jobj.put("name", "VALUE");
            jobj.put("numberOfPreviousOwners", "VALUE");
            jobj.put("sex", "VALUE");
            jobj.put("size", "VALUE");
            System.out.println("REPLACESTRING1: " + replace);
            replace = replace.replace("/", ":");
            System.out.println("REPLACESTRING2: " + replace);
            replace = replace.replaceAll("\\s+", "");
            System.out.println("REPLACESTRING3: " + replace);
            replace = escapeCommas(replace);
            System.out.println("REPLACESTRING4: " + replace);
            JSONObject jsonObject = new JSONObject(replace);

            if (jsonObject.get("age").toString().contains(lookupString)
                    || jsonObject.get("chipId").toString().contains(lookupString)
                    || jsonObject.get("currentOwner").toString().contains(lookupString)
                    || jsonObject.get("description").toString().contains(lookupString)
                    || jsonObject.get("disorders").toString().contains(lookupString)
                    || jsonObject.get("family").toString().contains(lookupString)
                    || jsonObject.get("location").toString().contains(lookupString)
                    || jsonObject.get("name").toString().contains(lookupString)
                    || jsonObject.get("numberOfPreviousOwners").toString().contains(lookupString)
                    || jsonObject.get("race").toString().contains(lookupString)
                    || jsonObject.get("sex").toString().contains(lookupString)
                    || jsonObject.get("size").toString().contains(lookupString)
                    ) {
                arrayList.add(jsonObject);
            }

        }
        startNextActivity(lookupString);
    }

    private String escapeCommas(String replace) {
        replace = replace.replace(",race", "escapedComma" + "race");
        replace = replace.replace(",sex", "escapedComma" + "sex");
        replace = replace.replace(",description", "escapedComma" + "description");
        replace = replace.replace(",numberOfPreviousOwners", "escapedComma" + "numberOfPreviousOwners");
        replace = replace.replace(",disorders", "escapedComma" + "disorders");
        replace = replace.replace(",size", "escapedComma" + "size");
        replace = replace.replace(",image", "escapedComma" + "image");
        replace = replace.replace(",currentOwner", "escapedComma" + "currentOwner");
        replace = replace.replace(",name", "escapedComma" + "name");
        replace = replace.replace(",location", "escapedComma" + "location");
        replace = replace.replace(",randomUUID", "escapedComma" + "randomUUID");
        replace = replace.replace(",family", "escapedComma" + "family");
        replace = replace.replace(",chipId", "escapedComma" + "chipId");
        replace = replace.replace(",age", "escapedComma" + "age");
        replace = replace.replace(",latitude", "escapedComma" + "latitude");
        replace = replace.replace(",longitude", "escapedComma" + "longitude");
        replace = replace.replace(",distFromUserLocation", "escapedComma" + "distFromUserLocation");
        replace = replace.replace(",", "§$%");
        replace = replace.replace("escapedComma", ",");
        return replace;
    }

    public void startNextActivity(String lookupString) {
        Intent intent = new Intent(this, SearchResultsActivity.class);
        String arrayString = createArrayString(arrayList);
        intent.putExtra("lookupString", lookupString);
        intent.putExtra("arrayString", arrayString);
        startActivity(intent);
    }

    private String createArrayString(ArrayList arrayList) {
        StringBuilder sb = new StringBuilder();
        for (Object o : arrayList) {
            sb.append(o);
            sb.append("\t");
        }
        return sb.toString();
    }

    private void searchDatabase() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    showData(dataSnapshot);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ){//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }
    }

}
