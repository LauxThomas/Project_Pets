package com.example.t_thinkpad.projectpetsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.HashMap;

public class SearchResultsActivity extends AppCompatActivity {
    HashMap hashMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        findViews();
        handleIntent();
        searchThroughDatabase();


    }

    public void findViews() {

    }

    public void handleIntent() {
        Intent intent = getIntent();
        HashMap hashMap = (HashMap) intent.getSerializableExtra("HashMap");
    }

    public void searchThroughDatabase() {
        //TODO: RETURNT EVTL ARRAY MIT PASSENDEN TIEREN
    }
}
