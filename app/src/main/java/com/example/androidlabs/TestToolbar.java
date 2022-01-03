package com.example.androidlabs;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class TestToolbar extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);

        Toolbar tBar = findViewById(R.id.toolbar);
        setSupportActionBar(tBar);

        DrawerLayout drawer = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, tBar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu, menu);
            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            String message = null;
            //Look at your menu XML file. Put a case for every id in that file:
            switch(item.getItemId())
            {
                //what to do when the menu item is selected:
                case R.id.item1:
                    message = "You clicked on Item 1";
                    break;
                case R.id.item2:
                    message = "You clicked on Item 2";
                    break;
                case R.id.item3:
                    message = "You clicked on Item 3";
                    break;
                case R.id.item4:
                    message = "You clicked on the Overflow Menu";
                    break;
            }
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            return true;
        }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent navigate;
        switch(item.getItemId())
        {
            case R.id.gotoChat:
                navigate = new Intent(this, ChatRoomActivity1.class);
                startActivity(navigate);
                break;
            case R.id.gotoWeather:
                navigate = new Intent(this, WeatherForecast.class);
                startActivity(navigate);
                break;
            case R.id.gotoLogin:
                setResult(500);
                finish();
                break;
        }
        return true;
    }

}