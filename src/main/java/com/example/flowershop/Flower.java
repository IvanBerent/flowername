package com.example.flowershop;

public class Flower {
    private int id;
    private String name;
    private String description;
    private Float purchasePrice;
    private Float sellPrice; // 20% above purchase price  // vielleicht machen wir das weg
    private int amount;


    public Flower(String name, String description, Float purchasePrice, Float sellPrice, int amount) {
        this.id = -1;
        this.name = name;
        this.description = description;
        this.purchasePrice = purchasePrice;
        this.sellPrice = sellPrice;
        this.amount = amount;
    }

    public Flower(int id, String name, String description, Float purchasePrice, Float sellPrice, int amount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.purchasePrice = purchasePrice;
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

    public Float getPurchasePrice() {
        return purchasePrice;
    }

    public Float getSellPrice() {
        return sellPrice;
    }

    public int getAmount() {return amount;}

    public void setId(int id){
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String toString(){
        return id + " " + name + " " + description + " " + purchasePrice + " " + sellPrice;
    }
}
