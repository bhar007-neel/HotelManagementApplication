package com.hotel.model;

import java.sql.Date;

public class Payment {
    private int paymentId;
    private double amount;
    private Date date;
    private String paymentMethod; // "Credit Card", "PayPal", "Debit Card", "Cash"

    // Constructors
    public Payment() {}

    public Payment(int paymentId, double amount, Date date, String paymentMethod) {
        this.paymentId = paymentId;
        this.amount = amount;
        this.date = date;
        setPaymentMethod(paymentMethod);
    }

    // Getters and Setters
    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
