package com.citizen.service.citizenservice.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class CitiesDbHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cities.db";
    private static final String TABLE_CITIES = "cities";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_CITY = "city";

    public CitiesDbHandler(Context context, SQLiteDatabase.CursorFactory factory){
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_CITIES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_CITY + " TEXT"
                + ");";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CITIES);
        onCreate(db);
    }

    public void addCity(String city){
        ArrayList<String> cities = getCities();

        if (!(cities.contains(city))) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(COLUMN_CITY, city);
            db.insert(TABLE_CITIES, null, values);

            db.close();
        }
    }

    public ArrayList<String> getCities(){
        ArrayList<String> cities = new ArrayList<String>();
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_CITIES + " WHERE 1;";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            if (cursor.getString(cursor.getColumnIndex(COLUMN_CITY)) != null){
                String item = cursor.getString(cursor.getColumnIndex(COLUMN_CITY));
                cities.add(item);
            }

            cursor.moveToNext();
        }

        cursor.close();
        db.close();

        return cities;
    }
}
