package com.example.calendar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.calendar.Models.DayEvent;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.lang3.StringUtils;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

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
    PieChart pieChart;

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
        Map<String, String> monthToMonth = new LinkedHashMap<String, String>() {{
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
        ArrayList<DayEvent> events = new ArrayList<>();
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
        pieChart = tempView.findViewById(R.id.piechart);
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                events.clear();
                if (checkFieldNotEmpty(tempView, period1) && checkFieldNotEmpty(tempView, period2)) {
                    String firstMonth = String.valueOf(period1.getText());
                    String lastMonth = String.valueOf(period2.getText());
                    ArrayList<String> monthArray = new ArrayList<>();
                    boolean withinRange = false;
                    for (Map.Entry<String, String> entry : monthToMonth.entrySet()) {
                        String month = entry.getKey();
                        if (month.equals(firstMonth)) {
                            withinRange = true;
                        }
                        if (withinRange) {
                            monthArray.add(entry.getValue());
                        }
                        if (month.equals(lastMonth)) {
                            break;
                        }
                    }
                    eventsBd.orderByChild("userUid").equalTo(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (String month : monthArray) {
                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                    DayEvent dayEvent = childSnapshot.getValue(DayEvent.class);
                                    if (dayEvent.getMonth().equals(month)) {
                                        events.add(dayEvent);
                                    }
                                }
                            }
                            setData(events, monthArray);
                            Integer workingTime = 0;
                            Float result = (float) 0;
                            for (DayEvent event : events) {
                                workingTime += event.getHour();
                                result += (float) (event.getHour() * event.getSalaryPerHour());
                                if (event.getAdditionHour() != 0) {
                                    float tmp = event.getAdditionHour() - 1;
                                    result = (float) (result + (((tmp) * event.getSalaryPerHour()) * 2) + (event.getSalaryPerHour() * 1.5));
                                }
                            }
                            workingRow.setVisibility(View.VISIBLE);
                            salaryRow.setVisibility(View.VISIBLE);
                            workingHour.setText(String.valueOf(workingTime));
                            salary.setText(String.valueOf(result));

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });


        return tempView;
    }

    private void setData(ArrayList<DayEvent> events, ArrayList<String> month) {
        pieChart.clearChart();
        Random rnd = new Random();
        for (String mth : month) {
            ArrayList<DayEvent> temp = events.stream().filter(p -> p.getMonth().equals(mth)).collect(Collectors.toCollection(ArrayList::new));
            float result = (float) 0;
            for (DayEvent event : temp) {
                result = (float) (event.getHour() * event.getSalaryPerHour());
                if (event.getAdditionHour() != 0) {
                    float tmp = event.getAdditionHour() - 1;
                    result = (float) (result + (((tmp) * event.getSalaryPerHour()) * 2) + (event.getSalaryPerHour() * 1.5));
                }
            }
            pieChart.addPieSlice(
                    new PieModel(
                            mth,
                            result,
                            Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))));
        }
        pieChart.startAnimation();
    }
}