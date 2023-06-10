package com.example.helloworld;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class secondActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final String FILE_NAME = "Picture.png";

    private static  final String SHARED_PREF = "Data";
    private SharedPreferences sharedPreferences;
    private static final String PHONE_KEY = "PhoneNumber";
    private ImageView profileImage;
    private TextView textView;
    private String emailAddress;
    private String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        EditText editNumber = findViewById(R.id.editTextPhone);
        String phoneNumber = editNumber.getText().toString();


        sharedPreferences = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        String savedNumber = sharedPreferences.getString(PHONE_KEY,"");
        editNumber.setText(savedNumber);

        Button picChange = findViewById(R.id.button5);
        profileImage = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);

        Intent fromPrevious = getIntent();
        emailAddress = fromPrevious.getStringExtra("EmailAddress");
        textView.setText("Welcome back, " + emailAddress);

        // Check if the image file exists in the persistent location
        File imageFile = new File(getFilesDir(), FILE_NAME);
        if (imageFile.exists()) {
            imagePath = imageFile.getAbsolutePath();
            Bitmap theImage = BitmapFactory.decodeFile(imagePath);
            profileImage.setImageBitmap(theImage);
        }

        // Register an activity result launcher for camera capture
        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                        profileImage.setImageBitmap(thumbnail);
                        saveImage(thumbnail);
                    }
                });

        // Launch camera intent to capture an image
        picChange.setOnClickListener(clk -> {
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            cameraResult.launch(cameraIntent);
        });

        Button phoneCall = findViewById(R.id.button4);
        phoneCall.setOnClickListener(clk -> {
            Intent call = new Intent(Intent.ACTION_DIAL);
            EditText phone = findViewById(R.id.editTextPhone);
            call.setData(Uri.parse("tel:" + phone.getText()));
            String number = editNumber.getText().toString();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(PHONE_KEY,number);
            editor.apply();

            startActivity(call);

        });
    }

    private void saveImage(Bitmap bitmap) {
        try {
            FileOutputStream fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check if the image file still exists in the persistent location
        File imageFile = new File(getFilesDir(), FILE_NAME);
        if (!imageFile.exists() || imagePath == null) {
            profileImage.setImageResource(R.drawable.default_profile_image);
        }
    }
}
