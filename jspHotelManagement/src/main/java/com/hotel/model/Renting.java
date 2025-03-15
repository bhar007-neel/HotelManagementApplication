package com.hotel.model;

import java.util.SplittableRandom;

public class Renting {
    private int rentingId;
    private String paymentStatus;

    // Constructors
    public Renting(){
    }
    public Renting( int rentingId, String paymentStatus){
        this.rentingId =rentingId;
        this.paymentStatus =paymentStatus;

    }

    public int getRentingId(){
        return rentingId;
    }

    public void setRentingId(int rentingId) {
        this.rentingId = rentingId;
    }

    public String getPaymentStatus(){
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
