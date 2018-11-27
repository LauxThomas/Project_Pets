package com.example.t_thinkpad.projectpetsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyProfileActivity extends AppCompatActivity {
    ImageView favoritesImageView;
    EditText nameEditText;
    TextView mailTextView;
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
        favoritesImageView = findViewById(R.id.favoritesImageView);
        mailTextView = findViewById(R.id.mailTextView);
        nameEditText = findViewById(R.id.nameEditText);
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
        startActivity(new Intent(this, SignupActivity.class));
        finish();
    }

    public void setListeners() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (firebaseAuth.getCurrentUser() != null) {
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    if (dataSnapshot.child(user.getUid()).child("name").getValue() != null &&
                            dataSnapshot.child(user.getUid()).child("location").getValue() != null &&
                            dataSnapshot.child(user.getUid()).child("isLookingFor").getValue() != null) {
                        String name = dataSnapshot.child(user.getUid()).child("name").getValue().toString();
                        nameEditText.setText(name);
                    }
                    isLoaded = true;
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MyProfileActivity.this, "Whoopsie, something went wrong!", Toast.LENGTH_SHORT).show();
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
        //</editor-fold>

        favoritesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMyPetsActivity();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            saveUserInformation();
            return true;
        }
        return false;
    }

    public void startMyPetsActivity() {
        Intent intent = new Intent(this, SearchResultsActivity.class);
        intent.putExtra("showFavorites", true);
        startActivity(intent);
        finish();
    }

    public void saveUserInformation() {
        String name = nameEditText.getText().toString().trim();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        UserInformation userInformation = new UserInformation(name, "", user.getEmail(), "");
        databaseReference.child("users").child(user.getUid()).setValue(userInformation); //sollte eignentlich unter root/user/<userid> einen neuen Eintrag mit jeweiligen Attributen für die Getter vorh sind erzeugen


        //DEBUGLOGGING:
//        Log.e("LOGG USER: ",databaseReference.child("hunde").getDatabase().getReference().toString());
//        Toast.makeText(this, "Information Saved...!", Toast.LENGTH_SHORT).show();
    }

}
