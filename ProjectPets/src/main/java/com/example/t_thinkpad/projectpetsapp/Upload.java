package com.example.t_thinkpad.projectpetsapp;

public class Upload {
    private String uploader, imageUrl;

    public Upload(){
        //NEEDED!
    }

    public Upload(String uploader, String imageUrl) {
        if (uploader.trim().equals("")){
            uploader = "No Name";
        }
        this.uploader = uploader;
        this.imageUrl = imageUrl;
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
