package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EmptyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empty);

        Bundle bundle = getIntent().getExtras();
            DetailFragment dFragment = new DetailFragment(); //add a DetailFragment
            dFragment.setArguments(bundle); //pass it a bundle for information
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentdetail, dFragment) //Add the fragment in FrameLayout
                    .commit(); //actually load the fragment.
    }
}