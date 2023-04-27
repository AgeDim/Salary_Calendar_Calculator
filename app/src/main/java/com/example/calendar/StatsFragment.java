package com.example.calendar;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.calendar.Models.DayEvent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StatsFragment extends Fragment {
    FirebaseDatabase db;
    DatabaseReference eventsBd;

    TextView workingHour;
    TextView salary;
    EditText period1;
    EditText period2;
    Button reload;
    TableRow workingRow;
    TableRow salaryRow;

    private boolean checkNumericValue(View view, EditText field) {
        if (StringUtils.isNumeric(field.getText())) {
            return true;
        } else {
            Toast.makeText(view.getContext(), "Значения введены некорректно", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private boolean checkFieldNotEmpty(View view, EditText editText) {
        if (editText.getText().length() == 0) {
            Toast.makeText(view.getContext(), "Значения основых полей пусты", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ArrayList<DayEvent> events = new ArrayList<>();
        Map<String, String> monthToMonth = new HashMap<String, String>() {{
            put("Январь", "January");
            put("Февраль", "February");
            put("Март", "March");
            put("Апрель", "April");
            put("Май", "May");
            put("Июнь", "June");
            put("Июль", "July");
            put("Август", "August");
            put("Сентябрь", "September");
            put("Октябрь", "October");
            put("Ноябрь", "November");
            put("Декабрь", "December");
        }};
        View tempView = inflater.inflate(R.layout.fragment_stats, container, false);
        db = FirebaseDatabase.getInstance();
        eventsBd = db.getReference("events");
        workingHour = tempView.findViewById(R.id.workingHour);
        workingRow = tempView.findViewById(R.id.workingRow);
        salary = tempView.findViewById(R.id.salary);
        salaryRow = tempView.findViewById(R.id.salaryRow);
        reload = tempView.findViewById(R.id.reload);
        period1 = tempView.findViewById(R.id.period1);
        period2 = tempView.findViewById(R.id.period2);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(tempView.getContext(), "click", Toast.LENGTH_SHORT);
                if (checkFieldNotEmpty(tempView, period1) && checkFieldNotEmpty(tempView, period2)) {
                    String firstMonth = monthToMonth.get(String.valueOf(period1.getText()));
                    String lastMonth = monthToMonth.get(String.valueOf(period2.getText()));
                    ArrayList<String> monthArray = new ArrayList<>();
                    boolean withinRange = false;
                    for (Map.Entry<String, String> entry : monthToMonth.entrySet()) {
                        String month = entry.getKey();
                        if (month.equals(firstMonth)) {
                            monthArray.add(entry.getValue());
                            withinRange = true;
                        }
                        if (withinRange) {
                            monthArray.add(entry.getValue());
                        }
                        if (month.equals(lastMonth)) {
                            monthArray.add(entry.getValue());
                            break;
                        }
                    }
                    Query query = eventsBd.orderByChild("userUid").equalTo(FirebaseAuth.getInstance().getUid());
                    for (String month : monthArray) {
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    if (snapshot.child("month").getValue().equals(month)) {
                                        events.add(snapshot.getValue(DayEvent.class));
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                    Integer workingTime = 0;
                    Float result = (float) 0;
                    for (DayEvent event : events) {
                        workingTime += event.getHour();
                        result = (float) (event.getHour() * event.getSalaryPerHour());
                        if (event.getAdditionHour() != 0) {
                            float tmp = event.getAdditionHour() - 1;
                            result = (float) (result + (((tmp) * event.getSalaryPerHour()) * 2) + (event.getSalaryPerHour() * 1.5));
                        }

                    }
                    workingHour.setText(String.valueOf(workingTime));
                    salary.setText(String.valueOf(result));
                    workingRow.setVisibility(View.VISIBLE);
                    salaryRow.setVisibility(View.VISIBLE);
                }
            }
        });


        return tempView;
    }
}