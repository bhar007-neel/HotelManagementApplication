package com.hotel.model;

public class RoomCapacity {
    private String hotelAddress;
    private int totalCapacity;

    public RoomCapacity(String hotelAddress, int totalCapacity) {
        this.hotelAddress = hotelAddress;
        this.totalCapacity = totalCapacity;
    }

    public String getHotelAddress() { return hotelAddress; }
    public int getTotalCapacity() { return totalCapacity; }
}

