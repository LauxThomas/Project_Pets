package com.example.t_thinkpad.projectpetsapp;

import android.graphics.Bitmap;
import android.media.Image;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

public class Pets implements Serializable {


    public static int petId;    //TODO: der Counter muss irgendwie global gesetzt werden
    //Essentials:
    public Bitmap image = null;
    public String name = "";
    public String family = "";
    public String race = "";
    public double age = -1;
    public boolean sex = true;          //Uhh look at this! sex = true means male ¯\_(ツ)_/¯
    public String location = "";
    public String currentOwner = "";
    public String emailOfCreator;

    //optional:
    public String size = "";
    public int numberOfPreviousOwners = -1;
    public String description = "";
    public int chipId = -1;
    public String disorders = "";

    //Bare essentials:
    public Pets(Bitmap image, String name, String family, String race, double age, boolean sex, String location, String currentOwner) {
        this.image = image;
        this.name = name;
        this.family = family;
        this.race = race;
        this.age = age;
        this.sex = sex;
        this.location = location;
        this.currentOwner = currentOwner;
        petId++;
    }

    //Full boom
    public Pets(Bitmap image, String name, String family, String race, double age, boolean sex, String location, String currentOwner, String size, int numberOfPreviousOwners, String description, int chipId, String disorders) {
        this.image = image;
        this.name = name;
        this.family = family;
        this.race = race;
        this.age = age;
        this.sex = sex;
        this.location = location;
        this.currentOwner = currentOwner;

        this.size = size;
        this.numberOfPreviousOwners = numberOfPreviousOwners;
        this.description = description;
        this.chipId = chipId;
        this.disorders = disorders;
        petId++;
    }

    //+size,numberOfPreviousOwners,currentOwner,description,chipId
    public Pets(Bitmap image, String name, String family, String race, double age, boolean sex, String location, String size, int numberOfPreviousOwners, String currentOwner, String description, int chipId) {
        this.image = image;
        this.name = name;
        this.family = family;
        this.race = race;
        this.age = age;
        this.sex = sex;
        this.location = location;
        this.size = size;
        this.numberOfPreviousOwners = numberOfPreviousOwners;
        this.currentOwner = currentOwner;
        this.description = description;
        this.chipId = chipId;
        petId++;
    }

    public String getEmailOfCreator() {
        return emailOfCreator;
    }

    public void setEmailOfCreator(String emailOfCreator) {
        this.emailOfCreator = emailOfCreator;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
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

    public double getAge() {
        return age;
    }

    public void setAge(double age) {
        this.age = age;
    }

    public String getSex() {
        if (sex) {
            return "male";
        } else {
            return "female";
        }
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public static int getPetId() {
        return petId;
    }

    public String getRandomUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

}
