package net.microwonk.microarchitecture.strategy;

@FunctionalInterface
public interface PaymentStrategy {
    public void pay(double amount);
}
