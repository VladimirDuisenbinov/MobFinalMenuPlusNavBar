package com.example.mobfinalmenuplusnavbar.pojo;

public class Account {

    private String name;
    private double amount;
    private String currency;
    private int icon;

    public Account(String name, double amount, String currency, int icon) {
        this.setName(name);
        this.setAmount(amount);
        this.setCurrency(currency);
        this.setIcon(icon);
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
}
