package com.example.t_thinkpad.projectpetsapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SearchResultsActivity extends AppCompatActivity {
    private ListView listView_tumbnails;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference petsRef, favsRef;
    private FusedLocationProviderClient mFusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        setContentView(R.layout.activity_search_results);
        initiateData();
        findViews();
        handleIntent();

    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initiateData() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        petsRef = mFirebaseDatabase.getReference().child("pets");
        favsRef = FirebaseDatabase.getInstance().getReference();
        favsRef = favsRef.child("users").child(FirebaseAuth.getInstance().getUid()).child("favs");
    }

    public void findViews() {
        listView_tumbnails = findViewById(R.id.listView_thumbnails);
    }

    public void handleIntent() {
        Intent intent = getIntent();
        String lookupString = intent.getStringExtra("lookupString");
        if (intent.getBooleanExtra("isShelter", false)) {
            handleDatabaseStuff(lookupString, true, false);
        } else if (intent.getBooleanExtra("showFavorites", false)) {
            handleDatabaseStuff(lookupString, false, true);
        } else {
            handleDatabaseStuff(lookupString, false, false);
        }
    }

    private void handleDatabaseStuff(final String lookupString, boolean isShelter, boolean showFavorites) {
        readData(showFavorites, isShelter, lookupString, new MyCallback() {
            @Override
            public void onCallback(Pets[] pets) {
                fillAdapter(pets);
            }
        });
    }

    private void fillAdapter(final Pets[] pets) {
        ArrayList<Pets> petsArrayList = new ArrayList<Pets>();
        for (Pets pet : pets) {
            petsArrayList.add(pet);
        }

        getCurrentLocation(petsArrayList);

        PetsAdapter petsAdapter = new PetsAdapter(this, petsArrayList);
        listView_tumbnails.setAdapter(petsAdapter);
    }

    public void readData(final boolean showFavorites, final boolean isShelter, final String lookupString, final MyCallback myCallback) {
        petsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot petsSnapshot) {
                ArrayList<Pets> petsArrayList = new ArrayList<Pets>();
                int index = 0;
                if (isShelter) {
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    for (DataSnapshot ds : petsSnapshot.getChildren()) {
                        Pets pet = ds.getValue(Pets.class);
                        if (pet.getEmailOfCreator().contains(firebaseAuth.getCurrentUser().getEmail())) {
                            petsArrayList.add(pet);
                            index++;
                        }
                    }
                    if (index == 0) {
                        Toast.makeText(SearchResultsActivity.this, "Nothing found for your parameters.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Pets[] pets = new Pets[petsArrayList.size()];
                    for (int i = 0; i < petsArrayList.size(); i++) {
                        pets[i] = petsArrayList.get(i);
                    }
                    myCallback.onCallback(pets);


                } else if (showFavorites) {
                    //iterate over all pets
                    for (final DataSnapshot petDs : petsSnapshot.getChildren()) {
                        final ArrayList<Pets> innerPetsArrayList = new ArrayList<Pets>();
                        favsRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot favSnapshot) {
                                //iterate over all favs
                                for (DataSnapshot favDs : favSnapshot.getChildren()) {
                                    System.out.println("--------");
                                    //find matches of both iterations
                                    if (favDs.getValue().equals(petDs.getKey())) {
                                        innerPetsArrayList.add(petDs.getValue(Pets.class));
                                        System.out.println("SCHNITZELBRÖTCHEN Diese Elemente sollen angezeigt werden: " + innerPetsArrayList);
                                        //TODO: warum wird Burgerbrötchen zuerst ausgeführt, 2 mal, und dann ein mal Schnitzelbrötchen??
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                        petsArrayList = innerPetsArrayList; //hier ist innerpetsArray leer
                        System.out.println("BURGERBRÖTCHEN Diese Elemente werden angezeigt: " + petsArrayList);

                    }
                    if (petsArrayList.size() == 0) {
                        Toast.makeText(SearchResultsActivity.this, "Nothing found for your parameters.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Pets[] pets = new Pets[petsArrayList.size()];
                    for (int i = 0; i < petsArrayList.size(); i++) {
                        pets[i] = petsArrayList.get(i);
                    }
                    myCallback.onCallback(pets);


                } else {


                    for (DataSnapshot ds : petsSnapshot.getChildren()) {

                        Pets pet = ds.getValue(Pets.class);

                        String replace = ds.getValue().toString().replace("=", ":");

                        if (replace.contains(lookupString)) {
                            petsArrayList.add(pet);
                            index++;
                        }
                    }
                    if (index == 0) {
                        Toast.makeText(SearchResultsActivity.this, "Nothing found for your parameters.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Pets[] pets = new Pets[petsArrayList.size()];
                    for (int i = 0; i < petsArrayList.size(); i++) {
                        pets[i] = petsArrayList.get(i);
                    }
                    myCallback.onCallback(pets);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void sortArrayList(ArrayList<Pets> petsArrayList, final Location currentLocation) {
        //TODO: gucken warums nich von anfang an richtig sortiert ist
        Location petLoc = new Location("dummyProvider");
        for (Pets pet : petsArrayList) {
            petLoc.setLatitude(pet.getLatitude());
            petLoc.setLongitude(pet.getLongitude());
            pet.setDistFromUserLocation(currentLocation.distanceTo(petLoc));
        }

        Collections.sort(petsArrayList, new Comparator<Pets>() {
            @Override
            public int compare(Pets p1, Pets p2) {
                return (int) (p1.getDistFromUserLocation() - p2.getDistFromUserLocation());
            }
        });


    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation(final ArrayList<Pets> petsArrayList) {
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location currentLocation) {
                        if (currentLocation != null) {
                            sortArrayList(petsArrayList, currentLocation);
                        }
                    }
                });

    }


}







