package com.uet.beman.database;

import android.provider.BaseColumns;

/**
 * Created by thanhpd on 3/11/2015.
 */
public abstract class ScheduleEntry implements BaseColumns{
    public static final String TEXT_TYPE = " TEXT";
    public static final String DATE_TIME_TYPE = " INT";
    public static final String COMMA_SEP = ",";
    public static final String DOT_SEP = ".";

    public static final String COLUMN_MESSAGE_ID = "messageId";
    public static final String COLUMN_MESSAGE = "msg";
    public static final String COLUMN_ALARM_TIME = "alarmTime";
    public static final String COLUMN_STATUS = "state";
    public static final String COLUMN_LABEL = "label";
    public static final String COLUMN_LANGUAGE = "lang";
    public static final String COLUMN_ENABLED = "enable";
    public static final String COLUMN_DAYS = "days";
    public static final String COLUMN_PLACES = "places";

    public static final String TABLE_MESSAGE = "Message";
    public static final String TABLE_MSG_TIME = "Schedule";
    public static final String TABLE_SENT_HISTORY = "History";
    public static final String TABLE_MSG_PLACE = "place";

    public static final String _ID = "cid";

    public static final String SQL_DELETE_TABLE_MESSAGE = "DROP TABLE IF EXISTS" + TABLE_MESSAGE;
    public static final String SQL_DELETE_TABLE_MSG_TIME = "DROP TABLE IF EXISTS" + TABLE_MSG_TIME;
    public static final String SQL_DELETE_TABLE_SENT_HISTORY = "DROP TABLE IF EXISTS" + TABLE_SENT_HISTORY;

    public static final String SQL_CREATE_TABLE_MESSAGE = "CREATE TABLE " + TABLE_MESSAGE + " (" +
                                                            _ID + " INTEGER PRIMARY KEY," +
                                                            COLUMN_MESSAGE + TEXT_TYPE + COMMA_SEP +
                                                            COLUMN_LANGUAGE + TEXT_TYPE + COMMA_SEP +
                                                            COLUMN_LABEL + TEXT_TYPE +
                                                            " )";

    public static final String SQL_CREATE_TABLE_MSG_TIME = "CREATE TABLE " + TABLE_MSG_TIME + " (" +
                                                            _ID + " INTEGER PRIMARY KEY," +
                                                            COLUMN_MESSAGE_ID + TEXT_TYPE + COMMA_SEP +
                                                            COLUMN_ALARM_TIME + TEXT_TYPE + COMMA_SEP +
                                                            "FOREIGN KEY (" + COLUMN_MESSAGE_ID + ") REFERENCES " +
                                                            TABLE_MESSAGE + "(" + _ID + ")" +
                                                            " )";

    public static final String SQL_CREATE_TABLE_SENT_HISTORY = "CREATE TABLE " + TABLE_SENT_HISTORY + " (" +
                                                                _ID + " INTEGER PRIMARY KEY," +
                                                                COLUMN_MESSAGE_ID + TEXT_TYPE + COMMA_SEP +
                                                                COLUMN_STATUS + TEXT_TYPE + COMMA_SEP +
                                                                COLUMN_ALARM_TIME + TEXT_TYPE + COMMA_SEP +
                                                                COLUMN_LABEL + TEXT_TYPE + COMMA_SEP +
                                                                "FOREIGN KEY (" + COLUMN_MESSAGE_ID + ") REFERENCES " +
                                                                TABLE_MESSAGE + "(" + _ID + ")" +
                                                                " )";

    public static final String SQL_CREATE_TABLE_MSG_PLACE = "CREATE TABLE " + TABLE_MSG_PLACE + " (" +
                                                            _ID + " INTEGER PRIMARY KEY," +
                                                            COLUMN_MESSAGE_ID + TEXT_TYPE + COMMA_SEP +
                                                            COLUMN_PLACES + TEXT_TYPE + COMMA_SEP +
                                                            "FOREIGN KEY (" + COLUMN_MESSAGE_ID + ") REFERENCES " +
                                                            TABLE_MESSAGE + "(" + _ID + ")" +
                                                            " )";

    public static final String SQL_JOIN_TABLES_BY_ID = "SELECT " + TABLE_MESSAGE + DOT_SEP + "*" +
                                                        COMMA_SEP + TABLE_MESSAGE + DOT_SEP + COLUMN_MESSAGE +
                                                        COMMA_SEP + TABLE_MSG_TIME + DOT_SEP + COLUMN_ALARM_TIME +
                                                        " FROM " + TABLE_MESSAGE + " JOIN " + TABLE_MSG_TIME + " ON " +
                                                        TABLE_MESSAGE + DOT_SEP + _ID + " = " +
                                                        TABLE_MSG_TIME + DOT_SEP + COLUMN_MESSAGE_ID;
                                                     //   select message.cid, message.message, schedule.alarmTime from

    public static final String SQL_DELETE_SENT_SMS = "DELETE FROM " + TABLE_MSG_TIME + " WHERE " +
                                                     COLUMN_ALARM_TIME + " = (SELECT MIN(" + COLUMN_ALARM_TIME +
                                                     ") FROM " + TABLE_MSG_TIME +")";

}
