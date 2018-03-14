package com.example.aviya.takeandgo2.model.entities;

/**
 * Created by aviya on 31/12/2017.
 */

public class CarModel {
    private String modelCode;
    private String carCompany;
    private String modelName;
    private double engineCapacity;
    private GEAR gearBox;
    private int seats;
    //EXTRA FIELDS
    private int doors;
    private double kilometersPerTank;
    private double acceleration;
    private boolean convertible;
    private int maxSpeed;
    private boolean buildInTurbo;
    private double trunkVolume;
    private double horsePower;
    private boolean codeRequired;
    private int codeNumber;//if codeRequired is true than enter the code number
    //pay attention not to reveal this field to the customer

    public CarModel(){}
    public CarModel(String modelCode, String carCompany, String modelName, double engineCapacity,
                    GEAR gearBox, int seats, int doors, double kilometersPerTank, double acceleration,
                    boolean convertible, int maxSpeed, boolean buildInTurbo, double trunkVolume,
                    double horsePower, boolean codeRequired, int codeNumber) {
        this.modelCode = modelCode;
        this.carCompany = carCompany;
        this.modelName = modelName;
        this.engineCapacity = engineCapacity;
        this.gearBox = gearBox;
        this.seats = seats;
        this.doors = doors;
        this.kilometersPerTank = kilometersPerTank;
        this.acceleration = acceleration;
        this.convertible = convertible;
        this.maxSpeed = maxSpeed;
        this.buildInTurbo = buildInTurbo;
        this.trunkVolume = trunkVolume;
        this.horsePower = horsePower;
        this.codeRequired = codeRequired;
        this.codeNumber = codeNumber;
    }

    public String getModelCode() {
        return modelCode;
    }

    public void setModelCode(String modelCode) {
        this.modelCode = modelCode;
    }

    public String getCarCompany() {
        return carCompany;
    }

    public void setCarCompany(String carCompany) {
        this.carCompany = carCompany;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public double getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(double engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public GEAR getGearBox() {
        return gearBox;
    }

    public void setGearBox(GEAR gearBox) {
        this.gearBox = gearBox;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public double getKilometersPerTank() {
        return kilometersPerTank;
    }

    public void setKilometersPerTank(double kilometersPerTank) {
        this.kilometersPerTank = kilometersPerTank;
    }

    public double getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(double acceleration) {
        this.acceleration = acceleration;
    }

    public boolean isConvertible() {
        return convertible;
    }

    public void setConvertible(boolean convertible) {
        this.convertible = convertible;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public boolean isBuildInTurbo() {
        return buildInTurbo;
    }

    public void setBuildInTurbo(boolean buildInTurbo) {
        this.buildInTurbo = buildInTurbo;
    }

    public double getTrunkVolume() {
        return trunkVolume;
    }

    public void setTrunkVolume(double trunkVolume) {
        this.trunkVolume = trunkVolume;
    }

    public double getHorsePower() {
        return horsePower;
    }

    public void setHorsePower(double horsePower) {
        this.horsePower = horsePower;
    }

    public boolean isCodeRequired() {
        return codeRequired;
    }

    public void setCodeRequired(boolean codeRequired) {
        this.codeRequired = codeRequired;
    }

    public int getCodeNumber() {
        return codeNumber;
    }

    public void setCodeNumber(int codeNumber) {
        this.codeNumber = codeNumber;
    }
}
