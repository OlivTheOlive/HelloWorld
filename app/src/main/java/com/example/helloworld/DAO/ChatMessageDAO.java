package com.example.helloworld.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.helloworld.data.ChatMessage;

import java.util.List;
@Dao
public interface ChatMessageDAO {
    @Insert
    void insertMessage(ChatMessage m);
    @Query("Select * from ChatMessage")
    List<ChatMessage> getAllMessage();
    @Delete
    void deleteMessage(ChatMessage m);

}
