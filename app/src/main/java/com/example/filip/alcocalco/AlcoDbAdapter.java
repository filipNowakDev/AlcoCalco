package com.example.filip.alcocalco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by filip on 7/20/17.
 */

public class AlcoDbAdapter
{
    public static final String KEY_ID = "_id";
    public static final String ID_OPTIONS = "INTEGER PRIMARY KEY AUTOINCREMENT";
    public static final int ID_COLUMN = 0;
    public static final String KEY_VOLUME = "volume";
    public static final String VOLUME_OPTIONS = "REAL DEFAULT 0";
    public static final int VOLUME_COLUMN = 1;
    public static final String KEY_PERCENTS = "percents";
    public static final String PERCENTS_OPTIONS = "REAL DEFAULT 0 ";
    public static final int PERCENTS_COLUMN = 2;
    private static final String DEBUG_TAG = "SqLiteAlcoCalco";
    private static final int DB_VERSION = 6;
    private static final String DB_NAME = "PromileCalcoDatabase.db";
    private static final String DB_ALCO_TABLE = "alcoTable";
    private static final String DB_CREATE_ALCO_TABLE =
            "CREATE TABLE " + DB_ALCO_TABLE + "( "
                    + KEY_ID + " " + ID_OPTIONS + ", "
                    + KEY_VOLUME + " " + VOLUME_OPTIONS + ", "
                    + KEY_PERCENTS + " " + PERCENTS_OPTIONS + ");";
    private static final String DROP_ALCO_TABLE = "DROP TABLE IF EXISTS " + DB_ALCO_TABLE;


    private SQLiteDatabase db;
    private Context context;
    private DatabaseHelper dbHelper;

    public AlcoDbAdapter(Context context)
    {
        this.context = context;
    }

    public AlcoDbAdapter open()
    {
        dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
        try
        {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e)
        {
            db = dbHelper.getReadableDatabase();
        }
        return this;
    }

    public void close()
    {
        dbHelper.close();
    }

    public long insertAlco(AlcoType alco)
    {
        ContentValues newAlcoValues = new ContentValues();
        newAlcoValues.put(KEY_VOLUME, alco.getVolume());
        newAlcoValues.put(KEY_PERCENTS, alco.getPercents());
        Log.d(DEBUG_TAG, "drink inserted");
        return db.insert(DB_ALCO_TABLE, null, newAlcoValues);
    }

    public boolean updateAlco(long id, AlcoType alco)
    {
        String where = KEY_ID + "=" + id;
        ContentValues updateAlcoValues = new ContentValues();
        updateAlcoValues.put(KEY_VOLUME, alco.getVolume());
        updateAlcoValues.put(KEY_PERCENTS, alco.getPercents());
        Log.d(DEBUG_TAG, "drink updated");
        return db.update(DB_ALCO_TABLE, updateAlcoValues, where, null) > 0;
    }

    public boolean deleteAlco(long id)
    {
        String where = KEY_ID + "=" + id;
        Log.d(DEBUG_TAG, "drink No." + id + "removed");
        return db.delete(DB_ALCO_TABLE, where, null) > 0;
    }

    public Cursor getAllAlcos()
    {
        String[] columns = {KEY_ID, KEY_VOLUME, KEY_PERCENTS};
        return db.query(DB_ALCO_TABLE, columns, null, null, null, null, null);
    }

    public AlcoType getAlco(long id)
    {
        String[] columns = {KEY_ID, KEY_VOLUME, KEY_PERCENTS};
        String where = KEY_ID + "=" + id;
        Cursor cursor = db.query(DB_ALCO_TABLE, columns, where, null, null, null, null);
        AlcoType alco = null;
        if (cursor != null && cursor.moveToFirst())
        {
            double volume = cursor.getDouble(VOLUME_COLUMN);
            double percents = cursor.getDouble(PERCENTS_COLUMN);
            alco = new AlcoType(id, 0, volume, percents);
            cursor.close();
        }
        return alco;
    }

    public void deleteAllAlcos()
    {
        //db.execSQL(DROP_ALCO_TABLE);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
        {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(DB_CREATE_ALCO_TABLE);

            Log.d(DEBUG_TAG, "Database creating...");
            Log.d(DEBUG_TAG, "Table " + DB_ALCO_TABLE + ", ver." + DB_VERSION + " created");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            db.execSQL(DROP_ALCO_TABLE);
            Log.d(DEBUG_TAG, "Database updating...");
            Log.d(DEBUG_TAG, "Table " + DB_ALCO_TABLE + " updated from ver." + oldVersion + " to ver." + newVersion);
            Log.d(DEBUG_TAG, "All data is lost.");

            onCreate(db);
        }
    }


}
