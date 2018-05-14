package com.example.t_thinkpad.projectpetsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class AddPetsActivity extends AppCompatActivity {
    ImageView imageView;
    EditText nameEditText, familyEditText, raceEditText, ageEditText,
            sexEditText, locationEditText, currentOwnerEditText, sizeEditText,
            numberOfPreviousOwnersEditText, descriptionEditText, chipIdEditText, disordersEditText;
    Button addPetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pets);
        findViews();
        setListeners();
    }

    private void setListeners() {
        addPetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewPet();
            }
        });
    }

    private void createNewPet() {
        // TODO: Lege unter https://projectpets-23864.firebaseio.com/pets das neue Tier an
        // Zusätzlich zu den oben eingegebenen Daten, soll noch eine fortlaufende PetID eingefügt werden

    }

    private void findViews() {
        imageView = findViewById(R.id.imageView);
        nameEditText = findViewById(R.id.nameEditText);
        familyEditText = findViewById(R.id.familyEditText);
        raceEditText = findViewById(R.id.raceEditText);
        ageEditText = findViewById(R.id.ageEditText);
        sexEditText = findViewById(R.id.sexEditText);
        locationEditText = findViewById(R.id.locationEditText);
        currentOwnerEditText = findViewById(R.id.currentOwnerEditText);
        sizeEditText = findViewById(R.id.sizeEditText);
        numberOfPreviousOwnersEditText = findViewById(R.id.numberOfPreviousOwnersEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        chipIdEditText = findViewById(R.id.chipIdEditText);
        disordersEditText = findViewById(R.id.disordersEditText);
        addPetButton = findViewById(R.id.addPetButton);
    }
}
