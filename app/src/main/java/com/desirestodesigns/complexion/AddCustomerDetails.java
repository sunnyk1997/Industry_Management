package com.desirestodesigns.complexion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCustomerDetails extends AppCompatActivity {
    private static final String TAG = "TAG";
    Toolbar toolbar;
    EditText mCustomerName,mCustomerId,mCustomerGeolocation,mCustomerManager,mSupervisorName,mSupervisorNumber,mManagerNumber,mCustomerDescription;
    Button mSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer_details);
        uiMethods();
        toolBar();
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
                Intent home = new Intent(AddCustomerDetails.this,MainActivity.class);
                startActivity(home);
            }
        });
        Log.i(TAG, "toolBar method Completed");
    }

    private void uiMethods() {
        toolbar = findViewById(R.id.toolbar6);
        mCustomerName = findViewById(R.id.cName);
        mCustomerId=findViewById(R.id.cId);
        mCustomerGeolocation=findViewById(R.id.cGeoLocation);
        mCustomerManager=findViewById(R.id.customerManager);
        mSupervisorName=findViewById(R.id.supervisorName);
        mSupervisorNumber=findViewById(R.id.supervisorNumber);
        mManagerNumber=findViewById(R.id.managerNumber);
        mCustomerDescription=findViewById(R.id.customerDescription);
        mSave=findViewById(R.id.btn8);
        Log.i(TAG, "uiInitialization method Completed in AddCustomerDetails");
    }

    public void save(View view) {
        Log.i(TAG, "Entered into Save loop in AddCustomerDetails");
        String cName = mCustomerName.getText().toString();
        String cId = mCustomerId.getText().toString();
        String cLocation = mCustomerGeolocation.getText().toString();
        String cManager = mCustomerManager.getText().toString();
        String cSupervisorName = mSupervisorName.getText().toString();
        String cSupervisorNumber = mSupervisorNumber.getText().toString();
        String cManagerNumber = mManagerNumber.getText().toString();
        String cDescription = mCustomerDescription.getText().toString();
        if((!TextUtils.isEmpty(cName))&& (!TextUtils.isEmpty(cId))&& (!TextUtils.isEmpty(cLocation))
                &&(!TextUtils.isEmpty(cManager)&& (!TextUtils.isEmpty(cSupervisorName))&& (!TextUtils.isEmpty(cSupervisorNumber))
                && (!TextUtils.isEmpty(cManagerNumber))&& (!TextUtils.isEmpty(cDescription)))){
            Log.i(TAG,"Entered in to IF loop");
            Intent returnIntent = new Intent();
            returnIntent.putExtra("CName",cName);
            returnIntent.putExtra("CId",cId);
            returnIntent.putExtra("CLocation",cLocation);
            returnIntent.putExtra("CManager",cManager);
            returnIntent.putExtra("CSupervisor",cSupervisorName);
            returnIntent.putExtra("CSupervisorNumber",cSupervisorNumber);
            returnIntent.putExtra("CManagerNumber",cManagerNumber);
            returnIntent.putExtra("CDescription",cDescription);
            setResult(RESULT_OK, returnIntent);
            Log.i(TAG,"Intents are returned to customer details activity");
            clearUi();
            Log.i(TAG,"clearUi method is executed");
            finish();
            Log.i(TAG,"Add Customer Activity is closed and returns to customer details Activity");
        }else {
            Log.i(TAG,"Entered into ELSE loop");
            Toast.makeText(this,"Please enter appropriate data", Toast.LENGTH_LONG).show();
            return;
        }
    }
    private void clearUi() {
        mCustomerDescription.setText("");
        mManagerNumber.setText("");
        mSupervisorName.setText("");
        mCustomerManager.setText("");
        mCustomerGeolocation.setText("");
        mManagerNumber.setText("");
        mSupervisorNumber.setText("");
        mCustomerId.setText("");
        mCustomerName.setText("");
    }
}
