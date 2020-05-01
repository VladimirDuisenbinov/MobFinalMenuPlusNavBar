package com.example.mobfinalmenuplusnavbar.db;

import com.example.mobfinalmenuplusnavbar.R;

import java.util.ArrayList;
import java.util.List;

public class Icon {
    private String name;
    private int id;
    static ArrayList<Icon> categoryIcons;
    static ArrayList<Icon> accountIcons;

    public Icon(String name, int id){
        this.setName(name);
        this.setId(id);
    }

    public static ArrayList<Icon> getCategoryIcons(){
        categoryIcons = new ArrayList();
        categoryIcons.add(new Icon("record_base_icon", R.drawable.record_base_icon));
        categoryIcons.add(new Icon("debt_icon", R.drawable.category_debt_icon));
        categoryIcons.add(new Icon("fix_icon", R.drawable.category_fix_icon));
        categoryIcons.add(new Icon("transaction_icon", R.drawable.category_transaction_icon));
        categoryIcons.add(new Icon("sport_icon", R.drawable.ic_sport));
        categoryIcons.add(new Icon("travel_icon", R.drawable.ic_travel));
        categoryIcons.add(new Icon("edu_icon", R.drawable.ic_education));
        categoryIcons.add(new Icon("food_icon", R.drawable.ic_food));
        categoryIcons.add(new Icon("entertainment_icon", R.drawable.ic_entertainment));

        return categoryIcons;
    }

    public static int getCategoryPosition(int id){
        for(Icon icon: categoryIcons){
            if (icon.getId() == id){
                return categoryIcons.indexOf(icon);
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

    public static ArrayList<Icon> convertToAccountIcons(List<Account> accounts){
        ArrayList<Icon> accountIcons = new ArrayList<>();

        for (Account account: accounts){
            accountIcons.add(new Icon(account.getName(), account.getIcon()));
        }

        return accountIcons;
    }

    public static ArrayList<Icon> convertToCategoryIcons(List<Category> categories){
        ArrayList<Icon> categoryIcons = new ArrayList<>();

        for (Category category: categories){
            categoryIcons.add(new Icon(category.getName(), category.getIcon()));
        }

        return categoryIcons;
    }

    public static ArrayList<Icon> getAccountIcons(){
        accountIcons = new ArrayList();
        accountIcons.add(new Icon("Base", R.drawable.account_base_icon));
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
