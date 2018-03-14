package com.example.aviya.takeandgo2.model.entities;

import java.util.Date;

/**
 * Created by aviya on 27/12/2017.
 */

public class Reservation {
    public static int RESERVATION_NUMBER = 10;
    private int reservationNumber;
    private int clientNumber;
    private boolean reservationClosed;
    private String carNumber;
    private Date rentStart;
    private Date rentEnd;
    private double kilometersStart;
    private double kilometersEnd;
    private boolean tankFilled;
    private double addedFuel;
    private double finalPayment;

    public Reservation(){
        tankFilled = false;
        addedFuel = 0;
    }
    public Reservation(int reservationNumber, int clientNumber, boolean reservationClosed,
                       String carNumber, Date rentStart, Date rentEnd, double kilometersStart,
                       double kilometersEnd, boolean tankFilled, double addedFuel, double finalPayment) {
        this.reservationNumber = reservationNumber;
        this.clientNumber = clientNumber;
        this.reservationClosed = reservationClosed;
        this.carNumber = carNumber;
        this.rentStart = rentStart;
        this.rentEnd = rentEnd;
        this.kilometersStart = kilometersStart;
        this.kilometersEnd = kilometersEnd;
        this.tankFilled = tankFilled;
        this.addedFuel = addedFuel;
        this.finalPayment = finalPayment;
    }

    public int getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public void setClientNumber(int clientNumber) {
        this.clientNumber = clientNumber;
    }

    public boolean isReservationClosed() {
        return reservationClosed;
    }

    public void setReservationClosed(boolean reservationClosed) {
        this.reservationClosed = reservationClosed;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public Date getRentStart() {
        return rentStart;
    }

    public void setRentStart(Date rentStart) {
        this.rentStart = rentStart;
    }

    public Date getRentEnd() {
        return rentEnd;
    }

    public void setRentEnd(Date rentEnd) {
        this.rentEnd = rentEnd;
    }

    public double getKilometersStart() {
        return kilometersStart;
    }

    public void setKilometersStart(double kilometersStart) {
        this.kilometersStart = kilometersStart;
    }

    public double getKilometersEnd() {
        return kilometersEnd;
    }

    public void setKilometersEnd(double kilometersEnd) {
        this.kilometersEnd = kilometersEnd;
    }

    public boolean isTankFilled() {
        return tankFilled;
    }

    public void setTankFilled(boolean tankFilled) {
        this.tankFilled = tankFilled;
    }

    public double getAddedFuel() {
        return addedFuel;
    }

    public void setAddedFuel(double addedFuel) {
        this.addedFuel = addedFuel;
    }

    public double getFinalPayment() {
        return finalPayment;
    }

    public void setFinalPayment(double finalPayment) {
        this.finalPayment = finalPayment;
    }
}
