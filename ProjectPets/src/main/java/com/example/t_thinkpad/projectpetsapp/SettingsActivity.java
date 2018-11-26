package com.example.t_thinkpad.projectpetsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {
    public TextView notificationsTextView, logoutTextView, emailTextView;
    public LinearLayout logoutLayout;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        findViewsAndInitialiseDatabase();
        setListeners();
        //TODO: reactivate when functions are working
        deactivateUnfinishedButtons();
    }

    private void deactivateUnfinishedButtons() {
        notificationsTextView.setEnabled(false);
    }

    public void findViewsAndInitialiseDatabase() {
        notificationsTextView = findViewById(R.id.notificationsTextView);
        logoutTextView = findViewById(R.id.logoutTextView);
        emailTextView = findViewById(R.id.emailTextView);
        logoutLayout = findViewById(R.id.logoutLayout);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null) {
            startLoginActivity();
        }
        emailTextView.setText(firebaseAuth.getCurrentUser().getEmail());

    }

    public void startLoginActivity() {
        startActivity(new Intent(this, SignupActivity.class));
        finish();
    }

    public void setListeners() {
        notificationsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotificationsActivity();
            }
        });
        logoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutAlert();
            }
        });
    }

    public void openNotificationsActivity() {
        Intent intent = new Intent(this, NotificationsActivity.class);
        startActivity(intent);
    }


    public void logOutAlert() {
        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        logOut();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void logOut() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, SignupActivity.class));
    }

}
