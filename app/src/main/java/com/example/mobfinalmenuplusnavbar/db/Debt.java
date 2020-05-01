package com.example.mobfinalmenuplusnavbar.db;

import java.util.Objects;

public class Debt {
    private String name;
    private Double amount;

    public Debt() {
    }

    public Debt(String name, Double amount) {
        this.name = name;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Debt{" +
                "name='" + name + '\'' +
                ", amount=" + amount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Debt debt = (Debt) o;
        return Objects.equals(name, debt.name) &&
                Objects.equals(amount, debt.amount);
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, amount);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
