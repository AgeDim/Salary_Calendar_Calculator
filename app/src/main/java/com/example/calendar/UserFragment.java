package com.example.calendar;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendar.Models.DayEvent;
import com.example.calendar.Recycle.DayEventAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.skyhope.eventcalenderlibrary.CalenderEvent;
import com.skyhope.eventcalenderlibrary.listener.CalenderDayClickListener;
import com.skyhope.eventcalenderlibrary.model.DayContainerModel;
import com.skyhope.eventcalenderlibrary.model.Event;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;


public class UserFragment extends Fragment {

    FirebaseDatabase db;
    DatabaseReference events;
    Button saveDayInfoButton;
    EditText hourRate;
    EditText workedHours;
    EditText extraHours;
    EditText eventText;
    EditText comment;
    TableLayout inputTable;
    TextView selectedDayTextView;
    ScrollView scrollViewHistory;

    ArrayList<DayEvent> dayEventList = new ArrayList<DayEvent>();

    DayEventAdapter dayEventAdapter;

    RecyclerView dayEvent;
    ImageButton imageButton;

    private void checkNumericValue(View view, EditText field) {
        if (StringUtils.isNumeric(field.getText())) {
            return;
        } else {
            Toast.makeText(view.getContext(), "Значения введены некорректно", Toast.LENGTH_LONG).show();
        }
    }

    private void checkFieldNotEmpty(View view, EditText editText) {
        if (editText.getText().length() == 0) {
            Toast.makeText(view.getContext(), "Значения основых полей пусты", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View tempView = inflater.inflate(R.layout.fragment_user, container, false);
        db = FirebaseDatabase.getInstance();
        events = db.getReference("events");
        CalenderEvent calenderEvent = tempView.findViewById(R.id.calender_event);
        saveDayInfoButton = tempView.findViewById(R.id.save);
        hourRate = tempView.findViewById(R.id.hour_rate);
        workedHours = tempView.findViewById(R.id.worked_hours);
        extraHours = tempView.findViewById(R.id.extra_hours);
        eventText = tempView.findViewById(R.id.event);
        comment = tempView.findViewById(R.id.comment);
        inputTable = tempView.findViewById(R.id.input_table);
        selectedDayTextView = tempView.findViewById(R.id.selected_day);

        dayEventAdapter = new DayEventAdapter(tempView.getContext(), dayEventList);
        scrollViewHistory = tempView.findViewById(R.id.history_scroll);
        dayEvent = tempView.findViewById(R.id.history_recycler);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(tempView.getContext());
        dayEvent.setLayoutManager(layoutManager);
        dayEvent.setAdapter(dayEventAdapter);
        imageButton = tempView.findViewById(R.id.icon_button);
        Calendar calendar = Calendar.getInstance();


        scrollViewHistory.setVisibility(View.VISIBLE);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputTable.setVisibility(View.INVISIBLE);
                saveDayInfoButton.setVisibility(View.INVISIBLE);
                scrollViewHistory.setVisibility(View.VISIBLE);
                selectedDayTextView.setText("Информация");

            }
        });

        calenderEvent.initCalderItemClickCallback(new CalenderDayClickListener() {
            @Override
            public void onGetDay(DayContainerModel dayContainerModel) {
                inputTable.setVisibility(View.VISIBLE);
                saveDayInfoButton.setVisibility(View.VISIBLE);
                selectedDayTextView.setText(dayContainerModel.getDate());
                scrollViewHistory.setVisibility(View.INVISIBLE);
                Query query = events.orderByChild("userUid").equalTo(FirebaseAuth.getInstance().getUid());
                query.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        dayEventList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            DayEvent messages = ds.getValue(DayEvent.class);
                            dayEventList.add(messages);
                        }
                        dayEventAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                saveDayInfoButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        checkNumericValue(tempView, hourRate);
                        checkNumericValue(tempView, workedHours);
                        checkNumericValue(tempView, extraHours);
                        checkFieldNotEmpty(tempView, hourRate);
                        checkFieldNotEmpty(tempView, workedHours);
                        DayEvent event = new DayEvent(FirebaseAuth.getInstance().getCurrentUser().getUid(),
                                dayContainerModel.getDate(),
                                Integer.parseInt(String.valueOf(hourRate.getText())),
                                Integer.parseInt(String.valueOf(workedHours.getText())),
                                Integer.parseInt(String.valueOf(extraHours.getText())),
                                String.valueOf(eventText.getText()),
                                String.valueOf(comment.getText()), dayContainerModel.getMonth());
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    if (snapshot.child("dayDate").getValue().equals(dayContainerModel.getDate())) {
                                        snapshot.getRef().setValue(event);
                                        return;
                                    }
                                }
                                events.push().setValue(event);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        inputTable.setVisibility(View.INVISIBLE);
                        saveDayInfoButton.setVisibility(View.INVISIBLE);
                        scrollViewHistory.setVisibility(View.VISIBLE);
                        selectedDayTextView.setText("Информация");
                    }

                });
            }
        });
        return tempView;
    }

}
