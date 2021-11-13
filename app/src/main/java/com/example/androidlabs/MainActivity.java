package com.example.androidlabs;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView loads objects onto the screen.
        // Before this function, the screen is empty.

        //setContentView(R.layout.activity_main_linear);
        //setContentView(R.layout.activity_main_relative);
        setContentView(R.layout.activity_main_grid);

        //Now that the screen was loaded, use findViewByid() to
        // get load the objects in Java:
        TextView text1 = findViewById(R.id.text1);

        EditText input = findViewById(R.id.input);

        Button button = findViewById(R.id.button);
        button.setOnClickListener( (click) -> Toast.makeText(MainActivity.this, getResources().getString(R.string.moreinfo), Toast.LENGTH_LONG).show());

            CheckBox checkbox = findViewById(R.id.checkbox);
            checkbox.setOnCheckedChangeListener((CompoundButton, click) -> {
                if (checkbox.isChecked()) {
                    Snackbar.make(checkbox, R.string.switchingON, Snackbar.LENGTH_LONG)
                            .setAction(R.string.undo, click2 -> checkbox.setChecked(false))
                            .show();
                } else {
                    Snackbar.make(checkbox, R.string.switchingOFF, Snackbar.LENGTH_LONG)
                            .setAction(R.string.undo, click2 -> checkbox.setChecked(true))
                            .show();
                }
            });
       Switch switched = findViewById(R.id.switched);
        switched.setOnCheckedChangeListener((CompoundButton, click) -> {
            if (switched.isChecked()) {
                Snackbar.make(switched, R.string.switchingOFF, Snackbar.LENGTH_LONG)
                        .setAction(R.string.undo, click2 -> switched.setChecked(false))
                        .show();
            } else {
                Snackbar.make(switched, R.string.switchingON, Snackbar.LENGTH_LONG)
                        .setAction(R.string.undo, click2 -> switched.setChecked(true))
                        .show();
            }
        });
    }}