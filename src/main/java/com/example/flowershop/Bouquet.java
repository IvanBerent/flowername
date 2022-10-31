package com.example.flowershop;

public class Bouquet {
    private int id;
    private String name;
    private String description;
    private Float sellPrice;
    private int amount;

    public Bouquet(String name, String description, Float sellPrice, int amount) {
        this.id = -1;
        this.name = name;
        this.description = description;
        this.sellPrice = sellPrice;
        this.amount = amount;
    }

    public Bouquet(int id, String name, String description, Float sellPrice, int amount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sellPrice = sellPrice;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Float getSellPrice() {
        return sellPrice;
    }

    public int getAmount() {return amount;}

    public String toString(){
        return id + " " + name + " " + description + " " + sellPrice;
    }
}
