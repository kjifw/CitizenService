package com.citizen.service.citizenservice.models;

import android.net.Uri;

public class IssueListItemModel {
    private Uri image;
    private String title;
    private String votesCount;
    private String description;
    private String author;

    public IssueListItemModel(Uri i, String t, String c, String description, String author){
        this.image = i;
        this.title = t;
        this.votesCount = c;
        this.description = description;
        this.author = author;
    }

    // Getters
    public Uri getImage() {
        return image;
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

    // Setters
    public void setImage(Uri image) {
        this.image = image;
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
