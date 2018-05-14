package com.example.t_thinkpad.projectpetsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenuShelter extends AppCompatActivity {

    Button settingsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_shelter);
        findViewsAndInitializeStuff();
        setListeners();
    }
    public void findViewsAndInitializeStuff(){
        settingsButton = findViewById(R.id.settingsButton);
    }


    public void setListeners(){
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSettingsActivity();
            }
        });
    }

    public void startSettingsActivity(){
        startActivity(new Intent(this,SettingsActivity.class));
        finish();
    }

}
