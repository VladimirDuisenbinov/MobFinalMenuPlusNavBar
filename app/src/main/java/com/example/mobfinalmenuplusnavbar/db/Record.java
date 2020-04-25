package com.example.mobfinalmenuplusnavbar.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Record {
    public static final String TABLE_NAME = "RECORDS";
    public static final String ID_COLUMN = "_id";
    public static final String TITLE_COLUMN = "TITLE";
    public static final String AMOUNT_COLUMN = "AMOUNT";
    public static final String DESCRIPTION_COLUMN = "DESCRIPTION";
    public static final String CATEGORY_ID_COLUMN = "CATEGORY_id";
    public static final String ACCOUNT_ID_COLUMN = "ACCOUNT_id";
    public static final String MANDATORY_COLUMN = "MANDATORY";
    public static final String SUBJECT_COLUMN = "SUBJECT";
    public static final String DATE_COLUMN = "DATE";
    public static final String DEBT_CATEGORY_NAME = Category.DEBT_NAME;

    public static final String TITLE_DEFAULT = "";
    public static final String DESCRIPTION_DEFAULT = "";
    public static final int MANDATORY_DEFAULT = 1;
    public static final String SUBJECT_DEFAULT = "";


    private static final String CREATE_TABLE_SCRIPT = "CREATE TABLE " + TABLE_NAME + " ("
            + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TITLE_COLUMN + " TEXT DEFAULT \"" + TITLE_DEFAULT + "\" NOT NULL, "
            + AMOUNT_COLUMN + " REAL DEFAULT 0 NOT NULL, "
            + DESCRIPTION_COLUMN + " TEXT DEFAULT \"" + DESCRIPTION_DEFAULT + "\" NOT NULL, "
            + CATEGORY_ID_COLUMN + " INTEGER DEFAULT 1 NOT NULL, "
            + ACCOUNT_ID_COLUMN + " INTEGER DEFAULT 1 NOT NULL, "
            + MANDATORY_COLUMN + " INTEGER DEFAULT " + MANDATORY_DEFAULT + " NOT NULL, "
            + SUBJECT_COLUMN + " TEXT DEFAULT \"" + SUBJECT_DEFAULT + "\" NOT NULL, "
            + DATE_COLUMN + " TEXT NOT NULL, "
            + "FOREIGN KEY(" + CATEGORY_ID_COLUMN + ") REFERENCES "
            + Category.TABLE_NAME + "(" + Category.ID_COLUMN + "), "
            + "FOREIGN KEY(" + ACCOUNT_ID_COLUMN + ") REFERENCES "
            + Account.TABLE_NAME + "(" + Account.ID_COLUMN + "));";

    private long id;
    private String title;
    private double amount;
    private String description;
    private long category_id;
    private long account_id;
    private int mandatory; // 1/0
    private String subject;
    private String date;

    public static void init_table(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_SCRIPT);
    }

    public Record(String title,
                  Double amount,
                  String description,
                  long category_id,
                  long account_id,
                  int mandatory,
                  String subject,
                  String date
    ) {
        this.id = -1;
        this.title = title;
        this.amount = amount;
        this.description = description;
        this.category_id = category_id;
        this.account_id = account_id;
        this.mandatory = mandatory;
        this.subject = subject;
        this.date = date;
    }

    public Record(){
        this.id = -1;
        this.title = TITLE_DEFAULT;
        this.amount = 0.0;
        this.description = DESCRIPTION_DEFAULT;
        this.category_id = -1;
        this.account_id = -1;
        this.mandatory = MANDATORY_DEFAULT;
        this.subject = SUBJECT_DEFAULT;
        this.date = DBHelper.now();
    }

    public Record(long id){
        this.id = id;
        this.title = TITLE_DEFAULT;
        this.amount = 0.0;
        this.description = DESCRIPTION_DEFAULT;
        this.category_id = -1;
        this.account_id = -1;
        this.mandatory = MANDATORY_DEFAULT;
        this.subject = SUBJECT_DEFAULT;
        this.date = DBHelper.now();
    }

    public Record(long id,
                  String title,
                  Double amount,
                  String description,
                  long category_id,
                  long account_id,
                  int mandatory,
                  String subject,
                  String date
    ) {
        this.id = id;
        this.title = title;
        this.amount = amount;
        this.description = description;
        this.category_id = category_id;
        this.account_id = account_id;
        this.mandatory = mandatory;
        this.subject = subject;
        this.date = date;
    }

    public static Record get(long id){
        List<Record> res = filter("_id = ?", new String[]{String.valueOf(id)});
        if (res.size() == 0) { return null; }
        return res.get(0);
    }

    public static List<Pair<String, Double>> getDebts(){
        Cursor cursor = DBHelper.db.query(
                TABLE_NAME,
                new String[]{
                        SUBJECT_COLUMN,
                        "SUM(" + AMOUNT_COLUMN + ")"
                },
                CATEGORY_ID_COLUMN + " = ?",
                new String[]{String.valueOf(Category.get(Category.DEBT_NAME).getId())},
                SUBJECT_COLUMN, null, DATE_COLUMN
        );
        List<Pair<String, Double>> res = new ArrayList<>();
        while (cursor.moveToNext()) {
            String subject = cursor.getString(0);
            Double amount = cursor.getDouble(1);
            res.add(new Pair<String, Double>(subject, amount));
        }
        cursor.close();
        return res;
    }

    public static List<Record> filter(String whereClause, String[] whereArgs){
        Cursor cursor = DBHelper.db.query(
                TABLE_NAME,
                new String[]{
                        ID_COLUMN,
                        TITLE_COLUMN,
                        AMOUNT_COLUMN,
                        DESCRIPTION_COLUMN,
                        CATEGORY_ID_COLUMN,
                        ACCOUNT_ID_COLUMN,
                        MANDATORY_COLUMN,
                        SUBJECT_COLUMN,
                        DATE_COLUMN
                },
                whereClause, whereArgs,
                null, null, DATE_COLUMN
        );
        List<Record> res = new ArrayList<>();
        while (cursor.moveToNext()){
            long id = cursor.getInt(0);
            String title = cursor.getString(1);
            Double amount = cursor.getDouble(2);
            String description = cursor.getString(3);
            long category_id = cursor.getLong(4);
            long account_id = cursor.getLong(5);
            int mandatory = cursor.getInt(6);
            String subject = cursor.getString(7);
            String date = cursor.getString(8);
            Record item = new Record(
                    id,
                    title,
                    amount,
                    description,
                    category_id,
                    account_id,
                    mandatory,
                    subject,
                    date
            );
            res.add(item);
        }
        cursor.close();
        return res;
    }

    public void validate() throws DBValidateDataException {
        if ( title == null || title.equals("")){
            throw new DBValidateDataException("Title cannot be empty");
        }
        if (description == null){ description = DESCRIPTION_DEFAULT; }
        if (category_id < 0) { category_id = 1; }
        if (Category.get(category_id) == null){
            throw new DBValidateDataException("Category not found");
        }
        if (Account.get(account_id) == null){
            throw new DBValidateDataException("Account not found");
        }
        if (mandatory < 0) { mandatory = 0; }
        if (mandatory > 1) { mandatory = 1; }
        if (subject == null) {
            subject = SUBJECT_DEFAULT;
        }
        if (date == null || date.equals("")) {
            date = DBHelper.now();
        }
    }

    public void save() throws DBValidateDataException{
        this.validate();
        this.change_account();
        ContentValues values = new ContentValues();
        values.put(TITLE_COLUMN, title);
        values.put(AMOUNT_COLUMN, amount);
        values.put(DESCRIPTION_COLUMN, description);
        values.put(CATEGORY_ID_COLUMN, category_id);
        values.put(ACCOUNT_ID_COLUMN, account_id);
        values.put(MANDATORY_COLUMN, mandatory);
        values.put(SUBJECT_COLUMN, subject);
        values.put(DATE_COLUMN, date);
        long res = DBHelper.save_item(TABLE_NAME, id, values);
        if (this.id < 0) {
            this.id = res;
        }
    }

    public void delete() throws DBValidateDataException{
        if (id < 0){
            throw new DBValidateDataException("Record does not exist");
        }
        amount = 0;
        change_account();
        DBHelper.db.delete(
                TABLE_NAME,
                "_id = ",
                new String[]{String.valueOf(id)}
        );
    }

    private void change_account() throws DBValidateDataException {
        double prev_amount = 0.0;
        long prev_account_id = account_id;
        if (id >= 0){
            prev_amount = get(id).amount;
            prev_account_id = get(id).account_id;
        }
        Account prev_account = Account.get(prev_account_id);
        prev_account.setAmount(prev_account.getAmount() - prev_amount);
        prev_account.save();
        Account account = Account.get(account_id);
        account.setAmount(account.getAmount() + amount);
        account.save();
    }

    public static String getCreateTableScript() {
        return CREATE_TABLE_SCRIPT;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCategory_id() {
        return category_id;
    }

    public void setCategory_id(long category_id) {
        this.category_id = category_id;
    }

    public long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public int getMandatory() {
        return mandatory;
    }

    public void setMandatory(int mandatory) {
        this.mandatory = mandatory;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Record{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", category_id=" + category_id +
                ", account_id=" + account_id +
                ", mandatory=" + mandatory +
                ", subject='" + subject + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
