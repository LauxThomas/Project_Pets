package com.example.t_thinkpad.projectpetsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MyPetsActivity extends AppCompatActivity {
    public ArrayList<Entries> pets;
    public LinearLayout searchResultContainer,petContainer, firstPart, secondPart;
    public TextView petNameTextView, familyTextView, raceTextView, ageTextView, sexTextView, locationTextView, dateAddedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_pets);
        findViews();
        ADDMULTIPLEANIMALSFORTESTING();
        ADDMULTIPLEVIEWSFORTESTING();
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

    public void ADDMULTIPLEANIMALSFORTESTING(){
        pets = new ArrayList<>();
        Entries p0 = new Entries("Hasso", "Dog", "Dalmatian", 5.3, true, "Dillingen");
        pets.add(p0);
        Entries p1 = new Entries("Rex", "Dog", "German Shepperd", 7, true, "Berlin");
        pets.add(p1);
        Entries p2 = new Entries("Leon", "Dog", "Collie", 9.7, true, "Rathen");
        pets.add(p2);
        Entries p3 = new Entries("Mausi", "Cat", "Housecat", 4, false, "Mettlach");
        pets.add(p3);
        Entries p4 = new Entries("Teddy", "Cat", "Shorthair mix", 3, true, "Mettlach");
        pets.add(p4);
        Entries p5 = new Entries("Miffi", "Rabbit", "dwarf rabbit", 3.2, false, "Mettlach");
        pets.add(p5);
        //Tests für Scrollview: funktioniert auch btw!
//        Entries p6 = new Entries("Miffi", "Rabbit", "dwarf rabbit", 3.2, false, "Mettlach");
//        pets.add(p6);
//        Entries p7 = new Entries("Miffi", "Rabbit", "dwarf rabbit", 3.2, false, "Mettlach");
//        pets.add(p7);
//        Entries p8 = new Entries("Miffi", "Rabbit", "dwarf rabbit", 3.2, false, "Mettlach");
//        pets.add(p8);
//        Entries p9 = new Entries("Miffi", "Rabbit", "dwarf rabbit", 3.2, false, "Mettlach");
//        pets.add(p9);
//        Entries p10 = new Entries("Miffi", "Rabbit", "dwarf rabbit", 3.2, false, "Mettlach");
//        pets.add(p10);
//        Entries p11 = new Entries("Miffi", "Rabbit", "dwarf rabbit", 3.2, false, "Mettlach");
//        pets.add(p11);
//        Entries p12 = new Entries("Miffi", "Rabbit", "dwarf rabbit", 3.2, false, "Mettlach");
//        pets.add(p12);
//        Entries p13 = new Entries("Miffi", "Rabbit", "dwarf rabbit", 3.2, false, "Mettlach");
//        pets.add(p13);
//        Entries p14 = new Entries("Miffi", "Rabbit", "dwarf rabbit", 3.2, false, "Mettlach");
//        pets.add(p14);
//        Entries p15 = new Entries("Miffi", "Rabbit", "dwarf rabbit", 3.2, false, "Mettlach");
//        pets.add(p15);
//        Entries p16 = new Entries("Miffi", "Rabbit", "dwarf rabbit", 3.2, false, "Mettlach");
//        pets.add(p16);
//        Entries p17 = new Entries("Miffi", "Rabbit", "dwarf rabbit", 3.2, false, "Mettlach");
//        pets.add(p17);

    }

    public void ADDMULTIPLEVIEWSFORTESTING(){
        for (int i = 0; i < pets.size(); i++) {
            //Linearlayout horizontal petContainer
            LinearLayout petContainer = new LinearLayout(MyPetsActivity.this);
            petContainer.setOrientation(LinearLayout.HORIZONTAL);
            petContainer.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
                //ImageView ImageView
            //TODO: BILDER EINFÜGEN!
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
            ageTextView.setText(""+pets.get(i).getAge());
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
        }
    }
}
