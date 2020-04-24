package com.example.mobfinalmenuplusnavbar.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class DBHelper extends SQLiteOpenHelper {
    static public String EX_DB = "QISAP";
    static public String DATE_FORMAT = "yy/MM/dd HH:mm:ss";

    static public int DBVER = 1;
    static public DBHelper singleton = null;
    static public SQLiteDatabase db = null;

    public DBHelper(Context context){
        super(context, EX_DB, null, DBVER);
        singleton = this;
        db = this.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Category.init_table(db);
        Account.init_table(db);
        Record.init_table(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static String join(String delimiter, List<String> tokens){
        if (tokens.size() == 0) return "";
        StringBuilder string_tokens = new StringBuilder(tokens.get(0));
        for (int i=1; i<tokens.size(); i++){
            string_tokens.append(delimiter).append(tokens.get(i));
        }
        return string_tokens.toString();
    }

    public static void save_item(String table_name, Integer id, List<String> value_names, List<String> values){
        if (id >= 0){
            List<String> kvpairs = new ArrayList<>();
            for (int i=0; i<value_names.size(); i++){
                kvpairs.add(value_names.get(i) + " = " + values.get(i));
            }
            db.execSQL("UPDATE " + table_name + " "
                    + "SET " + join(", ", kvpairs) + " "
                    + "WHERE _id = " + id + ";"
            );
        } else {
            db.execSQL("INSERT INTO " + table_name + " "
                    + "(" + join(", ", value_names) + ") "
                    + "values (" + join(", ", values) + ");"
            );
        }
    }

    public static long save_item(String table_name, long id, ContentValues values){
        if (id >= 0){
//            update
            db.update(table_name, values, "_id = ?" , new String[]{String.valueOf(id)});
            return -1;
        } else {
//            insert
            return db.insertOrThrow(table_name, null, values);
        }
    }

    public static String now(){
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss'Z'", Locale.US);
        Calendar calobj = Calendar.getInstance();
        return df.format(calobj.getTime());
    }
}

