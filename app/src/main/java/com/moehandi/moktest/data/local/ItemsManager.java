package com.moehandi.moktest.data.local;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import androidx.annotation.WorkerThread;

import java.util.ArrayList;
import java.util.List;

import com.moehandi.moktest.data.remote.model.Item;
import com.moehandi.moktest.util.AppUtil;
import com.moehandi.moktest.data.local.DbConstants.Tables;
import com.moehandi.moktest.data.local.DbConstants.ItemsTable;


/**
 * Created by moehandi on 23/5/19.
 */

public class ItemsManager {

    private final SQLiteDatabase mDbHelper;

    public ItemsManager(SQLiteDatabase mDbHelper) {
        this.mDbHelper = mDbHelper;
    }

    @WorkerThread
    public void insertItems(List<Item> items) {
        mDbHelper.beginTransaction();

        SQLiteStatement insert = mDbHelper.compileStatement(DbConstants.INSERT_ITEM_QUERY);
        for (Item item : items) {
            item.setPrice(item.getId() * AppUtil.getRandomNumber(10, 99));
            insert.bindDouble(1, item.getId());
            insert.bindDouble(2, item.getAlbumId());
            insert.bindString(3, item.getTitle());
            insert.bindDouble(4, item.getPrice());
            insert.bindString(5, item.getUrl());
            insert.bindString(6, item.getThumbnailUrl());
            insert.execute();
        }
        mDbHelper.setTransactionSuccessful();
        mDbHelper.endTransaction();
    }

    @WorkerThread
    public ArrayList<Item> getItems() {
        ArrayList<Item> items = new ArrayList<>();
        Cursor cursor = mDbHelper.query(Tables.ITEMS, null, null, null, null, null, ItemsTable.COLUMN_ITEM_ID + " ASC");
        while (cursor.moveToNext()) {
            items.add(CursorUtil.getItem(cursor));
        }
        if (cursor != null) {
            cursor.close();
        }

        return items;
    }


}
