package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Income extends AppCompatActivity {

    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;

    ArrayList<String> income_category;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        textInputLayout = (TextInputLayout) findViewById(R.id.income_menu_drop);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.income_drop_items);

        income_category = new ArrayList<>();
        income_category.add("Salary");
        income_category.add("Bonus");

        arrayAdapter = new ArrayAdapter<>(getApplication(), R.layout.list_item, income_category);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setThreshold(1);

    }
}