package com.example.helloworld.data;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ChatRoomViewModel extends ViewModel {
    public ArrayList<ChatMessage> messages = new ArrayList<>();
}
