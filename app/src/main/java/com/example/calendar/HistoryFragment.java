package com.example.calendar;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.calendar.Models.DayEvent;
import com.example.calendar.Recycle.DayEventAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {
    DatabaseReference events;
    ArrayList<DayEvent> dayEventList = new ArrayList<DayEvent>();
    DayEventAdapter dayEventAdapter;
    RecyclerView dayEvent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View tempView =inflater.inflate(R.layout.fragment_history, container, false);
        dayEventAdapter = new DayEventAdapter(tempView.getContext(), dayEventList);
        events = FirebaseDatabase.getInstance().getReference();
        dayEvent = tempView.findViewById(R.id.historyView);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(tempView.getContext());
        dayEvent.setLayoutManager(layoutManager);
        dayEvent.setAdapter(dayEventAdapter);
        events.child("events").orderByChild("userUid").equalTo(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
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
        return tempView;
    }
}