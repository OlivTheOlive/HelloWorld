package com.example.helloworld.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.helloworld.DAO.ChatMessageDAO;
@Database(entities = {ChatMessage.class}, version = 1)
public abstract class MessageDatabase extends RoomDatabase {
    public  abstract ChatMessageDAO cmDAO();
}
