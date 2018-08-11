package com.example.t_thinkpad.projectpetsapp;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Objects;
import java.util.UUID;

public class AddPetsActivity extends AppCompatActivity {
    ImageView imageView;
    EditText nameEditText, familyEditText, raceEditText, ageEditText,
            sexEditText, locationEditText, currentOwnerEditText, sizeEditText,
            numberOfPreviousOwnersEditText, descriptionEditText, chipIdEditText, disordersEditText;
    Button addPetButton;
    private static int RESULT_LOAD_IMAGE = 1;
    //    Uri file;
    Uri mImageUri;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int REQUEST_CAPTURE_IMAGE = 100;
    String imageFilePath;


    private FirebaseAuth firebaseAuth;
    private DatabaseReference ref, mDatabaseRef;
    private StorageTask mUploadTask;
    private StorageReference mStorageRef;
    private String randomUUID;
    private int REQ_CAMERA_IMAGE = 1;
    private int REQUEST_IMAGE_CAPTURE = 123;
    private String encodedPhoto;
    File photoFile;
    public String photoFileName = "photo.jpg";

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
                selectImage();
            }
        });
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddPetsActivity.this);
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (items[which].toString()) {
                    case "Take Photo":
                        Toast.makeText(AddPetsActivity.this, "will be available soon", Toast.LENGTH_SHORT).show();
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

    //works!
    private void startGalleryIntent() {
        //TODO: JPG als BASE64 encoden und als setImage speichern
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }

    private void startCameraIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }

    }

    private String convertToBase64(String imagePath) {
        Bitmap bm = BitmapFactory.decodeFile(imagePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] byteArrayImage = baos.toByteArray();
        String encodedImage = Base64.encodeToString(byteArrayImage, Base64.DEFAULT);
        return encodedImage;
    }

    public String encodeBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        imageEncoded = imageEncoded.replace("\\n", "");
        System.out.println("IMAGEENCODED: " + imageEncoded);
        return imageEncoded;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
//            //TODO: Bilder als BASE64 abspeichern
            mImageUri = data.getData();
//            String encodedImage = convertToBase64(data.getData().getPath());
            Picasso.with(this).load(mImageUri).into(imageView);
            if (mUploadTask != null && mUploadTask.isInProgress()) {
                Toast.makeText(this, "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                uploadFile();
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //TODO: Bilder speichern und auf firebase hochladen
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
            new File("/sdcard/Pictures").mkdirs();
            new File("/sdcard/DCIM/Camera").mkdirs();
            String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), photo, null, null);
            System.out.println("URITEST: " + path);
            Uri image1 = Uri.parse(path);
            if (mUploadTask != null && mUploadTask.isInProgress()) {
                Toast.makeText(this, "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                uploadFile();
            }


        } else {
            Toast.makeText(this, "Irgendetwas ist null", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtention(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    //            imageView.setImageBitmap(imageBitmap);


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


    private void createNewPet(String imageEncoded) {
    }

    private void createNewPet() {
        //TODO: Image aus Gallery oder Camera
        String name = nameEditText.getText().toString();
        String family = familyEditText.getText().toString();
        String race = raceEditText.getText().toString();
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
//      TODO:speichert als Bild ab   //works: Pets newPet = new Pets(/*image*/ "\"" + mStorageRef.toString() + "\"", name, family, race, age, sex, location, currentOwner);   //Lege neues Tier an
        //TODO:
        Pets newPet = new Pets(encodedPhoto, name, family, race, age, sex, location, currentOwner);   //Lege neues Tier an
        System.out.println("BASE64: " + encodedPhoto);
        newPet.setCurrentOwner(newPet.getCurrentOwner() + " bei " + newPet.getEmailOfCreator());
        newPet.setRandomUUID(randomUUID);
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
        ref.child(newPet.getName() + " @ " + randomUUID).setValue(newPet);
        Toast.makeText(this, name.toString() + " angelegt!", Toast.LENGTH_SHORT).show();
        finish();


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
