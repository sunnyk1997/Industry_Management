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
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class AddEmployee extends AppCompatActivity {
    private static final String TAG = "TAG";
    Toolbar toolbar;
    EditText meName, mpNo, mrole, mSalary, mdWages, moTime, mleaves;
    Spinner mdesg;
    ImageButton mimg;
    Button mbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        uiMethods();
        toolBar();
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
                    Intent home = new Intent(AddEmployee.this,MainActivity.class);
                    startActivity(home);
                }
            });
            Log.i(TAG, "toolBar method Completed");
        }

    private void uiMethods() {

//        textView = findViewById(R.id.tv1);
        meName = findViewById(R.id.ed1);
        mpNo = findViewById(R.id.ed2);
        mimg = findViewById(R.id.imgb);
        mdesg = findViewById(R.id.spinner1);
        mrole = findViewById(R.id.ed3);
        mSalary = findViewById(R.id.ed4);
        mdWages = findViewById(R.id.ed5);
        moTime = findViewById(R.id.ed6);
        mleaves = findViewById(R.id.ed7);
        mbtn = findViewById(R.id.btn2);
        Log.i(TAG, "uiInitialization method Completed in Activity2");
    }
    public void saveButton(View view) {
        Log.i(TAG, "Entered into Save loop");
        String ename = meName.getText().toString();
        String epno = mpNo.getText().toString();
        String designation =mdesg.getSelectedItem().toString();
        String erole = mrole.getText().toString();
        String msal = mSalary.getText().toString();
        String dwage = mdWages.getText().toString();
        String overTime = moTime.getText().toString();
        String absent = mleaves.getText().toString();


        if((!TextUtils.isEmpty(ename))&& (!TextUtils.isEmpty(epno))&& (!TextUtils.isEmpty(designation))

//                &&(TextUtils.isEmpty(msal)|| TextUtils.isEmpty(dwage))
        ){Log.i(TAG,"Entered in to IF loop");
            Intent returnIntent = new Intent();
            returnIntent.putExtra("EmpName",ename);
            returnIntent.putExtra("EmpMobileNo",epno);
            returnIntent.putExtra("EmpDesignation",designation);
            returnIntent.putExtra("EmpRole",erole);
            returnIntent.putExtra("Salary",msal);
            returnIntent.putExtra("Wage",dwage);
            returnIntent.putExtra("ExtraHours",overTime);
            returnIntent.putExtra("NoOfLeaves",absent);
            setResult(RESULT_OK, returnIntent);
            Log.i(TAG,"Intents are returned to Main activity");
            clearUi();
            Log.i(TAG,"clearUi method is executed");
            finish();
            Log.i(TAG,"Add Employee Activity is closed and returns to Main Activity");

        }
        else {

            Log.i(TAG,"Entered into ELSE loop");
            Toast.makeText(this,"Please enter appropriate data", Toast.LENGTH_LONG).show();
            return;
        }

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
}

