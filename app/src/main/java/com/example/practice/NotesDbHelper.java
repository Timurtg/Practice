package com.example.practice;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class NotesDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "notes.db";
    private static final int DB_VERSION = 1;

    private static final String TABLE_NOTES = "notes";
    private static final String COL_ID = "id";
    private static final String COL_TITLE = "title";
    private static final String COL_TEXT = "text";
    private static final String COL_DEADLINE = "deadline";
    private static final String COL_DONE = "done";

    public NotesDbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NOTES + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_TITLE + " TEXT NOT NULL, "
                + COL_TEXT + " TEXT, "
                + COL_DEADLINE + " TEXT, "
                + COL_DONE + " INTEGER DEFAULT 0)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

    public long addNote(String title, String text, String deadline, boolean done) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TITLE, title);
        values.put(COL_TEXT, text);
        values.put(COL_DEADLINE, deadline);
        values.put(COL_DONE, done ? 1 : 0);
        return db.insert(TABLE_NOTES, null, values);
    }

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_NOTES,
                null,
                null,
                null,
                null,
                null,
                COL_ID + " DESC"
        );

        while (cursor.moveToNext()) {
            notes.add(readNote(cursor));
        }
        cursor.close();
        return notes;
    }

    public Note getNoteById(int id) {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(
                TABLE_NOTES,
                null,
                COL_ID + "=?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null
        );

        Note note = null;
        if (cursor.moveToFirst()) {
            note = readNote(cursor);
        }
        cursor.close();
        return note;
    }

    public int updateNote(int id, String title, String text, String deadline, boolean done) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_TITLE, title);
        values.put(COL_TEXT, text);
        values.put(COL_DEADLINE, deadline);
        values.put(COL_DONE, done ? 1 : 0);

        return db.update(TABLE_NOTES, values, COL_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int deleteNote(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NOTES, COL_ID + "=?", new String[]{String.valueOf(id)});
    }

    private Note readNote(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ID));
        String title = cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE));
        String text = cursor.getString(cursor.getColumnIndexOrThrow(COL_TEXT));
        String deadline = cursor.getString(cursor.getColumnIndexOrThrow(COL_DEADLINE));
        boolean done = cursor.getInt(cursor.getColumnIndexOrThrow(COL_DONE)) == 1;
        return new Note(id, title, text, deadline, done);
    }
}
