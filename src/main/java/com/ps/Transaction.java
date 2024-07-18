package com.ps;


public class Transaction {
    private int    id;
    private String date;
    private String description;
    private String vendor;
    private float  amount;

    public Transaction(int id, String date, String description, String vendor, float amount) {
        this.id = id;
        this.date = date;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public Transaction(String date, String description, String vendor, float amount) {
        this.id = 0;
        this.date = date;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {

        if(amount == Math.abs(amount)) {
            return String.format("~ %-12s      %-30s %-20s $%-10.2f", date, description, vendor, amount);
        } else if(amount != Math.abs(amount)) {
            return String.format("~ %-12s      %-30s %-20s -$%-10.2f", date, description, vendor, Math.abs(amount));
        }
        return null;
    }
}