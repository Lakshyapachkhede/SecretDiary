package com.pachkhede.secretdiary;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DiaryDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "secret_diary_db";

    private static final String TABLE_DIARY = "diray_entries";


    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_CONTENT = "content";
    private static final String COLUMN_DATE = "entry_date";


    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_DIARY + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_TITLE + " TEXT," +
            COLUMN_CONTENT + " TEXT," +
            COLUMN_DATE + " TEXT" + ")";



    public DiaryDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIARY);
        onCreate(db);
    }

    public void addDiaryEntry(String title, String content, String date) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, title);
        contentValues.put(COLUMN_CONTENT, content);
        contentValues.put(COLUMN_DATE, date);

        db.insert(TABLE_DIARY, null, contentValues);

        db.close();

    }


    public List<DiaryEntry> getAllDiaryEntries() {
        List<DiaryEntry> diaryEntries = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_DIARY + " ORDER BY " + COLUMN_DATE + " DESC";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do{
                DiaryEntry diaryEntry = new DiaryEntry();

                diaryEntry.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
                diaryEntry.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
                diaryEntry.setContent(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)));

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date date = sdf.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)));
                    diaryEntry.setDate(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                diaryEntries.add(diaryEntry);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return diaryEntries;

    }

    public DiaryEntry getDiaryEntry(int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(TABLE_DIARY,
                new String[]{COLUMN_ID, COLUMN_TITLE, COLUMN_CONTENT, COLUMN_DATE},
                COLUMN_ID + "=?",
                new String[]{String.valueOf(id)},
                null, null, null, null
                );

        if (cursor != null) {
            cursor.moveToFirst();
        }

        DiaryEntry diaryEntry = new DiaryEntry();

        diaryEntry.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)));
        diaryEntry.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)));
        diaryEntry.setContent(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)));
            diaryEntry.setDate(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        cursor.close();
        return diaryEntry;

    }

    public int updateDiaryEntry(DiaryEntry diaryEntry) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_TITLE, diaryEntry.getTitle());
        contentValues.put(COLUMN_CONTENT, diaryEntry.getContent());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = diaryEntry.getDate();
        contentValues.put(COLUMN_DATE, sdf.format(date));

        return db.update(TABLE_DIARY, contentValues, COLUMN_ID + " = ?", new String[]{String.valueOf(diaryEntry.getId())});

    }

    public void deleteDiaryEntry(int id) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_DIARY, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }




}
