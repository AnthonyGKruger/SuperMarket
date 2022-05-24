package com.learning.supermarket;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

//db helper class that will create db and save information into the db.
public class DBHelper extends SQLiteOpenHelper {

    //declaring column names
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "supermarkets.db";
    public static final String TABLE_NAME = "ratings";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_STORE_NAME = "store_name";
    public static final String COLUMN_ADDRESS = "store_address";
    public static final String COLUMN_LIQUOR_DEP_RATING = "liquor_department_rating";
    public static final String COLUMN_PRODUCE_DEP_RATING = "produce_department_rating";
    public static final String COLUMN_CHEESE_DEP = "cheese_department_rating";
    public static final String COLUMN_EASE_OF_CHECKOUT = "ease_of_checkout_rating";
    public static final String COLUMN_AVERAGE_RATING = "average_rating";


    //constructor, creates the db
    public DBHelper(@Nullable Context context, @Nullable String name,
                    @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override//on load setup the db table columns and expected data types
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + COLUMN_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_STORE_NAME + " TEXT, " +
                COLUMN_ADDRESS + " TEXT, " + COLUMN_LIQUOR_DEP_RATING + " REAL, " +
                COLUMN_PRODUCE_DEP_RATING + " REAL, " + COLUMN_CHEESE_DEP + " REAL, " +
                COLUMN_EASE_OF_CHECKOUT + " REAL, " + COLUMN_AVERAGE_RATING + " REAL)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //helper function to add data to the dbb
    public void addToDB( String storeName, String address, double liquorRating,
                         double produceRating, double cheeseRating, double easeOfCheckOut,
                         double average){

        //setting up object that will store the values of the edit fields in a container with
        // key value pairs for us to put into the db table
        ContentValues values = new ContentValues();

        //inserting data in container
        values.put(COLUMN_STORE_NAME, storeName);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_LIQUOR_DEP_RATING, liquorRating);
        values.put(COLUMN_PRODUCE_DEP_RATING, produceRating);
        values.put(COLUMN_CHEESE_DEP, cheeseRating);
        values.put(COLUMN_EASE_OF_CHECKOUT, easeOfCheckOut);
        values.put(COLUMN_AVERAGE_RATING, average);

        SQLiteDatabase db = this.getWritableDatabase();

        //inserting into the table and closing the db
        db.insert(TABLE_NAME, null, values);
        db.close();
    }



    public void updateHandler(String storeName, String address, double liquorRating,
                                 double produceRating, double cheeseRating, double easeOfCheckOut,
                                 double average) {

        //setting up object that will store the values of the edit fields in a container
        // with key value pairs for us to put into the db table
        ContentValues values = new ContentValues();

        //inserting data in container
        values.put(COLUMN_STORE_NAME, storeName);
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_LIQUOR_DEP_RATING, liquorRating);
        values.put(COLUMN_PRODUCE_DEP_RATING, produceRating);
        values.put(COLUMN_CHEESE_DEP, cheeseRating);
        values.put(COLUMN_EASE_OF_CHECKOUT, easeOfCheckOut);
        values.put(COLUMN_AVERAGE_RATING, average);

        SQLiteDatabase db = this.getWritableDatabase();

        //updating the db
        db.update(TABLE_NAME, values, COLUMN_STORE_NAME + " = '" + storeName +
                "' AND " + COLUMN_ADDRESS + " = '" + address + "'", null);

        //closing the db
        db.close();
    }

    //function to check if details exist in db.
    public boolean findHandler(String storeName, String address) {

        //building query string.
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + COLUMN_STORE_NAME + " = " + "'"
                + storeName + "' AND " + COLUMN_ADDRESS + " = '" + address + "'" ;

        SQLiteDatabase db = this.getWritableDatabase();

        //cursor object to go through items in the db
        Cursor cursor = db.rawQuery(query, null);

        //if the cursor can move, if result was returned it means there is data matching the input
        //arguments.
        if (cursor.moveToFirst()) {
            //close the cursor
            cursor.close();
            //close the db and return true
            db.close();
            return true;
        }

        //return false, meaning there is no current store information in the db matching input
        return false;
    }
}

