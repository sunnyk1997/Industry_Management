package com.desirestodesigns.complexion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

public class ManagerDashboard extends AppCompatActivity {
    private static final String TAG = "TAG";
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_dashboard);
        uiMethods();
        toolBar();
    }

    private void toolBar() { // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar);
//        // Setting the title for an activity
//        getSupportActionBar().setTitle("Add Employee Details");
        //Used to add back button <- in the activity
        toolbar.setNavigationIcon(R.drawable.ic_backspace);
        //to go back to previous activity using back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(ManagerDashboard.this,MainActivity.class);
                startActivity(home);
            }
        });
        Log.i(TAG, "toolBar method Completed");
    }

    private void uiMethods() {
        toolbar=findViewById(R.id.toolbar7);
    }
}
