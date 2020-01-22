package com.desirestodesigns.complexion;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TAG";
    TextView mEmpCount,mInfo;
    Toolbar toolbar;
    DrawerLayout dl;
    ActionBarDrawerToggle abdt;
    NavigationView nav_view;
    MyAdapter adapter;
    RecyclerView recyclerView;
    Employee employee;
    ArrayList<Employee> employeeArrayList = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    FirebaseFirestore firebaseFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeUi();
        toolBar();
        readFromDb();
        recyclerMethod(employeeArrayList);
        Log.i(TAG, "onCreate Method Completed");
    }

    private void count(ArrayList<Employee> countArrayList) {

        int i = countArrayList.size();
        //        int i = employeeArrayList.size();
        Log.i(TAG, "count thing "+ i);
        Log.i(TAG, "count Method is invoked");
        if(i==1) mEmpCount.setText(i+" Employee");
        if (i !=0){
            mInfo.setText("");
            mEmpCount.setText(i+" Employees");
        }
    }

    private void readFromDb(){
        Log.i(TAG, "readFromDb Method Invoked");
        firebaseFirestore.collection("Employees")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            employeeArrayList.clear();
                            List<DocumentSnapshot> eList = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot doc : eList){
                             Employee employee = doc.toObject(Employee.class);
                             employeeArrayList.add(employee);
                             //countArrayList.add(employee);
                            }
                            //notify Adapter for Updated Data
                            adapter.notifyDataSetChanged();
                            count(employeeArrayList);
                        }
                    }
                });
    }
    private void sendToDb() {
        Log.i(TAG, "sendToDb method is invoked");
        firebaseFirestore.collection("Employees")
                .document()
                .set(employee)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(MainActivity.this, "Data successfully added", Toast.LENGTH_SHORT).show();
                        readFromDb();
                    }
                });
    }

    //If the user isn't logged in then they will be redirected to login activity
    @Override
    protected void onStart() {
        super.onStart();
        if (mCurrentUser == null) {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
        }
    }

    //Initializes Ui elements
    private void initializeUi() {
        mEmpCount = findViewById(R.id.tv1);
        mInfo = findViewById(R.id.info);
        toolbar = findViewById(R.id.toolbar1);
        dl = findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        nav_view = findViewById(R.id.nav_view);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        Log.i(TAG, "initializationUi method Completed in Activity1");
    }

    //Method that replaces Actionbar with Toolbar
    private void toolBar() {
        //toolbar =  findViewById(R.id.toolbar1);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
        dl.addDrawerListener(abdt);
        abdt.setDrawerIndicatorEnabled(true);
        abdt.syncState();
        //Enable Hamburger button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
        //METHOD TO PERFORM ACTIONS ON ELEMENTS IN THE NAVIGATION DRAWER
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.item1) {
                    Toast.makeText(MainActivity.this, "Inactive Employees", Toast.LENGTH_LONG).show();
                } else if (id == R.id.item2) {
                    Toast.makeText(MainActivity.this, "Attendance Report", Toast.LENGTH_LONG).show();
                } else if (id == R.id.item3) {
                    Toast.makeText(MainActivity.this, "Overtime Details", Toast.LENGTH_LONG).show();
                } else if (id == R.id.item4) {
                    Toast.makeText(MainActivity.this, "Employee Remarks", Toast.LENGTH_LONG).show();
                } else if (id == R.id.item5) {
                    Toast.makeText(MainActivity.this, "Send Feedback", Toast.LENGTH_LONG).show();
                }
                //CLOSES THE NAVIGATION DRAWER
                dl.closeDrawers();
                return true;
            }
        });
        Log.i(TAG, "Toolbar method Completed");
    }

    //METHOD TO IMPLEMENT RECYCLERMETHOD
    private void recyclerMethod(ArrayList<Employee> arrayList) {
        recyclerView = findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
//        attendanceArrayList = arrayList;
        adapter = new MyAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        Log.i(TAG, "recyclerMethod is Completed ");
    }

    //THE FOLLOWING CODE MUST BE INCLUDED FOR THE NAVIGATION DRAWER TO WORK
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    //THE FOLLOWING METHOD IS USED TO CALL THE ADDEMPLOYEE ACTIVITY
    public void onClickSwitchActivity(View view) {
        Intent intent = new Intent(this, AddEmployee.class);
        startActivityForResult(intent, 1);
        Log.i(TAG, "Add Employee class is called using Intent");
    }

    String name, pno, designation, role, salary, wage, ot, absent;
    int p;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        name = data.getStringExtra("EmpName");
        pno = data.getStringExtra("EmpMobileNo");
        designation = data.getStringExtra("EmpDesignation");
        role = data.getStringExtra("EmpRole");
        salary = data.getStringExtra("Salary");
        wage = data.getStringExtra("Wage");
        ot = data.getStringExtra("ExtraHours");
        absent = data.getStringExtra("NoOfLeaves");
        Log.i(TAG, "Data from the Add Employee details activity is set to local variables " + name);
        employeeObject();
    }

    private void employeeObject() {
        employee = new Employee();
        Log.i(TAG, "Employee object is created");
        employee.setEmpName(name);
        employee.setPhoneNum(Long.parseLong(pno));
        employee.setEmpDesignation(designation);
        employee.setEmpRole(role);
        employee.setMonSal(Long.parseLong(salary));
        employee.setdWage(Integer.parseInt(wage));
        employee.setOt(Integer.parseInt(ot));
        employee.setLeaves(Integer.parseInt(absent));
        //Add the employee object to the employeeArrayList
        //employeeArrayList.add(employee);
        //Notify the Adapter that new data is available
        //adapter.notifyDataSetChanged();
        sendToDb();
    }

    public void markAttendance(View view) {
        Intent i = new Intent(this,Attendance.class);
        startActivity(i);
        Log.i(TAG, "Attendance class is called using Intent");
    }

    public void categoryActivity(View view) {
        Intent i = new Intent(this,Categories.class);
        startActivity(i);
        Log.i(TAG, "Category activity is called using Intent");
    }

    public void economicsActivity(View view) {
        Intent i = new Intent(this,Economics.class);
        startActivity(i);
        Log.i(TAG, "Category activity is called using Intent");
    }
}

