package com.moehandi.moktest.data.local;

import android.database.Cursor;

import com.moehandi.moktest.data.local.DbConstants.ItemsTable;
import com.moehandi.moktest.data.local.DbConstants.ShoppingCartTable;
import com.moehandi.moktest.data.local.model.CartItem;
import com.moehandi.moktest.data.remote.model.Item;


/**
 * Created by moehandi on 23/5/19.
 */

public class CursorUtil {

    public static Item getItem(Cursor cursor) {
        Item item = new Item();

        item.setId(cursor.getInt(cursor.getColumnIndex(ItemsTable.COLUMN_ITEM_ID)));
        item.setAlbumId(cursor.getInt(cursor.getColumnIndex(ItemsTable.COLUMN_ITEM_ALBUM_ID)));
        item.setTitle(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_ITEM_TITLE)));
        item.setPrice(cursor.getInt(cursor.getColumnIndex(ItemsTable.COLUMN_ITEM_PRICE)));
        item.setUrl(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_ITEM_URL)));
        item.setThumbnailUrl(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_ITEM_THUMBNAIL_URL)));

        return item;
    }

    public static CartItem getItemFromCart(Cursor cursor, boolean joinTable) {
        CartItem item = new CartItem();

        item.setItemId(cursor.getInt(cursor.getColumnIndex(ShoppingCartTable.COLUMN_CART_ITEM_ID)));
        item.setQuantity(cursor.getInt(cursor.getColumnIndex(ShoppingCartTable.COLUMN_CART_ITEM_QUANTITY)));
        item.setTotalPrice(cursor.getDouble(cursor.getColumnIndex(ShoppingCartTable.COLUMN_CART_ITEM_TOTAL_PRICE)));
        item.setDiscount(cursor.getDouble(cursor.getColumnIndex(ShoppingCartTable.COLUMN_CART_ITEM_DISCOUNTS)));
        item.setDiscountRate(cursor.getDouble(cursor.getColumnIndex(ShoppingCartTable.COLUMN_CART_ITEM_DISCOUNT_RATE)));
        if (joinTable) {
            item.setItemTitle(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_ITEM_TITLE)));
            item.setThumbnailUrl(cursor.getString(cursor.getColumnIndex(ItemsTable.COLUMN_ITEM_THUMBNAIL_URL)));
        }

        return item;
    }
}
