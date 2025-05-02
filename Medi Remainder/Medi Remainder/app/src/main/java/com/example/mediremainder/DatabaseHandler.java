package com.example.mediremainder;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "MediRemainderDatabase";

    public DatabaseHandler(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        String sql = "CREATE TABLE mediremainder " +
                "( id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "medname TEXT, " +
                "dosage INTEGER, "+
                "time TEXT);";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

        String sql = "DROP TABLE IF EXISTS mediremainder";
        db.execSQL(sql);
        onCreate(db);
    }
}

