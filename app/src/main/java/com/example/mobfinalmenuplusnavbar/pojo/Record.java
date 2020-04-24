package com.example.mobfinalmenuplusnavbar.pojo;

import java.time.LocalDateTime;

public class Record {

    private String title;
    private String description;
    private int amount;
    private boolean mandatory;
    private LocalDateTime dateTime;

    public Record(String title, String description, int amount, boolean mandatory, LocalDateTime dateTime) {
        this.title = title;
        this.description = description;
        this.amount = amount;
        this.mandatory = mandatory;
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isMandatory() {
        return mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
