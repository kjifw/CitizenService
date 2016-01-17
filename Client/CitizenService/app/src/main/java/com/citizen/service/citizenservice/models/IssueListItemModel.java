package com.citizen.service.citizenservice.models;

import android.net.Uri;

public class IssueListItemModel {
    private String imageUrl;
    private String title;
    private String votesCount;
    private String description;
    private String author;

    public IssueListItemModel(String imageUrl, String title, String votesCount, String description, String author) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.votesCount = votesCount;
        this.description = description;
        this.author = author;
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
