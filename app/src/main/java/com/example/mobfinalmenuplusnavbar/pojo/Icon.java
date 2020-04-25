package com.example.mobfinalmenuplusnavbar.pojo;

import android.widget.ArrayAdapter;

import com.example.mobfinalmenuplusnavbar.R;

import java.util.ArrayList;
import java.util.HashMap;

public class Icon {
    private String name;
    private int id;
    static ArrayList<Icon> categeoryIcons;
    static ArrayList<Icon> accountIcons;

    public Icon(String name, int id){
        this.setName(name);
        this.setId(id);
    }

    public static ArrayList<Icon> getCategoryIcons(){
        categeoryIcons = new ArrayList();
        categeoryIcons.add(new Icon("sport_icon", R.drawable.ic_sport));
        categeoryIcons.add(new Icon("travel_icon", R.drawable.ic_travel));
        categeoryIcons.add(new Icon("edu_icon", R.drawable.ic_education));
        categeoryIcons.add(new Icon("food_icon", R.drawable.ic_food));
        categeoryIcons.add(new Icon("entertainment_icon", R.drawable.ic_entertainment));

        return categeoryIcons;
    }

    public static int getCategoryPosition(int id){
        for(Icon icon: categeoryIcons){
            if (icon.getId() == id){
                return categeoryIcons.indexOf(icon);
            }
        }

        return -1;
    }

    public static int getAccountPosition(int id){
        for(Icon icon: accountIcons){
            if (icon.getId() == id){
                return accountIcons.indexOf(icon);
            }
        }

        return -1;
    }

    public static ArrayList<Icon> getAccountIcons(){
        accountIcons = new ArrayList();
        accountIcons.add(new Icon("Eurasian", R.drawable.eurasian));
        accountIcons.add(new Icon("Halyk", R.drawable.halyk));
        accountIcons.add(new Icon("Jysan", R.drawable.jusan));
        accountIcons.add(new Icon("Kaspi", R.drawable.kaspi));
        accountIcons.add(new Icon("Qazkom", R.drawable.qazkom));
        accountIcons.add(new Icon("Sber", R.drawable.sberbank));

        return accountIcons;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
