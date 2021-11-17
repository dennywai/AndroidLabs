package com.example.androidlabs;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    SharedPreferences email = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView loads objects onto the screen.
        // Before this function, the screen is empty.

        setContentView(R.layout.activity_main_email);

        getSharedPreferences("EmailAddress", Context.MODE_PRIVATE);
        SharedPreferences email;

        email = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        String savedString = email.getString("EmailAddress", "");
        EditText typeField = findViewById(R.id.emailhint);
        typeField.setText(savedString);

        //Now that the screen was loaded, use findViewByid() to
        // get load the objects in Java:
        EditText input = findViewById(R.id.emailhint);
        TextView text = findViewById(R.id.enteremail);

    }
        @Override
        protected void onPause() {
            super.onPause();
            SharedPreferences sp = getSharedPreferences("EmailAddress", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor= sp.edit();
        }

            private void saveSharedPreferences(String stringToSave){
            SharedPreferences.Editor saveEmail = email.edit();
            saveEmail.putString("EmailAddress", stringToSave);
            saveEmail.commit();
        }
        }

            