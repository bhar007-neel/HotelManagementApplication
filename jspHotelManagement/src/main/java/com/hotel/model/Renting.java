package com.hotel.model;

public class Renting {
    private int rentingId;
    private int bookingId;
    private String paymentStatus;

    // Constructors
    public Renting() {
    }

    public Renting(int rentingId, int bookingId, String paymentStatus) {
        this.rentingId = rentingId;
        this.bookingId = bookingId;
        this.paymentStatus = paymentStatus;
    }

    public int getRentingId() {
        return rentingId;
    }

    public void setRentingId(int rentingId) {
        this.rentingId = rentingId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
