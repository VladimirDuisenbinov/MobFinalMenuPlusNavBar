package com.example.mobfinalmenuplusnavbar.db;

public class DBValidateDataException extends Exception {
    public DBValidateDataException(String message){
        super(message);
    }

    public static DBValidateDataException cannotBeEmpty(String column_name){
        return new DBValidateDataException(column_name + " cannot be empty");
    }

    public static DBValidateDataException alreadyExists(String column_name){
        return new DBValidateDataException(column_name + " already exists");
    }
}
