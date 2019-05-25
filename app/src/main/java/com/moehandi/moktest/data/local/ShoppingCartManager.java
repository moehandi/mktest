package com.moehandi.moktest.data.local;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.annotation.WorkerThread;

import java.util.ArrayList;

import com.moehandi.moktest.data.local.DbConstants.ItemsTable;
import com.moehandi.moktest.data.local.DbConstants.ShoppingCartTable;
import com.moehandi.moktest.data.local.DbConstants.Tables;
import com.moehandi.moktest.data.local.model.CartItem;

/**
 * Created by moehandi on 23/5/19.
 */

public class ShoppingCartManager {

    private final SQLiteDatabase mDbHelper;

    public ShoppingCartManager(SQLiteDatabase mDbHelper) {
        this.mDbHelper = mDbHelper;
    }

    @WorkerThread
    public long insertCartItem(CartItem item) {
        ContentValues values = new ContentValues();
        values.put(ShoppingCartTable.COLUMN_CART_ITEM_ID, item.getItemId());
        values.put(ShoppingCartTable.COLUMN_CART_ITEM_QUANTITY, item.getQuantity());
        values.put(ShoppingCartTable.COLUMN_CART_ITEM_TOTAL_PRICE, item.getTotalPrice());
        values.put(ShoppingCartTable.COLUMN_CART_ITEM_DISCOUNTS, item.getDiscount());
        values.put(ShoppingCartTable.COLUMN_CART_ITEM_DISCOUNT_RATE, item.getDiscountRate());
        return mDbHelper.insert(Tables.SHOPPING_CART, null, values);
    }

    @WorkerThread
    public int updateCartItem(CartItem item, double previousDiscount) {
        ContentValues values = new ContentValues();
        values.put(ShoppingCartTable.COLUMN_CART_ITEM_ID, item.getItemId());
        values.put(ShoppingCartTable.COLUMN_CART_ITEM_QUANTITY, item.getQuantity());
        values.put(ShoppingCartTable.COLUMN_CART_ITEM_TOTAL_PRICE, item.getTotalPrice());
        values.put(ShoppingCartTable.COLUMN_CART_ITEM_DISCOUNTS, item.getDiscount());
        values.put(ShoppingCartTable.COLUMN_CART_ITEM_DISCOUNT_RATE, item.getDiscountRate());
        return mDbHelper.update(Tables.SHOPPING_CART, values
                , ShoppingCartTable.COLUMN_CART_ITEM_ID + " =? AND " + ShoppingCartTable.COLUMN_CART_ITEM_DISCOUNTS + "=?"
                , new String[]{String.valueOf(item.getItemId()), String.valueOf(previousDiscount)});
    }

    @WorkerThread
    public ArrayList<CartItem> getItemsFromCart() {
        ArrayList<CartItem> items = new ArrayList<>();

        String query = "SELECT A." + ShoppingCartTable.COLUMN_CART_ITEM_ID + ",A." + ShoppingCartTable.COLUMN_CART_ITEM_QUANTITY
                + ",A." + ShoppingCartTable.COLUMN_CART_ITEM_DISCOUNTS + ",A." + ShoppingCartTable.COLUMN_CART_ITEM_DISCOUNT_RATE
                + ",A." + ShoppingCartTable.COLUMN_CART_ITEM_TOTAL_PRICE
                + ",B." + ItemsTable.COLUMN_ITEM_TITLE + ",B." + ItemsTable.COLUMN_ITEM_THUMBNAIL_URL
                + " FROM " + Tables.SHOPPING_CART + " A LEFT JOIN " + Tables.ITEMS + " B ON A.item_id = B.item_id "
                + " ORDER BY A." + ShoppingCartTable._ID + " ASC";

        Cursor cursor = mDbHelper.rawQuery(query, null);

        while (cursor.moveToNext()) {
            items.add(CursorUtil.getItemFromCart(cursor, true));
        }
        if (cursor != null) {
            cursor.close();
        }

        return items;
    }

    @WorkerThread
    public CartItem getItemFormCart(int itemId, double discount) {
        CartItem item = null;
        Cursor cursor = mDbHelper.query(Tables.SHOPPING_CART, null, ShoppingCartTable.COLUMN_CART_ITEM_ID + " =? AND " + ShoppingCartTable.COLUMN_CART_ITEM_DISCOUNTS + " =?"
                , new String[]{String.valueOf(itemId), String.valueOf(discount)}, null, null, null);

        if (cursor.moveToFirst()) {
            item = CursorUtil.getItemFromCart(cursor, false);
        }

        if (cursor != null) {
            cursor.close();
        }

        return item;
    }

    @WorkerThread
    public int clearShoppingCart() {
        return mDbHelper.delete(Tables.SHOPPING_CART, null, null);
    }

    @WorkerThread
    public double getInitialTotal() {
        double total = 0;
        String query = "SELECT SUM(" + ShoppingCartTable.COLUMN_CART_ITEM_TOTAL_PRICE + ") FROM " + Tables.SHOPPING_CART;
        Cursor cursor = mDbHelper.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        if (cursor != null) {
            cursor.close();
        }
        return total;
    }

    @WorkerThread
    public double getDiscountTotal() {
        double total = 0;
        String query = "SELECT SUM(" + ShoppingCartTable.COLUMN_CART_ITEM_DISCOUNT_RATE + ") FROM " + Tables.SHOPPING_CART;
        Cursor cursor = mDbHelper.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        if (cursor != null) {
            cursor.close();
        }
        return total;
    }
}
