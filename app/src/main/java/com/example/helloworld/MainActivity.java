package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.helloworld.databinding.ActivityMainBinding;

import java.text.BreakIterator;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding variableBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        TextView theText =  findViewById( R.id.theText );

        EditText myedit = findViewById( R.id.theEditText);
        myedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String editString = myedit.getText().toString();
                theText.setText("you clicked the button"+editString);
            }
        });
    }
}