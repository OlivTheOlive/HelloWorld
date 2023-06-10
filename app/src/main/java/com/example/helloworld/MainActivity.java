package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";

    private static  final String SHARED_PREF = "Data";
    private SharedPreferences sharedPreferences;

    private static final String EMAIL_KEY = "EMAIL";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        Button loginButton = findViewById(R.id.loginButton);
        EditText emailEditText = findViewById(R.id.editTextmailAddress);
        String emailAddress = emailEditText.getText().toString();


        sharedPreferences = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString(EMAIL_KEY,"");
        emailEditText.setText(savedEmail);


        super.onCreate(savedInstanceState);
        Log.w( "MainActivity", "In onCreate() - Loading Widgets" );
        Intent nextPage = new Intent( MainActivity.this, secondActivity.class);
        loginButton.setOnClickListener( clk-> {
                Intent:
                nextPage.putExtra("EmailAddress", emailEditText.getText().toString());
                startActivity(nextPage);

                String email = emailEditText.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(EMAIL_KEY,email);
                editor.apply();
            });

    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.w( "MainActivity", "In onStart - now visible on screen" );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w( "MainActivity", "In onResume - app is now responding to user input" );
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.w( "MainActivity", "In onPause() - app is now not responding to user input");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.w( "MainActivity", "In onStop() - app no longer visible" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w( "MainActivity", "In onDestroy() - memory used freed" );
    }
}