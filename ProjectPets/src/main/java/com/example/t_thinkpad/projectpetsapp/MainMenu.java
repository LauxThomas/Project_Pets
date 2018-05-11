package com.example.t_thinkpad.projectpetsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class MainMenu extends AppCompatActivity {
    public Button searchPets, myPets, myProfile, settings;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        findViewsAndInitializeStuff();
        setListeners();
    }

    public void findViewsAndInitializeStuff(){
        searchPets = findViewById(R.id.searchPets);
        myPets = findViewById(R.id.myPets);
        myProfile = findViewById(R.id.myProfile);
        settings = findViewById(R.id.settings);


        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            startLoginActivity();
        }

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
    public void startLoginActivity(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
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
