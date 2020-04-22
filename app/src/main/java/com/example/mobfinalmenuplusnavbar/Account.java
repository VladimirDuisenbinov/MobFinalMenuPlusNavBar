package com.example.mobfinalmenuplusnavbar;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class Account{
    public final static String TABLE_NAME = "ACCOUNTS";
    public final static String ID_COLUMN = "_id";
    public final static String NAME_COLUMN = "NAME";
    public final static String AMOUNT_COLUMN = "AMOUNT";
    public final static String CURRENCY_COLUMN = "CURRENCY";
    public final static String ICON_COLUMN = "ICON";
    private final static String CREATE_TABLE_SCRIPT = "CREATE TABLE " + TABLE_NAME + " ("
            + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NAME_COLUMN + " TEXT UNIQUE NOT NULL, "
            + AMOUNT_COLUMN + " REAL NOT NULL, "
            + CURRENCY_COLUMN + " TEXT DEFAULT \"KZT\" NOT NULL, "
            + ICON_COLUMN + " INTEGER DEFAULT " + R.drawable.account_base_icon + " NOT NULL);";

    private long id;
    private String name;
    private Double amount;
    private String currency;
    private int icon;

    static public void init_table(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_SCRIPT);
        db.execSQL("INSERT INTO "
                + TABLE_NAME + " "
                + "(" + NAME_COLUMN + ") VALUES (\"Cash\");"
        );
    }

    public Account(String name, Double amount, String currency, int icon){
        this.id = -1;
        this.name = name;
        this.amount = amount;
        this.currency = currency;
        this.icon = icon;
    }

    public Account(long id, String name, Double amount, String currency, int icon) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.currency = currency;
        this.icon = icon;
    }

    public static List<Account> filter(String whereClause, String[] whereArgs){
        Cursor cursor = DBHelper.db.query(
                TABLE_NAME,
                new String[]{ID_COLUMN, NAME_COLUMN, AMOUNT_COLUMN, CURRENCY_COLUMN},
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        List<Account> res = new ArrayList<>();
        while (cursor.moveToNext()){
            long id = cursor.getInt(0);
            String name = cursor.getString(1);
            Double amount = cursor.getDouble(2);
            String currency = cursor.getString(3);
            Account item = new Account(id, name, amount, currency, 0);
            res.add(item);
        }
        cursor.close();
        return res;
    }

//    legacy
    public static List<Account> filter(String where){
        Cursor cursor = DBHelper.db.rawQuery("SELECT "
                + ID_COLUMN + ", "
                + NAME_COLUMN + ", "
                + AMOUNT_COLUMN + ", "
                + CURRENCY_COLUMN + " "
                + "FROM " + TABLE_NAME + " "
                + where, null
        );
        List<Account> res = new ArrayList<>();
        while (cursor.moveToNext()){
            long id = cursor.getInt(0);
            String name = cursor.getString(1);
            Double amount = cursor.getDouble(2);
            String currency = cursor.getString(3);
            Account item = new Account(id, name, amount, currency, 0);
            res.add(item);
        }
        cursor.close();
        return res;
    }

    public void save(){
//        Some legacy code

//        List<String> value_names = new ArrayList<>();
//        List<String> values = new ArrayList<>();
        ContentValues values = new ContentValues();

//        value_names.add(NAME_COLUMN);
//        values.add("\"" + name + "\"");
        values.put(NAME_COLUMN, name);

//        value_names.add(AMOUNT_COLUMN);
//        values.add(amount.toString());
        values.put(AMOUNT_COLUMN, amount);

        if (currency != null){
//            value_names.add(CURRENCY_COLUMN);
//            values.add("\"" + currency + "\"");
            values.put(CURRENCY_COLUMN, currency);
        }

        if (icon != 0){
//            value_names.add(ICON_COLUMN);
//            values.add("icon");
            values.put(ICON_COLUMN, icon);
        }
//        DBHelper.save_item(TABLE_NAME, id, value_names, values);
        long res = DBHelper.save_item(TABLE_NAME, id, values);
        if (this.id < 0) {
            this.id = res;
        }
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
