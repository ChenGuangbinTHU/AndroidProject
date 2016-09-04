package com.ihandy.a2014011315;

import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by bingochen on 2016/9/1.
 */
public class Database extends SQLiteOpenHelper implements Runnable{

    private static final int VERSION = 1;

    private static Database db;

    public static SQLiteDatabase getInstance(Context context)
    {
        if(db == null)
        {
            db = new Database(context,"chenguangbin.db",null,1);
            Thread t = new Thread(db);
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return db.getReadableDatabase();
    }

    public Database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            db.execSQL("create table news if not exists(id integer primary key," +
                    "category text," +
                    "country text," +
                    "fetchedTime text, " +
                    "imgsUrl text," +
                    "localeCategory text," +
                    "newsId text," +
                    "origin text," +
                    "sourceName text," +
                    "sourceUrl text," +
                    "title text," +
                    "updateTime text," +
                    "imageByte blob," +
                    "love integer" +
            ")");

        } catch (Exception e) {
            Log.d("fuck_sqlite","fail");
            e.printStackTrace();
        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void run() {
        try {
            db.getReadableDatabase().execSQL("create table  if not exists news(id integer primary key," +
                    "category text," +
                    "country text," +
                    "fetchedTime text, " +
                    "imgsUrl text," +
                    "localeCategory text," +
                    "newsId text," +
                    "origin text," +
                    "sourceName text," +
                    "sourceUrl text," +
                    "title text," +
                    "updateTime text," +
                    "imageByte blob," +
                    "love integer" +
                    ")");

            db.getReadableDatabase().execSQL("create table  if not exists category(id integer primary key," +
                    "category text," +
                    "watch integer" +
                    ")");
            Log.d("fuck","finish create");

        } catch (Exception e) {
            Log.d("fuck_sqlite","fail");
            e.printStackTrace();
            Log.d("fuck_sqlite",e.toString());
        }
    }
}
