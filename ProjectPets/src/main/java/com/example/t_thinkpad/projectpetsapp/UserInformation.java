package com.example.t_thinkpad.projectpetsapp;

public class UserInformation {

    public String name;
    public String location;
    public boolean isAnimalShelter;
    public String isLookingFor;
    public String mail;

    public UserInformation() {
        //NEEDED!!
    }

    public UserInformation(String name, String location, String mail, String isLookingFor) {
        this.name = name;
        this.location = location;
        this.mail = mail;
        this.isLookingFor = isLookingFor;
    }

    public boolean isAnimalShelter() {
        return isAnimalShelter;
    }

    public void setAnimalShelter(boolean animalShelter) {
        isAnimalShelter = animalShelter;
    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getIsLookingFor() {
        return isLookingFor;
    }

    public void setIsLookingFor(String isLookingFor) {
        this.isLookingFor = isLookingFor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location.toString();
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
