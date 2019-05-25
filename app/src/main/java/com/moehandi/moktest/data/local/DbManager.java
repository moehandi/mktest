package com.moehandi.moktest.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by moehandi on 23/5/19.
 */

public class DbManager {

    private static final String TAG = DbManager.class.getName();

    private Context mContext;
    private static DbManager mDBManager;
    private SQLiteDatabase mDbHelper;

    private DbManager() {
    }

    private DbManager(Context context) {
        this.mContext = context;
        mDbHelper = new DateBaseHelper(mContext).getWritableDatabase();
    }

    public SQLiteDatabase getDbHelper() {
        return mDbHelper;
    }

    public static DbManager getInstance(Context context) {
        if (mDBManager == null) {
            mDBManager = new DbManager(context);
        }
        return mDBManager;
    }

    private class DateBaseHelper extends SQLiteOpenHelper {
        public DateBaseHelper(Context context) {
            super(context, DbConstants.DB_NAME, null, DbConstants.DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DbConstants.SQL_CREATE_ITEMS_TABLE);
            db.execSQL(DbConstants.SQL_CREATE_SHOPPING_CART_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL(DbConstants.SQL_DROP_ITEMS_TABLE);
            db.execSQL(DbConstants.SQL_DROP_SHOPPING_CART_TABLE);
        }
    }
}
