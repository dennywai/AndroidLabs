package com.example.androidlabs;

import android.database.sqlite.SQLiteDatabase;

import java.io.File;

public class Messages {
    //static final int senttext = 1;
    //static final int receivedtext =2;
    String message;
    //int texts;
    boolean isSent;
    private long id;

    public Messages(String message, boolean isSent, long id) {
        this.message = message;
        this.isSent = isSent;
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String toString() {
        return message + id + isSent;
    }

    public boolean isSent() {
        return isSent;
    }

}

