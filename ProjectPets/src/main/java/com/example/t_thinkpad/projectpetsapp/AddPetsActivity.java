package com.example.t_thinkpad.projectpetsapp;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
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
    Uri file, mImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;


    private FirebaseAuth firebaseAuth;
    private DatabaseReference ref, mDatabaseRef;
    private StorageTask mUploadTask;
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
        mStorageRef = FirebaseStorage.getInstance().getReference("pictureReferences");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("pictureReferences");
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

            if (mUploadTask!=null && mUploadTask.isInProgress()){
                Toast.makeText(this, "Upload in progress", Toast.LENGTH_SHORT).show();
            }else{
                uploadFile();
            }
        }

    }

    private String getFileExtention(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtention(mImageUri));


            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
//                                    progressBar.setProgress(0);
                                }
                            }, 2000);

                            Toast.makeText(AddPetsActivity.this, "Imageupload successful", Toast.LENGTH_SHORT).show();
                            Upload upload = new Upload(firebaseAuth.getCurrentUser().getEmail(),
                                    taskSnapshot.getDownloadUrl().toString());
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddPetsActivity.this, "Imageupload failed!", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
//                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());

                        }
                    });
        } else {
            Toast.makeText(this, "no Image selected!", Toast.LENGTH_SHORT).show();
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
