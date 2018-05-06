package com.example.t_thinkpad.projectpetsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.HashMap;

public class SearchResultsActivity extends AppCompatActivity {
    HashMap hashMap = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        Bundle bundle = this.getIntent().getExtras();

//        if(bundle != null) {
        hashMap = (HashMap) bundle.getSerializable("HashMap");
//        }
        //TODO:
        Toast.makeText(this, hashMap.get("general").toString(), Toast.LENGTH_SHORT).show();
    }
}
