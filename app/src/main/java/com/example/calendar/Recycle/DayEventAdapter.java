package com.example.calendar.Recycle;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendar.Models.DayEvent;
import com.example.calendar.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

public class DayEventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<DayEvent> dayEventList = new ArrayList<DayEvent>();
    private Context context;

    private DatabaseReference events = FirebaseDatabase.getInstance().getReference("events");

    public DayEventAdapter(Context context, ArrayList<DayEvent> messageList) {
        this.context = context;
        this.dayEventList = messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.day_event, parent, false);
        return new DayEventAdapter.ShowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        DayEvent currentDay = dayEventList.get(position);
        ((ShowViewHolder) holder).additionalHour.setText(String.valueOf(currentDay.getAdditionHour()));
        ((ShowViewHolder) holder).salaryPerHour.setText(String.valueOf(currentDay.getSalaryPerHour()));
        ((ShowViewHolder) holder).workingTime.setText(String.valueOf(currentDay.getHour()));
        ((ShowViewHolder) holder).day.setText(String.valueOf(currentDay.getDayDate()));
        ((ShowViewHolder) holder).salary.setText(String.valueOf(calculateSalary(currentDay) + " Руб."));

        ((ShowViewHolder) holder).deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query query = events.orderByChild("userUid_dayDate").equalTo(FirebaseAuth.getInstance().getUid() + "_" + currentDay.getDayDate());
                query.getRef().removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "День удален из истории", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return dayEventList.size();
    }

    public static class ShowViewHolder extends RecyclerView.ViewHolder {
        public ShowViewHolder(View itemView) {
            super(itemView);
        }

        TextView salaryPerHour = itemView.findViewById(R.id.hourSalaryValue);
        TextView workingTime = itemView.findViewById(R.id.workingHourValue);
        TextView additionalHour = itemView.findViewById(R.id.additionalHourValue);
        TextView salary = itemView.findViewById(R.id.salaryValue);
        TextView day = itemView.findViewById(R.id.dayTextName);

        ImageButton deleteButton = itemView.findViewById(R.id.dayDelete);

    }

    public static String calculateSalary(DayEvent event) {
        String wrkTimeStr = String.valueOf(event.getSalaryPerHour());
        String additionalStr = String.valueOf(event.getAdditionHour());
        String hourSalaryStr = String.valueOf(event.getHour());
        float result = 0;
        result = Float.parseFloat(wrkTimeStr) * Float.parseFloat(hourSalaryStr);
        if (Float.parseFloat(additionalStr) != 0) {
            float tmp = Float.parseFloat(additionalStr) - 1;
            result = (float) (result + (((tmp) * Float.parseFloat(hourSalaryStr)) * 2) + (Float.parseFloat(hourSalaryStr) * 1.5));
        }
        return String.valueOf(result);
    }

}
