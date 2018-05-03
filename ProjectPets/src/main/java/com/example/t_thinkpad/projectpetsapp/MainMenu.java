package com.example.t_thinkpad.projectpetsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity {
    public Button searchPets, myPets, myProfile, settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        findViews();
        setListeners();
    }

    public void findViews(){
        searchPets = findViewById(R.id.searchPets);
        myPets = findViewById(R.id.myPets);
        myProfile = findViewById(R.id.myProfile);
        settings = findViewById(R.id.settings);
    }

    public void setListeners(){
        searchPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchPetsActivity();
            }
        });
        myPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyPetsActivity();
            }
        });
        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMyProfileActivity();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsActivity();
            }
        });
    }

    public void openSearchPetsActivity(){
        Intent intent = new Intent(this, SearchPetsActivity.class);
        startActivity(intent);
    }
    public void openMyPetsActivity(){
        Intent intent = new Intent(this, MyPetsActivity.class);
        startActivity(intent);

    }
    public void openMyProfileActivity(){
        Intent intent = new Intent(this, MyProfileActivity.class);
        startActivity(intent);

    }
    public void openSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);

    }

}
