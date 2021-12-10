package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WeatherForecast extends AppCompatActivity {

    ImageView currentweather;
    TextView currenttemp;
    TextView mintemp;
    TextView maxtemp;
    TextView UV;
    ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        currentweather = findViewById(R.id.currentweather);
        currenttemp = findViewById(R.id.currenttemp);
        mintemp = findViewById(R.id.mintemp);
        maxtemp = findViewById(R.id.maxtemp);
        UV = findViewById(R.id.UV);

        progressbar.setVisibility(View.VISIBLE);
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        String uv;
        String min;
        String max;
        String currentTemp;
        Bitmap weather;

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... args) {
            progressbar.setVisibility(View.VISIBLE);
            progressbar.setProgress(args[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            currenttemp.setText("Current Temperature is: " + currentTemp);
            mintemp.setText("Min Temperature is: " + min);
            maxtemp.setText("Max Temperature is: " + max);
            UV.setText("UV Rating is: " + uv);
            currentweather.setImageBitmap(weather);

            progressbar.setVisibility(View.INVISIBLE);
        }
    }
}