package net.microwonk.microarchitecture.strategy;

public class PaypalStrategy implements PaymentStrategy {

    private final String emailId;
    private final String password;

    public PaypalStrategy(String email, String pwd){
        this.emailId = email;
        this.password = pwd;
    }

    @Override
    public void pay(double amount) {
        System.out.println(amount + " paid using Paypal.");
    }

}