package com.citizen.service.citizenservice.http;


import java.util.ArrayList;

public class PublishIssueData {

    private String city;
    private String title;
    private String description;
    private boolean isAnonymous;
    private ArrayList<String> imagesPaths;

    public PublishIssueData(String city, String title, String description,
                             boolean isAnonymous, ArrayList<String> imagesPaths) {
        this.city = city;
        this.title = title;
        this.description = description;
        this.isAnonymous = isAnonymous;
        this.imagesPaths = imagesPaths;
    }

    public String getCity() {
        return this.city;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean getIsAnonymous() {
        return this.isAnonymous;
    }

    public ArrayList<String> getImagesPaths() {
        return this.imagesPaths;
    }
}
