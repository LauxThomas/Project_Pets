package com.example.t_thinkpad.projectpetsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainMenuShelter extends AppCompatActivity{

    Button addPetsButton, myProfileButton, myPetsButton, settingsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_shelter);
        findViewsAndInitialiseDatabase();
        setListeners();

        //deactivateUnfinishedButtons();
    }

    private void deactivateUnfinishedButtons() {
        myPetsButton.setEnabled(false);
        myProfileButton.setEnabled(false);
    }

    public void findViewsAndInitialiseDatabase() {
        addPetsButton = findViewById(R.id.addPetsButton);
        myProfileButton = findViewById(R.id.myProfileButton);
        myPetsButton = findViewById(R.id.myPetsButton);
        settingsButton = findViewById(R.id.settingsButton);
    }

    public void setListeners(){
        addPetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAddPetsActivity();
            }
        });

        myProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMyProfileActivity();
            }
        });

        myPetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMyPetsActivity();
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSettingsActivity();
            }
        });
    }

    private void startMyPetsActivity() {
        startActivity(new Intent(this,MyPetsActivity.class));
    }


    private void startMyProfileActivity() {
        startActivity(new Intent(this,MyProfileActivity.class));
    }

    private void startAddPetsActivity() {
        startActivity(new Intent(this,AddPetsActivity.class));
    }

    public void startSettingsActivity() {
        startActivity(new Intent(this, SettingsActivity.class));
        finish();
    }


}
