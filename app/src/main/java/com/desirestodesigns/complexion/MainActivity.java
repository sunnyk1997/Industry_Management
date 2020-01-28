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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DeleteDialog.DeleteDialogListener {
    private static final String TAG = "MainActivity";
    TextView mEmpCount, mInfo;
    Toolbar toolbar;
    DrawerLayout dl;
    ActionBarDrawerToggle abdt;
    NavigationView nav_view;
    MyAdapter adapter;
    RecyclerView recyclerView;
    Employee employee;
    String dId;

    public String docId;
    ArrayList<Employee> employeeArrayList = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference collectionReference;


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
        collectionReference = firebaseFirestore.collection("Employees");
        mCurrentUser = mAuth.getCurrentUser();
        Log.i(TAG, "initializeUi method Completed");
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

    //THE FOLLOWING CODE MUST BE INCLUDED FOR THE NAVIGATION DRAWER TO WORK
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }

    //THE FOLLOWING METHOD IS USED TO CALL THE ADD EMPLOYEE ACTIVITY
    public void onClickSwitchActivity(View view) {
        Intent intent = new Intent(this, AddEmployee.class);
        startActivity(intent);
        Log.i(TAG, "Add Employee class is called using Intent");
    }

    //String name, pno, designation, documentId,role, salary, wage, ot, absent;

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        name = data.getStringExtra("EmpName");
//        pno = data.getStringExtra("EmpMobileNo");
//        designation = data.getStringExtra("EmpDesignation");
//        role = data.getStringExtra("EmpRole");
//        salary = data.getStringExtra("Salary");
//        wage = data.getStringExtra("Wage");
//        ot = data.getStringExtra("ExtraHours");
//        absent = data.getStringExtra("NoOfLeaves");
//        Log.i(TAG, "Data from the Add Employee details activity is set to local variables " + name);
//        DocumentReference ref = firebaseFirestore.collection("Employees").document();
//        documentId = ref.getId();
//        employeeObject();
//    }

//    private void employeeObject() {
//        employee = new Employee();
//        Log.i(TAG, "Employee object is created");
//        employee.setEmpName(name);
//        employee.setPhoneNum(Long.parseLong(pno));
//        employee.setEmpDesignation(designation);
//        employee.setEmpRole(role);
//        employee.setMonSal(Long.parseLong(salary));
//        employee.setdWage(Integer.parseInt(wage));
//        employee.setOt(Integer.parseInt(ot));
//        employee.setLeaves(Integer.parseInt(absent));
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy  hh:mm:ss");
//        String currentDate = simpleDateFormat.format(new Date());
//        Log.d("MainActivity", "Current Timestamp: " + currentDate);
//        employee.setDate(currentDate);
//        employee.setDocumentId(documentId);
//        //Add the employee object to the employeeArrayList
//        //employeeArrayList.add(employee);
//        //Notify the Adapter that new data is available
//        //adapter.notifyDataSetChanged();
//        sendToDb();
//    }

