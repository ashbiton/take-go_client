package com.example.aviya.takeandgo2.model.entities;

/**
 * Created by aviya on 27/12/2017.
 */

public class Car {
    private int homeBranch;
    private String modelCode;;
    private double kilometers;
    private String carNumber;
    private int carColor;

    public Car(int homeBranch, String modelCode, double kilometers, String carNumber, int carColor) {
        this.homeBranch = homeBranch;
        this.modelCode = modelCode;
        this.kilometers = kilometers;
        this.carNumber = carNumber;
        this.carColor = carColor;
    }
    public Car(){}

    public int getHomeBranch() {
        return homeBranch;
    }

    public void setHomeBranch(int homeBranch) {
        this.homeBranch = homeBranch;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public double getKilometers() {
        return kilometers;
    }

    public void setKilometers(double kilometers) {
        this.kilometers = kilometers;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public int getCarColor() {
        return carColor;
    }

    public void setCarColor(int carColor) {
        this.carColor = carColor;
    }
}
