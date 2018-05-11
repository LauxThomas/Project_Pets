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

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText mailEditText, passwordEditText;
    Button registerButton;
    TextView textViewSignin;

    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        findViewsAndInitializeStuff();
        setListeners();
    }

    public void findViewsAndInitializeStuff() {


        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            startMainMenuActivity();
        }


        progressDialog = new ProgressDialog(this);
        mailEditText = findViewById(R.id.mailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);
        textViewSignin = findViewById(R.id.textViewSignin);
    }

    public void setListeners() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
        textViewSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //will open login Activity
                startSecondLoginActivity();
            }
        });
    }

    public void registerUser() {
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

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startMainMenuActivity();
                } else {
                    Toast.makeText(LoginActivity.this, "registration failed", Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();   //stoppt den ProgressDialog
            }
        });

    }

    public void startSecondLoginActivity(){
        startActivity(new Intent(this,secondLoginActivity.class));
        finish();
    }
    public void startMainMenuActivity(){
        finish();
        startActivity(new Intent(getApplicationContext(),MainMenu.class));
    }
}
