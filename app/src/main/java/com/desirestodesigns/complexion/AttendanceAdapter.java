package com.desirestodesigns.complexion;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.MyViewHolder> {
    private static final String TAG = "TAG";
    public RadioGroup radioGroup;
    String s;
    private ArrayList<EmployeeAttendance> attendanceArrayList;

    public AttendanceAdapter(ArrayList<EmployeeAttendance> list) {
        attendanceArrayList = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.mrvitem, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {
        final EmployeeAttendance attendance = attendanceArrayList.get(position);
        holder.name.setText(attendance.getEmpName());
        holder.desg.setText(attendance.getEmpDesignation());

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                RadioButton rb = (RadioButton) radioGroup.findViewById(i);
                s = rb.getText().toString();
                Log.i(TAG, "String " + s);
                if (s.equals("Present")) {
                    int j = 1;
                    attendanceArrayList.get(position).setPresent(j);
                    Log.i(TAG, "Present " + j);
                } else if (s.equals("Absent")) {
                    int j = 0;
                    attendanceArrayList.get(position).setAbsent(j);
                    Log.i(TAG, "Absent " + j);
                } else if (s.equals("1.5")) {
                    double j = 1.5;
                    attendanceArrayList.get(position).setOne_and_half(j);
                    Log.i(TAG, "One ad half " + j);
                } else {
                    int j = 2;
                    attendanceArrayList.get(position).setTwo(j);
                    Log.i(TAG, "two " + j);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (attendanceArrayList == null) {
            return 0;
        } else {
            return attendanceArrayList.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, desg;

        //declare variables which are in the RecyclerView
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.empname);
            desg = itemView.findViewById(R.id.empdesg);
            radioGroup = itemView.findViewById(R.id.attendance);
//            radioGroup.clearCheck();

        }
    }
}
