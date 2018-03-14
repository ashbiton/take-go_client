package com.example.aviya.takeandgo2.model.entities;

/**
 * Created by aviya on 27/12/2017.
 */

public class Client {
    private int _id;
    private String name;
    private String lastName;
    private String phone;
    private String emailAddress;
    private String creditCardNumber;

    public Client(){}
    public Client(int _id, String name, String lastName, String phone, String emailAddress, String creditCardNumber) {
        this._id = _id;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.emailAddress = emailAddress;
        this.creditCardNumber = creditCardNumber;
    }

    public int get_id() { return _id; }

    public void set_id(int _id) { this._id = _id; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }
}
