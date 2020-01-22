package com.desirestodesigns.complexion;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyHolder> {
    private static final String TAG = "TAG";
    private ArrayList<Category> categoryArrayList;
    public CategoryAdapter(ArrayList<Category> list) {
        categoryArrayList = list;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.citem,parent,false);
        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        final Category category = categoryArrayList.get(position);
        holder.category.setText(category.getCategory());
        holder.categoryName.setText(category.getCategoryName());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryArrayList.remove(position);
                notifyDataSetChanged();
            }
        });
        Log.i(TAG, "onBindViewHolder method Completed");
    }

    @Override
    public int getItemCount() {
        if (categoryArrayList == null) {
            return 0;
        } else {
            return categoryArrayList.size();
        }

    }

    public class MyHolder extends RecyclerView.ViewHolder {
        public TextView category,categoryName;
        public ImageView edit,delete;
        //declare variables which are in the RecyclerView
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.categ);
            categoryName = itemView.findViewById(R.id.categName);

            delete = itemView.findViewById(R.id.imgb3);
        }
    }
}
