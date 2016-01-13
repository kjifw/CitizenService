package com.citizen.service.citizenservice.models;

import android.net.Uri;

public class IssueListItemModel {
    private Uri image;
    private String title;
    private String votesCount;

    public IssueListItemModel(Uri i, String t, String c){
        this.image = i;
        this.title = t;
        this.votesCount = c;
    }

    public Uri getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getVotesCount() {
        return votesCount;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVotesCount(String votesCount) {
        this.votesCount = votesCount;
    }
}
