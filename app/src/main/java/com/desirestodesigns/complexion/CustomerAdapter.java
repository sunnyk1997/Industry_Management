package com.desirestodesigns.complexion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.MyViewHolder4> {
    private static final String TAG = "TAG";
    private ArrayList<Customer> customerArrayList;
    public CustomerAdapter(ArrayList<Customer> list) {
        customerArrayList = list;
    }
    @NonNull
    @Override
    public MyViewHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.customeritem, parent, false);
        MyViewHolder4 myViewHolder = new MyViewHolder4(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder4 holder, int position) {
        final Customer customer= customerArrayList.get(position);
        holder.name.setText(customer.getmCustomerName());
        holder.id.setText(customer.getmCustomerId());
        holder.managerName.setText(customer.getmCustomerManager());
    }

    @Override
    public int getItemCount() {
        if (customerArrayList == null) {
            return 0;
        } else {
            return customerArrayList.size();
        }
    }

    public class MyViewHolder4 extends RecyclerView.ViewHolder {
        public TextView name, id, managerName;
//        public ImageView edit,delete;
        public MyViewHolder4(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.custName);
            id=itemView.findViewById(R.id.custId);
            managerName=itemView.findViewById(R.id.custManager);
        }
    }
}
