package com.example.t_thinkpad.projectpetsapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddPetsActivity extends AppCompatActivity {
    ImageView imageView;
    EditText nameEditText, familyEditText, raceEditText, ageEditText,
            sexEditText, locationEditText, currentOwnerEditText, sizeEditText,
            numberOfPreviousOwnersEditText, descriptionEditText, chipIdEditText, disordersEditText;
    Button addPetButton;
    private static int RESULT_LOAD_IMAGE = 1;
    Bitmap selectedImage;
    Uri file,mImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference ref;
    private StorageReference mStorageRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pets);
        findViews();
        initializeStuff();
        setListeners();
    }

    private void initializeStuff() {
        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("pets");  //petsreferenz
    }


    private void setListeners() {
        addPetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewPet();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleImageStuff();
            }
        });
    }

    private void handleImageStuff() {

//        //Auswählen aus Gallery:
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(intent, PICK_IMAGE_REQUEST);


        //Auswählen mit Kamera;
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
    }


    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (reqCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();

            Picasso.with(this).load(mImageUri).into(imageView);
        }

    }


    private void createNewPet() {
        //TODO: Image aus Gallery oder Camera
        String name = nameEditText.getText().toString();
        String family = familyEditText.getText().toString();
        String race = raceEditText.getText().toString();
        //TODO: check if ageEditText is ""
        double age;
        if (ageEditText.getText().toString().equals("")) {
            age = 0;
        } else {
            age = Double.parseDouble(ageEditText.getText().toString());
        }
        //TODO: Das kann man bestimmt eleganter machen...
        Boolean sex;
        if (sexEditText.getText().toString().contains("män")
                || sexEditText.getText().toString().contains("maen")
                || !sexEditText.getText().toString().contains("fem")
                || !sexEditText.getText().toString().contains("weib")
                || sexEditText.getText().toString().contains("rüd")
                || !sexEditText.getText().toString().contains("hün")
                || !sexEditText.getText().toString().contains("katz")
                || sexEditText.getText().toString().contains("kater")) {
            sex = true;
        } else {
            sex = false;
        }
        String location = locationEditText.getText().toString();
        String currentOwner = currentOwnerEditText.getText().toString();

        String size = sizeEditText.getText().toString();
        int numberOfPreviousOwners;
        if (numberOfPreviousOwnersEditText.getText().toString().equals("")) {
            numberOfPreviousOwners = 0;
        } else {
            numberOfPreviousOwners = Integer.parseInt(numberOfPreviousOwnersEditText.getText().toString());
        }
        String description = descriptionEditText.getText().toString();
        int chipId;
        if (chipIdEditText.getText().toString().equals("")) {
            chipId = 0;
        } else {
            chipId = Integer.parseInt(chipIdEditText.getText().toString());
        }
        String disorders = disordersEditText.getText().toString();
        Pets newPet = new Pets(/*image*/ file, name, family, race, age, sex, location, currentOwner);   //Lege neues Tier an
        //füge Optionals hinzu:
        if (!size.equals("")) {
            newPet.setSize(size);
        }
        if (numberOfPreviousOwners != -1) {
            newPet.setNumberOfPreviousOwners(numberOfPreviousOwners);
        }
        if (!description.equals("")) {
            newPet.setDescription(description);
        }
        if (chipId != -1) {
            newPet.setChipId(chipId);
        }
        if (!disorders.equals("")) {
            newPet.setDisorders(disorders);
        }
        ref.child(newPet.getName()).setValue(newPet);


    }


    private void findViews() {
        imageView = findViewById(R.id.imageView);
        nameEditText = findViewById(R.id.nameEditText);
        familyEditText = findViewById(R.id.familyEditText);
        raceEditText = findViewById(R.id.raceEditText);
        ageEditText = findViewById(R.id.ageEditText);
        sexEditText = findViewById(R.id.sexEditText);
        locationEditText = findViewById(R.id.locationEditText);
        currentOwnerEditText = findViewById(R.id.currentOwnerEditText);
        sizeEditText = findViewById(R.id.sizeEditText);
        numberOfPreviousOwnersEditText = findViewById(R.id.numberOfPreviousOwnersEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        chipIdEditText = findViewById(R.id.chipIdEditText);
        disordersEditText = findViewById(R.id.disordersEditText);
        addPetButton = findViewById(R.id.addPetButton);
    }
}
