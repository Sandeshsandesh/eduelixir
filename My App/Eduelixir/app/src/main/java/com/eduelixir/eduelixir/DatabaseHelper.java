package com.eduelixir.eduelixir;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sandesh on 1/24/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_TIMETABLE = "timetable.db";
    public static final String TABLE_PERIODTIMETABLE = "period_time";
    public static final String TABLE_MONTIMETABLE = "mon_time";
    public static final String TABLE_TUETIMETABLE = "tue_time";
    public static final String TABLE_WEDTIMETABLE = "wed_time";
    public static final String TABLE_THUTIMETABLE = "thu_time";
    public static final String TABLE_FRITIMETABLE = "fri_time";
    public static final String TABLE_SATTIMETABLE = "sat_time";
    public static final String COL_ID = "id";
    public static final String COL_SUB = "subName";
    public static final String COL_STAFF = "staffName";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_TIMETABLE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_PERIODTIMETABLE + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_SUB + " TEXT, " + COL_STAFF + " TEXT)");
        db.execSQL("create table " + TABLE_MONTIMETABLE + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_SUB + " TEXT, " + COL_STAFF + " TEXT)");
        db.execSQL("create table " + TABLE_TUETIMETABLE + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_SUB + " TEXT, " + COL_STAFF + " TEXT)");
        db.execSQL("create table " + TABLE_WEDTIMETABLE + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_SUB + " TEXT, " + COL_STAFF + " TEXT)");
        db.execSQL("create table " + TABLE_THUTIMETABLE + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_SUB + " TEXT, " + COL_STAFF + " TEXT)");
        db.execSQL("create table " + TABLE_FRITIMETABLE + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_SUB + " TEXT, " + COL_STAFF + " TEXT)");
        db.execSQL("create table " + TABLE_SATTIMETABLE + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_SUB + " TEXT, " + COL_STAFF + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERIODTIMETABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONTIMETABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TUETIMETABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEDTIMETABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_THUTIMETABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRITIMETABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SATTIMETABLE);
        onCreate(db);
    }

    public void insertPeriodData(String subName, String staffName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SUB, subName);
        contentValues.put(COL_STAFF, staffName);
        db.insert(TABLE_PERIODTIMETABLE, null, contentValues);
    }

    public void insertMonData(String subName, String staffName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SUB, subName);
        contentValues.put(COL_STAFF, staffName);
        db.insert(TABLE_MONTIMETABLE, null, contentValues);
    }

    public void insertTueData(String subName, String staffName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SUB, subName);
        contentValues.put(COL_STAFF, staffName);
        db.insert(TABLE_TUETIMETABLE, null, contentValues);
    }

    public void insertWedData(String subName, String staffName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SUB, subName);
        contentValues.put(COL_STAFF, staffName);
        db.insert(TABLE_WEDTIMETABLE, null, contentValues);
    }

    public void insertThuData(String subName, String staffName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SUB, subName);
        contentValues.put(COL_STAFF, staffName);
        db.insert(TABLE_THUTIMETABLE, null, contentValues);
    }

    public void insertFriData(String subName, String staffName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SUB, subName);
        contentValues.put(COL_STAFF, staffName);
        db.insert(TABLE_FRITIMETABLE, null, contentValues);
    }

    public void insertSatData(String subName, String staffName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_SUB, subName);
        contentValues.put(COL_STAFF, staffName);
        db.insert(TABLE_SATTIMETABLE, null, contentValues);
    }

    public Cursor fetchPeriodData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_PERIODTIMETABLE, null);
        return cursor;
    }

    public Cursor fetchMonData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MONTIMETABLE, null);
        return cursor;
    }

    public Cursor fetchTueData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_TUETIMETABLE, null);
        return cursor;
    }

    public Cursor fetchWedData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_WEDTIMETABLE, null);
        return cursor;
    }

    public Cursor fetchThuData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_THUTIMETABLE, null);
        return cursor;
    }

    public Cursor fetchFriData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_FRITIMETABLE, null);
        return cursor;
    }

    public Cursor fetchSatData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SATTIMETABLE, null);
        return cursor;
    }

    public void deleteAllTimeTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PERIODTIMETABLE, null, null);
        db.delete(TABLE_MONTIMETABLE, null, null);
        db.delete(TABLE_TUETIMETABLE, null, null);
        db.delete(TABLE_WEDTIMETABLE, null, null);
        db.delete(TABLE_THUTIMETABLE, null, null);
        db.delete(TABLE_FRITIMETABLE, null, null);
        db.delete(TABLE_SATTIMETABLE, null, null);
    }
}
