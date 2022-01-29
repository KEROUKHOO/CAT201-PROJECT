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

    /*
    private static final String CREATE_TABLE_INCOME = "CREATE TABLE " + TABLE_INCOME +
            " (" + INCOME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            INCOME_NAME + " TEXT, " +
            INCOME_AMOUNT + " REAL, " +
            INCOME_DATE + " TEXT, " +
            INCOME_CATEGORY + " TEXT);";
    */
    private static final String CREATE_TABLE_EXPENSES = "CREATE TABLE " + TABLE_EXPENSES +
            " (" + EXPENSES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            EXPENSES_NAME + " TEXT, " +
            EXPENSES_AMOUNT + " REAL, " +
            EXPENSES_DATE + " TEXT, " +
            EXPENSES_CATEGORY + " TEXT);";

    public ExpensesDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(CREATE_TABLE_INCOME);
        db.execSQL(CREATE_TABLE_EXPENSES);
        /*
        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_AMOUNT + " REAL, " +
                        COLUMN_DATE + " TEXT, " +
                        COLUMN_CATEGORY + " TEXT);";
        db.execSQL(query);
        */

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCOME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        onCreate(db);
    }

    /*void addIncome(String income_name, double income_amount, String income_date, String income_category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(INCOME_NAME, income_name);
        cv.put(INCOME_AMOUNT, income_amount);
        cv.put(INCOME_DATE, income_date);
        cv.put(INCOME_CATEGORY, income_category);
        long result = db.insert(TABLE_INCOME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

     */

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
}
