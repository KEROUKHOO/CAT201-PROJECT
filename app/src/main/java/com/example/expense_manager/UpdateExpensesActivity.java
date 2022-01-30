package com.example.expense_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.NumberFormat;
import java.util.ArrayList;

public class UpdateExpensesActivity extends AppCompatActivity {

    // Expenses Category
    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;

    TextInputEditText expenses_name_input, expenses_amount_input, expenses_date_input;
    Button expenses_update_button;

    String expenses_id, expenses_name, expenses_date, expenses_category;

    double expenses_amount;
    NumberFormat nm = NumberFormat.getNumberInstance();     //setText for Double

    // Expenses Category
    ArrayList<String> exp_category;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_expenses);

        expenses_name_input = (TextInputEditText) findViewById(R.id.expenses_name_input2);
        expenses_amount_input = (TextInputEditText) findViewById(R.id.expenses_amount_input2);
        expenses_update_button = findViewById(R.id.expenses_update_button);

        textInputLayout = (TextInputLayout) findViewById(R.id.expenses_menu_drop);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.expenses_drop_items2); //expenses_category
        expenses_date_input = (TextInputEditText) findViewById(R.id.expenses_date_input2);

//---------------------------------------------------------------------------------------------------------------------
        // Expenses Category
        exp_category = new ArrayList<>();
        exp_category.add("Food");
        exp_category.add("Transport");
        exp_category.add("Shopping");
        exp_category.add("Entertainment");
        exp_category.add("Bills");
        exp_category.add("Other");

        arrayAdapter = new ArrayAdapter<>(getApplication(), R.layout.list_item, exp_category);
        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setThreshold(1);

        // Expenses Date
        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        MaterialDatePicker picker = builder.build();

        expenses_date_input.setKeyListener(null);
        expenses_date_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                picker.show(getSupportFragmentManager(), "DATE_PICKER");
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
                    @Override
                    public void onPositiveButtonClick(Object selection) {
                        expenses_date_input.setText(picker.getHeaderText());
                    }
                });
            }
        });
//---------------------------------------------------------------------------------------------------------------

        // First we call this
        // Click Data to Pop-Up An Update Window
        getAndSetIntentDataExpenses();

        // Update Expenses Button
        expenses_update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // And only then we call this
                ExpensesDatabase myExpenseDB = new ExpensesDatabase(UpdateExpensesActivity.this);
                expenses_name = expenses_name_input.getText().toString().trim();
                expenses_amount = Double.parseDouble(expenses_amount_input.getText().toString().trim());
                expenses_date = expenses_date_input.getText().toString().trim();
                expenses_category = autoCompleteTextView.getText().toString().trim();
                myExpenseDB.updateExpensesData(expenses_id, expenses_name, expenses_amount,
                        expenses_date, expenses_category);
            }
        });

    }

    // Update Expenses Pop-Up Window
    void getAndSetIntentDataExpenses(){
        if (getIntent().hasExtra("expenses_id") && getIntent().hasExtra("expenses_name") &&
                getIntent().hasExtra("expenses_amount") && getIntent().hasExtra("expenses_date") &&
                getIntent().hasExtra("expenses_category")){

            // Getting Data From Intent
            expenses_id = getIntent().getStringExtra("expenses_id");
            expenses_name = getIntent().getStringExtra("expenses_name");
            expenses_amount = Double.parseDouble(getIntent().getStringExtra("expenses_amount"));
            expenses_date = getIntent().getStringExtra("expenses_date");
            expenses_category = getIntent().getStringExtra("expenses_category");

            // Setting Intent Data
            expenses_name_input.setText(expenses_name);
            expenses_amount_input.setText(nm.format(expenses_amount));
            expenses_date_input.setText(expenses_date);
            autoCompleteTextView.setText(expenses_category);
        }else{
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }
    }
}