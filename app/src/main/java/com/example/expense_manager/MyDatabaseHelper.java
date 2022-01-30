package com.example.expense_manager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String INCOME_DATABASE_NAME = "Income.db";
    private static final int INCOME_DATABASE_VERSION = 1;

    private static final String INCOME_TABLE_NAME = "income";
    private static final String INCOME_COLUMN_ID = "income_id";
    private static final String INCOME_COLUMN_NAME = "income_name";
    private static final String INCOME_COLUMN_AMOUNT = "income_amount";
    private static final String INCOME_COLUMN_DATE = "income_date";
    private static final String INCOME_COLUMN_CATEGORY = "income_category";


    MyDatabaseHelper(@Nullable Context context) {
        super(context, INCOME_DATABASE_NAME, null, INCOME_DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + INCOME_TABLE_NAME +
                        " (" + INCOME_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        INCOME_COLUMN_NAME + " TEXT, " +
                        INCOME_COLUMN_AMOUNT + " REAL, " +
                        INCOME_COLUMN_DATE + " TEXT, " +
                        INCOME_COLUMN_CATEGORY + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + INCOME_TABLE_NAME);
        onCreate(db);
    }

    void addIncome(String income_name, double income_amount, String income_date, String income_category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(INCOME_COLUMN_NAME, income_name);
        cv.put(INCOME_COLUMN_AMOUNT, income_amount);
        cv.put(INCOME_COLUMN_DATE, income_date);
        cv.put(INCOME_COLUMN_CATEGORY, income_category);
        long result = db.insert(INCOME_TABLE_NAME, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    // Display Data in Recycler View
    Cursor readAllData(){
        String query = "SELECT * FROM " + INCOME_TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    // Passing Data to Update Income
    void updateData (String income_row_id, String income_name, double income_amount,
                     String income_date, String income_category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(INCOME_COLUMN_NAME, income_name);
        cv.put(INCOME_COLUMN_AMOUNT, income_amount);
        cv.put(INCOME_COLUMN_DATE, income_date);
        cv.put(INCOME_COLUMN_CATEGORY, income_category);

        long result = db.update(INCOME_TABLE_NAME, cv, "income_id=?", new String[]{income_row_id});
        if (result == -1){
            Toast.makeText(context, "Failed to Update.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();
        }
    }

    // Delete Income
    void deleteOneRow(String income_row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(INCOME_TABLE_NAME, "income_id=?", new String[]{income_row_id});
        if (result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    // Delete All Income
    void deleteAllIncome(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + INCOME_TABLE_NAME);
    }
}
