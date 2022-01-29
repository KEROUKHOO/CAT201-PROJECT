package com.example.expense_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionBarPolicy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

//import com.example.expensemanager.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class Income extends AppCompatActivity {

    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;

    TextInputEditText income_date_input, income_name_input, income_amount_input;
    Button cancel_button, save_button;

    ArrayList<String> income_category;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);

        income_name_input = (TextInputEditText) findViewById(R.id.income_name_input);
        income_amount_input = (TextInputEditText) findViewById(R.id.income_amount_input);
        save_button = findViewById(R.id.income_save_button);

        textInputLayout = (TextInputLayout) findViewById(R.id.income_menu_drop);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.income_drop_items); //income_category
        income_date_input = (TextInputEditText) findViewById(R.id.income_date_input);
        cancel_button = findViewById(R.id.income_cancel_button);

        // Income Category
        income_category = new ArrayList<>();
        income_category.add("Salary");
        income_category.add("Bonus");

        arrayAdapter = new ArrayAdapter<>(getApplication(), R.layout.list_item, income_category);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setThreshold(1);

        // Income Date
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        MaterialDatePicker picker = builder.build();

        income_date_input.setKeyListener(null);
        income_date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.show( getSupportFragmentManager(), "DATE_PICKER" );
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        income_date_input.setText(picker.getHeaderText());
                    }
                });
            }
        });

        // Save Button
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(Income.this);
                myDB.addIncome(income_name_input.getText().toString().trim(),
                        Double.parseDouble(income_amount_input.getText().toString()),
                        income_date_input.getText().toString().trim(),
                        autoCompleteTextView.getText().toString().trim());
            }
        });

        // Cancel Button
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Income.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}