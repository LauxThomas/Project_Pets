package com.example.t_thinkpad.projectpetsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    public ImageView pictureImageView;
    public TextView labelNameTextView, labelFamilyTextView, labelRaceTextView, labelAgeTextView, labelSexTextView, labelLocationTextView, labelSizeTextView, labelNumberOfPreviousOwnersTextView, labelCurrentOwnerTextView, attributeNameTextView, attributeFamilyTextView, attributeRaceTextView, attributeAgeTextView, attributeSexTextView, attributeLocationTextView, attributeSizeTextView, attributeNumberOfPreviousOwnersTextView, attributeCurrentOwnerTextView, labelDescriptionTextView, attributeDescriptionTextView, labelChipIdTextView, attributeChipIdTextView, labelDiseasesTextView, attributeDiseasesTextView;
    int index;
    public ArrayList<Pets> pets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        findViews();
        handleIntent();
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
        labelDiseasesTextView = findViewById(R.id.labelDiseasesTextView);
        attributeDiseasesTextView = findViewById(R.id.attributeDiseasesTextView);
    }

    public void handleIntent() {
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BUNDLE");
        pets = (ArrayList<Pets>) bundle.getSerializable("ARRAYLIST");
        index = bundle.getInt("INDEX");
    }

    public void setViews() {
//        pictureImageView.setImageDrawable(getD);
        labelNameTextView.setText("Name: ");
        labelFamilyTextView.setText("Family: ");
        labelRaceTextView.setText("Race: ");
        labelAgeTextView.setText("Age: ");
        labelSexTextView.setText("Sex: ");
        labelLocationTextView.setText("Location: ");
        labelSizeTextView.setText("Size: ");
        labelNumberOfPreviousOwnersTextView.setText("Number of previous owners: ");
        labelCurrentOwnerTextView.setText("Current owner: ");
        attributeNameTextView.setText(pets.get(index).getName());
        attributeFamilyTextView.setText(pets.get(index).getFamily());
        attributeRaceTextView.setText(pets.get(index).getRace());
        attributeAgeTextView.setText(pets.get(index).getAge() + "");
        attributeSexTextView.setText(pets.get(index).getSex());
        attributeLocationTextView.setText(pets.get(index).getLocation());
        attributeSizeTextView.setText(pets.get(index).getSize());
        attributeNumberOfPreviousOwnersTextView.setText(pets.get(index).getNumberOfPreviousOwners() + "");
        attributeCurrentOwnerTextView.setText(pets.get(index).getCurrentOwner());
        labelDescriptionTextView.setText("Description: ");
        attributeDescriptionTextView.setText(pets.get(index).getDescription());
        labelChipIdTextView.setText("Chip ID: ");
        attributeChipIdTextView.setText(pets.get(index).getChipId()+"");
        labelDiseasesTextView.setText("Diseases: ");
        attributeDiseasesTextView.setText(pets.get(index).getDisorders());


    }
}
