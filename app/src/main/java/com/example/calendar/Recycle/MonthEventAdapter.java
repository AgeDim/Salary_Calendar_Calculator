package com.example.calendar.Recycle;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.calendar.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MonthEventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<String> months;

    private Map<String, Integer> monthColors;
    private Context context;

    private Map<String, String> monthToMonth = new LinkedHashMap<String, String>() {{
        put("January", "Январь");
        put("February", "Февраль");
        put("March", "Март");
        put("April", "Апрель");
        put("May", "Май");
        put("June", "Июнь");
        put("July", "Июль");
        put("August", "Август");
        put("September", "Сентябрь");
        put("October", "Октябрь");
        put("November", "Ноябрь");
        put("December", "Декабрь");
    }};

    public MonthEventAdapter(Context context, ArrayList<String> months, Map<String, Integer> monthColors){
        this.context = context;
        this.months = months;
        this.monthColors = monthColors;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.month_event, parent, false);
        return new ShowViewHolderMonth(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String month = months.get(position);
        ((ShowViewHolderMonth) holder).monthName.setText(monthToMonth.get(month));
        ((MonthEventAdapter.ShowViewHolderMonth) holder).monthColor.setBackgroundColor(monthColors.get(month));
    }

    @Override
    public int getItemCount() {
        return months.size();
    }

    public static class ShowViewHolderMonth extends RecyclerView.ViewHolder {
        public ShowViewHolderMonth(View itemView) {
            super(itemView);
        }

        View monthColor = itemView.findViewById(R.id.month_color);

        TextView monthName = itemView.findViewById(R.id.month_name);

    }
}
