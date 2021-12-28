package Eteration.BankApp.entity.dto;

public class Amount {
    double amount;

    public Amount(double amount) {
        this.amount = amount;
    }

    public Amount() {
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
