package com.example.pub2.model;

public class Match {

    private String title;
    private String description;
    private String photo;
    private String time;
    private String date;
    private String registered;
    private String host;

    //Constructors
    public Match() {}

    public Match(String title, String description, String photo, String time, String date, String registered, String host) {
        this.title = title;
        this.description = description;
        this.photo = photo;
        this.time = time;
        this.date = date;
        this.registered = registered;
        this.host = host;
    }

    //Getters and setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
        this.registered = registered;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
