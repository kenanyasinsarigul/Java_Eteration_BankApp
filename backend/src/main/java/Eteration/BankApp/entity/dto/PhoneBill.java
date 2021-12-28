package Eteration.BankApp.entity.dto;

public class PhoneBill {
    private String operator;
    private String phoneNumber;
    private double bill;

    public PhoneBill(String operator, String phoneNumber, double bill) {
        this.operator = operator;
        this.phoneNumber = phoneNumber;
        this.bill = bill;
    }

    public PhoneBill() {

    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }
}
