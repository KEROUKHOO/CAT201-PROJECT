package com.example.expense_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class ExpensesDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Expenses.db";
    private static final int DATABASE_VERSION = 1;


    private static final String TABLE_EXPENSES = "Expenses";
    private static final String EXPENSES_ID = "Expenses_id";
    private static final String EXPENSES_NAME = "Expenses_Name";
    private static final String EXPENSES_AMOUNT = "Expenses_Amount";
    private static final String EXPENSES_DATE = "Expenses_Date";
    private static final String EXPENSES_CATEGORY = "Expenses_Category";


    private static final String CREATE_TABLE_EXPENSES = "CREATE TABLE " + TABLE_EXPENSES +
            " (" + EXPENSES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            EXPENSES_NAME + " TEXT, " +
            EXPENSES_AMOUNT + " REAL, " +
            EXPENSES_DATE + " TEXT, " +
            EXPENSES_CATEGORY + " TEXT);";

    ExpensesDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EXPENSES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        onCreate(db);
    }

    void addExpenses(String expenses_name, double expenses_amount, String expenses_date, String expenses_category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(EXPENSES_NAME, expenses_name);
        cv.put(EXPENSES_AMOUNT, expenses_amount);
        cv.put(EXPENSES_DATE, expenses_date);
        cv.put(EXPENSES_CATEGORY, expenses_category);
        long result = db.insert(TABLE_EXPENSES, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    // Display Data in Recycler View
    Cursor readAllExpensesData(){
        String query = "SELECT * FROM " + TABLE_EXPENSES;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    // Passing data to Update Expenses
    void updateExpensesData(String expenses_row_id, String expenses_name, double expenses_amount,
                            String expenses_date, String expenses_category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(EXPENSES_NAME, expenses_name);
        cv.put(EXPENSES_AMOUNT, expenses_amount);
        cv.put(EXPENSES_DATE, expenses_date);
        cv.put(EXPENSES_CATEGORY, expenses_category);

        long result = db.update(TABLE_EXPENSES, cv, "Expenses_id=?", new String[]{expenses_row_id});
        if (result == -1){
            Toast.makeText(context, "Failed to Update.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    // Delete Expenses
    void deleteExpensesOneRow(String expenses_row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_EXPENSES, "Expenses_id=?", new String[]{expenses_row_id});
        if (result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    // Delete All Expenses
    void deleteAllExpensesData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_EXPENSES);
    }
}