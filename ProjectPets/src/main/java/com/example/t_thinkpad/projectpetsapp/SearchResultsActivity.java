package com.example.t_thinkpad.projectpetsapp;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SearchResultsActivity extends AppCompatActivity {
    private DatabaseReference usersRef;
    private DatabaseReference petsRef;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        findViews();
        handleIntent();
//        searchThroughDatabase();


    }

    private void initializeStuff() {
        usersRef = FirebaseDatabase.getInstance().getReference().child("users");
        petsRef = FirebaseDatabase.getInstance().getReference().child("pets");


        // ref.child('users').orderByChild('name').equalTo('Alex').on('child_added',  ...)
//        System.out.println("LOG THAT SHIT: " + usersRef.);


    }

    public void findViews() {
        listView = findViewById(R.id.ListView);

    }

    public void handleIntent() {
        Intent intent = getIntent();
    ArrayList listItems = intent.getParcelableArrayListExtra("arraylist");
        System.out.println("LOGTHATSHIT2: " + listItems);
        ArrayAdapter adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                listItems);
        listView.setAdapter(adapter);

    }

    public void searchThroughDatabase() {

//        System.out.println("LOG THAT SHIT " + petsRef.orderByChild("name").equalTo("Unicorn"));
//        System.out.println("LOG THAT SHIT " + petsRef.orderByChild("name").equalTo("black"));
////        petsRef.orderByChild("name").equalTo("Unicorn")

        //in sharedPreferences speichern// UPDATE: Firebase macht das automatisch!
        // Read from the database
//        petsRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) { //is triggered once when the listener is attached and again every time the data changes, including the children.
//
//
////                HashMap<String, Object> value = (HashMap<String, Object>) dataSnapshot.getValue();
////                assert value != null;
////                for (Object t : value.values()) {
////
////                    List<String> list = Arrays.asList(t.toString().split(","));
////                    for (int i = 0; i < list.size(); i++) {
////                        if (list.get(i).contains("name=")) {
////                            System.out.println("LOG THAT SHIT: " + list.get(i).substring(6, list.get(i).length()));
////                        }
////                    }
////
////                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                // Failed to read value
//                Log.w("LOG THAT SHIT", "Failed to read value.", error.toException());
//            }
//        });

    }
}
