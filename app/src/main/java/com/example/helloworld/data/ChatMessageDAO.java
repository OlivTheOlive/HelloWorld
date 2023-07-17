package com.example.helloworld.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;
@Dao
public interface ChatMessageDAO {
    @Insert
    long insertMessage(ChatMessage m);
    @Query("Select * from ChatMessage")
    List<ChatMessage> getAllMessage();
    @Delete
    void deleteMessage(ChatMessage m);

}