package com.example.t_thinkpad.projectpetsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.util.ArrayList;

public class PetsAdapter extends ArrayAdapter<Pets> {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference ref, mDatabaseRef;
    private StorageReference mStorageRef, fileReference;

        // View lookup cache
        private static class ViewHolder {
           ImageView thumbnail;
        }

        public PetsAdapter(Context context, ArrayList<Pets> pets) {
            super(context, R.layout.item_pets, pets);
            mStorageRef = FirebaseStorage.getInstance().getReference("pictureReferences");
            mDatabaseRef = FirebaseDatabase.getInstance().getReference("pictureReferences");
            firebaseAuth = FirebaseAuth.getInstance();
            ref = FirebaseDatabase.getInstance().getReference().child("pets");  //petsreferenz
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Pets pet = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolder viewHolder; // view lookup cache stored in tag
            if (convertView == null) {
                // If there's no view to re-use, inflate a brand new view for row
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item_pets, parent, false);
                viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnailImageView);
                // Cache the viewHolder object inside the fresh view
                convertView.setTag(viewHolder);
            } else {
                // View is being recycled, retrieve the viewHolder object from tag
                viewHolder = (ViewHolder) convertView.getTag();
            }
            // Populate the data from the data object via the viewHolder object
            // into the template view.
            fileReference = mStorageRef.child(pet.getRandomUUID() + ".jpg");
            GlideApp.with(getContext()).load(fileReference).into(viewHolder.thumbnail);
            // Return the completed view to render on screen
            return convertView;
        }
    }

