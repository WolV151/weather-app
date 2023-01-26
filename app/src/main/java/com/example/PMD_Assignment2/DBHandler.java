package com.example.PMD_Assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "locationDb";
    private static final String TABLE_Location = "locationDetails";
    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_WOEID = "woeid";

    public DBHandler(@Nullable Context context) {
        super(context,DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_Location + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_TITLE + " TEXT ,"
                + KEY_WOEID + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Drop older table if exist
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_Location);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public ArrayList<HashMap<String, String>> GetLocations(){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> locationList = new ArrayList<>();
        String query = "SELECT title, woeid FROM "+ TABLE_Location;
        Cursor cursor = db.rawQuery(query,null);
        while (cursor.moveToNext()){
            HashMap<String,String> user = new HashMap<>();
            user.put("title",cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)));
            user.put("woeid",cursor.getString(cursor.getColumnIndexOrThrow(KEY_WOEID)));
            locationList.add(user);
        }
        cursor.close();
        return locationList;
    }

    public ArrayList<HashMap<String, String>> FetchLocation(String woeid){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<HashMap<String, String>> locationList = new ArrayList<>();
        String query = "SELECT name, location, designation FROM "+ TABLE_Location;
        Cursor cursor = db.query(TABLE_Location, new String[]{KEY_TITLE, KEY_WOEID}, KEY_WOEID+ "=?",new String[]{String.valueOf(woeid)},null, null, null, null);
        if (cursor.moveToNext()){
            HashMap<String,String> location = new HashMap<>();
            location.put("title",cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)));
            location.put("woeid",cursor.getString(cursor.getColumnIndexOrThrow(KEY_TITLE)));
            locationList.add(location);
        }

        if(locationList.isEmpty()){
            return null;
        }

        return  locationList;
    }


    void insertRecord(String title, String woeid){
        //Get the Data Repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();
        //Create a new map of values, where column names are the keys
        ContentValues cValues = new ContentValues();
        cValues.put(KEY_TITLE, title);
        cValues.put(KEY_WOEID, woeid);
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_Location,null, cValues);
        db.close();
    }

    public void ClearDatabase(){
        SQLiteDatabase db = this.getWritableDatabase();
        String clearDBQuery = "DELETE FROM "+TABLE_Location;
        db.execSQL(clearDBQuery);
    }

    public void DeleteLocation(String locationTitle){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_Location, KEY_TITLE+" = ?",new String[]{String.valueOf(locationTitle)});
        db.close();
    }
}
