package com.appfitnessapp.app.fitnessapp.Arrays;

public class Chat {

    public String uid;
    public String name;
    public String text;
    public long timestamp;

    public Chat() {

    }

    public Chat(String uid, String name, String text, long timestamp) {
        this.uid = uid;
        this.name = name;
        this.text = text;
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

}
