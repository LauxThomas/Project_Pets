package com.example.t_thinkpad.projectpetsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainMenu extends AppCompatActivity {
    public Button searchPets, myPets, myProfile, settings;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference ref;
    private boolean isAnimalShelter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        findViewsAndInitialiseDatabase();
        setListeners();
        //deactivateUnfinishedButtons();
    }

    private void deactivateUnfinishedButtons() {
        myPets.setEnabled(false);
        myProfile.setEnabled(false);
    }

    public void findViewsAndInitialiseDatabase() {
        searchPets = findViewById(R.id.searchPets);
        myPets = findViewById(R.id.myPets);
        myProfile = findViewById(R.id.myProfile);
        settings = findViewById(R.id.settings);


        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            startSignupActivity();
        }
        checkIfUserIsShelter();
    }

    public void checkIfUserIsShelter() {
        ref = FirebaseDatabase.getInstance().getReference();
        ref = ref.child("users");


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (firebaseAuth.getCurrentUser() != null) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                    // Wenn user noch nichts aktualisiert hat, dann initialisiere Datenbank mit "" Strings:
                    if (dataSnapshot.child(user.getUid()).child("name").getValue() == null &&
                            dataSnapshot.child(user.getUid()).child("location").getValue() == null &&
                            dataSnapshot.child(user.getUid()).child("isLookingFor").getValue() == null) {
                        UserInformation userInformation = new UserInformation("", "", user.getEmail(), "");
                        ref.child(user.getUid()).setValue(userInformation);
                    }

                    //Wenn der Eintrag "isAnimalShelter" true ist, dann starte das ShelterMenu
                    //TODO: Wenn isAnimalShelter, dann lege einen Button an der Cardflip ermöglicht. https://developer.android.com/training/animation/reveal-or-hide-view
                    if (dataSnapshot.child(user.getUid()).child("isAnimalShelter").getValue() != null
                            && dataSnapshot.child(user.getUid()).child("isAnimalShelter").getValue().toString().equals("true")) {
                        startMainMenuShelter();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void setListeners() {
        searchPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearchPetsActivity();
            }
        });
        myPets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMyPetsActivity();
            }
        });
        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMyProfileActivity();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSettingsActivity();
            }
        });
    }

    public void startSignupActivity() {
        startActivity(new Intent(this, SignupActivity.class));
        finish();
    }

    public void startSearchPetsActivity() {
        Intent intent = new Intent(this, SearchPetsActivity.class);
        startActivity(intent);
    }

    public void startMyPetsActivity() {
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("showFavorites", true);
        startActivity(intent);

    }

    public void startMyProfileActivity() {
        Intent intent = new Intent(this, MyProfileActivity.class);
        startActivity(intent);

    }

    public void startSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);

    }

    public void startMainMenuShelter() {
        startActivity(new Intent(this, MainMenuShelter.class));
        Toast.makeText(this, "You're a shelter. You'll be redirected to the Shelterversion :)", Toast.LENGTH_SHORT).show();
    }

}
