package com.example.t_thinkpad.projectpetsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class SearchResultsActivity extends AppCompatActivity {
    HashMap hashMap;
    private DatabaseReference usersRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        findViews();
        initializeStuff();
        handleIntent();
        searchThroughDatabase();


    }

    private void initializeStuff() {
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");


        // ref.child('users').orderByChild('name').equalTo('Alex').on('child_added',  ...)
        System.out.println("LOG THAT SHIT: " + usersRef.);


    }

    public void findViews() {

    }

    public void handleIntent() {
        Intent intent = getIntent();
        HashMap hashMap = (HashMap) intent.getSerializableExtra("HashMap");
//        Toast.makeText(this, hashMap.toString(), Toast.LENGTH_SHORT).show();
    }

    public void searchThroughDatabase() {
        //TODO: RETURNT EVTL ARRAY MIT PASSENDEN TIEREN
    }
}
