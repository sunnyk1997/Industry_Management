package com.desirestodesigns.complexion;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private static final String TAG = "MyAdapter";
    private ArrayList<Employee> employeeArrayList;
    private OnItemClickListener mListener;
//    String docId;

    public interface OnItemClickListener {
        void onDeleteClick(String documentId);
        void onEditClick(String documentId);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public MyAdapter(ArrayList<Employee> list) {
        employeeArrayList = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.rvitem, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view, mListener);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,  int position) {
        final Employee employee = employeeArrayList.get(position);
//        final String docId;
//        docId = employee.getDocumentId();
//        Log.i(TAG, "After "+docId);
        holder.name.setText(employee.getEmpName());
        holder.desg.setText(employee.getEmpDesignation());
        holder.role.setText(employee.getEmpRole());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit(employee.getDocumentId(),mListener);
            }
        });
                    holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    delete(employee.getDocumentId(),mListener);
                }
            });
        Log.i(TAG, "onBindViewHolder method Completed");
    }

    private void delete(String documentId, OnItemClickListener listener) {
        if (listener != null) {
            if ( documentId != null) {
                listener.onDeleteClick(documentId);
                Log.i(TAG, "test  " +documentId);
                Log.i(TAG, "onDeleteClick METHOD IS CLICKED AND CONTROL SEND TO MAIN ACTIVITY");
            }
        }
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
    private void edit(String docId, final OnItemClickListener listener){
        if (listener != null) {

            if ( docId != null) {
                Log.i(TAG, "test  " +docId);
                listener.onEditClick(docId);
            }
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, desg, role;
        public ImageButton edit, delete;

        //declare variables which are in the RecyclerView
        public MyViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            // find view ids here


            name = itemView.findViewById(R.id.tv2);
            desg = itemView.findViewById(R.id.tv3);
            role = itemView.findViewById(R.id.tv4);
            delete = itemView.findViewById(R.id.imgb1);
            edit = itemView.findViewById(R.id.imgb2);


        }
    }
}
