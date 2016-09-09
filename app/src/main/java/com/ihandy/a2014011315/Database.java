package com.ihandy.a2014011315;

import android.content.Context;
//import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Vector;

/**
 * Created by bingochen on 2016/9/1.
 */
public class Database extends SQLiteOpenHelper implements Runnable{

    private static final int VERSION = 1;

    private static Database db;

    public static SQLiteDatabase getInstance(Context context)//使用单例模式获取数据库
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
            e.printStackTrace();
        }
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //将创建news和category表的操作放在线程中执行
    @Override
    public void run() {
        try {
            db.getReadableDatabase().execSQL(
                    "create table  if not exists news(id integer primary key," +
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

            db.getReadableDatabase().execSQL(
                    "create table  if not exists category(id integer primary key," +
                    "category text," +
                    "title text," +
                    "watch integer" +
                    ")");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Vector<String> getWatchNewsClassify()//获取可以观看的classify
    {
        Vector<String> newsClassify = new Vector<>();
        Cursor c = db.getReadableDatabase().query("category",null,"watch=?",new String[]{Integer.toString(1)},null,null,null,null);
        while(c.moveToNext())
        {
            String classify = c.getString(1);
            newsClassify.add(classify);
        }
        return newsClassify;
    }

    public static Vector<String> getWatchNewsTitle()//获取可以观看的title
    {
        Vector<String> newsTitle= new Vector<>();
        Cursor c = db.getReadableDatabase().query("category",null,"watch=?",new String[]{Integer.toString(1)},null,null,null,null);


        while(c.moveToNext())
        {
            String title = c.getString(2);
            newsTitle.add(title);
        }
        return newsTitle;
    }

    public static Vector<String> getUnWatchNewsTitle()//获取不可以观看的title
    {
        Vector<String> newsTitle= new Vector<>();
        Cursor c = db.getReadableDatabase().query("category",null,"watch=?",new String[]{Integer.toString(0)},null,null,null,null);
        while(c.moveToNext())
        {
            String title = c.getString(2);
            newsTitle.add(title);
        }
        return newsTitle;
    }


}
