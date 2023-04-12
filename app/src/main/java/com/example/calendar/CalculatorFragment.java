package com.example.calendar;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import org.apache.commons.lang3.StringUtils;

public class CalculatorFragment extends Fragment {
    Button calculateBtn;
    EditText period;
    EditText wrkTime;
    EditText hourSalary;
    EditText additional;
    TextView salary;

    RelativeLayout root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = container.findViewById(R.id.root_layer);
        calculateBtn = container.findViewById(R.id.CalculateBtn);
        period = container.findViewById(R.id.period);
        wrkTime = container.findViewById(R.id.workingTime);
        hourSalary = container.findViewById(R.id.hourSalary);
        additional = container.findViewById(R.id.additionalHour);
        salary = container.findViewById(R.id.salary);

        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String periodStr = period.getText().toString();
                String wrkTimeStr = wrkTime.getText().toString();
                String additionalStr = additional.getText().toString();
                String hourSalaryStr = hourSalary.getText().toString();
                float result = 0;
                if(StringUtils.isNumeric(periodStr)){
                    if(StringUtils.isNumeric(wrkTimeStr)){
                        if(StringUtils.isNumeric(hourSalaryStr)){
                            if(StringUtils.isNumeric(additionalStr)){
                                result = Float.parseFloat(periodStr) * Float.parseFloat(wrkTimeStr) * Float.parseFloat(hourSalaryStr);
                                if(Float.parseFloat(additionalStr) != 0){
                                    float tmp = Float.parseFloat(additionalStr) - 1;
                                result = result + (((tmp-1) * Float.parseFloat(hourSalaryStr)) * 2) + (Float.parseFloat(hourSalaryStr) * 2);
                                }
                            } else{
                            Snackbar.make(root, "Additional houraw# must be a number", Snackbar.LENGTH_LONG).show();
                            }
                        }else{
                            Snackbar.make(root, "Hour salary must be a number", Snackbar.LENGTH_LONG).show();
                        }
                    }else{
                        Snackbar.make(root, "Work time must be a number", Snackbar.LENGTH_LONG).show();
                    }
                } else{
                    Snackbar.make(root, "Period must be a number", Snackbar.LENGTH_LONG).show();
                }
                salary.setText(Float.toString(result));
            }
        });
        return inflater.inflate(R.layout.fragment_calculator, container, false);
    }
}