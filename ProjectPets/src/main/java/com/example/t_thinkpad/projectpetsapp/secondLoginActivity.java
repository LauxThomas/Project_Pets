package com.example.t_thinkpad.projectpetsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class secondLoginActivity extends AppCompatActivity {
    Button signinButton;
    EditText mailEditText, passwordEditText;
    TextView userLogin, textViewSignUp;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    DatabaseReference databaseReference;

    private boolean isAnimalShelter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_login);
        findViewsAndInitializeStuff();
        setListeners();
    }

    public void findViewsAndInitializeStuff() {
        signinButton = findViewById(R.id.signinButton);
        mailEditText = findViewById(R.id.mailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        textViewSignUp = findViewById(R.id.textViewSignUp);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            startMainMenuActivity();
        }
        progressDialog = new ProgressDialog(this);
    }

    public void setListeners() {
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startLoginActivity();
            }
        });
    }

    public void startLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    private void userLogin() {
        String email = mailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            //email is empty
            Toast.makeText(this, "Enter you Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            //password is empty
            Toast.makeText(this, "Enter you password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Registerung User...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            startMainMenuActivity();
                        } else {
                            Toast.makeText(secondLoginActivity.this, "oh, something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void startMainMenuActivity() {
        //TESTWEISE ISTS DIE PROFILE ACTIVITY
        startActivity(new Intent(getApplicationContext(), MainMenu.class));
//        startActivity(new Intent(getApplicationContext(), MyProfileActivity.class));
        finish();
    }
}
