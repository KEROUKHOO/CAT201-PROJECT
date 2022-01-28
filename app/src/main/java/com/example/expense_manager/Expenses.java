package com.example.expense_manager;

import androidx.appcompat.app.AppCompatActivity;

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

public class Expenses extends AppCompatActivity {

    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;
    TextInputEditText textInputEditText;
    Button button;

    ArrayList<String> expenses_category;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        textInputLayout = (TextInputLayout) findViewById(R.id.expenses_menu_drop);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.expenses_drop_items);
        textInputEditText = (TextInputEditText) findViewById(R.id.expenses_date_input);
        button = findViewById(R.id.expenses_cancel_button);

        expenses_category = new ArrayList<>();
        expenses_category.add("Food");
        expenses_category.add("Transport");

        arrayAdapter = new ArrayAdapter<>(getApplication(), R.layout.list_item, expenses_category);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setThreshold(1);

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        MaterialDatePicker picker = builder.build();

        textInputEditText.setKeyListener(null);
        textInputEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.show( getSupportFragmentManager(), "DATE_PICKER" );
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        textInputEditText.setText(picker.getHeaderText());
                    }
                });
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Expenses.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }
}