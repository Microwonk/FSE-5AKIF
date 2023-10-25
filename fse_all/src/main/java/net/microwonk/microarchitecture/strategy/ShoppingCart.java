package net.microwonk.microarchitecture.strategy;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private final List<Item> items;

    public ShoppingCart(){
        this.items = new ArrayList<Item>();
    }

    public void addItem(Item item){
        this.items.add(item);
    }

    public void removeItem(Item item){
        this.items.remove(item);
    }

    public double calculateTotal(){
        return items.stream().mapToDouble(Item::getPrice).sum();
    }

    public void pay(PaymentStrategy paymentMethod){
        var amount = calculateTotal();
        paymentMethod.pay(amount);
    }
}