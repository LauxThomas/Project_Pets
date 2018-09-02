package com.example.t_thinkpad.projectpetsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

//TODO: Beim Klick auf den button sortiert nach Entfernung die zutreffenden Tiere anzeigen
public class SearchPetsActivity extends AppCompatActivity {
    public TextView generalSearchTextView, nameTextView;
    public SearchView generalSearchView, nameSeachView, familySearchView, raceSearchView, ageSearchView,
            sexSearchView, locationSearchView, currentOwnerSearchView, sizeSearchView,
            numberOfPreviousOwnersSearchView, descriptionSearchView,
            chipIdSearchView, disordersSearchView;

    public LinearLayout animateThis;
    //    public Button showMoreButton;
    public FloatingActionButton searchButton;
    boolean check = false;
    ArrayList arrayList = new ArrayList();

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pets);

        // show FAB above Keyboard:
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        findViews();
        initiateDatabase();
        setListeners();
//        animateThis.setTranslationX(-2500);
        //TODO: reactivate when functions are working
        deactivateNotFinishedStuff();
    }

    private void deactivateNotFinishedStuff() {
//        showMoreButton.setEnabled(false);
    }

    private void initiateDatabase() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("pets");
    }


    public void findViews() {
//        petsRef = FirebaseDatabase.getInstance().getReference().child("pets");  //Referenz auf pets
//        generalSearchTextView = findViewById(R.id.generalSearchTextView);
//        nameTextView = findViewById(R.id.nameTextView);
//        animateThis = findViewById(R.id.animateThis);
//        showMoreButton = findViewById(R.id.showMoreButton);
        searchButton = findViewById(R.id.fab);
        generalSearchView = findViewById(R.id.generalSearchSearchView);
//        nameSeachView = findViewById(R.id.nameSearchView);
//        familySearchView = findViewById(R.id.familySearchView);
//        raceSearchView = findViewById(R.id.raceSearchView);
//        ageSearchView = findViewById(R.id.ageSearchView);
//        sexSearchView = findViewById(R.id.sexSearchView);
//        locationSearchView = findViewById(R.id.locationSearchView);
//        sizeSearchView = findViewById(R.id.sizeSearchView);
//        currentOwnerSearchView = findViewById(R.id.currentOwnerSearchView);
//        numberOfPreviousOwnersSearchView = findViewById(R.id.numberOfPreviousOwnersSearchView);
//        descriptionSearchView = findViewById(R.id.descriptionSearchView);
//        chipIdSearchView = findViewById(R.id.chipIdSearchView);
//        disordersSearchView = findViewById(R.id.disordersSearchView);
    }

    public void setListeners() {
//        showMoreButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {   //TODO: entfernen?
//                if (check) {
//                    animateThis.animate().translationX(-2500);
//                    showMoreButton.setText("more");
//                } else {
//                    animateThis.animate().translationX(0);
//                    showMoreButton.setText("less");
//                }
//                check = !check;
//            }
//        });
        //TODO: Realtime aktualisierung der ergebnisse einfügen
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SearchPetsActivity.this, "suche geklickt", Toast.LENGTH_SHORT).show();
                searchDatabase();
            }
        });
    }

    private void showData(DataSnapshot dataSnapshot) throws JSONException {
        String lookupString = generalSearchView.getQuery().toString();


        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            //TODO: replace Modular aufbauen. also ds.getValue().getName() + ", " + ds.getValue().getAge() + ...
            //TODO:https://stackoverflow.com/questions/4249897/how-to-send-objects-through-bundle

            String replace = ds.getValue().toString().replace("=", ":");
            JSONObject jobj = new JSONObject();
            String value = ds.getValue().toString();
            String s = value.substring(value.indexOf("\"image\"") + 8);
            s = s.substring(0, s.indexOf(","));
            System.out.println("SOUTTEST: " + s);
            jobj.put("image", s);
            //currentOwner:
//            s = backUpString.substring(backUpString.indexOf(" currentOwner=") + 14);
//            s = s.substring(0, s.indexOf(", name="));
//            newPet.setCurrentOwner(s);

            jobj.put("age", "VALUE");
            jobj.put("chipId", "VALUE");
            jobj.put("currentOwner", "VALUE");
            jobj.put("description", "VALUE");
            jobj.put("disorders", "VALUE");
            jobj.put("family", "VALUE");
            jobj.put("location", "VALUE");
            jobj.put("name", "VALUE");
            jobj.put("numberOfPreviousOwners", "VALUE");
            jobj.put("sex", "VALUE");
            jobj.put("size", "VALUE");
            System.out.println("REPLACESTRING1: " + replace);
            replace = replace.replace("/", ":");
            System.out.println("REPLACESTRING2: " + replace);
            replace = replace.replaceAll("\\s+", "");
            System.out.println("REPLACESTRING3: " + replace);
            replace = escapeCommas(replace);
            System.out.println("REPLACESTRING4: " + replace);
            JSONObject jsonObject = new JSONObject(replace);    //TODO: Hier wird oft der Fehler geworfen!

            if (jsonObject.get("age").toString().contains(lookupString)
                    || jsonObject.get("chipId").toString().contains(lookupString)
                    || jsonObject.get("currentOwner").toString().contains(lookupString)
                    || jsonObject.get("description").toString().contains(lookupString)
                    || jsonObject.get("disorders").toString().contains(lookupString)
                    || jsonObject.get("family").toString().contains(lookupString)
                    || jsonObject.get("location").toString().contains(lookupString)
                    || jsonObject.get("name").toString().contains(lookupString)
                    || jsonObject.get("numberOfPreviousOwners").toString().contains(lookupString)
                    || jsonObject.get("race").toString().contains(lookupString)
                    || jsonObject.get("sex").toString().contains(lookupString)
                    || jsonObject.get("size").toString().contains(lookupString)
                    ) {
                arrayList.add(jsonObject);
            }

        }
        startNextActivity(lookupString);
    }

    private String escapeCommas(String replace) {
        replace = replace.replace(",race", "escapedComma" + "race");
        replace = replace.replace(",sex", "escapedComma" + "sex");
        replace = replace.replace(",description", "escapedComma" + "description");
        replace = replace.replace(",numberOfPreviousOwners", "escapedComma" + "numberOfPreviousOwners");
        replace = replace.replace(",disorders", "escapedComma" + "disorders");
        replace = replace.replace(",size", "escapedComma" + "size");
        replace = replace.replace(",image", "escapedComma" + "image");
        replace = replace.replace(",currentOwner", "escapedComma" + "currentOwner");
        replace = replace.replace(",name", "escapedComma" + "name");
        replace = replace.replace(",location", "escapedComma" + "location");
        replace = replace.replace(",randomUUID", "escapedComma" + "randomUUID");
        replace = replace.replace(",family", "escapedComma" + "family");
        replace = replace.replace(",chipId", "escapedComma" + "chipId");
        replace = replace.replace(",age", "escapedComma" + "age");
        replace = replace.replace(",", "§$%"); //TODO: reformat "§$%" into "," / Das wird aber nicht weiter benötigt
        replace = replace.replace("escapedComma", ",");
        return replace;
    }

    public void startNextActivity(String lookupString) {
        Intent intent = new Intent(this, SearchResultsActivity.class);
        String arrayString = createArrayString(arrayList);
        intent.putExtra("lookupString", lookupString);
        intent.putExtra("arrayString", arrayString);
        startActivity(intent);
    }

    private String createArrayString(ArrayList arrayList) {
        StringBuilder sb = new StringBuilder();
        for (Object o : arrayList) {
            sb.append(o);
            sb.append("\t");
        }
        return sb.toString();
    }

    private void searchDatabase() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    showData(dataSnapshot);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
