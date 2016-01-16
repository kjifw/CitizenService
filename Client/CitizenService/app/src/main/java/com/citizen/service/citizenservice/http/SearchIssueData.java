package com.citizen.service.citizenservice.http;

public class SearchIssueData {

    private String title;
    private String city;

    public SearchIssueData(String title, String city) {
        this.title = title;
        this.city = city;
    }

    public String getTitle() {
        return title;
    }

    public String getCity() {
        return city;
    }
}
