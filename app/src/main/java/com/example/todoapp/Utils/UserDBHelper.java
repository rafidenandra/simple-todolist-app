package com.example.todoapp.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class UserDBHelper extends SQLiteOpenHelper {
    private static final int VERSION = 5;
    private static final String TAG = UserDBHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "myapp.db";
    private static final String TABLE_NAME = "users";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_PASS = "password";
    String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_NAME + " ("
                                 + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                 + COLUMN_EMAIL + " TEXT,"
                                 + COLUMN_PASS + " TEXT)";

//    public static final String CREATE_TABLE_USERS = "CREATE TABLE " + USER_TABLE + "("
//            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//            + COLUMN_EMAIL + " TEXT,"
//            + COLUMN_PASS + " TEXT);";

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean checkUser(String email, String pass) {
        SQLiteDatabase db = getReadableDatabase();

        String[] columns = {COLUMN_ID};
        String selection = COLUMN_EMAIL + "=?" + " AND " + COLUMN_PASS + "=?";
        String[] selectionArgs = {email, pass};

        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null,
                           null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkCursor(Cursor cursor) {
        return cursor.getCount() > 0 && cursor.moveToFirst();
    }

    public int getIdUser(String email, String pass) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT " + COLUMN_ID + " FROM " + TABLE_NAME + " WHERE " + COLUMN_ID +
                " =? AND " + COLUMN_PASS + " =?";

        if (db != null) {
            Cursor cursor = db.rawQuery(query, new String[] {email, pass});

            if (checkCursor(cursor)) {
                return cursor.getInt(0);
            } else {
                return 0;
            }
        }

        return 0;
    }

    public void addUser(String email, String pass) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASS, pass);

        long id = db.insert(TABLE_NAME, null, values);
        db.close();

        Log.d(TAG, "user inserted" + id);
    }
}
