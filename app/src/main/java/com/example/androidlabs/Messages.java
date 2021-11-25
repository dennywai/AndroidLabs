package com.example.androidlabs;

public class Messages {
    static final int senttext = 1;
    static final int receivedtext=2;
    String content;
    int texts;

   public Messages(String content, int texts){
    this.content = content;
    this.texts = texts;
    }

    public String toString(){
       return content;
    }
    }

