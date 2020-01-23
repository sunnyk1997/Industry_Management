package com.desirestodesigns.complexion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Categories extends AppCompatActivity implements CategoryDialog.CategoryDialogListener {
    private static final String TAG = "TAG";
    Toolbar toolbar;
    TextView tv;
    RecyclerView recyclerView;
    CategoryAdapter adapter;
    final Context context = this;
    private FloatingActionButton mbutton;
    Category category;
    ArrayList<Category> categoryArrayList = new ArrayList<>();
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        initializeUiC();
        toolBarC();
        readFromDb();
        recyclerMethod(categoryArrayList);
    }

    private void recyclerMethod(ArrayList<Category> arrayList) {
        recyclerView = findViewById(R.id.rv3);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
//        attendanceArrayList = arrayList;
        adapter = new CategoryAdapter(arrayList);
        recyclerView.setAdapter(adapter);
        Log.i(TAG, "recyclerMethod is Completed ");
    }

    private void initializeUiC() {
        toolbar = findViewById(R.id.toolbar4);
        mbutton = findViewById(R.id.fab2);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

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
                                //notify Adapter for Updated Data
                                adapter.notifyDataSetChanged();
                            }

                        }
                    }
                });
    }

    private void sendToDb() {

        Log.i(TAG, "sendToDb method is invoked in Categories");
        firebaseFirestore.collection("Categories")
                .document()
                .set(category)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(Categories.this, "Data successfully added", Toast.LENGTH_SHORT).show();
                        readFromDb();
                    }
                });
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
                Intent home = new Intent(Categories.this, MainActivity.class);
                startActivity(home);
            }
        });
        Log.i(TAG, "toolBar method Completed");
    }

    private void openDialog() {
        CategoryDialog categoryDialog = new CategoryDialog();
        categoryDialog.show(getSupportFragmentManager(), "C Dialog");
    }

    String cat, catName;

    @Override
    public void applyTexts(String category, String categoryName) {
        cat = category;
        catName = categoryName;
        Log.i(TAG, "applyTexts " + cat);
        Log.i(TAG, "applyTexts " + catName);
        catObj();
    }

    private void catObj() {
        category = new Category();
        Log.i(TAG, "Category object is created");
        category.setCategory(cat);
        category.setCategoryName(catName);
        sendToDb();
    }


}
