package com.uet.beman.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.uet.beman.common.BM_Utils;
import com.uet.beman.common.SharedPreferencesHelper;
import com.uet.beman.object.Node;
import com.uet.beman.object.SentenceNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thanhpd on 3/12/2015.
 */
public class BM_ModelScheduler {
    private static BM_ModelScheduler instance = null;
    private ScheduleDbHelper mDbHelper;
    private SharedPreferencesHelper spHelper;
    private SQLiteDatabase db;
    private BM_Utils utils;

    public BM_ModelScheduler() {
        mDbHelper = ScheduleDbHelper.getInstance();
        utils = BM_Utils.getInstance();
        spHelper = SharedPreferencesHelper.getInstance();

//        try {
//            mDbHelper.
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public static BM_ModelScheduler getInstance() {
        if (instance == null) instance = new BM_ModelScheduler();
        return instance;
    }

    private void openDb() {
//        try {
//            mDbHelper.createDB();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        db = mDbHelper.getReadableDatabase();
        db = mDbHelper.getWritableDatabase();
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
        db.execSQL(ScheduleEntry.SQL_CREATE_TABLE_MSG_PLACE);
        closeDb();
    }

    private Node cursorToSchedule(Cursor cursor) {
        SentenceNode node = new SentenceNode();
//        node.setMessageId(cursor.getInt(cursor.getColumnIndex(ScheduleEntry.COLUMN_MESSAGE_ID)));
        node.setMessage(cursor.getString(cursor.getColumnIndex(ScheduleEntry.COLUMN_MESSAGE)));
//        node.setParentId(cursor.getInt(cursor.getColumnIndex(ScheduleEntry)));
        node.setSendTime(cursor.getString(cursor.getColumnIndex(ScheduleEntry.COLUMN_ALARM_TIME)));
//        node.setSendTimeEpoch(cursor.getString(cursor.getColumnIndex(ScheduleEntry.COLUMN_ALARM_TIME)));
        node.setId(cursor.getString(cursor.getColumnIndex(ScheduleEntry._ID)));
        node.setLabel(cursor.getString(cursor.getColumnIndex(ScheduleEntry.COLUMN_LABEL)));
        node.setLanguage(cursor.getString(cursor.getColumnIndex(ScheduleEntry.COLUMN_LANGUAGE)));
        node.setEnabled(cursor.getString(cursor.getColumnIndex(ScheduleEntry.COLUMN_ENABLED)));
        return node;
    }

    private Node cursorToMessageList(Cursor cursor) {
        SentenceNode node = new SentenceNode();
//        node.setMessageId(cursor.getInt(cursor.getColumnIndex(ScheduleEntry.COLUMN_MESSAGE_ID)));
        node.setMessage(cursor.getString(cursor.getColumnIndex(ScheduleEntry.COLUMN_MESSAGE)));
//        node.setParentId(cursor.getInt(cursor.getColumnIndex(ScheduleEntry)));
        //        node.setSendTimeEpoch(cursor.getString(cursor.getColumnIndex(ScheduleEntry.COLUMN_ALARM_TIME)));
        node.setEnabled(cursor.getString(cursor.getColumnIndex(ScheduleEntry.COLUMN_ENABLED)));
        node.setDays(cursor.getString(cursor.getColumnIndex(ScheduleEntry.COLUMN_DAYS)));
        node.setId(cursor.getString(cursor.getColumnIndex(ScheduleEntry._ID)));
        node.setLabel(cursor.getString(cursor.getColumnIndex(ScheduleEntry.COLUMN_LABEL)));
        node.setLanguage(cursor.getString(cursor.getColumnIndex(ScheduleEntry.COLUMN_LANGUAGE)));
        return node;
    }

    public long addSentence(SentenceNode node) {
        int dbVer = spHelper.getDatabaseVersion();
        long returnidx = -1;
        openDb();
        String[] allColumn = {ScheduleEntry.COLUMN_MESSAGE, ScheduleEntry.COLUMN_LANGUAGE, ScheduleEntry.COLUMN_LABEL, ScheduleEntry.COLUMN_ENABLED, ScheduleEntry.COLUMN_DAYS};

        String selection = ScheduleEntry._ID + " = " + node.getId();

        Cursor cursor = db.query(ScheduleEntry.TABLE_MESSAGE, allColumn, selection, null, null, null, null);

        if (cursor.getCount() <= 0) {
            ContentValues values = new ContentValues();
            values.put(ScheduleEntry.COLUMN_MESSAGE, node.getMessage());
            values.put(ScheduleEntry.COLUMN_LANGUAGE, node.getLanguage());
            values.put(ScheduleEntry.COLUMN_LABEL, node.getLabel());
            values.put(ScheduleEntry.COLUMN_ENABLED, node.getEnabled());
            values.put(ScheduleEntry.COLUMN_DAYS, node.getDays());

            returnidx = db.insert(ScheduleEntry.TABLE_MESSAGE, null, values);
        }
        closeDb();
        cursor.close();
        spHelper.setDatabaseVersion(dbVer + 1);
        return returnidx;
    }

    public void addSchedule(SentenceNode node) {
        openDb();
        String[] allColumn1 = {ScheduleEntry._ID, ScheduleEntry.COLUMN_MESSAGE};

        String selection1 = ScheduleEntry.COLUMN_MESSAGE + " = '" + node.getMessage() + "'";

        Cursor cursor1 = db.query(ScheduleEntry.TABLE_MESSAGE, allColumn1, selection1, null, null, null, null);

        if (cursor1.getCount() == 0) {
            ContentValues values = new ContentValues();
//            values.put(ScheduleEntry.COLUMN_MESSAGE_ID, node.getMessageId());
            values.put(ScheduleEntry.COLUMN_MESSAGE, node.getMessage());
            values.put(ScheduleEntry.COLUMN_LANGUAGE, "VIE");
            values.put(ScheduleEntry.COLUMN_LABEL, node.getLabel());
            values.put(ScheduleEntry.COLUMN_DAYS, node.getDays());
            values.put(ScheduleEntry.COLUMN_ENABLED, node.getEnabled());

            long id = db.insert(ScheduleEntry.TABLE_MESSAGE, null, values);
            node.setMessageId(String.valueOf(id));
        } else if (cursor1.getCount() > 0) {
            cursor1.moveToFirst();
            int id = cursor1.getInt(cursor1.getColumnIndex(ScheduleEntry._ID));
            node.setMessageId(String.valueOf(id));
        }
        cursor1.close();

        String[] allColumn2 = {ScheduleEntry.COLUMN_MESSAGE_ID, ScheduleEntry.COLUMN_ALARM_TIME};

        String selection2 = ScheduleEntry.COLUMN_ALARM_TIME + " = '" + node.getSendTime() + "' AND " +
                ScheduleEntry.COLUMN_MESSAGE_ID + " = " + node.getMessageId();

        Cursor cursor2 = db.query(ScheduleEntry.TABLE_MSG_TIME, allColumn2, selection2, null, null, null, null);

        if (cursor2.getCount() == 0) {
            ContentValues values = new ContentValues();
            values.put(ScheduleEntry.COLUMN_MESSAGE_ID, node.getMessageId());
            values.put(ScheduleEntry.COLUMN_ALARM_TIME, node.getSendTime());

            db.insert(ScheduleEntry.TABLE_MSG_TIME, null, values);
        }
        cursor2.close();
        closeDb();
    }

    public void delMessageInSchedule(SentenceNode node)
    {
        openDb();
        String where = ScheduleEntry.COLUMN_ALARM_TIME + " = " + addQuote(node.getSendTime());
        db.delete(ScheduleEntry.TABLE_MSG_TIME, where,null);
        closeDb();
    }

    public void delSchedule()
    {
        openDb();

        db.delete(ScheduleEntry.TABLE_MSG_TIME, null,null);
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
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                SentenceNode tmp = (SentenceNode) cursorToSchedule(cursor);
                result.add(tmp);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDb();
        return result;
    }

    public List<SentenceNode> getSentenceNodeByMessage(String body) {
        openDb();
        List<SentenceNode> result = new ArrayList<>();
//        String[] tableMessageColumn = { ScheduleEntry.COLUMN_MESSAGE_ID, ScheduleEntry.COLUMN_MESSAGE };
//        String[] tableScheduleColumn = { ScheduleEntry.COLUMN_MESSAGE_ID, ScheduleEntry.COLUMN_ALARM_TIME };

        String selection = ScheduleEntry.SQL_JOIN_TABLES_BY_ID + " WHERE " + ScheduleEntry.TABLE_MESSAGE +
                ScheduleEntry.DOT_SEP + ScheduleEntry.COLUMN_MESSAGE + " = '" + body + "'";

        Cursor cursor = db.rawQuery(selection, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                SentenceNode tmp = (SentenceNode) cursorToSchedule(cursor);
                result.add(tmp);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDb();
        return result;
    }

    public boolean isInMessageList(String body) {
        openDb();


        String selection = "select " + ScheduleEntry.COLUMN_MESSAGE + " from "
                            + ScheduleEntry.TABLE_MESSAGE + " WHERE " + ScheduleEntry.COLUMN_MESSAGE
                            + " = '" + body + "'";
        //select msg from message where msg = 'body'
        Cursor cursor = db.rawQuery(selection, null);
        boolean result = cursor.getCount() > 0;
        cursor.close();
        closeDb();
        return result;
    }


    public List<SentenceNode> getSentenceNodeByDate(String date) {
        date = addQuote(date);
        openDb();
        List<SentenceNode> result = new ArrayList<>();
//        String[] tableMessageColumn = { ScheduleEntry.COLUMN_MESSAGE_ID, ScheduleEntry.COLUMN_MESSAGE };
//        String[] tableScheduleColumn = { ScheduleEntry.COLUMN_MESSAGE_ID, ScheduleEntry.COLUMN_ALARM_TIME };

        String getDate = ScheduleEntry.TABLE_MSG_TIME + ScheduleEntry.DOT_SEP +
                ScheduleEntry.COLUMN_ALARM_TIME;
        String selection = ScheduleEntry.SQL_JOIN_TABLES_BY_ID + " WHERE " + getDate + " = " + date;

        Cursor cursor = db.rawQuery(selection, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                SentenceNode tmp = (SentenceNode) cursorToSchedule(cursor);
                result.add(tmp);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDb();
        return result;
    }
    // get all message in schedule
    public List<SentenceNode> getAllNodes() {
        openDb();
        List<SentenceNode> result = new ArrayList<>();

        String getDate = "date('" + ScheduleEntry.TABLE_MSG_TIME + ScheduleEntry.DOT_SEP +
                ScheduleEntry.COLUMN_ALARM_TIME + "')";
        String selection = ScheduleEntry.SQL_JOIN_TABLES_BY_ID + " ORDER BY Schedule.alarmTime";

        Cursor cursor = db.rawQuery(selection, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                SentenceNode tmp = (SentenceNode) cursorToSchedule(cursor);
                result.add(tmp);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDb();
        return result;
    }

    public void updateEarliestNode() {
        openDb();
        db.execSQL(ScheduleEntry.SQL_DELETE_SENT_SMS);
        closeDb();
    }


    public List<SentenceNode> getMessagesOnSchedule(String label) {
        label = addQuote(label);
        openDb();
        List<SentenceNode> result = new ArrayList<>();

        String selection = "SELECT " + ScheduleEntry.TABLE_MESSAGE + ".*, "
                + ScheduleEntry.TABLE_MSG_TIME + "."
                + ScheduleEntry.COLUMN_ALARM_TIME + " FROM " + ScheduleEntry.TABLE_MESSAGE
                + " JOIN " + ScheduleEntry.TABLE_MSG_TIME
                + " ON " + ScheduleEntry.TABLE_MESSAGE + "." + ScheduleEntry._ID
                + "=" + ScheduleEntry.TABLE_MSG_TIME + "." + ScheduleEntry._ID
                + " WHERE " + ScheduleEntry.TABLE_MESSAGE + "."
                + ScheduleEntry.COLUMN_LABEL + " = " + label;

        Cursor cursor = db.rawQuery(selection, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                SentenceNode tmp = (SentenceNode) cursorToSchedule(cursor);
                result.add(tmp);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDb();
        return result;
    }

    public ArrayList<SentenceNode> getMessages(String label) {
        label = addQuote(label);
        openDb();
        ArrayList<SentenceNode> result = new ArrayList<>();

        String selection = "SELECT *"
                + " FROM " + ScheduleEntry.TABLE_MESSAGE
                + " WHERE " + ScheduleEntry.COLUMN_LABEL + " = " + label;

        Cursor cursor = db.rawQuery(selection, null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                SentenceNode tmp = (SentenceNode) cursorToMessageList(cursor);
                int idx = Integer.parseInt(tmp.getId());
                result.add(tmp);
                cursor.moveToNext();
            }
        }
        cursor.close();
        closeDb();
        return result;
    }

    public void updateDialog(SentenceNode currentNode) {
        int dbVersion = spHelper.getDatabaseVersion();
        openDb();

        ContentValues values = new ContentValues();
        values.put(ScheduleEntry.COLUMN_MESSAGE, currentNode.getMessage());
        values.put(ScheduleEntry.COLUMN_ENABLED, currentNode.getEnabled());
        values.put(ScheduleEntry.COLUMN_DAYS, currentNode.getDays());

        db.update(ScheduleEntry.TABLE_MESSAGE, values, ScheduleEntry._ID + " = ?", new String[]{String.valueOf(currentNode.getId())});

        closeDb();
        spHelper.setDatabaseVersion(dbVersion + 1);
    }

    private String addQuote(String s)
    {
        if (s.charAt(0) == '\"') return s;
        return '\"' + s + '\"';
    }

}

