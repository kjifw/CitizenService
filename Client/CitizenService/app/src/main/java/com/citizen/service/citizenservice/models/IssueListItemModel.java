package com.citizen.service.citizenservice.models;

import android.net.Uri;

import java.util.ArrayList;

public class IssueListItemModel {

    private int id;
    private String imageUrl;
    private ArrayList<String> imagesUrls = new ArrayList<>();
    private String title;
    private String votesCount;
    private String description;
    private String author;
    private String city;

    public IssueListItemModel(int id, String imageUrl, String title, String votesCount, String description, String author, String city) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.votesCount = votesCount;
        this.description = description;
        this.author = author;
        this.city = city;
    }

    public int getId() { return this.id; }

    public void addImageUrl(String imageUrl) {
        this.imagesUrls.add(imageUrl);
    }

    public String getImage() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getVotesCount() {
        return votesCount;
    }

    public String getDescription() {
        return description;
    }

    public String getAuthor() {
        return author;
    }

    public ArrayList<String> getImagesUrls() {
        return imagesUrls;
    }

    public String getCity (){
        return  this.city;
    }

    public void setImage(String image) {
        this.imageUrl = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVotesCount(String votesCount) {
        this.votesCount = votesCount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
