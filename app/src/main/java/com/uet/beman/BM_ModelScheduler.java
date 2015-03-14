package com.uet.beman;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

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

    private Node cursorToSchedule(Cursor cursor) {
        SentenceNode node = new SentenceNode();
        node.setMessageId(cursor.getInt(cursor.getColumnIndex(ScheduleEntry.COLUMN_MESSAGE_ID)));
        node.setMessage(cursor.getString(cursor.getColumnIndex(ScheduleEntry.COLUMN_MESSAGE)));
//        node.setParentId(cursor.getInt(cursor.getColumnIndex(ScheduleEntry)));
        node.setSendTime(cursor.getString(cursor.getColumnIndex(ScheduleEntry.COLUMN_ALARM_TIME)));
//        node.setSendTimeEpoch(cursor.getString(cursor.getColumnIndex(ScheduleEntry.COLUMN_ALARM_TIME)));
        return node;
    }

    public void addSentence(Node node) {
        openDb();
        String[] allColumn = { ScheduleEntry.COLUMN_MESSAGE_ID, ScheduleEntry.COLUMN_MESSAGE };

        String selection = ScheduleEntry.COLUMN_MESSAGE_ID + " = " + node.getMessageId();

        Cursor cursor = db.query(ScheduleEntry.TABLE_MESSAGE, allColumn, selection, null, null, null, null);

        if(cursor.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put(ScheduleEntry.COLUMN_MESSAGE_ID, node.getMessageId());
            values.put(ScheduleEntry.COLUMN_MESSAGE, node.getMessage());

            db.insert(ScheduleEntry.TABLE_MESSAGE, null, values);
        }
        closeDb();
    }

    public void addSchedule(SentenceNode node) {
        openDb();
        String[] allColumn = { ScheduleEntry.COLUMN_MESSAGE_ID, ScheduleEntry.COLUMN_ALARM_TIME };

        String selection = ScheduleEntry.COLUMN_MESSAGE_ID + " = " + node.getMessageId();

        Cursor cursor = db.query(ScheduleEntry.TABLE_MSG_TIME, allColumn, selection, null, null, null, null);

        if(cursor.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put(ScheduleEntry.COLUMN_MESSAGE_ID, node.getMessageId());
            values.put(ScheduleEntry.COLUMN_ALARM_TIME, node.getSendTime());

            db.insert(ScheduleEntry.TABLE_MSG_TIME, null, values);
        }
        cursor.close();
        closeDb();
    }

    public List<SentenceNode> getSentenceNodeByMessage(int id) {
        openDb();
        List<SentenceNode> result = new ArrayList<>();
//        String[] tableMessageColumn = { ScheduleEntry.COLUMN_MESSAGE_ID, ScheduleEntry.COLUMN_MESSAGE };
//        String[] tableScheduleColumn = { ScheduleEntry.COLUMN_MESSAGE_ID, ScheduleEntry.COLUMN_ALARM_TIME };

        String selection = ScheduleEntry.SQL_JOIN_TABLES_BY_ID + " WHERE " + ScheduleEntry.TABLE_MESSAGE +
                            ScheduleEntry.DOT_SEP + ScheduleEntry.COLUMN_MESSAGE_ID + " = '" + id + "'";

        Cursor cursor = db.rawQuery(selection, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                SentenceNode tmp = (SentenceNode) cursorToSchedule(cursor);
                result.add(tmp);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDb();
        return result;
    }

    public List<SentenceNode> getSentenceNodeByDate(String date) {
        openDb();
        List<SentenceNode> result = new ArrayList<>();
//        String[] tableMessageColumn = { ScheduleEntry.COLUMN_MESSAGE_ID, ScheduleEntry.COLUMN_MESSAGE };
//        String[] tableScheduleColumn = { ScheduleEntry.COLUMN_MESSAGE_ID, ScheduleEntry.COLUMN_ALARM_TIME };

        String getDate = "date('" + ScheduleEntry.TABLE_MSG_TIME + ScheduleEntry.DOT_SEP +
                         ScheduleEntry.COLUMN_ALARM_TIME + "')";
        String selection = ScheduleEntry.SQL_JOIN_TABLES_BY_ID + " WHERE " + getDate + " = " + date;

        Cursor cursor = db.rawQuery(selection, null);
        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                SentenceNode tmp = (SentenceNode) cursorToSchedule(cursor);
                result.add(tmp);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDb();
        return result;
    }
}

