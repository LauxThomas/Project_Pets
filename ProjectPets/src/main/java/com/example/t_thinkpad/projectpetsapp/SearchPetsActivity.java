package com.example.t_thinkpad.projectpetsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//TODO: Floating Action Button mit Anzahl der Suchergebnisse in realtime.
//TODO: Beim Klick auf den button sortiert nach Entfernung die zutreffenden Tiere anzeigen
public class SearchPetsActivity extends AppCompatActivity {
    public TextView generalSearchTextView, nameTextView;
    public SearchView generalSearchView, nameSeachView, familySearchView, raceSearchView, ageSearchView,
            sexSearchView, locationSearchView, currentOwnerSearchView, sizeSearchView,
            numberOfPreviousOwnersSearchView, descriptionSearchView,
            chipIdSearchView, disordersSearchView;

    public LinearLayout animateThis;
    public Button showMoreButton, searchButton;
    boolean check = false;
    private DatabaseReference petsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pets);
        findViews();
        setListeners();
        animateThis.setTranslationX(-2500);
    }

    public void findViews() {
        petsRef = FirebaseDatabase.getInstance().getReference().child("pets");  //Referenz auf pets
        generalSearchTextView = findViewById(R.id.generalSearchTextView);
        nameTextView = findViewById(R.id.nameTextView);
        animateThis = findViewById(R.id.animateThis);
        showMoreButton = findViewById(R.id.showMoreButton);
        searchButton = findViewById(R.id.searchButton);
        generalSearchView = findViewById(R.id.generalSearchSearchView);
        nameSeachView = findViewById(R.id.nameSearchView);
        familySearchView = findViewById(R.id.familySearchView);
        raceSearchView = findViewById(R.id.raceSearchView);
        ageSearchView = findViewById(R.id.ageSearchView);
        sexSearchView = findViewById(R.id.sexSearchView);
        locationSearchView = findViewById(R.id.locationSearchView);
        sizeSearchView = findViewById(R.id.sizeSearchView);
        currentOwnerSearchView = findViewById(R.id.currentOwnerSearchView);
        numberOfPreviousOwnersSearchView = findViewById(R.id.numberOfPreviousOwnersSearchView);
        descriptionSearchView = findViewById(R.id.descriptionSearchView);
        chipIdSearchView = findViewById(R.id.chipIdSearchView);
        disordersSearchView = findViewById(R.id.disordersSearchView);
    }

    public void setListeners() {
//TODO: Für jedes Kind in dataSnapshot, guck, ob die Eingaben richtige ergebnisse liefert
        //TODO: Also iteriere durch jedes Kind von jedem Kind von petsRef und checke die Eingaben: (name||family||race|...)
        //TODO: Dann liefere Anzahl Ergebnisse zurück, bzw überschreibe lokale Variable
        petsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Pets pet = snapshot.getValue(Pets.class);
                    System.out.println("TESTTHATSHIT: " + pet.getName());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        showMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check) {
                    animateThis.animate().translationX(-2500);
                    showMoreButton.setText("more");
                } else {
                    animateThis.animate().translationX(0);
                    showMoreButton.setText("less");
                }
                check = !check;
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResults();
            }
        });

    }

    public void showResults() {
        //TODO: Intent + Suchergebnisse
        //Do stuff
//        getAllInputs(); //returns a HashMap with all SearchView Queries
        Intent intent = new Intent(this, SearchResultsActivity.class);
//        Bundle bundle = new Bundle();
        HashMap hashMap = new HashMap();
        hashMap = getAllInputs();
//        bundle.putSerializable("HashMap",hashMap);
//        intent
        intent.putExtra("HashMap", hashMap);
        startActivity(intent);
    }

    public HashMap getAllInputs() {
        HashMap hashMap = new HashMap();

        //TODO: Das ist nicht schön gemacht!:
        if (isNotEmpty(generalSearchTextView.getText().toString())) {
            hashMap.put("general", generalSearchView.getQuery());
        }
        if (isNotEmpty(nameSeachView.getQuery().toString())) {
            hashMap.put("name", nameSeachView.getQuery());
        }
        if (isNotEmpty(familySearchView.getQuery().toString())) {
            hashMap.put("family", familySearchView.getQuery());
        }
        if (isNotEmpty(raceSearchView.getQuery().toString())) {
            hashMap.put("race", raceSearchView.getQuery());
        }
        if (isNotEmpty(ageSearchView.getQuery().toString())) {
            hashMap.put("age", ageSearchView.getQuery());
        }
        if (isNotEmpty(sexSearchView.getQuery().toString())) {
            hashMap.put("sex", sexSearchView.getQuery());
        }
        if (isNotEmpty(locationSearchView.getQuery().toString())) {
            hashMap.put("location", locationSearchView.getQuery());
        }
        if (isNotEmpty(currentOwnerSearchView.getQuery().toString())) {
            hashMap.put("currentOwner", currentOwnerSearchView.getQuery());
        }
        if (isNotEmpty(sizeSearchView.getQuery().toString())) {
            hashMap.put("size", sizeSearchView.getQuery());
        }
        if (isNotEmpty(numberOfPreviousOwnersSearchView.getQuery().toString())) {
            hashMap.put("numberOfPreviousOwners", numberOfPreviousOwnersSearchView.getQuery());
        }
        if (isNotEmpty(descriptionSearchView.getQuery().toString())) {
            hashMap.put("description", descriptionSearchView.getQuery());
        }
        if (isNotEmpty(chipIdSearchView.getQuery().toString())) {
            hashMap.put("chipId", chipIdSearchView.getQuery());
        }
        if (isNotEmpty(disordersSearchView.getQuery().toString())) {
            hashMap.put("disorders", disordersSearchView.getQuery());
        }
        return hashMap;
    }

    private boolean isNotEmpty(String content) {
        if (content.trim().equals("")) {
            return false;
        } else {
            return true;
        }
    }
}
