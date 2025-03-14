package com.hotel.model;

public class Room {
    private int RoomID;
    private int HotelID;
    private double Price;
    private String RoomType;
    private String Damage;
    private String View;
    private boolean Extendable;


    public Room(int RoomID, int HotelID, double Price, String RoomType, String Damage, String View, boolean Extendable) {
        this.RoomID = RoomID;
        this.HotelID = HotelID;
        this.Price = Price;
        this.RoomType = RoomType;
        this.Damage = Damage;
        this.View = View;
        this.Extendable = Extendable;
    }

    public void setRoomID(int RoomID) {
        this.RoomID = RoomID;
    }

    public int getRoomID() {
        return this.RoomID;
    }

    public void setHotelID(int HotelID) {
        this.HotelID = HotelID;
    }

    public int getHotelID() {
        return this.HotelID;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public double getPrice() {
        return this.Price;
    }

    public void setRoomType(String RoomType) {
        this.RoomType = RoomType;
    }

    public String getRoomType() {
        return this.RoomType;
    }

    public void setDamage(String Damage) {
        this.Damage = Damage;
    }

    public String getDamage() {
        return this.Damage;
    }

    public void setView(String View) {
        this.View = View;
    }

    public String getView() {
        return this.View;
    }
    
    public void setExtendable(boolean Extendable) {
        this.Extendable = Extendable;
    }

    public boolean getExtendable() {
        return this.Extendable;
    }
}
