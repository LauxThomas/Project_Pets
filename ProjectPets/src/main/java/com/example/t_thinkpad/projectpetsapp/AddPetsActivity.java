package com.example.t_thinkpad.projectpetsapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class AddPetsActivity extends AppCompatActivity {
    ImageView imageView;
    AutoCompleteTextView raceAutoComplete;
    EditText nameEditText, locationEditText, currentOwnerEditText, sizeEditText,
            descriptionEditText, chipIdEditText, disordersEditText;
    Spinner sexSpinner, ageSpinner, numOfPreviousOwnersSpinner, familySpinner, raceSpinner;
    Button addPetButton, pickLocationButton;
    Uri mImageUri;
    static final int REQUEST_PICK_IMAGE = 1;
    static final int REQUEST_IMAGE_CAPTURE = 123;
    static final int PLACE_PICKER_REQUEST = 37;
    //private final String[] FAMILIES = new String[]{"Dogs", "Cats", "Birds", "Fish", "Small animals", "other"};
    //TODO: erweitern
    /*
    private final String[] DOGS = new String[]{"Französische Bulldogge", "Labrador", "Australian Shepherd", "Chihuahua", "Golden Retriever", "Border Collie", "Labradoodle", "Rottweiler", "Beagle", "Mops"};
    private final String[] CATS = new String[]{"Maine Coon", "Norwegische Waldkatze", "Bengalkatze", "Britisch Kurzhaar", "Siamkatze", "Ragdoll", "Savannah Katze", "Perserkatze", "Heilige Birma", "Hauskatze"};
    private final String[] BIRDS = new String[]{"Amadinen", "Bergsittich", "Felsensittich", "Gelbbrustara", "Goldnackenara", "Kanarienvogel", "Königssittich", "Nymphensittich", "Weißhaubenkakadu", "Wellensittich"};
    private final String[] FISH = new String[]{"Guppy", "Neonfisch", "Platy", "Kardinalfisch", "Black Molly", "Panzerwels", "Antennenwels", "Blauer Fadenfisch", "Koi", "Hammerhai"};
    private final String[] SMALL_ANIMALS = new String[]{"Kaninchen", "Ratte", "Maus", "Hamster", "Chinchilla", "Hase", "Meerschweinchen", "Wüstenspringmaus"};
    private final String[] OTHER = new String[]{"Känguru", "Spinne", "Schlange", "Schildkröte", "Bartagame", "Gecko", "Schwein", "Kuh", "Schaf", "Ziege"};
    */


    private FirebaseAuth firebaseAuth;
    private DatabaseReference ref, mDatabaseRef;
    private StorageTask mUploadTask;
    private StorageReference mStorageRef;
    private String randomUUID, mCurrentPhotoPath;
    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pets);
        findViews();
        initializeFirebase();
        initializeSpinners();
        setListeners();
        if (getIntent().getBooleanExtra("isEdit", false)) {
            fillViews(getIntent());
        }
    }

    private void fillViews(Intent intent) {
        //TODO:IMAGE EINBINDEN!
        nameEditText.setText(intent.getStringExtra("name"));
        familySpinner.setSelection(((ArrayAdapter) familySpinner.getAdapter()).getPosition(intent.getStringExtra("family")));
        updateRaceSpinnerWithPosition(((ArrayAdapter) familySpinner.getAdapter()).getPosition(intent.getStringExtra("family")));
        System.out.println("SCHNITZELBRÖTCHEN: " + intent.getStringExtra("race"));
        System.out.println("SCHNITZELBRÖTCHEN: " +raceSpinner);
        System.out.println("SCHNITZELBRÖTCHENPOSITION: " +((ArrayAdapter) raceSpinner.getAdapter()).getPosition(intent.getStringExtra("race")));
        raceSpinner.setSelection(((ArrayAdapter) raceSpinner.getAdapter()).getPosition(intent.getStringExtra("race")));
        ageSpinner.setSelection(intent.getIntExtra("age", 0));
        sexSpinner.setSelection(((ArrayAdapter) sexSpinner.getAdapter()).getPosition(intent.getStringExtra("sex")));
        locationEditText.setText(intent.getStringExtra("location"));
        currentOwnerEditText.setText(intent.getStringExtra("currentOwner"));
        sizeEditText.setText(intent.getStringExtra("size"));
        numOfPreviousOwnersSpinner.setSelection(((ArrayAdapter) numOfPreviousOwnersSpinner.getAdapter()).getPosition(intent.getIntExtra("numberOfPreviousOwners", 0)));
        descriptionEditText.setText(intent.getStringExtra("description"));
        chipIdEditText.setText("" + intent.getIntExtra("chipId", 0));
        disordersEditText.setText(intent.getStringExtra("disorders"));
        //TODO: LOCATION, LATITUDE UND LONGITUDE RICHTIG SETZEN
        addPetButton.setOnClickListener(null);
        addPetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlacePicker.IntentBuilder place = new PlacePicker.IntentBuilder();
                place.setLatLngBounds(new LatLngBounds(new LatLng(1, 2), new LatLng(1, 2)));

                createNewPet();
            }
        });
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


        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, GeneralPetsData.getFamilies());
        familySpinner.setAdapter(adapter4);
        familySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                updateRaceSpinnerWithPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });
    }

    private void updateRaceSpinnerWithPosition(int position) {
        String[] RACES = new String[]{};
        switch (familySpinner.getSelectedItemPosition()) {
            case 0:
                break;
            case 1:
                RACES = GeneralPetsData.getDogs();
                break;
            case 2:
                RACES =  GeneralPetsData.getCats();;
                break;
            case 3:
                RACES =  GeneralPetsData.getBirds();;
                break;
            case 4:
                RACES = GeneralPetsData.getFish();
                break;
            case 5:
                RACES = GeneralPetsData.getSmallAnimals();
                break;
            case 6:
                RACES = GeneralPetsData.getOther();
                break;
            default:
                break;
        }
        /*AutoCompleteTextView raceAutoCompleteTextView = (AutoCompleteTextView)
                findViewById(R.id.raceAutoComplete);
        //umshreiben sodass getter der neuen klasse aufrufbar sind
        ArrayAdapter<String> raceAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, RACES);
        raceAutoCompleteTextView.setAdapter(raceAdapter);*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, RACES);
        raceSpinner.setAdapter(adapter);
    }

    private void initializeFirebase() {
        mStorageRef = FirebaseStorage.getInstance().getReference("pictureReferences");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("pictureReferences");
        firebaseAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("pets");  //petsreferenz
    }

    private void setListeners() {

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageSource();
            }
        });
        pickLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPickLocationIntent();
            }
        });
        addPetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AddPetsActivity.this, "Please add an image to your pet :)", Toast.LENGTH_SHORT).show();
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
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {

            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    private void startPickLocationIntent() {
        //TODO: attributions to google when using place picker might need to be shown on screen
        //https://developers.google.com/places/android-sdk/attributions
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

        try {
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
            mImageUri = data.getData();
            GlideApp.with(this).load(mImageUri).into(imageView);
            if (mUploadTask != null && mUploadTask.isInProgress()) {
                Toast.makeText(this, "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                uploadFile();
            }
        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap photo = null;
            try {
                photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.fromFile(new File(mCurrentPhotoPath)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(photo);
            if (mUploadTask != null && mUploadTask.isInProgress()) {
                Toast.makeText(this, "Upload in progress", Toast.LENGTH_SHORT).show();
            } else {
                mImageUri = Uri.fromFile(new File(mCurrentPhotoPath));
                uploadFile();
            }
        } else if (requestCode == PLACE_PICKER_REQUEST && resultCode == RESULT_OK) {
            place = PlacePicker.getPlace(this, data);
            locationEditText.setText(place.getName());
        } else {
            Toast.makeText(this, "Etwas ist schiefgelaufen", Toast.LENGTH_SHORT).show();
        }
    }


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
/*
    private String getFileExtention(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }*/

    private void uploadFile() {
        if (mImageUri != null) {
            randomUUID = UUID.randomUUID().toString();
            StorageReference fileReference = mStorageRef.child(randomUUID + ".jpg");
            mUploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                }
                            }, 2000);

                            Toast.makeText(AddPetsActivity.this, "Imageupload successful", Toast.LENGTH_SHORT).show();
                            Upload upload = new Upload(Objects.requireNonNull(Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail()),
                                    Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getDownloadUrl()).toString()));
                            String uploadId = mDatabaseRef.push().getKey();
                            mDatabaseRef.child(uploadId).setValue(upload.getImageUrl());
                            addPetButton.setOnClickListener(null);
                            addPetButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    createNewPet();
                                }
                            });
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
            Toast.makeText(this, "No Image selected!", Toast.LENGTH_SHORT).show();
        }
    }

    private void createNewPet() {
        String name = nameEditText.getText().toString();
        String family = (String) familySpinner.getSelectedItem();
        String race = (String) raceSpinner.getSelectedItem();
        int age = (int) ageSpinner.getSelectedItem();
        String sex = sexSpinner.getSelectedItem().toString();
        String location;
        if (getIntent().getBooleanExtra("isEdit", false) || place == null || !place.getAddress().toString().isEmpty()) {
            location = "";
        } else {
            location = place.getAddress().toString();
        }
        String currentOwner = currentOwnerEditText.getText().toString();
        String size = sizeEditText.getText().toString();
        int numberOfPreviousOwners = (int) numOfPreviousOwnersSpinner.getSelectedItem();
        String description = descriptionEditText.getText().toString();
        int chipId;
        if (chipIdEditText.getText().toString().equals("")) {
            chipId = 0;
        } else {
            chipId = Integer.parseInt(chipIdEditText.getText().toString());
        }
        String disorders = disordersEditText.getText().toString();
        Pets newPet = new Pets();
        newPet.setImage(null);
        newPet.setName(name);
        newPet.setFamily(family);
        newPet.setRace(race);
        newPet.setAge(age);
        newPet.setSex(sex);
        newPet.setLocation(location);
        newPet.setCurrentOwner(currentOwner);
        newPet.setCurrentOwner(newPet.getCurrentOwner());
        if (getIntent().getBooleanExtra("isEdit", false) || location.equals("")) {
            newPet.setLatitude(getIntent().getDoubleExtra("latitude", 0.0));
            newPet.setLongitude(getIntent().getDoubleExtra("longitude", 0.0));
            if (getIntent().getStringExtra("randomUUID") != null) {
                newPet.setRandomUUID(getIntent().getStringExtra("randomUUID"));
            } else {
                newPet.setRandomUUID(randomUUID);
            }
        } else {
            newPet.setLatitude(place.getLatLng().latitude);
            newPet.setLongitude(place.getLatLng().longitude);
            newPet.setRandomUUID(randomUUID);
        }
        newPet.setEmailOfCreator(firebaseAuth.getCurrentUser().getEmail());   //TODO: Unterminated object at character 172 of...
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
        if (getIntent().getBooleanExtra("isEdit", false)) {
            ref.child(getIntent().getStringExtra("name") + " @ " + newPet.getRandomUUID()).setValue(newPet);
        } else {
            ref.child(newPet.getName() + " @ " + newPet.getRandomUUID()).setValue(newPet);

        }
        Toast.makeText(this, name.toString() + " angelegt!", Toast.LENGTH_SHORT).show();
        finish();
    }


    private void findViews() {
        imageView = findViewById(R.id.imageView);
        nameEditText = findViewById(R.id.nameEditText);
        locationEditText = findViewById(R.id.locationEditText);
        currentOwnerEditText = findViewById(R.id.currentOwnerEditText);
        sizeEditText = findViewById(R.id.sizeEditText);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        chipIdEditText = findViewById(R.id.chipIdEditText);
        disordersEditText = findViewById(R.id.disordersEditText);
        addPetButton = findViewById(R.id.addPetButton);
        sexSpinner = findViewById(R.id.spinner_sex);
        ageSpinner = findViewById(R.id.spinner_age);
        familySpinner = findViewById(R.id.spinner_family);
        raceSpinner = findViewById(R.id.spinner_race);
        numOfPreviousOwnersSpinner = findViewById(R.id.spinner_numOfPreviousOwners);
        pickLocationButton = findViewById(R.id.button_location_picker);
    }
}
