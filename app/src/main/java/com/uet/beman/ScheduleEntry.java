package com.uet.beman;

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

    public static final String TABLE_MESSAGE = "Message";
    public static final String TABLE_MSG_TIME = "Schedule";

    public static final String _ID = "cid";

    public static final String SQL_DELETE_TABLE_MESSAGE = "DROP TABLE IF EXISTS" + TABLE_MESSAGE;
    public static final String SQL_DELETE_TABLE_MSG_TIME = "DROP TABLE IF EXISTS" + TABLE_MSG_TIME;

    public static final String SQL_CREATE_TABLE_MESSAGE = "CREATE TABLE " + TABLE_MESSAGE + " (" +
                                                            _ID + " INTEGER PRIMARY KEY," +
                                                            COLUMN_MESSAGE_ID + TEXT_TYPE + COMMA_SEP +
                                                            COLUMN_MESSAGE + TEXT_TYPE +
                                                            " )";

    public static final String SQL_CREATE_TABLE_MSG_TIME = "CREATE TABLE " + TABLE_MSG_TIME + " (" +
                                                            _ID + " INTEGER PRIMARY KEY," +
                                                            COLUMN_MESSAGE_ID + TEXT_TYPE + COMMA_SEP +
                                                            COLUMN_ALARM_TIME + TEXT_TYPE + COMMA_SEP +
                                                            "FOREIGN KEY (" + COLUMN_MESSAGE_ID + ") REFERENCES " +
                                                            TABLE_MSG_TIME + "(" + COLUMN_MESSAGE_ID + ")" +
                                                            " )";

    public static final String SQL_JOIN_TABLES_BY_ID = "SELECT " + TABLE_MESSAGE + DOT_SEP + _ID +
                                                        COMMA_SEP + TABLE_MESSAGE + DOT_SEP + COLUMN_MESSAGE +
                                                        COMMA_SEP + TABLE_MSG_TIME + DOT_SEP + COLUMN_ALARM_TIME +
                                                        " FROM " + TABLE_MESSAGE + " JOIN " + TABLE_MSG_TIME + " ON " +
                                                        TABLE_MESSAGE + DOT_SEP + _ID + " = " +
                                                        TABLE_MSG_TIME + DOT_SEP + COLUMN_MESSAGE_ID;

}
