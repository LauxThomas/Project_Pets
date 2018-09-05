package com.example.t_thinkpad.projectpetsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    public ImageView pictureImageView;
    public TextView labelNameTextView, labelFamilyTextView, labelRaceTextView, labelAgeTextView, labelSexTextView, labelLocationTextView, labelSizeTextView, labelNumberOfPreviousOwnersTextView, labelCurrentOwnerTextView, attributeNameTextView, attributeFamilyTextView, attributeRaceTextView, attributeAgeTextView, attributeSexTextView, attributeLocationTextView, attributeSizeTextView, attributeNumberOfPreviousOwnersTextView, attributeCurrentOwnerTextView, labelDescriptionTextView, attributeDescriptionTextView, labelChipIdTextView, attributeChipIdTextView, labelDisordersTextView, attributeDisordersTextView;
    int index;
    public ArrayList<Pets> pets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        findViews();
        handleIntent();
        setViews();
        setViews();

    }

    public void findViews() {
        pictureImageView = findViewById(R.id.pictureImageView);
        labelNameTextView = findViewById(R.id.labelNameTextView);
        labelFamilyTextView = findViewById(R.id.labelFamilyTextView);
        labelRaceTextView = findViewById(R.id.labelRaceTextView);
        labelAgeTextView = findViewById(R.id.labelAgeTextView);
        labelSexTextView = findViewById(R.id.labelSexTextView);
        labelLocationTextView = findViewById(R.id.labelLocationTextView);
        labelSizeTextView = findViewById(R.id.labelSizeTextView);
        labelNumberOfPreviousOwnersTextView = findViewById(R.id.labelNumberOfPreviousOwnersTextView);
        labelCurrentOwnerTextView = findViewById(R.id.labelCurrentOwnerTextView);
        attributeNameTextView = findViewById(R.id.attributeNameTextView);
        attributeFamilyTextView = findViewById(R.id.attributeFamilyTextView);
        attributeRaceTextView = findViewById(R.id.attributeRaceTextView);
        attributeAgeTextView = findViewById(R.id.attributeAgeTextView);
        attributeSexTextView = findViewById(R.id.attributeSexTextView);
        attributeLocationTextView = findViewById(R.id.attributeLocationTextView);
        attributeSizeTextView = findViewById(R.id.attributeSizeTextView);
        attributeNumberOfPreviousOwnersTextView = findViewById(R.id.attributeNumberOfPreviousOwnersTextView);
        attributeCurrentOwnerTextView = findViewById(R.id.attributeCurrentOwnerTextView);

        labelDescriptionTextView = findViewById(R.id.labelDescriptionTextView);
        attributeDescriptionTextView = findViewById(R.id.attributeDescriptionTextView);
        labelChipIdTextView = findViewById(R.id.labelChipIdTextView);
        attributeChipIdTextView = findViewById(R.id.attributeChipIdTextView);
        labelDisordersTextView = findViewById(R.id.labelDisordersTextView);
        attributeDisordersTextView = findViewById(R.id.attributeDisordersTextView);
    }

    public void handleIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        pets = (ArrayList<Pets>) bundle.getSerializable("ARRAYLIST");
        index = bundle.getInt("INDEX");
    }

    public void setViews() {
        //TODO: Alle zugehörigen TextViews in ein horizontales LinearLayout packen, damit die Alignments richtig sind.
        //TODO: EVTL KOMPLETT AUF suCHE TRANSFERIEREN? BILDER EINFÜGEN! https://stackoverflow.com/a/39708645
        //pictureImageView.setImageDrawable(getD);
        labelNameTextView.setText("Name: ");
        attributeNameTextView.setText(pets.get(index).getName());
        labelFamilyTextView.setText("Family: ");
        attributeFamilyTextView.setText(pets.get(index).getFamily());
        labelRaceTextView.setText("Race: ");
        attributeRaceTextView.setText(pets.get(index).getRace());
        labelAgeTextView.setText("Age: ");
        attributeAgeTextView.setText(pets.get(index).getAge() + "");
        labelSexTextView.setText("Sex: ");
        attributeSexTextView.setText(pets.get(index).getSex());
        labelLocationTextView.setText("Location: ");
        attributeLocationTextView.setText(pets.get(index).getLocation());
        labelCurrentOwnerTextView.setText("Current owner: ");
        attributeCurrentOwnerTextView.setText(pets.get(index).getCurrentOwner());

        setOptionalViews();

    }

    public void setOptionalViews() {
        if (!(pets.get(index).getSize() == "")) {
            labelSizeTextView.setText("Size: ");
            attributeSizeTextView.setText(pets.get(index).getSize());
        } else {
            labelSizeTextView.setText("");
            attributeSizeTextView.setText("");
        }

        if (pets.get(index).getNumberOfPreviousOwners() != -1) {
            labelNumberOfPreviousOwnersTextView.setText("Number of previous owners: ");
            attributeNumberOfPreviousOwnersTextView.setText(pets.get(index).getNumberOfPreviousOwners() + "");
        } else {
            labelNumberOfPreviousOwnersTextView.setText("");
            attributeNumberOfPreviousOwnersTextView.setText("");
        }

        if (!(pets.get(index).getDescription() == "")) {
            labelDescriptionTextView.setText("Description: ");
            attributeDescriptionTextView.setText(pets.get(index).getDescription());
        } else {
            labelDescriptionTextView.setText("");
            attributeDescriptionTextView.setText("");
        }

        if (pets.get(index).getChipId() != -1) {
            labelChipIdTextView.setText("Chip ID: ");
            attributeChipIdTextView.setText(pets.get(index).getChipId() + "");
        } else {
            labelChipIdTextView.setText("");
            attributeChipIdTextView.setText("");
        }

        if (!(pets.get(index).getDisorders() == "")) {
            labelDisordersTextView.setText("Disorders: ");
            attributeDisordersTextView.setText(pets.get(index).getDisorders());
        } else {
            labelDisordersTextView.setText("");
            attributeDisordersTextView.setText("");
        }
    }
}
