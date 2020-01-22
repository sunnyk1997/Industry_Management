package com.desirestodesigns.complexion;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.MyViewHolder3> {
    private static final String TAG = "TAG";
    private ArrayList<IncomeExpense> incomeExpensesArrayList;
    public TransactionAdapter(ArrayList<IncomeExpense> list) {
        incomeExpensesArrayList = list;
    }
    @NonNull
    @Override
    public TransactionAdapter.MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ieitem, parent, false);
        MyViewHolder3 myViewHolder3 = new MyViewHolder3(view);
        return myViewHolder3;
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionAdapter.MyViewHolder3 holder, int position) {
        final IncomeExpense incomeExpense = incomeExpensesArrayList.get(position);
        holder.transaction.setText(incomeExpense.getTranscationType());
        holder.description.setText(incomeExpense.getDescription());
        holder.amount.setText(incomeExpense.getAmount());
    }

    @Override
    public int getItemCount() {
        if (incomeExpensesArrayList == null) {
            return 0;
        } else {
            return incomeExpensesArrayList.size();
        }
    }

    public class MyViewHolder3 extends RecyclerView.ViewHolder {
        public TextView transaction, description,amount;
        public MyViewHolder3(@NonNull View itemView) {
            super(itemView);
            transaction = itemView.findViewById(R.id.transaction2);
            description = itemView.findViewById(R.id.description2);
            amount = itemView.findViewById(R.id.amount2);
        }
    }
}
