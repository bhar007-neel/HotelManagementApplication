package com.hotel.model;

public class AvailableRooms {
    private String hotelAddress;
    private int availableRoomCount;

    public AvailableRooms(String hotelAddress, int availableRoomCount) {
        this.hotelAddress = hotelAddress;
        this.availableRoomCount = availableRoomCount;
    }

    public String getHotelAddress() { return hotelAddress; }
    public int getAvailableRoomCount() { return availableRoomCount; }
}

