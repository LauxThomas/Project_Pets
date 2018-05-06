package com.example.t_thinkpad.projectpetsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchPetsActivity extends AppCompatActivity {
    public TextView generalSearchTextView, nameTextView;
    public SearchView generalSearchView, familySearchView, raceSearchView, ageSearchView,
            sexSearchView, locationSearchView, currentOwnerSearchView,
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
        familySearchView = findViewById(R.id.familySearchView);
        raceSearchView = findViewById(R.id.raceSearchView);
        ageSearchView = findViewById(R.id.ageSearchView);
        sexSearchView = findViewById(R.id.sexSearchView);
        locationSearchView = findViewById(R.id.locationSearchView);
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
        Toast.makeText(this, "showResults", Toast.LENGTH_SHORT).show();
        //Do stuff
    }
}
