package com.example.t_thinkpad.projectpetsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DetailedSearchResult extends AppCompatActivity implements OnMapReadyCallback {

    public ImageView pictureImageView;
    public TextView labelNameTextView, labelFamilyTextView, labelRaceTextView, labelAgeTextView, labelSexTextView, labelLocationTextView,
            labelSizeTextView, labelNumberOfPreviousOwnersTextView, labelCurrentOwnerTextView, attributeNameTextView, attributeFamilyTextView,
            attributeRaceTextView, attributeAgeTextView, attributeSexTextView, attributeLocationTextView, attributeSizeTextView,
            attributeNumberOfPreviousOwnersTextView, attributeCurrentOwnerTextView, labelDescriptionTextView, attributeDescriptionTextView,
            labelChipIdTextView, attributeChipIdTextView, labelDisordersTextView, attributeDisordersTextView, labelDistFromUserLocation, attributeDistFromUserLocation;
    private Pets pet;
    //TODO: editButton und deleteButton in der actionbar anzeigen https://medium.com/@101/android-toolbar-for-appcompatactivity-671b1d10f354
    //TODO: fav icon anzeigen, wenn nutzer, die beiden adneren, wenn nicht
    //private Toolbar mTopToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_search_result);
        findViews();
        pet = getSelectedPet();
        fillViews(pet);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final boolean isShelter = false;
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref = ref.child("users");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (firebaseAuth.getCurrentUser() != null) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (dataSnapshot.child(user.getUid()).child("isAnimalShelter").getValue() != null
                            && dataSnapshot.child(user.getUid()).child("isAnimalShelter").getValue().toString().equals("true")) {
                        System.out.println("ANGEMELDET ALS SHELTER");
                        MenuItem item = menu.findItem(R.id.action_favorite);
                        item.setVisible(false);
                    } else {
                        System.out.println("ANGEMELDET ALS PRIVATNUTZER");
                        MenuItem item = menu.findItem(R.id.action_edit);
                        item.setVisible(false);
                        item = menu.findItem(R.id.action_delete);
                        item.setVisible(false);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        if (isShelter) {
//            MenuItem item = menu.findItem(R.id.action_favorite);
//            item.setVisible(false);
//        } else {
//            MenuItem item = menu.findItem(R.id.action_edit);
//            item.setVisible(false);
//            item = menu.findItem(R.id.action_delete);
//            item.setVisible(false);
//        }
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_detailed_search_results, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_favorite) {
            Toast.makeText(DetailedSearchResult.this, "fav clicked", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_delete) {
            Toast.makeText(DetailedSearchResult.this, "delete clicked", Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.action_edit) {
            Toast.makeText(DetailedSearchResult.this, "edit clicked", Toast.LENGTH_LONG).show();
            return true;
        } else if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng petLoc = new LatLng(pet.getLatitude(), pet.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(petLoc)
                .title("Here is " + pet.getName()));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(petLoc));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(10));
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(petLoc, 10);
        googleMap.animateCamera(cameraUpdate);
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

        //mTopToolbar = (Toolbar) findViewById(R.id.action_favorite);
        //setSupportActionBar(mTopToolbar);

    }

    public void fillViews(Pets pet) {
        setPicture(pictureImageView, pet);
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
        attributeDistFromUserLocation.setText(pet.getDistFromUserLocation() + "");

        setOptionalViews(pet);

    }

    private void setPicture(ImageView imageView, Pets pet) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference folderRef = storageRef.child("pictureReferences");
        StorageReference imageRef = folderRef.child(pet.getRandomUUID() + ".jpg");
        GlideApp.with(this)
                .load(imageRef)
                .into(imageView);
    }

    //damit SearchResultActivity nicht zerstört wird:
    //TODO: wiederherstellen:
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            onBackPressed();
//            return true;
//        }
//        return false;
//    }

    public void setOptionalViews(Pets pet) {
        if (!(pet.getSize().equals(""))) {
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

        if (!(pet.getDescription().equals(""))) {
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

        if (!(pet.getDisorders().equals(""))) {
            labelDisordersTextView.setText("Disorders: ");
            attributeDisordersTextView.setText(pet.getDisorders());
        } else {
            labelDisordersTextView.setText("");
            attributeDisordersTextView.setText("");
        }
    }

    private Pets getSelectedPet() {
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
        pet.setDistFromUserLocation(Double.parseDouble(intent.getStringExtra("distFromUserLocation")));

        pet.setName(intent.getStringExtra("name"));
        pet.setNumberOfPreviousOwners(Integer.parseInt(intent.getStringExtra("numberOfPreviousOwners")));
        pet.setRace(intent.getStringExtra("race"));
        pet.setRandomUUID(intent.getStringExtra("randomUUID"));
        pet.setSex(intent.getStringExtra("sex"));
        pet.setSize(intent.getStringExtra("size"));
        return pet;
    }
}
