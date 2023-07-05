package com.example.helloworld;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Class javadoc
 *
 * @author oliviebergeron
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {
    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = findViewById(R.id.textView3);
        EditText et = findViewById(R.id.editTextPassword);
        Button btn = findViewById(R.id.loginButton);


        btn.setOnClickListener( clk ->{
            String password = et.getText().toString();
            if(checkPasswordComplexity( password ) == true){
                tv.setText("password checks out");
            }else{
                tv.setText("incorrect password format");
            }


        });
    }

    /** This function checks for password complexity.
     *
     * @param pw The string object that we are checking for complexity
     * @return Returns true if
     */
    boolean checkPasswordComplexity(String pw){
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;
        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;
        int duration = Toast.LENGTH_SHORT;
        char c;


        for(int i = 0; i <pw.length(); i++){
            c = pw.charAt(i) ;
            if (isDigit(c)){
                foundNumber = true;
            }
            else if (isUpperCase(c)) {
                foundUpperCase = true;
            }
            else if(isLowerCase(c)) {
                foundLowerCase = true;
            }
            else{
                switch (c){
                    case '/':
                    case '(':
                    case ')':
                    case '*':
                    case '&':
                    case '^':
                    case '%':
                    case '$':
                    case '#':
                    case '@':
                    case '!':
                    case '<':
                        return foundSpecial = true;
                    default:
                        return foundSpecial = false;

                }
            }

        }

        if(!foundUpperCase){
            Toast.makeText(this, "missing uppercase", duration).show();
            return false;
        } else if(!foundLowerCase){
            Toast.makeText(this, "missing lowercase", duration).show();
            return false;
        }
        else if(!foundNumber){
            Toast.makeText(this, "missing number", duration).show();
            return false;
        }
        else if(!foundSpecial){
            Toast.makeText(this, "missing special Char", duration).show();
            return false;
        } else{
            return true;
        }


    }

}