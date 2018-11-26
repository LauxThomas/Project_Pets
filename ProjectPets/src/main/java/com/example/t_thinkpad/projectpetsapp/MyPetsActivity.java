package com.example.t_thinkpad.projectpetsapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class MyPetsActivity extends AppCompatActivity {


    public ArrayList<Pets> pets;
    public LinearLayout searchResultContainer, petContainer, firstPart, secondPart;
    public TextView petNameTextView, familyTextView, raceTextView, ageTextView, sexTextView, locationTextView, dateAddedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);
        findViews();
        addDummypets();
        searchResultContainer.removeAllViews();
        addDummyviews();
    }

    public void findViews() {
        searchResultContainer = findViewById(R.id.searchResultContainer);
        petContainer = findViewById(R.id.petContainer);
        firstPart = findViewById(R.id.firstPart);
        secondPart = findViewById(R.id.secondPart);
        petNameTextView = findViewById(R.id.petNameTextView);
        familyTextView = findViewById(R.id.familyTextView);
        raceTextView = findViewById(R.id.raceTextView);
        ageTextView = findViewById(R.id.ageTextView);
        sexTextView = findViewById(R.id.sexTextView);
        locationTextView = findViewById(R.id.locationTextView);
        dateAddedTextView = findViewById(R.id.dateAddedTextView);
    }

    public void addDummypets() {
        pets = new ArrayList<>();
        Pets pet1 = new Pets();
        pet1.setImage("");
        pet1.setName("pet1");
        pet1.setFamily("Dogs");
        pet1.setRace("Dalamatian");
        pet1.setAge(6);
        pet1.setSex("Male");
        pet1.setLocation("Here");
        pet1.setCurrentOwner("Me");
        pet1.setLatitude(0.0f);
        pet1.setLongitude(0.0f);

        Pets pet2 = new Pets();
        pet2.setImage("");
        pet2.setName("pet1");
        pet2.setFamily("Dogs");
        pet2.setRace("Dalamatian");
        pet2.setAge(6);
        pet2.setSex("Male");
        pet2.setLocation("Here");
        pet2.setCurrentOwner("Me");
        pet2.setLatitude(0.0f);
        pet2.setLongitude(0.0f);


        pets.add(pet1);
        pets.add(pet2);
    }

    public void addDummyviews() {
        for (int i = 0; i < pets.size(); i++) {
            final int index = i;
            LinearLayout petContainer = new LinearLayout(MyPetsActivity.this);
            petContainer.setOrientation(LinearLayout.HORIZONTAL);
            petContainer.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            petContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startDetailedSearchResultActivity(pets.get(index));
                }
            });
            ImageView imageView = new ImageView(MyPetsActivity.this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            //LinearLayout Vertical firstPart
            LinearLayout firstPart = new LinearLayout(MyPetsActivity.this);
            firstPart.setOrientation(LinearLayout.VERTICAL);
            firstPart.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            //TextView petNameTextView
            TextView petNameTextView = new TextView(MyPetsActivity.this);
            petNameTextView.setText(pets.get(i).getName());
            petNameTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            //LinearLayout horizontal secondPart
            LinearLayout secondPart = new LinearLayout(MyPetsActivity.this);
            secondPart.setOrientation(LinearLayout.HORIZONTAL);
            secondPart.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            //TextView familyTextView
            TextView familyTextView = new TextView(MyPetsActivity.this);
            familyTextView.setText(pets.get(i).getFamily());
            familyTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            //TextView raceTextView
            TextView raceTextView = new TextView(MyPetsActivity.this);
            raceTextView.setText(pets.get(i).getRace());
            raceTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            //TextView ageTextView
            TextView ageTextView = new TextView(MyPetsActivity.this);
            ageTextView.setText("" + pets.get(i).getAge());
            ageTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            //TextView sexTextView
            TextView sexTextView = new TextView(MyPetsActivity.this);
            sexTextView.setText(pets.get(i).getSex());
            sexTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            //TextView locationTextView
            TextView locationTextView = new TextView(MyPetsActivity.this);
            locationTextView.setText(pets.get(i).getLocation());
            locationTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            //TextView dateAddedTextView
            TextView dateAddedTextView = new TextView(MyPetsActivity.this);
            dateAddedTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            //Create Dividers:
            View divider = new View(this);
            divider.setMinimumHeight(7);
            divider.setMinimumWidth(LinearLayout.LayoutParams.MATCH_PARENT);
            divider.setBackgroundColor(Color.BLACK);

            secondPart.addView(familyTextView);
            secondPart.addView(raceTextView);
            secondPart.addView(ageTextView);
            secondPart.addView(sexTextView);
            secondPart.addView(locationTextView);
            secondPart.addView(dateAddedTextView);
            firstPart.addView(petNameTextView);
            firstPart.addView(secondPart);
            petContainer.addView(imageView);
            petContainer.addView(firstPart);
            searchResultContainer.addView(petContainer);
            searchResultContainer.addView(divider);
        }
    }

    public void openDetailView(int index) {
        Intent intent = new Intent(this, DetailedSearchResult.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ARRAYLIST", (Serializable) pets);
        bundle.putInt("INDEX", index);
        intent.putExtra("BUNDLE", bundle);
        startActivity(intent);
    }

    public ArrayList<Pets> getPets() {
        return pets;
    }

    private void startDetailedSearchResultActivity(Pets pet){
        Intent intent = new Intent(this, DetailedSearchResult.class);
        intent.putExtra("age", "" + pet.getAge());
        intent.putExtra("chipId", "" + pet.getChipId());
        intent.putExtra("currentOwner", pet.getCurrentOwner());
        intent.putExtra("description", pet.getDescription());
        intent.putExtra("disorders", pet.getDisorders());
        intent.putExtra("family", pet.getFamily());
        intent.putExtra("image", pet.getImage());
        intent.putExtra("location", pet.getLocation());
        intent.putExtra("latitude", ""+pet.getLatitude());
        intent.putExtra("longitude", ""+pet.getLongitude());
        intent.putExtra("distFromUserLocation",""+pet.getDistFromUserLocation());
        intent.putExtra("name", pet.getName());
        intent.putExtra("numberOfPreviousOwners", "" + pet.getNumberOfPreviousOwners());
        intent.putExtra("race", pet.getRace());
        intent.putExtra("randomUUID", pet.getRandomUUID());
        intent.putExtra("sex", pet.getSex());
        intent.putExtra("size", pet.getSize());
        intent.putExtra("wholePet", pet);
        this.startActivity(intent);
    }
}
