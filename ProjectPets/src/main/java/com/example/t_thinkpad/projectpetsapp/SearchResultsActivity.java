package com.example.t_thinkpad.projectpetsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static android.R.layout.simple_list_item_1;

public class SearchResultsActivity extends AppCompatActivity {
    private DatabaseReference usersRef;
    private DatabaseReference petsRef;
    private ListView listView;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private ArrayList arrayList = new ArrayList();
    private boolean foundItem = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        initiateData();
        findViews();
        handleIntent();
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
            public void onCallback(LinkedHashMap value) {
                try {
                    fillAdapter(value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void fillAdapter(LinkedHashMap value) throws JSONException {
        ArrayList resultArray = new ArrayList();
        resultArray.addAll(value.values());

        //TODO: extract names from resultArrayfor just showing those in the listView

        ArrayAdapter adapter;
        adapter = new

                ArrayAdapter(this,
                simple_list_item_1,
                resultArray);
        listView.setAdapter(adapter);
    }


    public void readData(final String lookupString, final MyCallback myCallback) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            int index = 0;

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LinkedHashMap<String, Object> value = new LinkedHashMap<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    String replace = ds.getValue().toString().replace("=", ":");
                    replace.replace("/", ":");

                    if (replace.contains(lookupString)
                            || replace.contains(lookupString)
                            || replace.contains(lookupString)
                            || replace.contains(lookupString)
                            || replace.contains(lookupString)
                            || replace.contains(lookupString)
                            || replace.contains(lookupString)
                            || replace.contains(lookupString)
                            || replace.contains(lookupString)
                            || replace.contains(lookupString)
                            || replace.contains(lookupString)
                            || replace.contains(lookupString)
                            ) {
                        value.put(Integer.toString(index), ds.getValue());
                        index++;
                    }
                }
                myCallback.onCallback(value);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}







