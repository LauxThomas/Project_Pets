package com.example.t_thinkpad.projectpetsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

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
    ArrayList arrayList = new ArrayList();

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pets);
        findViews();
        initiateDatabase();
        setListeners();
        animateThis.setTranslationX(-2500);
    }

    private void initiateDatabase() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("pets");
    }


    public void findViews() {
//        petsRef = FirebaseDatabase.getInstance().getReference().child("pets");  //Referenz auf pets
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
//TODO: SUCHEN BUTTON: Für jedes Kind in dataSnapshot, guck, ob die Eingaben richtige ergebnisse liefert
        //TODO: Also iteriere durch jedes Kind von jedem Kind von petsRef und checke die Eingaben: (name||family||race|...)
        //TODO: Dann liefere Anzahl Ergebnisse zurück, bzw überschreibe lokale Variable


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
                searchDatabase();

            }
        });
    }

    private void showData(DataSnapshot dataSnapshot) throws JSONException {
        String lookupString = generalSearchView.getQuery().toString();

        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            String replace = ds.getValue().toString().replace("=", ":");
            replace.replace("/", ":");
            JSONObject jsonObject = new JSONObject(replace);

            //TODO: search through every child for lookupString
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
            System.out.println("LOGTHATSHiT0: " + arrayList.toString());

        }
        startNextActivity();
    }

    public void startNextActivity() {
        //TODO: Intent + Suchergebnisse
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putParcelableArrayListExtra("arrayList", arrayList);
        startActivity(intent);
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

}
