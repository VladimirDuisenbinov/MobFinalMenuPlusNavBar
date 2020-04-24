package com.example.mobfinalmenuplusnavbar.pojo;

import com.example.mobfinalmenuplusnavbar.R;

import java.util.ArrayList;

public class Icon {
    private String name;
    private int id;
    static ArrayList<Icon> icons;

    public Icon(String name, int id){
        this.setName(name);
        this.setId(id);
        icons = new ArrayList();
    }

    public static ArrayList<Icon> getCategoryIcons(){
        icons = new ArrayList();
        icons.add(new Icon("sport_icon", R.drawable.ic_sport));
        icons.add(new Icon("travel_icon", R.drawable.ic_travel));
        icons.add(new Icon("edu_icon", R.drawable.ic_education));
        icons.add(new Icon("food_icon", R.drawable.ic_food));
        icons.add(new Icon("entertainment_icon", R.drawable.ic_entertainment));

        return icons;
    }

    public static int getPosition(int id){
        for(Icon icon: icons){
            if (icon.getId() == id){
                return icons.indexOf(icon);
            }
        }

        return -1;
    }

    public static ArrayList<Icon> getAccountIcons(){
        icons = new ArrayList();
        icons.add(new Icon("Eurasian", R.drawable.eurasian));
        icons.add(new Icon("Halyk", R.drawable.halyk));
        icons.add(new Icon("Jysan", R.drawable.jusan));
        icons.add(new Icon("Kaspi", R.drawable.kaspi));
        icons.add(new Icon("Qazkom", R.drawable.qazkom));
        icons.add(new Icon("Sber", R.drawable.sberbank));

        return icons;
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
