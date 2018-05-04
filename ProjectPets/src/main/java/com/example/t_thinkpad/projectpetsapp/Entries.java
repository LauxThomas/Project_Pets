package com.example.t_thinkpad.projectpetsapp;

import android.media.Image;

import java.util.Date;

public class Entries {


    public Image image;
    public String name;
    public String family;
    public String race;
    public double age;
    public boolean sex;
    public String location;

    public Entries(Image image, String name, String family, String race, double age, boolean sex, String location) {
        this.image = image;
        this.name = name;
        this.family = family;
        this.race = race;
        this.age = age;
        this.sex = sex;
        this.location = location;
    }

    public Entries(String name, String family, String race, double age, boolean sex, String location) {
        this.name = name;
        this.family = family;
        this.race = race;
        this.age = age;
        this.sex = sex;
        this.location = location;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
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


}
