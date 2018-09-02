package com.example.t_thinkpad.projectpetsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

public class SearchResultsActivity extends AppCompatActivity {
    private ListView listView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        initiateData();
        findViews();
        handleIntent();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initiateData() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("pets");
    }

    public void findViews() {
        listView = findViewById(R.id.ListView);
    }

    public void handleIntent() {
        Intent intent = getIntent();
        String lookupString = intent.getStringExtra("lookupString");
        handleDatabaseStuff(lookupString);
    }

    private void handleDatabaseStuff(final String lookupString) {
        readData(lookupString, new MyCallback() {
            @Override
            public void onCallback(Pets[] pets) {
                fillAdapter(pets);
            }
        });
    }

    private void fillAdapter(final Pets[] pets) {
        //TODO: extract names from resultArrayfor just showing those in the listView (toString in Pets umschreiben)
        listView.setAdapter(new ArrayAdapter(this, simple_list_item_1, pets));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startNextActivity(pets[position]);
            }
        });
    }

    private void startNextActivity(Pets pet) {
        Intent intent = new Intent(this, DetailedSearchResult.class);
        intent.putExtra("age", "" + pet.getAge());
        intent.putExtra("chipId", "" + pet.getChipId());
        intent.putExtra("currentOwner", pet.getCurrentOwner());
        intent.putExtra("description", pet.getDescription());
        intent.putExtra("disorders", pet.getDisorders());
        intent.putExtra("family", pet.getFamily());
        intent.putExtra("image", pet.getImage());
        intent.putExtra("location", pet.getLocation());
        intent.putExtra("name", pet.getName());
        intent.putExtra("numberOfPreviousOwners", "" + pet.getNumberOfPreviousOwners());
        intent.putExtra("race", pet.getRace());
        intent.putExtra("randomUUID", pet.getRandomUUID());
        intent.putExtra("sex", pet.getSex());
        intent.putExtra("size", pet.getSize());
        intent.putExtra("wholePet", pet);
        startActivity(intent);
    }


    public void readData(final String lookupString, final MyCallback myCallback) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Pets> petsArrayList = new ArrayList<Pets>();
                int index = 0;
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    Pets pet = ds.getValue(Pets.class);

                    System.out.println("PETSOUTNAME: " + pet.getName());
                    String replace = ds.getValue().toString().replace("=", ":");
                    System.out.println("REPLACESTRING: " + replace);
                    replace.replace("/", ":");
                    //TODO: Image einbinden https://stackoverflow.com/a/39708645

                    if (replace.contains(lookupString)) {
                        petsArrayList.add(pet);
                        index++;
                    }
                }
                if (index == 0) {
                    Toast.makeText(SearchResultsActivity.this, "nothing found for your parameters", Toast.LENGTH_SHORT).show();
                    return;
                }
                Pets[] pets = new Pets[petsArrayList.size()];
                for (int i = 0; i < petsArrayList.size(); i++) {
                    pets[i] = petsArrayList.get(i);
                }
                myCallback.onCallback(pets);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}







