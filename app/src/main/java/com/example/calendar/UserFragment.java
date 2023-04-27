package com.example.calendar;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.skyhope.eventcalenderlibrary.CalenderEvent;
import com.skyhope.eventcalenderlibrary.listener.CalenderDayClickListener;
import com.skyhope.eventcalenderlibrary.model.DayContainerModel;
import com.skyhope.eventcalenderlibrary.model.Event;


public class UserFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View tempView = inflater.inflate(R.layout.fragment_user, container, false);
        CalenderEvent calenderEvent = tempView.findViewById(R.id.calender_event);

        calenderEvent.initCalderItemClickCallback(new CalenderDayClickListener() {
            @Override
            public void onGetDay(DayContainerModel dayContainerModel) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(tempView.getContext());
                dialog.setTitle("Day");
                LayoutInflater inflater = LayoutInflater.from(tempView.getContext());
                View eventWindow = inflater.inflate(R.layout.setevent_layout, null);
                dialog.setView(eventWindow);
                MaterialEditText workingTime = eventWindow.findViewById(R.id.workingTm);
                MaterialEditText hourSalary = eventWindow.findViewById(R.id.salary);
                MaterialEditText additionalHour = eventWindow.findViewById(R.id.additHour);
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                dialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Event event = new Event(dayContainerModel.getTimeInMillisecond(), "Test", Color.RED);
                        calenderEvent.addEvent(event);
                    }
                });
                dialog.show();
            }

        });
        return tempView;
    }

}
