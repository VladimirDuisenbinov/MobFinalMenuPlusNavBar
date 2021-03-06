package com.example.mobfinalmenuplusnavbar.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mobfinalmenuplusnavbar.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TransferQueue;

public class Category {
    public final static String TABLE_NAME = "CATEGORIES";
    public final static String ID_COLUMN = "_id";
    public final static String NAME_COLUMN = "NAME";
    public final static String DESCRIPTION_COLUMN = "DESCRIPTION";
    public final static String ICON_COLUMN = "ICON";
    public final static String GENERAL_NAME = "GENERAL";
    public final static String DEBT_NAME = "Debt";
    public final static String FIX_NAME = "Fix";
    public final static String TRANSACTION_NAME = "Transaction";

    public static final String DESCRIPTION_DEFAULT = "";
    private final static int ICON_DEFAULT = R.drawable.record_base_icon;

    private final static String CREATE_TABLE_SCRIPT = "CREATE TABLE " + TABLE_NAME + " ("
            + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + NAME_COLUMN + " TEXT UNIQUE NOT NULL, "
            + DESCRIPTION_COLUMN + " TEXT DEFAULT \"" + DESCRIPTION_DEFAULT + "\" NOT NULL,"
            + ICON_COLUMN + " INTEGER DEFAULT " + ICON_DEFAULT + " NOT NULL);";
    private final static List<Category> BASE_ITEMS = Arrays.asList(
            new Category(GENERAL_NAME, null, 0),
            new Category(DEBT_NAME, null, R.drawable.category_debt_icon),
            new Category(FIX_NAME, null, R.drawable.category_fix_icon),
            new Category(TRANSACTION_NAME, null, R.drawable.category_transaction_icon)
    );

    private long id;
    private String name;
    private String description;
    private int icon;

    public static void init_table(SQLiteDatabase db){
        db.execSQL(CREATE_TABLE_SCRIPT);
        for (Category category: BASE_ITEMS){
            if (category.getIcon() == 0 ){
                db.execSQL("INSERT INTO "
                        + TABLE_NAME + " "
                        + "(" + NAME_COLUMN + ") VALUES (\"" + category.getName() + "\");"
                );
            } else {
                db.execSQL("INSERT INTO "
                        + TABLE_NAME + " "
                        + "(" + NAME_COLUMN + ", " + ICON_COLUMN + ") VALUES "
                        + "(\"" + category.getName() + "\", " + category.getIcon() + ");"
                );
            }

        }
    }

    public Category(String name, String description, int icon){
        this.id = -1;
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

    public Category(){
        this.id = -1;
        this.name = "";
        this.description = DESCRIPTION_DEFAULT;
        this.icon = ICON_DEFAULT;
    }

    public Category(int id){
        this.id = id;
        this.name = "";
        this.description = DESCRIPTION_DEFAULT;
        this.icon = ICON_DEFAULT;
    }

    public Category(long id, String name, String description, int icon){
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
    }

    public static Category get(long id){
        List<Category> res = filter("_id = ?", new String[]{String.valueOf(id)});
        if (res.size() == 0) { return null; }
        return res.get(0);
    }

    public static Category get(String name){
        List<Category> res = filter("name = ?", new String[]{name});
        if (res.size() == 0) { return null; }
        return res.get(0);
    }

    public static List<Category> filter(String whereClause, String[] whereArgs){
        Cursor cursor = DBHelper.db.query(
                TABLE_NAME,
                new String[]{ID_COLUMN, NAME_COLUMN, DESCRIPTION_COLUMN, ICON_COLUMN},
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        List<Category> res = new ArrayList<>();
        while (cursor.moveToNext()){
            long id = cursor.getInt(0);
            String name = cursor.getString(1);
            String description = cursor.getString(2);
            int icon = cursor.getInt(3);
            Category item = new Category(id, name, description, icon);
            res.add(item);
        }
        cursor.close();
        return res;
    }

    public void validate() throws DBValidateDataException {
        if (id == get(DEBT_NAME).id || id == get(FIX_NAME).id ||
                id == get(TRANSACTION_NAME).id || id == get(GENERAL_NAME).id) {
            throw new DBValidateDataException("This Category cannot be changed");
        }
        if (name == null || name.equals("")){
            throw DBValidateDataException.cannotBeEmpty(NAME_COLUMN);
        }
        if (description == null){ description = DESCRIPTION_DEFAULT; }
        Category other = Category.get(name);
        if (other != null && other.id != id){
            throw DBValidateDataException.alreadyExists(NAME_COLUMN);
        }
        if (icon == 0){
            icon = ICON_DEFAULT;
        }
    }

    public void save() throws DBValidateDataException{
        this.validate();

        ContentValues values = new ContentValues();

        values.put(NAME_COLUMN, name);
        values.put(DESCRIPTION_COLUMN, description);
        values.put(ICON_COLUMN, icon);

        long res = DBHelper.save_item(TABLE_NAME, id, values);
        if (this.id < 0) {
            this.id = res;
        }
    }

    public void delete() throws DBValidateDataException {
        if (id < 0){
            throw new DBValidateDataException("Category does not exist");
        }
        if (id == get(DEBT_NAME).id || id == get(FIX_NAME).id ||
                id == get(TRANSACTION_NAME).id || id == get(GENERAL_NAME).id) {
            throw new DBValidateDataException("This Category cannot be deleted");
        }
        List<Record> recs = Record.filter(Record.CATEGORY_ID_COLUMN + " = ?",
                new String[]{String.valueOf(id)});
        for (Record rec : recs){
            rec.setCategory_id(1);
            rec.save();
        }
        DBHelper.db.delete(
                TABLE_NAME,
                "_id =  ",
                new String[]{String.valueOf(id)}
        );
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", icon=" + icon +
                '}';
    }
}
