package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Expenses extends AppCompatActivity {

    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;

    ArrayList<String> expenses_category;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        textInputLayout = (TextInputLayout) findViewById(R.id.expenses_menu_drop);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.expenses_drop_items);

        expenses_category = new ArrayList<>();
        expenses_category.add("Food");
        expenses_category.add("Transport");

        arrayAdapter = new ArrayAdapter<>(getApplication(), R.layout.list_item, expenses_category);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setThreshold(1);
    }
}