//    private void sendToDb() {
//        Log.i(TAG, "sendToDb method is invoked");
//        collectionReference.document(documentId)
//                .set(employee).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void aVoid) {
//                Toast.makeText(MainActivity.this, "Data successfully added", Toast.LENGTH_SHORT).show();
//                readFromDb();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(MainActivity.this, "Issue while adding data", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    private void readFromDb() {
        firebaseFirestore.collection("Employees")
                .orderBy("empName", Query.Direction.ASCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            employeeArrayList.clear();

                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                Employee employee = documentSnapshot.toObject(Employee.class);
                                //Getting document id from database
                                employee.setDocumentId(documentSnapshot.getId());
                                //Setting document id to String variable
                                docId = employee.getDocumentId();
                                String name = employee.getEmpName();
                                String desg = employee.getEmpDesignation();
                                String role = employee.getEmpRole();
                                employee.setEmpName(name);
                                employee.setEmpDesignation(desg);
                                employee.setEmpRole(role);
                                Log.i(TAG, "read fro Db method is invoked" + docId);

                                employeeArrayList.add(employee);
                                adapter.notifyDataSetChanged();
                                count(employeeArrayList);
                            }


                        }
                        else{
                            employeeArrayList.clear();
                            count(employeeArrayList);
                        }
                    }
                });
    }

    private void count(ArrayList<Employee> employeeArrayList) {

        int i = employeeArrayList.size();
        //        int i = employeeArrayList.size();
        Log.i(TAG, "count thing " + i);
        Log.i(TAG, "count Method is invoked");
        if (i == 1) {
            mInfo.setVisibility(View.GONE);
            mEmpCount.setText(i + " Employee");
        } else if (i > 0) {
            mInfo.setVisibility(View.GONE);
            mEmpCount.setText(i + " Employees");
        }
        else{
            mInfo.setVisibility(View.VISIBLE);
            mEmpCount.setText(i + " Employees");
        }
    }

    //METHOD TO IMPLEMENT RECYCLER METHOD
    private void recyclerMethod(ArrayList<Employee> arrayList) {
        recyclerView = findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
//        attendanceArrayList = arrayList;
        adapter = new MyAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        //Delete using image button in recycler view
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(String docId) {
                removeItem(docId);
            }

            @Override
            public void onEditClick(String docId) {
//                dId = docId;
                Intent intent = new Intent(getBaseContext(), AddEmployee.class);
                intent.putExtra("DOCUMENT_ID", docId);
                startActivity(intent);
            }
        });
        Log.i(TAG, "recyclerMethod is Completed ");
    }



    private void removeItem(String docId) {

        openDeleteDialog();
    }

    private void openDeleteDialog() {
        DeleteDialog deleteDialog = new DeleteDialog();
        deleteDialog.show(getSupportFragmentManager(), "delete dialog");
    }

    @Override
    public void onDeleteYesClicked() {

        collectionReference.document(dId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.i(TAG, "DocumentSnapshot successfully deleted!");

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.i(TAG, "Error deleting document", e);
                    }
                });
        Toast.makeText(this, "Card Deleted", Toast.LENGTH_SHORT).show();

    }

//    @Override
//    public void onEditYesClicked() {
//        collectionReference.document(docId).addSnapshotListener(new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//
//                employeeArrayList.clear();
//
//                if (documentSnapshot != null) {
//                    employee = documentSnapshot.toObject(Employee.class);
//                    String name = employee.getEmpName();
//                    long phoneNumber = employee.getPhoneNum();
//                    String desg = employee.getEmpDesignation();
//                    String role = employee.getEmpRole();
//                    long salary = employee.getMonSal();
//                    int wage = employee.getdWage();
//                    int overTime = employee.getOt();
//                    int leaves = employee.getLeaves();
//                    employee.setEmpName(name);
//                    employee.setPhoneNum(phoneNumber);
//                    employee.setEmpDesignation(desg);
//                    employee.setEmpRole(role);
//                    employee.setMonSal(salary);
//                    employee.setdWage(wage);
//                    employee.setOt(overTime);
//                    employee.setLeaves(leaves);
//                    employeeArrayList.add(employee);
//                }
//
//
//            }
//        });
//        Toast.makeText(this, "Card Edited", Toast.LENGTH_SHORT).show();
//    }


    public void markAttendance(View view) {
        Intent i = new Intent(this, Attendance.class);
        startActivity(i);
        Log.i(TAG, "Attendance class is called using Intent");
    }

    public void categoryActivity(View view) {
        Intent i = new Intent(this, Categories.class);
        startActivity(i);
        Log.i(TAG, "Category activity is called using Intent");
    }

    public void economicsActivity(View view) {
        Intent i = new Intent(this, Economics.class);
        startActivity(i);
        Log.i(TAG, "Category activity is called using Intent");
    }

    public void customerDetails(View view) {
        Intent i = new Intent(MainActivity.this, CustomerDetails.class);
        startActivity(i);
        Log.i(TAG, "Category activity is called using Intent");
    }


}

