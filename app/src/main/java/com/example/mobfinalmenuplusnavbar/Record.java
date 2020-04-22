package com.example.mobfinalmenuplusnavbar;

import android.database.sqlite.SQLiteDatabase;

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
    private static final String CREATE_TABLE_SCRIPT = "CREATE TABLE " + TABLE_NAME + " ("
            + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TITLE_COLUMN + " TEXT NOT NULL, "
            + AMOUNT_COLUMN + " REAL NOT NULL, "
            + DESCRIPTION_COLUMN + " TEXT DEFAULT \"\" NOT NULL, "
            + CATEGORY_ID_COLUMN + " INTEGER NOT NULL, "
            + ACCOUNT_ID_COLUMN + " INTEGER NOT NULL, "
            + MANDATORY_COLUMN + " INTEGER DEFAULT 1 NOT NULL, "
            + SUBJECT_COLUMN + " TEXT DEFAULT \"\" NOT NULL, "
            + DATE_COLUMN + " TEXT NOT NULL, "
            + "FOREIGN KEY(" + CATEGORY_ID_COLUMN + ") REFERENCES "
            + Category.TABLE_NAME + "(" + Category.ID_COLUMN + "), "
            + "FOREIGN KEY(" + ACCOUNT_ID_COLUMN + ") REFERENCES "
            + Account.TABLE_NAME + "(" + Account.ID_COLUMN + "));";

    private long id;
    private String title;
    private Double amount;
    private String description;
    private Integer category_id;
    private Integer account_id;
    private Integer mandatory;
    private String subject;
    private String date;

    public static void init_table(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_SCRIPT);
    }

    public Record(String title,
                  Double amount,
                  String description,
                  Integer category_id,
                  Integer account_id,
                  Integer mandatory,
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

    public Record(long id,
                  String title,
                  Double amount,
                  String description,
                  Integer category_id,
                  Integer account_id,
                  Integer mandatory,
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

    public static String getCreateTableScript() {
        return CREATE_TABLE_SCRIPT;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public Integer getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Integer account_id) {
        this.account_id = account_id;
    }

    public Integer getMandatory() {
        return mandatory;
    }

    public void setMandatory(Integer mandatory) {
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
}
