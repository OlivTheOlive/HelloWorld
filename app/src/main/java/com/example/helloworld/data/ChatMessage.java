package com.example.helloworld.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    public int id;
    @ColumnInfo(name = "message")
    protected String message;
    @ColumnInfo(name = "TimeSent")
    protected String timeSent;
    @ColumnInfo(name = "SendOrReceive")
    protected boolean isSentButton;


    public ChatMessage(String m, String t, boolean sent)
    {
        message = m;
        timeSent = t;
        isSentButton = sent;
    }

    public ChatMessage(){};
    public boolean isSentButton() {
        return isSentButton;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public String getMessage() {
        return message;
    }
}
