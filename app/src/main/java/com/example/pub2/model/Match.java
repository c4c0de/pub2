package com.example.pub2.model;

public class Match {

    private String title;
    private String description;
    private String photo;
    private String time;
    private String date;
    private String killreward;
    private String winreward;
    private String fee;
    private String matchid;
    private String slot;

    //Constructors
    public Match() {}

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

    public void setPhoto(String photo) { this.photo = photo; }

    public String getTime() { return time;}

    public void setTime(String time) { this.time = time; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getKillreward() {
        return killreward;
    }

    public void setKillreward(String killreward) {
        this.killreward = killreward;
    }

    public String getWinreward() { return winreward; }

    public void setWinreward(String winreward) { this.winreward = winreward;}

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getMatchid() {
        return matchid;
    }

    public void setMatchid(String matchid) {
        this.matchid = matchid;
    }

    public String getSlot() { return slot;}

    public void setSlot(String slot) { this.slot = "Players: "+ slot + "/100";}

}
