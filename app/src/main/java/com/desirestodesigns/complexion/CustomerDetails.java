package com.desirestodesigns.complexion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class CustomerDetails extends AppCompatActivity {
    private static final String TAG = "TAG";
    public static final String REQUEST_RESULT = "REQUEST_RESULT";
    Toolbar toolbar;
    TextView mcustomers,mcustInfo;
    Customer customer;
    CustomerAdapter adapter;
    RecyclerView recyclerView;
    ArrayList<Customer> customerArrayList = new ArrayList<>();
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_details);
        initializeUiC();
        toolBarC();
        readFromDb();
        recyclerMethod(customerArrayList);
        Log.i(TAG, "onCreate Method Completed");
    }

    private void recyclerMethod(ArrayList<Customer> arrayList) {
        recyclerView = findViewById(R.id.rv3);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
//        attendanceArrayList = arrayList;
        adapter = new CustomerAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        Log.i(TAG, "recyclerMethod is Completed ");
    }

    private void readFromDb() {
        Log.i(TAG, "readFromDb Method Invoked");
        firebaseFirestore.collection("Customers")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            customerArrayList.clear();
                            List<DocumentSnapshot> cList = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot doc : cList){
                                Customer customer = doc.toObject(Customer.class);
                                customerArrayList.add(customer);
                                //countArrayList.add(employee);
                            }
                            //notify Adapter for Updated Data
                            adapter.notifyDataSetChanged();
                            count(customerArrayList);
                        }
                    }
                });
    }

    private void count(ArrayList<Customer> customerArrayList) {
        int i = customerArrayList.size();
        //        int i = employeeArrayList.size();
        Log.i(TAG, "count thing "+ i);
        Log.i(TAG, "count Method is invoked");
        if(i==1) mcustomers.setText(i+" Customer");
        else if(i !=0){
            mcustInfo.setText("");
            mcustomers.setText(i+" "+"Customers");
        }
    }

    private void initializeUiC() {
        toolbar = findViewById(R.id.toolbar4);
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mCurrentUser = mAuth.getCurrentUser();
        mcustomers=findViewById(R.id.customers);
        mcustInfo=findViewById(R.id.custinfo);
    }

    private void toolBarC() {
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
                Intent home = new Intent(CustomerDetails.this, MainActivity.class);
                startActivity(home);
            }
        });
        Log.i(TAG, "toolBar method Completed");
    }

    public void onClickSwitchActivityCustomer(View view) {
        Intent i = new Intent(this,AddCustomerDetails.class);
        startActivityForResult(i,1);
        Log.i(TAG, "Add customer details activity is called using Intent");
    }
    String name,id,location,managerName,supervisorName,supervisorNumber,managerNumber,description;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        name = data.getStringExtra("CName");
        id = data.getStringExtra("CId");
        location = data.getStringExtra("CLocation");
        managerName = data.getStringExtra("CManager");
        managerNumber = data.getStringExtra("CManagerNumber");
        supervisorName = data.getStringExtra("CSupervisor");
        supervisorNumber = data.getStringExtra("CSupervisorNumber");
        description = data.getStringExtra("CDescription");
        Log.i(TAG, "Data from the Add Employee details activity is set to local variables " + name);
        customerObject();
    }

    private void customerObject() {
        customer = new Customer();
        Log.i(TAG, "Customer object is created");
        customer.setmCustomerName(name);
        customer.setmCustomerId(id);
        customer.setmCustomerGeolocation(location);
        customer.setmCustomerManager(managerName);
        customer.setmManagerNumber(managerNumber);
        customer.setmSupervisorName(supervisorName);
        customer.setmManagerNumber(supervisorNumber);
        customer.setmCustomerDescription(description);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy  hh:mm:ss");
        String currentDate = simpleDateFormat.format(new Date());
        Log.d("MainActivity", "Current Timestamp: " + currentDate);
        customer.setDate(currentDate);
        sendToDb();
    }

    private void sendToDb() {
        Log.i(TAG, "sendToDb method is invoked");
        firebaseFirestore.collection("Customers")
                .document()
                .set(customer)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(CustomerDetails.this, "Data successfully added", Toast.LENGTH_SHORT).show();
                        readFromDb();
                    }
                });
    }
}
