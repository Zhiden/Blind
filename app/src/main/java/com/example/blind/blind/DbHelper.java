package com.example.blind.blind;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by drcov on 04.06.2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, "test.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table mytable ("
                + "_id integer primary key autoincrement,"
                + "text text,"
                + "month text,"
                + "day integer" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addTask(Task t) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("_id", t.getId());
        cv.put("text", t.getText());
        cv.put("month", t.getMonth());
        cv.put("day", t.getDay());

        long rowID = db.insert("mytable", null, cv);
        Log.d("DATABASE", "row inserted, ID = " + rowID);
        db.close();

    }

    public List<Task> getTasks(int day, String month) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("mytable",
                new String[]{"_id", "text", "month", "day"},
                "month = ? and day = ?",
                new String[]{month, String.valueOf(day)}, null, null, null);
        List<Task> tasks = new ArrayList<>();
        if (cursor.moveToFirst()) {

            // определяем номера столбцов по имени в выборке
            int idColIndex = cursor.getColumnIndex("_id");
            int monthColIndex = cursor.getColumnIndex("month");
            int dayColIndex = cursor.getColumnIndex("day");
            int text = cursor.getColumnIndex("text");

            do {
                // получаем значения по номерам столбцов и пишем все в лог
                Task t = new Task();
                t.setId(cursor.getInt(idColIndex));
                t.setMonth(cursor.getString(monthColIndex));
                t.setDay(cursor.getInt(dayColIndex));
                t.setText(cursor.getString(text));
                Log.d("DATABASE",
                        "ID = " + cursor.getInt(idColIndex) +
                                ", month = " + cursor.getString(monthColIndex) +
                                ", day = " + cursor.getInt(dayColIndex) + ", text = " + cursor.getString(text));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
                tasks.add(t);
            } while (cursor.moveToNext());
        } else
            Log.d("DATABASE", "0 rows");
        cursor.close();

        db.close();

        return tasks;
    }

    public void delete(Task pos) {
        SQLiteDatabase db = this.getReadableDatabase();
        db.delete("mytable",
                "_id = ?",
                new String[]{String.valueOf(pos.getId())});
        db.close();
    }
}
