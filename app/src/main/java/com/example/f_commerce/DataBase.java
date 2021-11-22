package com.example.f_commerce;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "MyDBName";
    public static final String CONTACTS_TABLE_NAME = "Location";
    public DataBase(Context context) {
        super(context,DATABASE_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ CONTACTS_TABLE_NAME +"(number id,Lat text,Long text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
public String read(){

        return "";
}
    public boolean insert(String s, String s1 , int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Id",id);
        contentValues.put("Lat", s);
        contentValues.put("Long", s1);
        db.insert(CONTACTS_TABLE_NAME, null, contentValues);
        return true;
    }
}
