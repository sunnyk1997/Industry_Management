package com.desirestodesigns.complexion;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private static final String TAG = "TAG";
    private ArrayList<Employee> employeeArrayList;
//    AdapterView.OnItemClickListener itemClickListener;

    public MyAdapter(ArrayList<Employee> list) {
        employeeArrayList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rvitem, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Employee employee = employeeArrayList.get(position);
        holder.name.setText(employee.getEmpName());
        holder.desg.setText(employee.getEmpDesignation());
        holder.role.setText(employee.getEmpRole());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                employeeArrayList.remove(position);
                notifyDataSetChanged();
            }
        });
        Log.i(TAG, "onBindViewHolder method Completed");
    }

    @Override
    public int getItemCount() {
        if (employeeArrayList == null) {
            return 0;
        } else {
            return employeeArrayList.size();
        }
    }

    private void remove(int position) {
        employeeArrayList.remove(position);
        notifyItemRemoved(position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, desg, role;
        public ImageView edit,delete;
        //declare variables which are in the RecyclerView
        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv2);
            desg = itemView.findViewById(R.id.tv3);
            role = itemView.findViewById(R.id.tv4);

            delete = itemView.findViewById(R.id.imgb1);
        }
    }
}
