package com.desirestodesigns.complexion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Economics extends AppCompatActivity {
    private static final String TAG = "TAG";
    Toolbar toolbar;
    RadioGroup mTransactionType;
    IncomeExpense incomeExpense;
    EditText mAmount;
    Spinner mCategoryType;
    EditText mDescription;
    List<String> categoryList = new ArrayList<>();
    Button mAdd;
    RecyclerView recyclerView;
    TransactionAdapter adapter;
    ArrayList<Category> categoryArrayList = new ArrayList<>();
    ArrayList<IncomeExpense> incomeExpenseArrayList = new ArrayList<>();
    FirebaseFirestore firebaseFirestore;
    private Spinner spinner;
    String selectedCategory;
    String selectedRadioButtonText;
    ArrayAdapter<String> spinneradapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_economics);
        uiMethods();
        toolBar();
        readFromDbTransaction();
        readFromDb();
        recyclerMethod(incomeExpenseArrayList);
        Log.i(TAG, "onCreate Method Completed");
    }

    private void recyclerMethod(ArrayList<IncomeExpense> arrayList) {
        recyclerView = findViewById(R.id.rv4);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
//        attendanceArrayList = arrayList;
        adapter = new TransactionAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        Log.i(TAG, "recyclerMethod is Completed ");
    }

    private void readFromDb() {
        Log.i(TAG, "readFromDb Method Invoked");
        firebaseFirestore.collection("Categories")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            categoryArrayList.clear();
                            List<DocumentSnapshot> eList = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot doc : eList) {
                                Category category = doc.toObject(Category.class);
                                categoryArrayList.add(category);

                            }//notify Adapter for Updated Data
                            //adapter.notifyDataSetChanged();
                            for (Category catg : categoryArrayList) {
                                String categoryName = catg.getCategoryName();
                                categoryList.add(categoryName);
                                Log.i(TAG, "Data is added to catg " + categoryName);
                            }
                            spinneradapter.notifyDataSetChanged();
                        }
                    }
                });
    }

    private void uiMethods() {
        // Attaching the layout to the toolbar object
        firebaseFirestore = FirebaseFirestore.getInstance();
        toolbar = findViewById(R.id.toolbar5);
        mTransactionType = findViewById(R.id.income_expense);
        mAmount = findViewById(R.id.amount);
        mCategoryType = findViewById(R.id.spinner2);
        mDescription = findViewById(R.id.desc);
        spinner = findViewById(R.id.spinner2);
        mAdd = findViewById(R.id.btn6);
        mAdd.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        // Get the checked Radio Button ID from Radio Grou[
                                        int selectedRadioButtonID = mTransactionType.getCheckedRadioButtonId();
                                        // If nothing is selected from Radio Group, then it return -1
                                        if (selectedRadioButtonID != -1) {
                                            RadioButton selectedRadioButton = findViewById(selectedRadioButtonID);
                                            selectedRadioButtonText = selectedRadioButton.getText().toString();
                                            Log.i(TAG, "String " + selectedRadioButtonText);
                                        }
                                        setData();
                                    }
                                });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                selectedCategory = adapterView.getItemAtPosition(position).toString();
//                Toast.makeText(Economics.this, selectedCategory, Toast.LENGTH_SHORT).show();
                spinner.setPrompt(selectedCategory);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        spinneradapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categoryList);
        // attaching data adapter to spinner
        spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinneradapter);
        String catType = String.valueOf(spinner.getSelectedItem());
    }

    private void setData() {
        String amt =mAmount.getText().toString();
        String transaction = selectedRadioButtonText;
        String catType = mCategoryType.getSelectedItem().toString();
        String desc = mDescription.getText().toString();
        if((!TextUtils.isEmpty(desc))){
            incomeExpense = new IncomeExpense();
            incomeExpense.setTranscationType(transaction);
            incomeExpense.setAmount(amt);
            incomeExpense.setCategoryType(catType);
            incomeExpense.setDescription(desc);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy  hh:mm:ss");
            String currentDate = simpleDateFormat.format(new Date());
            Log.d("MainActivity", "Current Timestamp: " + currentDate);
            incomeExpense.setDate(currentDate);
            sendToDb();
        }else {

            Log.i(TAG,"Entered into ELSE loop");
            Toast.makeText(this,"Please enter appropriate data", Toast.LENGTH_LONG).show();
            return;
        }


    }

    private void toolBar() {

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
                Intent home = new Intent(Economics.this, MainActivity.class);
                startActivity(home);
            }
        });
        Log.i(TAG, "toolBar method Completed");
    }


    private void sendToDb() {
        Log.i(TAG, "sendToDb method is invoked");
        firebaseFirestore.collection("Transactions")
                .document()
                .set(incomeExpense)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Economics.this, "Data successfully added", Toast.LENGTH_SHORT).show();
                        readFromDbTransaction();
                    }
                });
    }

    private void readFromDbTransaction() {
        Log.i(TAG, "readFromDb Method Invoked");
        firebaseFirestore.collection("Transactions")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            incomeExpenseArrayList.clear();
                            List<DocumentSnapshot> eList = queryDocumentSnapshots.getDocuments();
                            for(DocumentSnapshot doc : eList){
                                IncomeExpense incomeExpense = doc.toObject(IncomeExpense.class);
                                incomeExpenseArrayList.add(incomeExpense);
                                //countArrayList.add(employee);
                            }
                            //notify Adapter for Updated Data
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
    }
}
