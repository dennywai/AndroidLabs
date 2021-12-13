package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

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

        progressbar = findViewById(R.id.progressbar);
        progressbar.setVisibility(View.VISIBLE);

        ForecastQuery fquery = new ForecastQuery();
        fquery.execute("https://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric",
                "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389");
    }

    private class ForecastQuery extends AsyncTask<String, Integer, String> {
        //String currentWeather;
        Double uvRating;
        String minTemp;
        String maxTemp;
        String currentTemp;
        Bitmap weatherIcon;
        String iconName;
        String fileName;
        HttpURLConnection connection;

        @Override
        protected String doInBackground(String... args) {
            try {
                //create a URL object of what server to contact:
                URL url = new URL(args[0]);

                //open the connection
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                //wait for data:
                InputStream response = urlConnection.getInputStream();

                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(response, "UTF-8");

                int eventType = xpp.getEventType(); //The parser is currently at START_DOCUMENT

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        //If you get here, then you are pointing at a start tag
                        if (xpp.getName().equals("temperature")) {
                            //If you get here, then you are pointing to a <Weather> start tag
                            currentTemp = xpp.getAttributeValue(null, "value");
                            publishProgress(25);
                            minTemp = xpp.getAttributeValue(null, "min");
                            publishProgress(50);
                            maxTemp = xpp.getAttributeValue(null, "max");
                            publishProgress(75);
                        }

                        else if (xpp.getName().equals("weather")) {

                            iconName = xpp.getAttributeValue(null, "icon");
                            fileName = iconName + ".png";
                            Log.i("tag", "Searching for file..." + fileName);

                            if (fileExistance(fileName)) {
                                Log.i("tag", "File found: " + fileName);
                                FileInputStream fis = null;
                                try {
                                    fis = openFileInput(fileName);
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }

                                weatherIcon = BitmapFactory.decodeStream(fis);
                            }
                                else {
                                Log.i("tag", "File not found: " + fileName);

                                URL urlIcon = new URL("https://openweathermap.org/img/w/" + fileName);
                                connection = (HttpURLConnection) urlIcon.openConnection();
                                connection.connect();

                                int responseCode = connection.getResponseCode();
                                if (responseCode == 200) {
                                    weatherIcon = BitmapFactory.decodeStream(connection.getInputStream());
                                    FileOutputStream outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
                                    weatherIcon.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    outputStream.flush();
                                    outputStream.close();
                                }
                            }
                            publishProgress(100);
                        }
                    }
                    eventType = xpp.next();
                }
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }


            try {
                URL url = new URL(args[1]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream response = urlConnection.getInputStream();

                //JSON reading:
                //Build the entire string response:
                BufferedReader reader = new BufferedReader(new InputStreamReader(response, StandardCharsets.UTF_8), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String report = sb.toString(); //result is the whole string

                // convert string to JSON:
                JSONObject uvReport = new JSONObject(report);

                //get the double associated with "value"
                uvRating = uvReport.getDouble("value");
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
            return "Done";
        }

        @Override
        protected void onProgressUpdate(Integer... args) {
            progressbar.setVisibility(View.VISIBLE);
            progressbar.setProgress(args[0]);
        }

        @Override
        protected void onPostExecute(String fromDoInBackground) {
            Log.i("HTTP", fromDoInBackground);
            currenttemp.setText("Current Temperature: " + currentTemp);
            mintemp.setText("Min. Temperature: " + minTemp);
            maxtemp.setText("Max Temperature: " + maxTemp);
            UV.setText("UV Rating: " + uvRating.toString());
            currentweather.setImageBitmap(weatherIcon);
            progressbar.setVisibility(View.INVISIBLE);
        }
        public boolean fileExistance(String fileName) {
            File file = getBaseContext().getFileStreamPath(fileName);
            return file.exists();
        }
    }
}
