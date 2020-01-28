package com.desirestodesigns.complexion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddEmployee extends AppCompatActivity {
    private static final String TAG = "Add Employee";
    Toolbar toolbar;
    EditText meName, mpNo, mrole, mSalary, mdWages, moTime, mleaves;
    Spinner mDesg;
    String docId;
    ToggleButton mSalType;
    Employee employee;
    Button mBtn;
    String ename, epno, designation, erole, msal, dwage, overTime, absent;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        uiMethods();
        toolBar();
        docId = getIntent().getStringExtra("DOCUMENT_ID");
        Log.i(TAG, "retr vale test" + docId);
        if (!TextUtils.isEmpty(docId)) {
            Toast.makeText(this, docId, Toast.LENGTH_SHORT).show();
            editItem(docId);
        }else
        {
            DocumentReference ref = firebaseFirestore.collection("Employees").document();
            docId = ref.getId();
        }
    }

    private void toolBar() {
        // Attaching the layout to the toolbar object
        toolbar = findViewById(R.id.toolbar2);
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
                Intent home = new Intent(AddEmployee.this, MainActivity.class);
                startActivity(home);
            }
        });
        Log.i(TAG, "toolBar method Completed");
    }

    private void uiMethods() {
        meName = findViewById(R.id.ed1);
        mpNo = findViewById(R.id.ed2);
        mDesg = findViewById(R.id.spinner1);
        mrole = findViewById(R.id.ed3);
        mSalType = findViewById(R.id.togglemd);
        mSalary = findViewById(R.id.ed4);
        mdWages = findViewById(R.id.ed5);
        moTime = findViewById(R.id.ed6);
        mleaves = findViewById(R.id.ed7);
        mBtn = findViewById(R.id.btn2);

        firebaseFirestore = FirebaseFirestore.getInstance();
        collectionReference = firebaseFirestore.collection("Employees");
        mSalType.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    // The toggle is enabled
                    //make the daily wage field to visible and monthly field to invisible state
                    mSalary.setVisibility(View.GONE);
                    mleaves.setVisibility(View.GONE);
                    mdWages.setVisibility(View.VISIBLE);
                    mSalary.setText("0");
                    mleaves.setText("0");
                    mdWages.setText("");

                } else {
                    // The toggle is disabled
                    mSalary.setVisibility(View.VISIBLE);
                    mleaves.setVisibility(View.VISIBLE);
                    mdWages.setVisibility(View.GONE);
                    mdWages.setText("0");
                    mSalary.setText("");
                    mleaves.setText("");
                }
            }
        });


        Log.i(TAG, "uiInitialization method Completed in Activity2");
    }

    public void saveButton(View view) {
        Log.i(TAG, "Entered into Save loop");
            Log.i(TAG, "EDITED DATA WILL BE SENT TO DATABASE");
            ename = meName.getText().toString();
            epno = mpNo.getText().toString();
            designation = mDesg.getSelectedItem().toString();
            erole = mrole.getText().toString();
            msal = mSalary.getText().toString();
            dwage = mdWages.getText().toString();
            overTime = moTime.getText().toString();
            absent = mleaves.getText().toString();
        if ((!TextUtils.isEmpty(ename)) && (!TextUtils.isEmpty(epno)) && ((!TextUtils.isEmpty(designation))
//                && (!TextUtils.isEmpty(msal))&& (!TextUtils)
//                    ||(!TextUtils.isEmpty(erole))) && (msal!=null) && (dwage!=null)&& (overTime!=null)
//                &&(absent!=null)
//                &&(TextUtils.isEmpty(msal)|| TextUtils.isEmpty(dwage))
        )) {
            Log.i(TAG, "Entered in to IF loop");
            employeeObject();
        } else {

            Log.i(TAG, "Entered into ELSE loop");
            Toast.makeText(this, "Please enter appropriate data", Toast.LENGTH_LONG).show();
            return;
        }

    }

    private void employeeObject() {
        employee = new Employee();
        Log.i(TAG, "Employee object is created");
        employee.setEmpName(ename);
        employee.setPhoneNum(Long.parseLong(epno));
        employee.setEmpDesignation(designation);
        employee.setEmpRole(erole);
        employee.setMonSal(Long.parseLong(msal));
        employee.setdWage(Integer.parseInt(dwage));
        employee.setOt(Integer.parseInt(overTime));
        employee.setLeaves(Integer.parseInt(absent));
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy  hh:mm:ss");
        String currentDate = simpleDateFormat.format(new Date());
        Log.i(TAG, "Current Timestamp: " + currentDate);
        employee.setDate(currentDate);
        employee.setDocumentId(docId);
        //Add the employee object to the employeeArrayList
        //employeeArrayList.add(employee);
        //Notify the Adapter that new data is available
        //adapter.notifyDataSetChanged();
        sendToDb(docId);
    }

    private void sendToDb(final String documentId) {
        Log.i(TAG, "sendToDb method is invoked");
        collectionReference.document(docId)
                .set(employee).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                clearUi();
                Log.i(TAG, "clearUi method is executed");
                finish();
                Log.i(TAG, "Add Employee Activity is closed and returns to Main Activity");
                Toast.makeText(AddEmployee.this, "Data successfully added", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddEmployee.this, "Issue while adding data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearUi() {
        meName.setText("");
        // pNo.setText("");
        mrole.setText("");
        mSalary.setText("");
        mdWages.setText("");
        moTime.setText("");
        mleaves.setText("");
    }

    private void editItem(String dId) {
        collectionReference.document(dId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                //editedEmployeeArrayList.clear();
                if (documentSnapshot != null) {
                    Employee editedEmployee = new Employee();
                    editedEmployee = documentSnapshot.toObject(Employee.class);

                        meName.setText(editedEmployee.getEmpName());
                        mpNo.setText(String.valueOf(editedEmployee.getPhoneNum()));
                        //set Spinner value here
                        mrole.setText(editedEmployee.getEmpRole());
                        mSalary.setText(String.valueOf(editedEmployee.getMonSal()));
                        mdWages.setText(String.valueOf(editedEmployee.getdWage()));
                        moTime.setText(String.valueOf(editedEmployee.getOt()));
                        mleaves.setText(String.valueOf(editedEmployee.getLeaves()));



                }

            }
        });
    }
}

