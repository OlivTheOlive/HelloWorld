package com.example.helloworld;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.helloworld.DAO.ChatMessageDAO;
import com.example.helloworld.data.ChatMessage;
import com.example.helloworld.data.ChatRoomViewModel;
import com.example.helloworld.data.MessageDatabase;
import com.example.helloworld.databinding.ActivityChatRoomBinding;
import com.example.helloworld.databinding.ReceiveMessageBinding;
import com.example.helloworld.databinding.SentMessageBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ChatRoom extends AppCompatActivity {
    private ActivityChatRoomBinding binding;
    private RecyclerView.Adapter myAdapter;
    ChatRoomViewModel chatModel;
    ArrayList<ChatMessage> messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatModel = new ViewModelProvider(this).get(ChatRoomViewModel.class);
        messages = chatModel.messages.getValue();
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class,"database-name").build();
        ChatMessageDAO mDAO = db.cmDAO();
        binding = ActivityChatRoomBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if(messages == null)
        {
            chatModel.messages.setValue(messages = new ArrayList<>());

            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                messages.addAll( mDAO.getAllMessage() ); //Once you get the data from database

                runOnUiThread( () ->  binding.recycleView.setAdapter( myAdapter )); //You can then load the RecyclerView
            });
        }

        binding.sendButton.setOnClickListener(click->{
            String typed = binding.editText.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDataAndTime = sdf.format(new Date());
            ChatMessage typedMessage = new ChatMessage(typed, currentDataAndTime, true);
            messages.add(typedMessage);
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.editText.setText("");
        });

        binding.receiveButton.setOnClickListener(click->{
            String typed = binding.editText.getText().toString();
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd-MMM-yyyy hh-mm-ss a");
            String currentDataAndTime = sdf.format(new Date());
            ChatMessage typedMessage = new ChatMessage(typed, currentDataAndTime, false);
            messages.add(typedMessage);
            myAdapter.notifyItemInserted(messages.size()-1);
            binding.editText.setText("");
        });

        binding.recycleView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {

            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                //viewType will be either 0 or 1, determined by getItemViewType(int position)
                if (viewType == 0) {
                    SentMessageBinding rowBinding = SentMessageBinding.inflate(getLayoutInflater(), parent, false);

                    return new MyRowHolder(rowBinding.getRoot());
                } else {
                    ReceiveMessageBinding rowBinding = ReceiveMessageBinding.inflate(getLayoutInflater(), parent, false);
                    return new MyRowHolder(rowBinding.getRoot());
                }
            }


            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                ChatMessage message = messages.get(position);
                holder.messageText.setText(message.getMessage());
                holder.timeText.setText(message.getTimeSent());
            }
            @Override
            public int getItemCount() {
                return messages.size();
            }
            @Override
            public int getItemViewType(int position) {
                ChatMessage message = messages.get(position);
                if (message.isSentButton()) {
                    return 0;
                } else return 1;
            }
        });
        binding.recycleView.setLayoutManager(new LinearLayoutManager(this));
    }
    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        ChatMessageDAO mDAO;
        TextView timeText;
        public MyRowHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(clk->{
                int position = getAbsoluteAdapterPosition();
                AlertDialog.Builder builder = new AlertDialog.Builder(ChatRoom.this);
                builder.setTitle("Question")
                        .setNegativeButton("No", (dialog, cl) ->{ })
                        .setPositiveButton("Yes", (dialog, cl) ->{
                            ChatMessage m = messages.get(position);
                            mDAO.deleteMessage(m);
                            messages.remove(position);
                            myAdapter.notifyItemRemoved(position);
                        })
                        .create().show();


            });

            messageText = itemView.findViewById(R.id.messageRec);
            timeText = itemView.findViewById(R.id.time);

        }
    }
}
