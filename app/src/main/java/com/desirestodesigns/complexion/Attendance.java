package com.desirestodesigns.complexion;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Attendance extends AppCompatActivity {
    private static final String TAG = "TAG";
    RecyclerView recyclerView;
    AttendanceAdapter adapter;
    AttendanceAdapter adapter2;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    FirebaseFirestore firebaseFirestore;
    ArrayList<EmployeeAttendance> attendanceArrayList = new ArrayList<>();
    ArrayList<Employee> employeeArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        initializeUi();
        toolbarA();

        recyclerMethod(attendanceArrayList);
        readFromDb();
    }


    private void toolbarA() {
        // Attaching the layout to the toolbar object
        toolbar = findViewById(R.id.toolbar3);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar);
//        // Setting the title for an activity
//        getSupportActionBar().setTitle("Add Employee Details");
        //Used to add back button <- in the activity
        toolbar.setNavigationIcon(R.drawable.ic_backspace);
        //to go back to previous activity using back button
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent home = new Intent(Attendance.this, MainActivity.class);
                startActivity(home);
            }
        });
        Log.i(TAG, "toolBar method Completed in Attendance.java");
    }


    private void initializeUi() {
        TextView tv = findViewById(R.id.date_text);
        String date = new SimpleDateFormat("MM/DD/YYYY", Locale.getDefault()).format(new Date());
        tv.setText(date);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
    }

    private void readFromDb() {
        Log.i(TAG, "readFromDb Method Invoked in attendance class");
        firebaseFirestore.collection("Employees")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            employeeArrayList.clear();
                            List<DocumentSnapshot> eList = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot doc : eList) {
                                Employee employee = doc.toObject(Employee.class);
                                employeeArrayList.add(employee);
                                //countArrayList.add(employee);
                            }
                            for (Employee att : employeeArrayList) {
                                EmployeeAttendance employeeAttendance = new EmployeeAttendance();
                                employeeAttendance.setEmpName(att.getEmpName());
                                employeeAttendance.setEmpDesignation(att.getEmpDesignation());
                                attendanceArrayList.add(employeeAttendance);
                                Log.i(TAG, "Data is added to eAtt " + att.getEmpName());
                            }
                            //notify Adapter for Updated Data
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void recyclerMethod(ArrayList<EmployeeAttendance> arrayList) {
        recyclerView = findViewById(R.id.mrv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new AttendanceAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        Log.i(TAG, "recyclerMethod is Completed in Attendance.java ");

    }
}
