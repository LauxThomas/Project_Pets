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
        resultArray.addAll(value.entrySet());
        System.out.println("RESULTARRAY:" + resultArray.get(1).getClass());
        Pets newPet = new Pets(resultArray.get(0));
        System.out.println("NEWPET: " + newPet.getName());

        //TODO: extract names from resultArrayfor just showing those in the listView
        //GETKEY?
        Object[] objectArray = value.entrySet().toArray();
        ArrayAdapter adapter;
        adapter = new

                ArrayAdapter(this,
                simple_list_item_1,
                resultArray);
        listView.setAdapter(adapter);
    }


    public void readData(final String lookupString, final MyCallback myCallback) {
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                LinkedHashMap<String, Object> value = new LinkedHashMap<>();
                int index = 0;
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
                        value.put(Integer.toString(index), createNewPet(ds));
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

    private Pets createNewPet(DataSnapshot ds) {
        Pets newPet = new Pets();
        String backUpString = ds.toString();
        String s;

        System.out.println("BACKUPSTRING: " + backUpString);

        //age:
        s = backUpString.substring(backUpString.indexOf(" age=") + 5);
        s = s.substring(0, s.indexOf("}"));
        newPet.setAge(Double.parseDouble(s));

        //chipId:
        s = backUpString.substring(backUpString.indexOf(" chipId=") + 8);
        s = s.substring(0, s.indexOf(","));
        newPet.setChipId(Integer.parseInt(s));

        //currentOwner:
        s = backUpString.substring(backUpString.indexOf(" currentOwner=") + 14);
        s = s.substring(0, s.indexOf(","));
        newPet.setCurrentOwner(s);

        //description:
        s = backUpString.substring(backUpString.indexOf(" description=") + 13);
        s = s.substring(0, s.indexOf(","));
        newPet.setDescription(s);

        //disorders:
        s = backUpString.substring(backUpString.indexOf(" disorders=") + 11);
        s = s.substring(0, s.indexOf(","));
        newPet.setDisorders(s);

        //family:
        s = backUpString.substring(backUpString.indexOf(" family=") + 8);
        s = s.substring(0, s.indexOf(","));
        newPet.setFamily(s);

        //image:
        s = backUpString.substring(backUpString.indexOf(" image=") + 7);
        s = s.substring(0, s.indexOf(","));
        newPet.setCurrentOwner(s);

        //location:
        s = backUpString.substring(backUpString.indexOf(" location=") + 10);
        s = s.substring(0, s.indexOf(","));
        newPet.setCurrentOwner(s);

        //name:
        s = backUpString.substring(backUpString.indexOf(" name=") + 6);
        s = s.substring(0, s.indexOf(","));
        newPet.setName(s);

        //numberOfPreviousOwners:
        s = backUpString.substring(backUpString.indexOf(" numberOfPreviousOwners=") + 24);
        s = s.substring(0, s.indexOf(","));
        newPet.setNumberOfPreviousOwners(Integer.parseInt(s));

        //race:
        s = backUpString.substring(backUpString.indexOf(" race=") + 6);
        s = s.substring(0, s.indexOf(","));
        newPet.setRace(s);

        //sex:
        s = backUpString.substring(backUpString.indexOf(" sex=") + 5);
        s = s.substring(0, s.indexOf(","));
        newPet.setCurrentOwner(s);

        //size:
        s = backUpString.substring(backUpString.indexOf(" size=") + 6);
        s = s.substring(0, s.indexOf(","));
        newPet.setCurrentOwner(s);

        return newPet;
    }
}







