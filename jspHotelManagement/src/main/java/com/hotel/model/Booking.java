package com.hotel.model;
import java.sql .Date;


public class Booking {
    private String customerName;
    private String idType;
    private String idNumber;
    public int bookingId;
    public int customerId;
    public int roomID;
    public Date startDate;
    public Date endDate;
    public String status;// "Confirmed, Pending , Cancelled "

    //Constructors
    public Booking(){}
    public Booking(int bookingId, int customerId, int roomID, Date startDate, Date endDate, String status){
        this.bookingId = bookingId;
        this.customerId = customerId;
        this.roomID = roomID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }
    public int getBookingId(){
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getCustomerId(){
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRoomId(){
        return roomID;
    }

    public void setRoomId(int roomID) {
        this.roomID = roomID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getIdType() { return idType; }
    public void setIdType(String idType) { this.idType = idType; }

    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }
}
