package com.example.expense_manager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
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

import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {

    // Income Category
    TextInputLayout textInputLayout;
    AutoCompleteTextView autoCompleteTextView;

    TextInputEditText income_name_input, income_amount_input, income_date_input;
    Button inc_update_button, inc_delete_button;

    String income_id, income_name, income_date, income_category;

    double income_amount;
    NumberFormat nm = NumberFormat.getNumberInstance();     //setText for Double

    // Income Category
    ArrayList<String> inc_category;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        income_name_input = (TextInputEditText) findViewById(R.id.income_name_input2);
        income_amount_input = (TextInputEditText) findViewById(R.id.income_amount_input2);
        income_date_input = (TextInputEditText) findViewById(R.id.income_date_input2);

        //---------------------------------------------------------------------------------------------------------
        // Income Category
        textInputLayout = (TextInputLayout) findViewById(R.id.income_menu_drop);
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.income_drop_items2);

        inc_category = new ArrayList<>();
        inc_category.add("Salary");
        inc_category.add("Bonus");

        arrayAdapter = new ArrayAdapter<>(getApplication(), R.layout.list_item, inc_category);
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

        //-----------------------------------------------------------------------------------------------------

        // Update Income
        inc_update_button = findViewById(R.id.inc_update_button);
        // Delete Income
        inc_delete_button = findViewById(R.id.inc_delete_button);

        // First we call this
        getAndSetIntentData();

        // Set actionbar title after getAndSetIntentData method
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(income_name);
        }

        inc_update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // And only then we call this
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                income_name = income_name_input.getText().toString().trim();
                income_amount = Double.parseDouble(income_amount_input.getText().toString().trim());
                income_date = income_date_input.getText().toString().trim();
                income_category = autoCompleteTextView.getText().toString().trim();
                myDB.updateData(income_id, income_name, income_amount, income_date, income_category);
            }
        });
        inc_delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

    }

    void getAndSetIntentData(){
        if (getIntent().hasExtra("income_id") && getIntent().hasExtra("income_name") &&
                getIntent().hasExtra("income_amount") && getIntent().hasExtra("income_date") &&
                getIntent().hasExtra("income_category")){

            // Getting Data From Intent
            income_id = getIntent().getStringExtra("income_id");
            income_name = getIntent().getStringExtra("income_name");
            income_amount = Double.parseDouble(getIntent().getStringExtra("income_amount"));
            income_date = getIntent().getStringExtra("income_date");
            income_category = getIntent().getStringExtra("income_category");

            // Setting Intent Data
            income_name_input.setText(income_name);
            income_amount_input.setText(nm.format(income_amount));
            income_date_input.setText(income_date);
            autoCompleteTextView.setText(income_category);
        }else{
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }
    }

    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + income_name + "?");
        builder.setMessage("Are you sure you want to delete " + income_name + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(income_id);
                finish();   // Return to MainActivity after action
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}