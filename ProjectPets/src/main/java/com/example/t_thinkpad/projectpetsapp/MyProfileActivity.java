package com.example.t_thinkpad.projectpetsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyProfileActivity extends AppCompatActivity {
    ImageView wallpaperImageView, historyImageView, profilepictureImageView, favoritesImageView, badge1, badge2, badge3, badge4;
    EditText  locationEditText, lookingForEditText;
    TextView usernameTextView,locationTextView, lookingForTextView, badgesTextView;
    private FirebaseAuth firebaseAuth;

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
        usernameTextView = findViewById(R.id.usernameEditText);
        locationEditText = findViewById(R.id.locationEditText);
        lookingForEditText = findViewById(R.id.lookingForEditText);
        locationTextView = findViewById(R.id.locationTextView);
        lookingForTextView = findViewById(R.id.lookingForTextView);
        badgesTextView = findViewById(R.id.badgesTextView);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            startLoginActivity();
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        usernameTextView.setText(user.getEmail().toString());

    }
    public void startLoginActivity(){
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    public void setListeners(){
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
        favoritesImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMyPetsActivity();
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
    public void startMyPetsActivity(){
        Intent intent = new Intent(this,MyPetsActivity.class);
        startActivity(intent);
    }

}
