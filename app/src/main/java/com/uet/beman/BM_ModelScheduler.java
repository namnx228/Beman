package com.uet.beman;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by thanhpd on 3/12/2015.
 */
public class BM_ModelScheduler {
    private ScheduleDbHelper mDbHelper;
    private SQLiteDatabase db;

    private static BM_ModelScheduler instance = null;

    public static BM_ModelScheduler getInstance() {
        if(instance == null) instance = new BM_ModelScheduler();
        return instance;
    }

    public BM_ModelScheduler() {
        mDbHelper = ScheduleDbHelper.getInstance();
    }

    private void openDb() {
        db = mDbHelper.getReadableDatabase();
    }

    private void closeDb() {
        db.close();
    }

    public void dropDb() {
        openDb();
        db.execSQL(ScheduleEntry.SQL_DELETE_TABLE_MESSAGE);
        db.execSQL(ScheduleEntry.SQL_DELETE_TABLE_MSG_TIME);
        db.execSQL(ScheduleEntry.SQL_CREATE_TABLE_MESSAGE);
        db.execSQL(ScheduleEntry.SQL_CREATE_TABLE_MSG_TIME);
        closeDb();
    }
}
