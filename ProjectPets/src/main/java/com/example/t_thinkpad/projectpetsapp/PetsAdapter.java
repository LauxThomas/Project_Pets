package com.example.t_thinkpad.projectpetsapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageException;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.io.IOException;
import java.util.ArrayList;

public class PetsAdapter extends ArrayAdapter<Pets> {
    private FirebaseAuth firebaseAuth;
    private DatabaseReference ref, mDatabaseRef;
    private StorageReference mStorageRef, fileReference;

    // View lookup cache
    private static class ViewHolder {
        ImageView thumbnail;
        TextView description;
        TextView dist;
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
        final Pets pet = getItem(position);

        ViewHolder viewHolder;
        if (/*convertView == null*/true) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_pets, parent, false);
            viewHolder.thumbnail = (ImageView) convertView.findViewById(R.id.thumbnailImageView);
            viewHolder.description = (TextView) convertView.findViewById(R.id.textView5);
            viewHolder.dist = (TextView) convertView.findViewById(R.id.textView6);

            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.description.setText(null);
            viewHolder.thumbnail.setImageBitmap(null);
        }

        try {
            fileReference = mStorageRef.child("thumb_" + pet.getRandomUUID() + ".jpg");
            GlideApp.with(getContext()).load(fileReference).into(viewHolder.thumbnail);
            //System.out.println("THUMBNAIL LOADED FOR"+pet.getName());
        } catch (Exception e) {
            //fileReference = mStorageRef.child(pet.getRandomUUID() + ".jpg");
            //GlideApp.with(getContext()).load(fileReference).into(viewHolder.thumbnail);
            //System.out.println("FULL SIZE IMAGE LOADED FOR" + pet.getName());
            e.printStackTrace();
        }

        viewHolder.description.setText(pet.toString());
        viewHolder.dist.setText("dist: "+ pet.getDistFromUserLocation()+" km");

        viewHolder.description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDetailedSearchResultActivity(pet);
            }
        });
        viewHolder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDetailedSearchResultActivity(pet);
            }
        });



        return convertView;
    }

    private void startDetailedSearchResultActivity(Pets pet){
        Intent intent = new Intent(this.getContext(), DetailedSearchResult.class);
        intent.putExtra("age", "" + pet.getAge());
        intent.putExtra("chipId", "" + pet.getChipId());
        intent.putExtra("currentOwner", pet.getCurrentOwner());
        intent.putExtra("description", pet.getDescription());
        intent.putExtra("disorders", pet.getDisorders());
        intent.putExtra("family", pet.getFamily());
        intent.putExtra("image", pet.getImage());
        intent.putExtra("location", pet.getLocation());
        intent.putExtra("latitude", ""+pet.getLatitude());
        intent.putExtra("longitude", ""+pet.getLongitude());
        intent.putExtra("distFromUserLocation",""+pet.getDistFromUserLocation());
        intent.putExtra("name", pet.getName());
        intent.putExtra("numberOfPreviousOwners", "" + pet.getNumberOfPreviousOwners());
        intent.putExtra("race", pet.getRace());
        intent.putExtra("randomUUID", pet.getRandomUUID());
        intent.putExtra("sex", pet.getSex());
        intent.putExtra("size", pet.getSize());
        intent.putExtra("wholePet", pet);
        this.getContext().startActivity(intent);
    }
}

