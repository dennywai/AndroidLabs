package com.example.androidlabs;

public class Messages {
    //static final int senttext = 1;
    //static final int receivedtext =2;
    String message;
    //int text;
    boolean isSent;
    private long id;

    public Messages(String message, boolean isSent) {
        this.message = message;
        this.isSent = isSent;
    }

    public Messages(String message, boolean isSent, long id){
        this.message = message;
        this.isSent = isSent;
        this.id = id;
    }
    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }
    public String toString(){
        return message + id + isSent;
    }

    public String getMessage() {
        return message;
    }
}

