package com.example.t_thinkpad.projectpetsapp;

import android.location.Location;

import com.google.firebase.storage.StorageReference;

import java.io.Serializable;

public class Pets implements Serializable {

    //Essentials:
    public String image = "";
    public String name = "";
    public String family = "";
    public String race = "";
    public int age = -1;
    public String sex = "";          //Uhh look at this! sex = true means male ¯\_(ツ)_/¯
    public String location;
    public String currentOwner = "";
    String emailOfCreator;
    public double latitude=0.0;
    public double longitude=0.0;

    public void setRandomUUID(String randomUUID) {
        this.randomUUID = randomUUID;
    }

    public String randomUUID;       //2^128 sollte eindeutig genug sein...

    //optional:
    public String size = "0";
    public int numberOfPreviousOwners = -1;
    public String description = "none";
    public int chipId = -1;
    public String disorders = "none";

    public double distFromUserLocation;


    public Pets() {
        //wird zur Abfrage benötigt
    }

    public Pets(Object o) {
    }

    String getEmailOfCreator() {
        return emailOfCreator;
    }

    void setEmailOfCreator(String emailOfCreator) {
        this.emailOfCreator = emailOfCreator;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

//    public void setSex(String sex) {
//        if (sex.equalsIgnoreCase("male")) {
//            this.sex = true;
//        } else
//            this.sex = false;
//    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getLatitude(){
        return latitude;
    }

    public void setLatitude(double latitude){
        this.latitude=latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public void setLongitude(double longitude){
        this.longitude=longitude;
    }


    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getNumberOfPreviousOwners() {
        return numberOfPreviousOwners;
    }

    public void setNumberOfPreviousOwners(int numberOfPreviousOwners) {
        this.numberOfPreviousOwners = numberOfPreviousOwners;
    }

    public String getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(String currentOwner) {
        this.currentOwner = currentOwner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getChipId() {
        return chipId;
    }

    public void setChipId(int chipId) {
        this.chipId = chipId;
    }

    public String getDisorders() {
        return disorders;
    }

    public void setDisorders(String disorders) {
        this.disorders = disorders;
    }


    public String getRandomUUID() {
        return randomUUID;
    }

    @Override
    public String toString() {
        String petString;
        petString = "name: " + getName() + "\nfamily: " + getFamily() + "\nrace: " + getRace() + "\nage: " + getAge() + "\nsize: " + getSize() + "\nsex: " + getSex() + "\ndescription: " + getDescription();
        return petString;
    }

    public double getDistFromUserLocation(){
        return distFromUserLocation;
    }

    public void setDistFromUserLocation(double distFromUserLocation){
        this.distFromUserLocation = distFromUserLocation;
    }
}
