package com.desirestodesigns.complexion;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class cDialog extends AppCompatDialogFragment {
    private EditText category;
    private EditText cName;
    private CategoryDialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.categorydetails,null);
        builder.setView(view)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String s1 = category.getText().toString();
                        String s2 = cName.getText().toString();
                        listener.applyTexts(s1,s2);
                    }
                });
        category = view.findViewById(R.id.category);
        cName = view.findViewById(R.id.cName);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (CategoryDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+
                    "must implement CategoryDialogListener");
        }
    }

    public interface CategoryDialogListener{
        void applyTexts(String category,String categoryName);
    }
}
