package com.example.t_thinkpad.projectpetsapp;

import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
        ADDMULTIPLEANIMALSFORTESTING();
        searchResultContainer.removeAllViews();
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

    public void ADDMULTIPLEANIMALSFORTESTING() {

        pets = new ArrayList<>();
        pets.add(new Pets(null, "Leon", "Dog", "Collie", 9.7, true, "Rathen", "DogGod", "medium", 0, generateDescription(), 1337, "none"));
        pets.add(new Pets(null, "Teddy", "Cat", "Shorthair mix", 3.4, true, "Mettlach", "Sarah Grünewald"));

    }

    public void ADDMULTIPLEVIEWSFORTESTING() {
        for (int i = 0; i < pets.size(); i++) {
            final int index = i;
            //Linearlayout horizontal petContainer
            LinearLayout petContainer = new LinearLayout(MyPetsActivity.this);
            petContainer.setOrientation(LinearLayout.HORIZONTAL);
            petContainer.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            petContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDetailView(index);
                }
            });
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
        Intent intent = new Intent(this, DetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("ARRAYLIST", (Serializable) pets);
        bundle.putInt("INDEX", index);
        intent.putExtra("BUNDLE", bundle);
        startActivity(intent);
    }

    public ArrayList<Pets> getPets() {
        return pets;
    }

    public String generateDescription(){
        return "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   \n" +
                "\n" +
                "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   \n" +
                "\n" +
                "Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.   \n" +
                "\n" +
                "Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat.   \n" +
                "\n" +
                "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis.   \n" +
                "\n" +
                "At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, At accusam aliquyam diam diam dolore dolores duo eirmod eos erat, et nonumy sed tempor et et invidunt justo labore Stet clita ea et gubergren, kasd magna no rebum. sanctus sea sed takimata ut vero voluptua. est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat.   \n" +
                "\n" +
                "Consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus.   \n" +
                "\n" +
                "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.   \n" +
                "\n" +
                "Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat.   \n" +
                "\n" +
                "Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo consequat. Duis autem vel eum iriure dolor in hendrerit in vulputate velit esse molestie consequat, vel illum dolore eu feugiat nulla facilisis at vero eros et accumsan et iusto odio dignissim qui blandit praesent luptatum zzril delenit augue duis dolore te feugait nulla facilisi.   \n" +
                "\n" +
                "Nam liber tempor cum soluta nobis eleifend option congue nihil imperdiet doming id quod mazim placerat facer possim assum. Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed diam nonummy nibh euismod tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi enim ad minim veniam, quis nostrud exerci tation ullamcorper suscipit lobortis nisl ut aliquip ex ea commodo";
    }
}
