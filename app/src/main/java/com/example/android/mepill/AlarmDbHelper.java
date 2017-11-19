package com.example.android.mepill;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.FileNotFoundException;

/**
 * Created by Elena on 19-Nov-17.
 */

public class AlarmDbHelper {

    private static final String TABLE_CREATE =
            "create table if not exists ALARM(id integer primary key autoincrement, "
                    + "quantity integer not null,"
                    + " medicine text not null,"
                    + " hour integer not null,"
                    + " minute integer not null"
                    +");";

    private static final String DATABASE_NAME = "MEPILLDB";

    private static final String DATABASE_TABLE = "ALARM";

    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public AlarmDbHelper(Context ctx) {
        db = ctx.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
        db.execSQL(TABLE_CREATE);
    }

    public void close() {
        db.close();
    }

    public void createRow(Alarm alarm) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("quantity", alarm.getQuantity());
        initialValues.put("medicine", alarm.getMedicine());
        initialValues.put("hour", alarm.getHour());
        initialValues.put("minute", alarm.getMinute());

        long id = db.insert(DATABASE_TABLE, null, initialValues);
        Log.i("DB", "inserted with id="+id);
    }
}
