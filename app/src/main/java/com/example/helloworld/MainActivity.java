package com.example.helloworld;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.helloworld.databinding.ActivityMainBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.net.URLEncoder;




public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    protected String cityName;
    RequestQueue queue = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        queue = Volley.newRequestQueue(this);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.loginButton.setOnClickListener(clk -> {
            cityName = binding.editTextPassword.getText().toString();
            String stringURL = "https://api.openweathermap.org/data/2.5/weather?q=" + URLEncoder.encode(cityName) + "&appid=7e943c97096a9784391a981c4d878b22&units=metric";

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                    (response) ->{
                        JSONObject main = null;
                        try {
                            JSONArray weatherArray = response.getJSONArray("weather");
                            JSONObject position0 = weatherArray.getJSONObject(0);

                            String description = position0.getString("description");
                            String iconName = position0.getString("icon");

                            JSONObject mainObject = response.getJSONObject("main");

                            double current = mainObject.getDouble("temp");
                            double min = mainObject.getDouble("temp_min");
                            double max = mainObject.getDouble("temp_max");
                            int humidity = mainObject.getInt("humidity");

                            String pictureURL = "https://openweathermap.org/img/w/" + iconName + ".png";

                            ImageRequest imgReq = new ImageRequest(pictureURL, new Response.Listener<Bitmap>() {
                                @Override
                                public void onResponse(Bitmap bitmap) {

                                    binding.iconid.setImageBitmap(bitmap);
                                    Bitmap image = bitmap;
                                    try {
                                        image.compress(Bitmap.CompressFormat.PNG, 100, MainActivity.this.openFileOutput(iconName + ".png", Activity.MODE_PRIVATE));
                                    } catch (FileNotFoundException e) {
                                        throw new RuntimeException(e);
                                    }
                                    int i = 0;
                                }
                            }, 1024, 1024, ImageView.ScaleType.CENTER, null,
                                    (error) -> {
                                        int i = 0;
                                    });
                            queue.add(imgReq);
                            runOnUiThread(() -> {
                                binding.tempId.setText("The current temperature is " + current);
                                binding.tempId.setVisibility(View.VISIBLE);
                                binding.lowTempId.setText("The min temperature is " + min);
                                binding.lowTempId.setVisibility(View.VISIBLE);
                                binding.maxTempId.setText("The max temperature is " + max);
                                binding.maxTempId.setVisibility(View.VISIBLE);
                                binding.humidityId.setText("The humidity is " + humidity + "%");
                                binding.humidityId.setVisibility(View.VISIBLE);

                                binding.iconid.setVisibility(View.VISIBLE);
                                binding.descId.setText(description);
                                binding.descId.setVisibility(View.VISIBLE);


                            });
                        } catch (JSONException e){
                            throw new RuntimeException(e);
                        }
                    },
                    error -> {
                        int i = 0;
                    });
            queue.add(request);
        });
    }


    /**
     * This method checks the entered password to see if it contains an upper case,
     * lowercase, number, and special character. If it contains all required fields it returns
     * true.
     * @param pw The String object that we are checking
     * @return Returns true if password contains all required fields otherwise returns false.
     */
    boolean checkPasswordComplexity(String pw){
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for (int i = 0; i < pw.length(); i++) {
            char c = pw.charAt(i);

            if (Character.isUpperCase(c)) {
                foundUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                foundLowerCase = true;
            } else if (Character.isDigit(c)) {
                foundNumber = true;
            } else if (isSpecialCharacter(c)) {
                foundSpecial = true;
            }
        }

        if(!foundUpperCase)
        {
            Toast.makeText(this,"Password requires an uppercase letter",Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundLowerCase) {
            Toast.makeText(this,"Password requires a lowercase letter",Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundNumber) {
            Toast.makeText(this,"Password requires a number",Toast.LENGTH_SHORT).show();
            return false;
        } else if (!foundSpecial) {
            Toast.makeText(this,"Password requires a special character",Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    /**
     * This method checks each to see if there is a special character in the string
     *
     * @param c the current character in the password String
     * @return Returns true if there is a special character otherwise returns false
     */
    boolean isSpecialCharacter(char c) {
        switch (c){
            case '#':
            case '?':
            case '*':
            case '!':
            case '&':
            case '@':
            case '%':
            case '^':
                return true;
            default:
                return false;
        }
    }
}