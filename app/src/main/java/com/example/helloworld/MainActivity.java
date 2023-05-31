package com.example.helloworld;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Switch;

import com.google.android.material.internal.VisibilityAwareImageButton;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    Switch sw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.flag_view);
        sw = findViewById(R.id.switch1);

        sw.setOnCheckedChangeListener((btn, isChecked)->{
            if(isChecked){
                RotateAnimation rotate = new RotateAnimation(0,360, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF, 0.0f);
                rotate.setDuration(5000);
                rotate.setRepeatCount(Animation.INFINITE);
                rotate.setInterpolator(new LinearInterpolator());

                imageView.startAnimation(rotate);
            }
            else {
                imageView.clearAnimation();
            }
        });
    }
}