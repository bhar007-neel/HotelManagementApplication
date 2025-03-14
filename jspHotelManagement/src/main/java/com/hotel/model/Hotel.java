package com.hotel.model;

public class Hotel {

    /* "HotelID" SERIAL PRIMARY KEY,
  "ChainID" INT NOT NULL REFERENCES "HotelChain"("ChainID") ON DELETE CASCADE,
  "Stars" INT CHECK ("Stars" BETWEEN 1 AND 5),
  "NumberOfRoom" INT CHECK ("NumberOfRoom" >= 1),
  "Address" TEXT NOT NULL,
  "Email" VARCHAR(255) UNIQUE NOT NULL,
  "PhoneNumber"
*/

private int HotelID;
private int ChainID;
private int Stars;
private int NumberOfRoom;
private String Address;
private String Email;
private String PhoneNumber;

public Hotel(int HotelID, int ChainID, int Stars, int NumberOfRoom, String Address, String Email, String PhoneNumber) {
    this.HotelID = HotelID;
    this.ChainID = ChainID;
    this.Stars = Stars;
    this.NumberOfRoom = NumberOfRoom;
    this.Address = Address;
    this.Email = Email;
    this.PhoneNumber = PhoneNumber;
}

public void setHotelID(int HotelID) {
    this.HotelID = HotelID;
}

public int getHotelID() {
    return this.HotelID;
}

public void setChainID(int ChainID) {
    this.ChainID = ChainID;
}

public int getChainID() {
    return this.ChainID; 
}

public void setStars(int Stars) {
    this.Stars = Stars;
}

public int getStars() {
    return this.Stars;
}

public void setNumberOfRoom(int NumberOfRoom) {
    this.NumberOfRoom = NumberOfRoom;
}

public int getNumberOfRoom() {
    return this.NumberOfRoom;
}

public void setAddress(String Address) {
    this.Address = Address;
}    
public String getAddress() {
    return this.Address;
}

public void setEmail(String Email) {
    this.Email = Email;
}    
public String getEmail() {
    return this.Email;
}

public void setPhoneNumber(String PhoneNumber) {
    this.PhoneNumber = PhoneNumber;
}    
public String getPhoneNumber() {
    return this.PhoneNumber;
}

}
