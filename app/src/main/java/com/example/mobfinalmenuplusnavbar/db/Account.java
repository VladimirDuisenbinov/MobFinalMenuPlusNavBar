package com.example.mobfinalmenuplusnavbar.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mobfinalmenuplusnavbar.R;

import java.util.ArrayList;
import java.util.List;

public class Account{
    public final static String TABLE_NAME = "ACCOUNTS";
    public final static String ID_COLUMN = "_id";
    public final static String NAME_COLUMN = "NAME";
    public final static String AMOUNT_COLUMN = "AMOUNT";
    public final static String CURRENCY_COLUMN = "CURRENCY";
    public final static String ICON_COLUMN = "ICON";
    public final static String CASH_NAME = "Cash";

    private final static String CURRENCY_DEFAULT = "KZT";
    private final static int ICON_DEFAULT = R.drawable.account_base_icon;

    private final static String CREATE_TABLE_SCRIPT = "CREATE TABLE " + TABLE_NAME + " ("
            + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + NAME_COLUMN + " TEXT UNIQUE NOT NULL, "
            + AMOUNT_COLUMN + " REAL DEFAULT 0 NOT NULL, "
            + CURRENCY_COLUMN + " TEXT DEFAULT \"" + CURRENCY_DEFAULT + "\" NOT NULL, "
            + ICON_COLUMN + " INTEGER DEFAULT " + ICON_DEFAULT + " NOT NULL);";

    private long id;
    private String name;
    private double amount;
    private String currency;
    private int icon;

    static public void init_table(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_SCRIPT);
        db.execSQL("INSERT INTO "
                + TABLE_NAME + " "
                + "(" + NAME_COLUMN + ") VALUES (\"" + CASH_NAME + "\");"
        );
    }

    public Account(String name, double amount, String currency, int icon){
        this.id = -1;
        this.name = name;
        this.amount = amount;
        this.currency = currency;
        this.icon = icon;
    }

    public Account(){
        this.id = -1;
        name = "";
        amount = 0.0;
        currency = CURRENCY_DEFAULT;
        icon = ICON_DEFAULT;
    }

    public Account(long id){
        this.id = id;
        name = "";
        amount = 0.0;
        currency = CURRENCY_DEFAULT;
        icon = ICON_DEFAULT;
    }

    public Account(long id, String name, double amount, String currency, int icon) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.currency = currency;
        this.icon = icon;
    }

    public static Account get(long id){
        List<Account> res = filter("_id = ?", new String[]{String.valueOf(id)});
        if (res.size() == 0) { return null; }
        return res.get(0);
    }

    public static Account get(String name){
        List<Account> res = filter("name = ?", new String[]{name});
        if (res.size() == 0) { return null; }
        return res.get(0);
    }

    public static List<Account> filter(String whereClause, String[] whereArgs){
        Cursor cursor = DBHelper.db.query(
                TABLE_NAME,
                new String[]{ID_COLUMN, NAME_COLUMN, AMOUNT_COLUMN, CURRENCY_COLUMN, ICON_COLUMN},
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
            double amount = cursor.getDouble(2);
            String currency = cursor.getString(3);
            int icon = cursor.getInt(4);
            Account item = new Account(id, name, amount, currency, icon);
            res.add(item);
        }
        cursor.close();
        return res;
    }

    public void validate() throws DBValidateDataException {
        if (id == get(CASH_NAME).id) {
            throw new DBValidateDataException("This Account cannot be changed");
        }
        if (name == null || name.equals("")){
            throw DBValidateDataException.cannotBeEmpty(NAME_COLUMN);
        }
        Account other = Account.get(name);
        if (other != null && other.id != id){
            throw DBValidateDataException.alreadyExists(NAME_COLUMN);
        }
        if (currency == null || currency.equals("")){
            currency = CURRENCY_DEFAULT;
        }
        if (icon == 0){
            icon = ICON_DEFAULT;
        }
    }

    public void delete() throws DBValidateDataException {
        if (id < 0){
            throw new DBValidateDataException("Account does not exist");
        }
        if (id == get(CASH_NAME).id) {
            throw new DBValidateDataException("This Account cannot be changed");
        }
        List<Record> recs = Record.filter(Record.ACCOUNT_ID_COLUMN + " = ?",
                new String[]{String.valueOf(id)});
        for (Record rec : recs){
            rec.setAccount_id(1);
            rec.save();
        }
        DBHelper.db.delete(
                TABLE_NAME,
                "_id = ",
                new String[]{String.valueOf(id)}
        );
    }

    public void save() throws DBValidateDataException{
        this.validate();
        addFix();

        ContentValues values = new ContentValues();

        values.put(NAME_COLUMN, name);
        values.put(AMOUNT_COLUMN, amount);
        values.put(CURRENCY_COLUMN, currency);
        values.put(ICON_COLUMN, icon);

        long res = DBHelper.save_item(TABLE_NAME, id, values);
        if (this.id < 0) {
            this.id = res;
        }
    }

    private void addFix() throws DBValidateDataException {
        if (id < 0){
            return;
        }
        double prev_amount = get(id).amount;
        if (prev_amount == amount){
            return;
        }

        Record fix = new Record(
                "Fix for " + name,
                amount - prev_amount,
                "Auto generated record",
                Category.get(Category.FIX_NAME).getId(),
                id,
                1,
                "",
                ""
        );
        fix.save();
    }

    public void addRecord(double amount){
        this.amount += amount;
        ContentValues values = new ContentValues();

        values.put(AMOUNT_COLUMN, amount);

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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", icon=" + icon +
                '}';
    }
}
