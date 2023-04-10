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
                .setActiveColor(R.color.primary)
                .setInActiveColor("#ffccff")
                .setBarBackgroundColor("#ffccff");
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.calc, ""))
                .addItem(new BottomNavigationItem(R.drawable.user, ""))
                .addItem(new BottomNavigationItem(R.drawable.history, ""))
                .addItem(new BottomNavigationItem(R.drawable.working_time, ""))
                .setFirstSelectedPosition(0)
                .initialise();
    }
}