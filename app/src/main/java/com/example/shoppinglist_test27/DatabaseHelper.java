package com.example.shoppinglist_test27;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "shopping.db";
    private static final int DATABASE_VERSION = 2; // Sürüm numarasını artırdık.


    private static final String TABLE_SHOPPING_LISTS = "shopping_lists";
    private static final String COLUMN_LIST_ID = "list_id";
    private static final String COLUMN_LIST_NAME = "list_name";
    private static final String COLUMN_LIST_DATE = "list_date";


    private static final String TABLE_ITEMS = "items";
    private static final String COLUMN_ITEM_ID = "item_id";
    private static final String COLUMN_ITEM_NAME = "item_name";
    private static final String COLUMN_ITEM_LIST_ID = "list_id";
    private static final String COLUMN_ITEM_CHECKED = "is_checked";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createShoppingListsTable = "CREATE TABLE " + TABLE_SHOPPING_LISTS + " (" +
                COLUMN_LIST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LIST_NAME + " TEXT NOT NULL, " +
                COLUMN_LIST_DATE + " TEXT NOT NULL)";
        db.execSQL(createShoppingListsTable);

        String createItemsTable = "CREATE TABLE " + TABLE_ITEMS + " (" +
                COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ITEM_NAME + " TEXT NOT NULL, " +
                COLUMN_ITEM_LIST_ID + " INTEGER NOT NULL, " +
                COLUMN_ITEM_CHECKED + " INTEGER DEFAULT 0, " +
                "FOREIGN KEY(" + COLUMN_ITEM_LIST_ID + ") REFERENCES " + TABLE_SHOPPING_LISTS + "(" + COLUMN_LIST_ID + "))";
        db.execSQL(createItemsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_ITEMS + " ADD COLUMN " + COLUMN_ITEM_CHECKED + " INTEGER DEFAULT 0");
        }
    }


    public long addShoppingList(String listName, String listDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LIST_NAME, listName);
        values.put(COLUMN_LIST_DATE, listDate);
        return db.insert(TABLE_SHOPPING_LISTS, null, values);
    }


    public Cursor getShoppingLists() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_SHOPPING_LISTS, null, null, null, null, null, COLUMN_LIST_DATE + " DESC");
    }


    public Cursor getItemsForList(long listId) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_ITEMS, null, COLUMN_ITEM_LIST_ID + " = ?", new String[]{String.valueOf(listId)}, null, null, null);
    }


    public long addItem(String itemName, long listId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_NAME, itemName);
        values.put(COLUMN_ITEM_LIST_ID, listId);
        return db.insert(TABLE_ITEMS, null, values);
    }


    public void updateItemChecked(long itemId, boolean isChecked) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_ITEM_CHECKED, isChecked ? 1 : 0);
        db.update(TABLE_ITEMS, values, COLUMN_ITEM_ID + " = ?", new String[]{String.valueOf(itemId)});
    }
}

