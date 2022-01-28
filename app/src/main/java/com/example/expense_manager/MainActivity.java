package com.example.expense_manager;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.example.expense_manager.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    RecyclerView recyclerView;

    //Display Data in Recycler View
    MyDatabaseHelper myDB;
    ArrayList<String> inc_id, inc_name, inc_amount, inc_date, inc_category;
    CustomAdapter customAdapter;

    //fab1 is plus
    //fab2 is income
    //fab3 is expenses
    FloatingActionButton fab1, fab2, fab3;
    Boolean isTrue = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        // Display Data in recycler View
        myDB = new MyDatabaseHelper(MainActivity.this);
        inc_id = new ArrayList<>();
        inc_name = new ArrayList<>();
        inc_amount = new ArrayList<>();
        inc_date = new ArrayList<>();
        inc_category = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this, inc_id, inc_name, inc_amount,
                inc_date, inc_category);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

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

        // fab2 is income
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Income.class);
                startActivity(intent);
            }
        });

        // fab3 is expenses
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Expenses.class);
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

    // Display Data in Recycler View
    void storeDataInArrays(){
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0){
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }else{
            while (cursor.moveToNext()){
                inc_id.add(cursor.getString(0));
                inc_name.add(cursor.getString(1));
                inc_amount.add(cursor.getString(2));
                inc_date.add(cursor.getString(3));
                inc_category.add(cursor.getString(4));
            }
        }
    }
}