package com.example.t_thinkpad.projectpetsapp;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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

import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class AddPetsActivity extends AppCompatActivity {
    ImageView imageView;
    EditText nameEditText, familyEditText, raceEditText, ageEditText,
            sexEditText, locationEditText, currentOwnerEditText, sizeEditText,
            numberOfPreviousOwnersEditText, descriptionEditText, chipIdEditText, disordersEditText;
    Spinner sexSpinner, ageSpinner, numOfPreviousOwnersSpinner;
    Button addPetButton;
    Uri mImageUri;
    static final int REQUEST_PICK_IMAGE = 1;
    static final int REQUEST_CAPTURE_IMAGE = 100;
    static final int REQUEST_IMAGE_CAPTURE = 123;


    private FirebaseAuth firebaseAuth;
    private DatabaseReference ref, mDatabaseRef;
    private StorageTask mUploadTask;
    private StorageReference mStorageRef;
    private String randomUUID;

    private String encodedPhoto = "nicht BASE64 decodiert";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pets);
        findViews();
        initializeFirebase();
        initializeSpinners();
        setListeners();
    }

    private void initializeSpinners() {
        //initializing spinner values
        String[] sexSpinnerItems = new String[]{"männlich", "weiblich"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sexSpinnerItems);
        sexSpinner.setAdapter(adapter);

        Integer[] ageSpinnerItems = new Integer[100];
        for (int i = 0; i < 100; ++i) {
            ageSpinnerItems[i] = i;
        }
        ArrayAdapter<Integer> adapter2 = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, ageSpinnerItems);
        ageSpinner.setAdapter(adapter2);

        Integer[] numOfPreviousOwnersSpinnerItems = new Integer[20];
        for (int i = 0; i < 20; ++i) {
            numOfPreviousOwnersSpinnerItems[i] = i;
        }
        ArrayAdapter<Integer> adapter3 = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, numOfPreviousOwnersSpinnerItems);
        numOfPreviousOwnersSpinner.setAdapter(adapter3);
    }

    private void initializeFirebase() {
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
                selectImageSource();
            }
        });
    }

    private void selectImageSource() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddPetsActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (items[which].toString()) {
                    case "Take Photo":
                        //TODO:
                        Toast.makeText(AddPetsActivity.this, "will be available soon", Toast.LENGTH_SHORT).show();
                        //TODO: REACTIVATE IF WORKING
                        startCameraIntent();
                        break;
                    case "Choose from Gallery":
                        startGalleryIntent();
                        break;
                    default:
                        dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void startGalleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PICK_IMAGE);
    }

    private void startCameraIntent() {
        /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }*/

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File...

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
            mImageUri = data.getData();
            Picasso.with(this).load(mImageUri).into(imageView);
            if (mUploadTask != null && mUploadTask.isInProgress()) {
                Toast.makeText(this, "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                uploadFile();
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            System.out.println("test");
            //Bitmap photo = (Bitmap) data.getExtras().get("data");
            Bitmap photo = null;
            try {
                photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(new File(mCurrentPhotoPath)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(photo);
            //TODO: aus "photo" eine uri erzeugen
            //new File("/sdcard/Pictures").mkdirs();
            //new File("/sdcard/DCIM/Camera").mkdirs();
            //TODO: path wird nicht richtig erzeugt. (null)
            /*String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), photo, null, null);
            System.out.println("URITEST: " + path);
            Uri image1 = Uri.parse(path);
            */if (mUploadTask != null && mUploadTask.isInProgress()) {
                Toast.makeText(this, "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                mImageUri = Uri.fromFile(new File(mCurrentPhotoPath));
                uploadFile();
            }
        } else {
            Toast.makeText(this, "Irgendetwas ist null", Toast.LENGTH_SHORT).show();
        }
    }

    String mCurrentPhotoPath;

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private String getFileExtention(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            randomUUID = UUID.randomUUID().toString();
            StorageReference fileReference = mStorageRef.child(randomUUID + "." + getFileExtention(mImageUri));


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
                            Upload upload = new Upload(Objects.requireNonNull(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail()),
                                    Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getDownloadUrl()).toString()));
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload.getImageUrl());
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
        int age = (int) ageSpinner.getSelectedItem();
        /*if (ageEditText.getText().toString().equals("")) {
            age = 0;
        } else {
            age = Double.parseDouble(ageEditText.getText().toString());
        }*/
        //TODO: Das kann man bestimmt eleganter machen...
        /*Boolean sex;
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
        }*/
        String sex = sexSpinner.getSelectedItem().toString();
        String location = locationEditText.getText().toString();
        String currentOwner = currentOwnerEditText.getText().toString();

        String size = sizeEditText.getText().toString();
        int numberOfPreviousOwners = (int) numOfPreviousOwnersSpinner.getSelectedItem();
        /*if (numberOfPreviousOwnersEditText.getText().toString().equals("")) {
            numberOfPreviousOwners = 0;
        } else {
            numberOfPreviousOwners = Integer.parseInt(numberOfPreviousOwnersEditText.getText().toString());
        }*/
        String description = descriptionEditText.getText().toString();
        int chipId;
        if (chipIdEditText.getText().toString().equals("")) {
            chipId = 0;
        } else {
            chipId = Integer.parseInt(chipIdEditText.getText().toString());
        }
        String disorders = disordersEditText.getText().toString();
        //encodedPhoto could be a BASE64 String
        Pets newPet = new Pets(encodedPhoto, name, family, race, age, sex, location, currentOwner);   //Lege neues Tier an

        System.out.println("BASE64: " + encodedPhoto);
        newPet.setCurrentOwner(newPet.getCurrentOwner());
        newPet.setRandomUUID(randomUUID);
//        newPet.setEmailOfCreator(firebaseAuth.getCurrentUser().getEmail());   //TODO: Unterminated object at character 172 of...
        //füge Optionals hinzu:
        if (!size.equals("")) {
            newPet.setSize(size);
        }
        //abfrage redundant
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
        ref.child(newPet.getName() + " @ " + randomUUID).setValue(newPet);
        Toast.makeText(this, name.toString() + " angelegt!", Toast.LENGTH_SHORT).show();
        finish();
    }


    private void findViews() {
        imageView = findViewById(R.id.imageView);
        nameEditText = findViewById(R.id.nameEditText);
        familyEditText = findViewById(R.id.familyEditText);
        raceEditText = findViewById(R.id.raceEditText);
        //ageEditText = findViewById(R.id.ageEditText);
        //sexEditText = findViewById(R.id.sexEditText);
        locationEditText = findViewById(R.id.locationEditText);
        currentOwnerEditText = findViewById(R.id.currentOwnerEditText);
        sizeEditText = findViewById(R.id.sizeEditText);
        //numberOfPreviousOwnersEditText = findViewById(R.id.numberOfPreviousOwnersEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        chipIdEditText = findViewById(R.id.chipIdEditText);
        disordersEditText = findViewById(R.id.disordersEditText);
        addPetButton = findViewById(R.id.addPetButton);
        sexSpinner = findViewById(R.id.spinner_sex);
        ageSpinner = findViewById(R.id.spinner_age);
        numOfPreviousOwnersSpinner = findViewById(R.id.spinner_numOfPreviousOwners);
    }
}
