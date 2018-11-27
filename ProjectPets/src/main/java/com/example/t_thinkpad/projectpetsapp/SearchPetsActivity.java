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
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.util.ArrayList;

//TODO: Beim Klick auf den button sortiert nach Entfernung die zutreffenden Tiere anzeigen
public class SearchPetsActivity extends AppCompatActivity {
    public TextView generalSearchTextView, fABText;
    public SearchView generalSearchView, nameSeachView, familySearchView, raceSearchView, ageSearchView,
            sexSearchView, locationSearchView, currentOwnerSearchView, sizeSearchView,
            numberOfPreviousOwnersSearchView, descriptionSearchView,
            chipIdSearchView, disordersSearchView;
    public Spinner sex_spinner, family_spinner, race_spinner;

    private final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 123;
    private boolean locationPermissionGranted;
    public LinearLayout animateThis;
    public FloatingActionButton searchButton;
    //boolean check = false;
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
        initialiseSpinners();
        setListeners();

        checkPermission();


    }

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
                RACES =  GeneralPetsData.getCats();;
                break;
            case 3:
                RACES =  GeneralPetsData.getBirds();;
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
        /*
        AutoCompleteTextView raceAutoCompleteTextView = (AutoCompleteTextView)
                findViewById(R.id.race_autoCompleteTextView);
        ArrayAdapter<String> raceAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, RACES);
        raceAutoCompleteTextView.setAdapter(raceAdapter);*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, RACES);
        race_spinner.setAdapter(adapter);
    }

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
    }

    private void initiateDatabase() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("pets");
    }


    public void findViews() {
        //fABText = findViewById(R.id.fABTextView);
        family_spinner = findViewById(R.id.family_spinner);
        race_spinner = findViewById(R.id.race_spinner);
        sex_spinner = findViewById(R.id.sex_spinner);
        searchButton = findViewById(R.id.fab);
        generalSearchView = findViewById(R.id.generalSearchSearchView);
        /*
        nameSeachView = findViewById(R.id.ASDF);
        familySearchView = findViewById(R.id.ASDF);
        raceSearchView = findViewById(R.id.ASDF);
        ageSearchView = findViewById(R.id.ASDF);
        sexSearchView = findViewById(R.id.ASDF);
        locationSearchView = findViewById(R.id.ASDF);
        currentOwnerSearchView = findViewById(R.id.ASDF);
        sizeSearchView = findViewById(R.id.ASDF);
        numberOfPreviousOwnersSearchView = findViewById(R.id.ASDF);
        descriptionSearchView = findViewById(R.id.ASDF);
        chipIdSearchView = findViewById(R.id.ASDF);
        disordersSearchView = findViewById(R.id.ASDF);
*/
    }

    public void setListeners() {
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchPetsActivity.this, "Search button clicked.", Toast.LENGTH_SHORT).show();
                searchDatabase();
            }
        });
    }


    private void showData(DataSnapshot dataSnapshot) throws JSONException {
        String lookupString = generalSearchView.getQuery().toString();

        startNextActivity(lookupString);
    }


    public void startNextActivity(String lookupString) {
        Intent intent = new Intent(this, SearchResultsActivity.class);
        //String arrayString = createArrayString(arrayList);
        intent.putExtra("lookupString", lookupString);
        intent.putExtra("family", (String) family_spinner.getSelectedItem());
        intent.putExtra("race", (String) race_spinner.getSelectedItem());
        intent.putExtra("sex", (String) sex_spinner.getSelectedItem());
        //intent.putExtra("arrayString", arrayString);
        startActivity(intent);
    }

    /*
    private String createArrayString(ArrayList arrayList) {
        StringBuilder sb = new StringBuilder();
        for (Object o : arrayList) {
            sb.append(o);
            sb.append("\t");
        }
        return sb.toString();
    }*/

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

    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                ) {//Can add more as per requirement

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    123);
        }
    }

}
