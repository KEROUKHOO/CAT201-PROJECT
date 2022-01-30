package com.example.expense_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

//import com.example.expense_manager.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Expenses extends AppCompatActivity {

    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;

    TextInputEditText expenses_date_input;
    TextInputEditText expenses_name_input;
    TextInputEditText expenses_amount_input;
    Button cancel_button, save_button;

    ArrayList<String> expenses_category;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        expenses_name_input = (TextInputEditText) findViewById(R.id.expenses_name_input);
        expenses_amount_input = (TextInputEditText) findViewById(R.id.expenses_amount_input);
        save_button = findViewById(R.id.expenses_save_button);

        textInputLayout = (TextInputLayout) findViewById(R.id.expenses_menu_drop);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.expenses_drop_items); //expenses_category
        expenses_date_input = (TextInputEditText) findViewById(R.id.expenses_date_input);
        cancel_button = findViewById(R.id.expenses_cancel_button);

        // Expenses Category
        expenses_category = new ArrayList<>();
        expenses_category.add("Food");
        expenses_category.add("Transport");
        expenses_category.add("Shopping");
        expenses_category.add("Entertainment");
        expenses_category.add("Other");

        arrayAdapter = new ArrayAdapter<>(getApplication(), R.layout.list_item, expenses_category);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setThreshold(1);

        // Expenses Date
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        MaterialDatePicker picker = builder.build();

        expenses_date_input.setKeyListener(null);
        expenses_date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.show( getSupportFragmentManager(), "DATE_PICKER" );
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        expenses_date_input.setText(picker.getHeaderText());
                    }
                });
            }
        });

        // Save Button
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExpensesDatabase myExpensesDB = new ExpensesDatabase(Expenses.this);
                myExpensesDB.addExpenses(expenses_name_input.getText().toString().trim(),
                        Double.parseDouble(expenses_amount_input.getText().toString()),
                        expenses_date_input.getText().toString().trim(),
                        autoCompleteTextView.getText().toString().trim());
            }
        });

        // Cancel Button
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Expenses.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}