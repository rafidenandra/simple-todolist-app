package com.example.todoapp.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
import com.example.todoapp.Model.ToDoModel;
import java.util.ArrayList;
import java.util.List;


public class TaskDBHelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    private static final int VERSION = 5;
    private static final String DATABASE_NAME = "TODO_DATABASE";
    private static final String TABLE_NAME = "TODOLIST_TABLE";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_TASK = "TASK";
    private static final String COLUMN_STATUS = "STATUS";
    private static final String COLUMN_USERID = "USER_ID";

    public TaskDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TODO_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID
                                          + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                                          + COLUMN_TASK + " TEXT, "
                                          + COLUMN_STATUS + " INTEGER, "
                                          + COLUMN_USERID + " INTEGER, FOREIGN KEY "
                                          + " (" + COLUMN_USERID + ") " + "REFERENCES "
                                          + TABLE_NAME + " (" + COLUMN_ID + "));";
//        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
//                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, TASK TEXT, STATUS INTEGER)");
        db.execSQL(CREATE_TODO_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertTask(ToDoModel model) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, model.getTask());
        values.put(COLUMN_STATUS, 0);
        values.put(COLUMN_USERID, model.getUserID());
        db.insert(TABLE_NAME, null, values);
    }

    public void updateTask(int id, String task) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TASK, task);
        db.update(TABLE_NAME, values, "ID=?", new String[]{String.valueOf(id)});
    }

    public void updateStatus(int id, int status) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_STATUS, status);
        db.update(TABLE_NAME, values, "ID=?", new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id) {
        db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID=?", new String[]{String.valueOf(id)});
    }

    public List<ToDoModel> getAllTask(int userId) {
        List<ToDoModel> modelList = new ArrayList<>();
        Cursor cursor = null;
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_USERID + "=" + userId;
        ToDoModel task = new ToDoModel();

        db = this.getWritableDatabase();
        db.beginTransaction();

        try {
//            String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + COL_4 + " = " + email;
//            cursor = db.query(TABLE_NAME, null, null, null, null,
//                    null, null);
            cursor = db.rawQuery(sql, null);

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        task.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                        task.setTask(cursor.getString(cursor.getColumnIndex(COLUMN_TASK)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_STATUS)));

                        modelList.add(task);
                    } while (cursor.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            cursor.close();
        }

        return modelList;
    }
}
