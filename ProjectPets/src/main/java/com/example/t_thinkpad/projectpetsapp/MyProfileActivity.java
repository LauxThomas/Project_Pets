package com.example.t_thinkpad.projectpetsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyProfileActivity extends AppCompatActivity {
    ImageView wallpaperImageView, historyImageView, profilepictureImageView, favoritesImageView, badge1, badge2, badge3, badge4;
    EditText locationEditText, nameEditText, lookingForEditText;
    TextView mailTextView, locationTextView, lookingForTextView, badgesTextView;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private DatabaseReference ref;
    private boolean isLoaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        findViews();
        setListeners();
    }


    public void findViews() {
        wallpaperImageView = findViewById(R.id.wallpaperImageView);
        historyImageView = findViewById(R.id.historyImageView);
        profilepictureImageView = findViewById(R.id.profilepictureImageView);
        favoritesImageView = findViewById(R.id.favoritesImageView);
        badge1 = findViewById(R.id.badge1);
        badge2 = findViewById(R.id.badge2);
        badge3 = findViewById(R.id.badge3);
        badge4 = findViewById(R.id.badge4);
        mailTextView = findViewById(R.id.mailTextView);
        nameEditText = findViewById(R.id.nameEditText);
        locationEditText = findViewById(R.id.locationEditText);
        lookingForEditText = findViewById(R.id.lookingForEditText);
        locationTextView = findViewById(R.id.locationTextView);
        lookingForTextView = findViewById(R.id.lookingForTextView);
        badgesTextView = findViewById(R.id.badgesTextView);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            startLoginActivity();
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        ref = databaseReference.child("users");
        mailTextView.setText(user.getEmail().toString());
        isLoaded = false;

    }

    public void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void setListeners() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //TODO: funktioniert zwar, ist aber nicht wirklich schön. ÄNDERN! //edit: doch, sieht gut aus^^
                if (firebaseAuth.getCurrentUser() != null) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    //TODO: IF NOT NULL
                    if (dataSnapshot.child(user.getUid()).child("name").getValue() != null &&
                            dataSnapshot.child(user.getUid()).child("location").getValue() != null &&
                            dataSnapshot.child(user.getUid()).child("isLookingFor").getValue() != null) {
                        String name = dataSnapshot.child(user.getUid()).child("name").getValue().toString();
                        nameEditText.setText(name);
                        String location = dataSnapshot.child(user.getUid()).child("location").getValue().toString();
                        locationEditText.setText(location);
                        String isLookingFor = dataSnapshot.child(user.getUid()).child("isLookingFor").getValue().toString();
                        lookingForEditText.setText(isLookingFor);
                    }
                    isLoaded = true;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MyProfileActivity.this, "Whoopsie, something went wrong!", Toast.LENGTH_SHORT).show();
            }
        });


        //<editor-fold desc="TODO: Werte ändern sich nur beim focuschange, sollte auch beim klick auf den Back button funzen">
        locationEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (isLoaded) {
                    saveUserInformation();
                }
            }
        });
        nameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (isLoaded) {
                    saveUserInformation();
                }
            }
        });
        lookingForEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (isLoaded) {
                    saveUserInformation();
                }
            }
        });
        //</editor-fold>

        favoritesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMyPetsActivity();
            }
        });
        //TODO: Funktionen implementieren!
        wallpaperImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyProfileActivity.this, "Wallpaper Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        historyImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyProfileActivity.this, "history klicked", Toast.LENGTH_SHORT).show();
            }
        });
        profilepictureImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyProfileActivity.this, "profilepicture clicked", Toast.LENGTH_SHORT).show();
            }
        });
        badge1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyProfileActivity.this, "badge1 Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        badge2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyProfileActivity.this, "badge2 Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        badge3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyProfileActivity.this, "badge3 Clicked", Toast.LENGTH_SHORT).show();
            }
        });
        badge4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyProfileActivity.this, "badge4 Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void startMyPetsActivity() {
        Intent intent = new Intent(this, MyPetsActivity.class);
        startActivity(intent);
        finish();
    }

    public void saveUserInformation() {
        String name = nameEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();
        String isLookingFor = lookingForEditText.getText().toString().trim();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        UserInformation userInformation = new UserInformation(name, location, user.getEmail(), isLookingFor);
        databaseReference.child("users").child(user.getUid()).setValue(userInformation); //sollte eignentlich unter root/user/<userid> einen neuen Eintrag mit jeweiligen Attributen für die Getter vorh sind erzeugen


        //DEBUGLOGGING:
//        Log.e("LOGG USER: ",databaseReference.child("hunde").getDatabase().getReference().toString());
//        Toast.makeText(this, "Information Saved...!", Toast.LENGTH_SHORT).show();
    }

}
