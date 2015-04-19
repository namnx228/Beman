package com.uet.beman.database;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.uet.beman.common.BM_Application;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by thanhpd on 3/11/2015.
 */
public class ScheduleDbHelper extends SQLiteOpenHelper{
    private static final String DB_PATH= "data/data/com.uet.beman/databases/";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Scheduler.db";
    public static final String DB_NAME = "Scheduler";

    private static ScheduleDbHelper instance = null;
    SQLiteDatabase dbObj;

    public static ScheduleDbHelper getInstance() {
        if (instance == null) instance = new ScheduleDbHelper(BM_Application.getInstance());
        return instance;
    }

    public ScheduleDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void createDB() throws IOException {

        if(dbObj == null) dbObj = this.getReadableDatabase();
        Log.i("Readable ends","end");

        try {
            copyDB();
            Log.i("copy db ends","end");

        } catch (IOException e) {

            throw new Error("Error copying database");
        }
    }

    private boolean checkDB(){

        SQLiteDatabase checkDB = null;

        try{
            String path = DB_PATH + DB_NAME;
            Log.i("myPath ......",path);
            checkDB = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READONLY);

            Log.i("myPath ......",path);
            if (checkDB != null)
            {
                Cursor c= checkDB.rawQuery("SELECT * FROM Messages", null);
                //Log.i("Cursor.......",c.getString(0));
                c.moveToFirst();
                String contents[]=new String[80];
                int flag=0;

                while(! c.isAfterLast())
                {
                    String temp="";
                    String s2=c.getString(0);
                    String s3=c.getString(1);
                    String s4=c.getString(2);
                    temp=temp+"\n Id:"+s2+"\tType:"+s3+"\tBal:"+s4;
                    contents[flag]=temp;
                    flag=flag+1;

                    //Log.i("DB values.........",temp);
                    c.moveToNext();

                }
                c.close();
            }
            else
            {
                return false;
            }

        }catch(SQLiteException e){
            e.printStackTrace();
        }

        if(checkDB != null){

            checkDB.close();

        }
        return checkDB != null ? true : false;
    }

    public void copyDB() throws IOException{
        try {
            //Log.i("inside copyDB","start");

            InputStream ip =  BM_Application.getInstance().getAssets().open(DB_NAME+".db");
            //Log.i("Input Stream....",ip+"");
            String op=  DB_PATH  +  DATABASE_NAME ;
            OutputStream output = new FileOutputStream( op);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = ip.read(buffer))>0){
                output.write(buffer, 0, length);
                //Log.i("Content.... ",length+"");
            }
            output.flush();
            output.close();
            ip.close();
        }
        catch (IOException e) {
            Log.v("error", e.toString());
        }
    }

    public void openDB() throws SQLException {

        String myPath = DB_PATH + DB_NAME;
        dbObj = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
        //Log.i("open DB......", dbObj.toString());
    }

    @Override
    public synchronized void close() {

        if(dbObj != null)
            dbObj.close();

        super.close();
    }

    public void onCreate(SQLiteDatabase db) {
        //db.execSQL(ScheduleEntry.SQL_CREATE_TABLE_MESSAGE);
//        try {
//            createDB();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        db.execSQL(ScheduleEntry.SQL_CREATE_TABLE_MSG_TIME);
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
