package com.example.calendar;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.applandeo.materialcalendarview.CalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserFragment extends Fragment {

    CalendarView calendarView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View tempView = inflater.inflate(R.layout.fragment_user, container, false);
        calendarView = tempView.findViewById(R.id.calendarView);
        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(@NonNull EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
//                if (eventDay.getLabelColor$library_release() != R.color.red) {
//                    eventDay.setLabelColor$library_release(R.color.red);
//                } else {
//                    eventDay.setLabelColor$library_release(R.color.white);
//                }
//                List<Calendar> calendars = new ArrayList<>();
//                calendarView.setHighlightedDays(calendars);
//                clickedDayCalendar.set();
            }
        });

        return tempView;
    }
}