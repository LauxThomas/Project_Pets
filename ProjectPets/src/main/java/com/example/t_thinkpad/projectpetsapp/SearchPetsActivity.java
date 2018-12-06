package com.example.t_thinkpad.projectpetsapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

//TODO: Beim Klick auf den button sortiert nach Entfernung die zutreffenden Tiere anzeigen
public class SearchPetsActivity extends AppCompatActivity {
    public SearchView generalSearchView;
    public Spinner sex_spinner, family_spinner, race_spinner;

    /*
    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 123;
    private boolean locationPermissionGranted;
    */
    public FloatingActionButton searchButton;

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
        initialiseSpinners();
        setListeners();

        //checkPermission();


    }



    private void initialiseSpinners() {
        String[] sexSpinnerItems = new String[]{"", "männlich", "weiblich"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sexSpinnerItems);
        sex_spinner.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, GeneralPetsData.getFamilies());
        family_spinner.setAdapter(adapter2);
        family_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                updateRaceSpinnerWithPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
    }

    private void updateRaceSpinnerWithPosition(int position) {
        String[] RACES = new String[]{};
        switch (family_spinner.getSelectedItemPosition()) {
            case 0:
                break;
            case 1:
                RACES = GeneralPetsData.getDogs();
                break;
            case 2:
                RACES = GeneralPetsData.getCats();
                break;
            case 3:
                RACES = GeneralPetsData.getBirds();
                break;
            case 4:
                RACES = GeneralPetsData.getFish();
                break;
            case 5:
                RACES = GeneralPetsData.getSmallAnimals();
                break;
            case 6:
                RACES = GeneralPetsData.getOther();
                break;
            default:
                break;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, RACES);
        race_spinner.setAdapter(adapter);
    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true;
                } else {
                    locationPermissionGranted = false;
                }
                return;
            }
        }
    }*/

    private void initiateDatabase() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("pets");
    }


    public void findViews() {
        family_spinner = findViewById(R.id.family_spinner);
        race_spinner = findViewById(R.id.race_spinner);
        sex_spinner = findViewById(R.id.sex_spinner);
        searchButton = findViewById(R.id.fab);
        generalSearchView = findViewById(R.id.generalSearchSearchView);
    }

    public void setListeners() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startNextActivity();
            }
        });
    }

    public void startNextActivity() {
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("lookupString", generalSearchView.getQuery().toString());
        intent.putExtra("family", (String) family_spinner.getSelectedItem());
        intent.putExtra("race", (String) race_spinner.getSelectedItem());
        intent.putExtra("sex", (String) sex_spinner.getSelectedItem());
        startActivity(intent);
    }

    /*
    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }
    }*/

}
