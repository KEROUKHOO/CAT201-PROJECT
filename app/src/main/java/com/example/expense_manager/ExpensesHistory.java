package com.example.expense_manager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Toast;

import com.example.expense_manager.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ExpensesHistory extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    RecyclerView recyclerView;

    // Income Recycler View Button
    Button income_button;

    //Display Data in Recycler View

    ExpensesDatabase myExpensesDB;

    ArrayList<String> exp_id, exp_name, exp_amount, exp_date, exp_category;
    CustomExpensesAdapter customExpensesAdapter;

    //fab1 is plus
    //fab2 is income
    //fab3 is expenses
    FloatingActionButton fab1, fab2, fab3;
    Boolean isTrue = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_history);

        recyclerView = findViewById(R.id.recyclerView);
        income_button = findViewById(R.id.incomeButton);

        // Display Income Data in recycler View
        myExpensesDB = new ExpensesDatabase(ExpensesHistory.this);
        exp_id = new ArrayList<>();
        exp_name = new ArrayList<>();
        exp_amount = new ArrayList<>();
        exp_date = new ArrayList<>();
        exp_category = new ArrayList<>();

        storeDataInArrays();

        customExpensesAdapter = new CustomExpensesAdapter(ExpensesHistory.this, this, exp_id, exp_name, exp_amount,
                exp_date, exp_category);
        recyclerView.setAdapter(customExpensesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ExpensesHistory.this));

        //-------------------------------------------------------------------------------------------

        bottomNavigationView = findViewById(R.id.bottom_navigator);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.dashboard:
                        startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.home:
                        return true;

                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(), About.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        fab1 = findViewById(R.id.ma_add_fab);
        fab2 = findViewById(R.id.ma_income_fab);
        fab3 = findViewById(R.id.ma_expenses_fab);

        // Income Button
        income_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpensesHistory.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // fab2 is income
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpensesHistory.this, Income.class);
                startActivity(intent);
            }
        });

        // fab3 is expenses
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExpensesHistory.this, Expenses.class);
                startActivity(intent);
            }
        });

        final Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        Animation rotateBack = AnimationUtils.loadAnimation(this, R.anim.rotate_back);
        final Animation Open = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        Animation Close = AnimationUtils.loadAnimation(this, R.anim.fab_close);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isTrue){
                    fab1.startAnimation(rotate);
                    fab2.startAnimation(Open);
                    fab3.startAnimation(Open);
                    fab2.setVisibility(View.VISIBLE);
                    fab3.setVisibility(View.VISIBLE);
                    fab2.setClickable(true);
                    fab3.setClickable(true);
                    isTrue = false;
                }
                else{
                    fab1.startAnimation(rotateBack);
                    fab2.startAnimation(Close);
                    fab3.startAnimation(Close);
                    fab2.setVisibility(View.INVISIBLE);
                    fab3.setVisibility(View.INVISIBLE);
                    fab2.setClickable(false);
                    fab3.setClickable(false);
                    isTrue = true;
                }
            }
        });

    }


//------------------------------------------------------------------------------------------------------

    // Refresh Expenses History Recycler View
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            recreate();
        }
    }

    // Display Data in Recycler View
    void storeDataInArrays(){
        Cursor expensesCursor = myExpensesDB.readAllExpensesData();
        if (expensesCursor.getCount() == 0){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }else{
            while (expensesCursor.moveToNext()){
                exp_id.add(expensesCursor.getString(0));
                exp_name.add(expensesCursor.getString(1));
                exp_amount.add(expensesCursor.getString(2));
                exp_date.add(expensesCursor.getString(3));
                exp_category.add(expensesCursor.getString(4));
            }
        }
    }

}