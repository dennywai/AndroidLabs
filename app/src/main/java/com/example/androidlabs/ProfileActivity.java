package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int TEST_TOOLBAR = 2;
    private static final String ACTIVITY_NAME = "ProfileActivity";
    ImageButton mImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.e(ACTIVITY_NAME, "In function: onCreate");

        mImageButton = (ImageButton) findViewById(R.id.picture);
        mImageButton.setOnClickListener(click -> dispatchTakePictureIntent());

        Button chatroom = findViewById(R.id.chatroom);
        Intent ChatRoomActivity1 = new Intent(this, ChatRoomActivity1.class);
        chatroom.setOnClickListener(click -> startActivity(ChatRoomActivity1));

        Button weatherforecast = findViewById(R.id.weatherforecast);
        Intent WeatherForecast = new Intent(this, WeatherForecast.class);
        weatherforecast.setOnClickListener(click -> startActivity(WeatherForecast));

        Button toolbar = findViewById(R.id.toolbar_button);
        Intent testTB = new Intent(this, TestToolbar.class);
        toolbar.setOnClickListener(click -> startActivityForResult(testTB, TEST_TOOLBAR));
    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.e(ACTIVITY_NAME, "In function: onStart");
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
        if (requestCode == TEST_TOOLBAR && resultCode == 500) {
            finish();
        }
        Log.e(ACTIVITY_NAME, "In function: onActivityResult()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(ACTIVITY_NAME, "In function: onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(ACTIVITY_NAME, "In function: onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.e(ACTIVITY_NAME, "In function: onStop");

    } @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(ACTIVITY_NAME, "In function: onDestroy");
    }

}