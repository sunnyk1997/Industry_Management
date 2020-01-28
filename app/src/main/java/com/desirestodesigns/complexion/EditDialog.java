package com.desirestodesigns.complexion;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class EditDialog extends AppCompatDialogFragment {
    private EditDialogListener listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Attention!")
                .setMessage("Are you sure to edit this Employee details?")
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onEditYesClicked();
                    }
                });

        return builder.create();
    }
    public interface EditDialogListener{
        void onEditYesClicked();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (EditDialogListener) context;
        }catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + "must implement ExampleDialogListener");
        }
    }
}
