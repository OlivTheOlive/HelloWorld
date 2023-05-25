package ui;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.helloworld.R;
import com.example.helloworld.databinding.ActivityMainBinding;

import data.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private MainViewModel model;
    private ActivityMainBinding variableBinding;
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new ViewModelProvider(this).get(MainViewModel.class);
        variableBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(variableBinding.getRoot());

        TextView theText =  findViewById( R.id.theText );
        CheckBox checkBox = variableBinding.checkBox;
        RadioButton radioButton = variableBinding.radioButton;
        Switch switch1 = variableBinding.switch1;

        variableBinding.button.setOnClickListener(click -> {
            model.editString.postValue(variableBinding.theEditText.getText().toString());
        });
        model.editString.observe(this, s -> {
            variableBinding.theText.setText("your edit text has: "+ s);
        });


        model.isSelected.observe ( this, selected -> {
            variableBinding.checkBox.setChecked((Boolean) selected);
            variableBinding.radioButton.setChecked((Boolean) selected);
            variableBinding.switch1.setChecked((Boolean) selected);

        });

        checkBox.setOnCheckedChangeListener( (btn, isChecked) -> {
                model.isSelected.postValue(isChecked);
                theText.setText("The value is now: "+isChecked);
                showToast("CheckBox is:"+isChecked);
            });
        radioButton.setOnCheckedChangeListener( (btn, isChecked) -> {
            model.isSelected.postValue(isChecked);
            theText.setText("The value is now: "+isChecked);
            showToast("RadioButton is:"+isChecked);
        });
        switch1.setOnCheckedChangeListener( (btn, isChecked) -> {
            model.isSelected.postValue(isChecked);
            theText.setText("The value is now: "+isChecked);
            showToast("Switch is:"+isChecked);
        });

        ImageView Img = variableBinding.schoolImg;
        Img.setOnClickListener(new View.OnClickListener() {
            @Override
                    public void onClick(View view){
                    Toast.makeText (MainActivity.this, "You Click The Image Bozo", Toast.LENGTH_SHORT).show();
            }
        });

        ImageButton ImgButton = variableBinding.myImgButton;

        ImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Toast.makeText (MainActivity.this, "The width "+ Img.getWidth()+ ", the height "+ Img.getHeight(), Toast.LENGTH_SHORT).show();
            }
        });
        }
    }