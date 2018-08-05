package com.example.t_thinkpad.projectpetsapp;

import java.io.Serializable;

public class Pets implements Serializable {


    //Essentials:
    public String image = "";
    public String name = "";
    public String family = "";
    public String race = "";
    public double age = -1;
    public boolean sex = true;          //Uhh look at this! sex = true means male ¯\_(ツ)_/¯
    public String location = "";
    public String currentOwner = "";
    public String emailOfCreator;

    public void setRandomUUID(String randomUUID) {
        this.randomUUID = randomUUID;
    }

    public String randomUUID;

    //optional:
    public String size = "0";
    public int numberOfPreviousOwners = -1;
    public String description = "none";
    public int chipId = -1;
    public String disorders = "none";

    //Bare essentials:
    public Pets(String image, String name, String family, String race, double age, boolean sex, String location, String currentOwner) {
        this.image = image;
        this.name = name;
        this.family = family;
        this.race = race;
        this.age = age;
        this.sex = sex;
        this.location = location;
        this.currentOwner = currentOwner;
    }


    //Full boom
    public Pets(String image, String name, String family, String race, double age, boolean sex, String location, String currentOwner, String size, int numberOfPreviousOwners, String description, int chipId, String disorders) {
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
    }

    public Pets() {
        //wird zur Abfrage benötigt
    }

    //+size,numberOfPreviousOwners,currentOwner,description,chipId
    public Pets(String image, String name, String family, String race, double age, boolean sex, String location, String size, int numberOfPreviousOwners, String currentOwner, String description, int chipId) {
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
    }

    public Pets(Object o) {
    }

    public String getEmailOfCreator() {
        return emailOfCreator;
    }

    public void setEmailOfCreator(String emailOfCreator) {
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

}
