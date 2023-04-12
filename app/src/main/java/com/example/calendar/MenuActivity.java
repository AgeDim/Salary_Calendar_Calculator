package com.example.calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.CalendarView;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

public class MenuActivity extends AppCompatActivity {

    BottomNavigationBar bottomNavigationBar;
    StatsFragment stats = new StatsFragment();
    HistoryFragment history = new HistoryFragment();
    CalculatorFragment calculatorFragment = new CalculatorFragment();
    WorkingTimeFragment workingTimeFragment = new WorkingTimeFragment();
    UserFragment userFragment = new UserFragment();

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
        getSupportFragmentManager().beginTransaction().replace(R.id.container, userFragment).commit();

        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                switch (position){
                    case (0):
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, stats).commit();
                        break;
                    case (1):
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, history).commit();
                        break;
                    case (2):
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, calculatorFragment).commit();
                        break;
                    case (3):
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, workingTimeFragment).commit();
                        break;
                    case (4):
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, userFragment).commit();
                        break;
                }

            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });
    }
    }
