package com.example.t_thinkpad.projectpetsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailedSearchResult extends AppCompatActivity implements OnMapReadyCallback{

    public ImageView pictureImageView;
    public TextView labelNameTextView, labelFamilyTextView, labelRaceTextView, labelAgeTextView, labelSexTextView, labelLocationTextView,
            labelSizeTextView, labelNumberOfPreviousOwnersTextView, labelCurrentOwnerTextView, attributeNameTextView, attributeFamilyTextView,
            attributeRaceTextView, attributeAgeTextView, attributeSexTextView, attributeLocationTextView, attributeSizeTextView,
            attributeNumberOfPreviousOwnersTextView, attributeCurrentOwnerTextView, labelDescriptionTextView, attributeDescriptionTextView,
            labelChipIdTextView, attributeChipIdTextView, labelDisordersTextView, attributeDisordersTextView, labelDistFromUserLocation, attributeDistFromUserLocation;
    private Pets pet;
    //TODO: editButton und deleteButton in der actionbar anzeigen https://medium.com/@101/android-toolbar-for-appcompatactivity-671b1d10f354

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_search_result);
        findViews();
        pet = handleIntent();
        fillViews(pet);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng petLoc = new LatLng(pet.getLatitude(), pet.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(petLoc)
                .title("Hier befindet sich "+pet.getName()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(petLoc));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(10));
    }

    private void findViews() {
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
        labelDistFromUserLocation = findViewById(R.id.labelDistFromUserLocation);
        attributeDistFromUserLocation = findViewById(R.id.attributeDistFromUserLocation);

    }

    public void fillViews(Pets pet) {
        setPicture(pictureImageView, pet);
        //pictureImageView.setImageDrawable(getD);
        labelNameTextView.setText("Name: ");
        attributeNameTextView.setText(pet.getName());
        labelFamilyTextView.setText("Family: ");
        attributeFamilyTextView.setText(pet.getFamily());
        labelRaceTextView.setText("Race: ");
        attributeRaceTextView.setText(pet.getRace());
        labelAgeTextView.setText("Age: ");
        attributeAgeTextView.setText(pet.getAge() + "");
        labelSexTextView.setText("Sex: ");
        attributeSexTextView.setText(pet.getSex());
        labelLocationTextView.setText("Location: ");
        attributeLocationTextView.setText(pet.getLocation());
        labelCurrentOwnerTextView.setText("Current owner: ");
        attributeCurrentOwnerTextView.setText(pet.getCurrentOwner());
        labelDistFromUserLocation.setText("Distance From User: ");
        attributeDistFromUserLocation.setText(pet.getDistFromUserLocation()+"");

        setOptionalViews(pet);

    }

    //TODO: auf vielfache abfragen prüfen. https://www.youtube.com/watch?v=Lb-Pnytoi-8
    private void setPicture(ImageView imageView, Pets pet) {
        // Reference to an image file in Cloud Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference folderRef = storageRef.child("pictureReferences");
        StorageReference imageRef = folderRef.child(pet.getRandomUUID() + ".jpg");
        // Download directly from StorageReference using Glide
        GlideApp.with(this /* context */)
                .load(imageRef)
                .into(imageView);
    }

    //damit SearchResultActivity nicht zerstört wird:
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

    public void setOptionalViews(Pets pet) {
        if (!(pet.getSize() == "")) {
            labelSizeTextView.setText("Size: ");
            attributeSizeTextView.setText(pet.getSize());
        } else {
            labelSizeTextView.setText("");
            attributeSizeTextView.setText("");
        }

        if (pet.getNumberOfPreviousOwners() != -1) {
            labelNumberOfPreviousOwnersTextView.setText("Number of previous owners: ");
            attributeNumberOfPreviousOwnersTextView.setText(pet.getNumberOfPreviousOwners() + "");
        } else {
            labelNumberOfPreviousOwnersTextView.setText("");
            attributeNumberOfPreviousOwnersTextView.setText("");
        }

        if (!(pet.getDescription() == "")) {
            labelDescriptionTextView.setText("Description: ");
            attributeDescriptionTextView.setText(pet.getDescription());
        } else {
            labelDescriptionTextView.setText("");
            attributeDescriptionTextView.setText("");
        }

        if (pet.getChipId() != -1) {
            labelChipIdTextView.setText("Chip ID: ");
            attributeChipIdTextView.setText(pet.getChipId() + "");
        } else {
            labelChipIdTextView.setText("");
            attributeChipIdTextView.setText("");
        }

        if (!(pet.getDisorders() == "")) {
            labelDisordersTextView.setText("Disorders: ");
            attributeDisordersTextView.setText(pet.getDisorders());
        } else {
            labelDisordersTextView.setText("");
            attributeDisordersTextView.setText("");
        }
    }

    private Pets handleIntent() {
        Intent intent = getIntent();
        Pets pet = new Pets();
        pet.setAge(Integer.parseInt(intent.getStringExtra("age")));
        pet.setChipId(Integer.parseInt(intent.getStringExtra("chipId")));
        pet.setCurrentOwner(intent.getStringExtra("currentOwner"));
        pet.setDescription(intent.getStringExtra("description"));
        pet.setDisorders(intent.getStringExtra("disorders"));
        pet.setFamily(intent.getStringExtra("family"));
        pet.setImage(intent.getStringExtra("image"));
        pet.setLocation(intent.getStringExtra("location"));

        pet.setLatitude(Double.parseDouble(intent.getStringExtra("latitude")));
        pet.setLongitude(Double.parseDouble(intent.getStringExtra("longitude")));
        //pet.setLatitude(intent.getDoubleExtra("latitude",0.0));
        //pet.setLongitude(intent.getDoubleExtra("longitude",0.0));
        pet.setDistFromUserLocation(Double.parseDouble(intent.getStringExtra("distFromUserLocation")));

        pet.setName(intent.getStringExtra("name"));
        pet.setNumberOfPreviousOwners(Integer.parseInt(intent.getStringExtra("numberOfPreviousOwners")));
        pet.setRace(intent.getStringExtra("race"));
        pet.setRandomUUID(intent.getStringExtra("randomUUID"));
        /*boolean sex = true;
        if (intent.getStringExtra("sex").contains("fem") || intent.getStringExtra("sex").contains("wei")) {
            sex = false;
        }*/
        pet.setSex(intent.getStringExtra("sex"));
        pet.setSize(intent.getStringExtra("size"));
        return pet;
    }
}
