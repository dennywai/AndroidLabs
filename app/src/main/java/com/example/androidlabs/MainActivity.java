package com.example.androidlabs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView loads objects onto the screen.
        // Before this function, the screen is empty.

        setContentView(R.layout.activity_main_email);

        //Now that the screen was loaded, use findViewByid() to
        // get load the objects in Java:

        prefs = getSharedPreferences("FileName", Context.MODE_PRIVATE);
        String savedString = prefs.getString("EmailAddress", "");
        EditText typeField = findViewById(R.id.emailhint);
        typeField.setText(savedString);

        Button button = findViewById(R.id.login);

            Intent profileActivity = new Intent(this, ProfileActivity.class);

            button.setOnClickListener(click -> startActivity(profileActivity));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
        protected void onPause() {
            super.onPause();
            SharedPreferences sp = getSharedPreferences("EmailAddress", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor= sp.edit();
            editor.apply();
        }

            private void saveSharedPreferences(String stringToSave){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("EmailAddress", stringToSave);
            editor.commit();
        }
        }

            