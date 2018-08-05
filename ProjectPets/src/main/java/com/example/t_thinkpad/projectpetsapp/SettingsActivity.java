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
    public TextView notificationsTextView, deleteSearchHistoryTextView, logoutTextView, emailTextView;
    public LinearLayout logoutLayout;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        findViewsAndInitializeStuff();
        setListeners();
    }

    public void findViewsAndInitializeStuff() {
        notificationsTextView = findViewById(R.id.notificationsTextView);
        deleteSearchHistoryTextView = findViewById(R.id.deleteSearchHistoryTextView);
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
        startActivity(new Intent(this, LoginActivity.class));
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
        deleteSearchHistoryTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteHistoryAlert();
            }
        });
    }

    public void openNotificationsActivity() {
        Intent intent = new Intent(this, NotificationsActivity.class);
        startActivity(intent);
    }


    //call lagOutAlert(); from listener on Button or sth
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
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void logOut() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void showDeleteHistoryAlert() {

        AlertDialog.Builder builder;
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete search history")
                .setMessage("Are you sure you want to delete your search history?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteHistory();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    public void deleteHistory() {   //TODO: Dafür müsste erstmal ne History angelegt werden. Entfernen?
        //TODO: History löschen
        Toast.makeText(this, "History deleted!", Toast.LENGTH_SHORT).show();
    }


}
