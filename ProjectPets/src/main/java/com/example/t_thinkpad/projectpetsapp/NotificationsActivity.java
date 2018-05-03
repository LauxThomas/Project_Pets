package com.example.t_thinkpad.projectpetsapp;

import android.media.AudioManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class NotificationsActivity extends AppCompatActivity {
    public Switch notificationsSwitch, themeSwitch;
    public TextView notificationsTextView, notificationsStatusTextView, vibrationTextView,
            soundsTextView, themeTextView, themeStatusTextView;
    public CheckBox vibrationCheckBox, soundsCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        findViews();
        setListeners();
    }
    public void findViews() {
        notificationsSwitch = findViewById(R.id.notificationsSwitch);
        themeSwitch = findViewById(R.id.themeSwitch);
        notificationsTextView = findViewById(R.id.notificationsTextView);
        notificationsStatusTextView = findViewById(R.id.notificationsStatusTextView);
        vibrationTextView = findViewById(R.id.vibrationTextView);
        soundsTextView = findViewById(R.id.soundsTextView);
        themeTextView = findViewById(R.id.themeTextView);
        themeStatusTextView = findViewById(R.id.themeStatusTextView);
        vibrationCheckBox = findViewById(R.id.vibrationCheckBox);
        soundsCheckBox = findViewById(R.id.soundsCheckBox);
    }
    public void setListeners() {
        vibrationCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handleVibration();
            }
        });
        soundsCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handleSounds();
            }
        });
        notificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handleNotifications();
            }
        });
        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                handleTheme();
            }
        });

    }
    public void handleNotifications() {
        if (notificationsSwitch.isChecked()) {
            //TODO: activate notifications
        } else {
            //TODO: deactivate notifications
        }
    }
    public void handleVibration() {

        if (vibrationCheckBox.isChecked()) {
            //TODO: turn on vibration
        } else {
            //TODO: turn off vibration
        }
    }
    public void handleSounds() {
//        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (soundsCheckBox.isChecked()) {
            //TODO: turn on sounds
//            audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
        } else {
            //TODO: turn off sounds
//            audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
        }
    }
    public void handleTheme() {
        if (themeSwitch.isChecked()) {
            //TODO: Show Dark Theme
        } else {
            //TODO: Show Light Theme
        }
    }
}
