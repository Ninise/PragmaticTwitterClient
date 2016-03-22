package com.ninise.pragmatictwitterclient.project.mvp.model.pojos.github;


public class Committer {

    private String date;

    public String getDate() {
        date = date.replaceAll("[^:\\d-]", " ");
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
