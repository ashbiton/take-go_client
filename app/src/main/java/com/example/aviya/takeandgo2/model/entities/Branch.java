package com.example.aviya.takeandgo2.model.entities;

/**
 * Created by aviya on 27/12/2017.
 */

public class Branch {
    public int houseNumber;
    public String city;
    public String street;
    private int parkingSpaces;
    private int branchNumber;

    public Branch(){}

    public Branch(int houseNumber, String city, String street, int parkingSpaces, int branchNumber) {
        this.houseNumber = houseNumber;
        this.city = city;
        this.street = street;
        this.parkingSpaces = parkingSpaces;
        this.branchNumber = branchNumber;
    }

    public int getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(int parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }

    public int getBranchNumber() {
        return branchNumber;
    }

    public void setBranchNumber(int branchNumber) {
        this.branchNumber = branchNumber;
    }

    public int getHouseNumber() { return houseNumber; }

    public void setHouseNumber(int houseNumber) { this.houseNumber = houseNumber; }

    public String getCity() { return city; }

    public void setCity(String city) { this.city = city; }

    public String getStreet() { return street; }

    public void setStreet(String street) { this.street = street; }
}
