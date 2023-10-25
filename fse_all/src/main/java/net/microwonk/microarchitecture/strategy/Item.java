package net.microwonk.microarchitecture.strategy;

public class Item {

    private final String upcCode;
    private final double price;

    public Item(String upc, double price){
        this.upcCode = upc;
        this.price = price;
    }

    public String getUpcCode() {
        return upcCode;
    }

    public double getPrice() {
        return price;
    }

}