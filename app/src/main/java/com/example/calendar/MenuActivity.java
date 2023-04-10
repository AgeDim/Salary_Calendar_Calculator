package com.example.calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class MenuActivity extends AppCompatActivity {

    BottomNavigationBar bottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        bottomNavigationBar
                .setActiveColor(R.color.white)
                .setInActiveColor(R.color.light_purple)
                .setBarBackgroundColor(R.color.light_purple);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.stats, ""))
                .addItem(new BottomNavigationItem(R.drawable.history, ""))
                .addItem(new BottomNavigationItem(R.drawable.calc, ""))
                .addItem(new BottomNavigationItem(R.drawable.working_time, ""))
                .addItem(new BottomNavigationItem(R.drawable.user, ""))
                .setFirstSelectedPosition(4)
                .initialise();
    }
}