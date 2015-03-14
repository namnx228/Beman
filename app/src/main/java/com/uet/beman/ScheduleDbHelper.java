package com.uet.beman;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by thanhpd on 3/11/2015.
 */
public class ScheduleDbHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Scheduler.db";

    private static ScheduleDbHelper instance = null;

    public static ScheduleDbHelper getInstance() {
        if (instance == null) instance = new ScheduleDbHelper(BM_Application.getInstance());
        return instance;
    }

    public ScheduleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ScheduleEntry.SQL_CREATE_TABLE_MESSAGE);
        db.execSQL(ScheduleEntry.SQL_CREATE_TABLE_MSG_TIME);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ScheduleEntry.SQL_DELETE_TABLE_MESSAGE);
        db.execSQL(ScheduleEntry.SQL_DELETE_TABLE_MSG_TIME);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }


}
