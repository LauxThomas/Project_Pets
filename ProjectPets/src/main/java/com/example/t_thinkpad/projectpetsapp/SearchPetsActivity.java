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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SearchPetsActivity extends AppCompatActivity {
    public TextView generalSearchTextView, nameTextView;
    public SearchView generalSearchView, nameSeachView,familySearchView, raceSearchView, ageSearchView,
            sexSearchView, locationSearchView, currentOwnerSearchView, sizeSearchView,
            numberOfPreviousOwnersSearchView, descriptionSearchView,
            chipIdSearchView, disordersSearchView;

    public LinearLayout animateThis;
    public Button showMoreButton, searchButton;
    boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pets);
        findViews();
        setListeners();
        animateThis.setTranslationY(5000);
    }

    public void findViews() {
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
        showMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check) {
                    animateThis.animate().translationY(5000);
                    showMoreButton.setText("more");
                } else {
                    animateThis.animate().translationY(0);
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
        Intent intent = new Intent(this,SearchResultsActivity.class);
//        Bundle bundle = new Bundle();
        HashMap hashMap = new HashMap();
        hashMap = getAllInputs();
//        bundle.putSerializable("HashMap",hashMap);
//        intent
        intent.putExtra("HashMap",hashMap);
        startActivity(intent);
    }
    public HashMap getAllInputs(){
        HashMap hashMap = new HashMap();
        hashMap.put("general",generalSearchView.getQuery());
        hashMap.put("name", nameSeachView.getQuery());
        hashMap.put("family",familySearchView.getQuery());
        hashMap.put("race",raceSearchView.getQuery());
        hashMap.put("age",ageSearchView.getQuery());
        hashMap.put("sex",sexSearchView.getQuery());
        hashMap.put("location",locationSearchView.getQuery());
        hashMap.put("currentOwner",currentOwnerSearchView.getQuery());
        hashMap.put("size",sizeSearchView.getQuery());
        hashMap.put("numberOfPreviousOwners",numberOfPreviousOwnersSearchView.getQuery());
        hashMap.put("description",descriptionSearchView.getQuery());
        hashMap.put("chipId",chipIdSearchView.getQuery());
        hashMap.put("disorders",disordersSearchView.getQuery());
        return hashMap;
    }
}
