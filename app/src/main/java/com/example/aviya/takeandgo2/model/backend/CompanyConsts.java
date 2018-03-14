package com.example.aviya.takeandgo2.model.backend;

import android.content.ContentValues;

import com.example.aviya.takeandgo2.model.entities.Branch;
import com.example.aviya.takeandgo2.model.entities.Car;
import com.example.aviya.takeandgo2.model.entities.CarModel;
import com.example.aviya.takeandgo2.model.entities.Client;
import com.example.aviya.takeandgo2.model.entities.GEAR;
import com.example.aviya.takeandgo2.model.entities.Reservation;
import com.example.aviya.takeandgo2.model.entities.UserClient;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by aviya on 15/01/2018.
 */

public class CompanyConsts {

    public static class BranchConst{
        //public static final String ADDRESS = "address
        public static final String CITY = "city";
        public static final String STREET = "street";
        public static final String HOUSE_NUMBER = "houseNumber";
        public static final String BRANCH_NUMBER = "branchNumber";
        public static final String PARKING_SPACES = "parkingSpaces";
    }

    public static class CarModelConst{
        public static final String MODEL_CODE = "modelCode";
        public static final String COMPANY = "carCompany";
        public static final String MODEL_NAME = "modelName";
        public static final String CAPACITY = "engineCapacity";
        public static final String SEATS = "seats";
        public static final String DOORS = "doors";
        public static final String KM_TANK = "kilometersPerTank";
        public static final String ACCELERATION = "acceleration";
        public static final String CONVERTIBLE = "convertible";
        public static final String MAX_SPEED = "maxSpeed";
        public static final String TURBO = "buildInTurbo";
        public static final String TRUNK_VOLUME = "trunkVolume";
        public static final String HORSE_POWER = "horsePower";
        public static final String CODE_REQUIRED = "codeRequired";
        public static final String CODE_NUMBER = "codeNumber";
        public static final String GEAR="gearBox";
    }

    public static class CarConst{
        public static final String HOME_BRANCH = "homeBranch";
        public static final String KILOMETERS = "kilometers";
        public static final String CAR_NUMBER = "carNumber";
        public static final String CAR_COLOR = "carColor";
        public static final String MODEL_CODE = "modelCode";
    }

    public static class ClientConst{
        public static final String ID = "_id";
        public static final String NAME = "name";
        public static final String LAST_NAME = "lastName";
        public static final String PHONE = "phone";
        public static final String EMAIL = "emailAddress";
        public static final String CREDIT_CARD = "creditCardNumber";
    }

    public static class ReservationConst{
        public static final String RESERV_NUMBER = "reservationNumber";
        public static final String CLIENT_NUMBER = "clientNumber";
        public static final String CAR_NUMBER = "carNumber";
        public static final String RESERV_CLOSED = "reservationClosed";
        public static final String RENT_START = "rentStart";
        public static final String RENT_END = "rentEnd";
        public static final String KM_START = "kilometersStart";
        public static final String KM_END = "kilometersEnd";
        public static final String TANK_FILLED = "tankFilled";
        public static final String ADDED_FUEL = "addedFuel";
        public static final String PAYMENT = "finalPayment";
    }

    public static class UserClientConst{
        public static final String USER_NAME = "userName";
        public static final String PASSWORD = "password";
        public static final String ID="_id";
    }



    public static ContentValues BranchToContentValues(Branch branch)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BranchConst.CITY, branch.getCity());
        contentValues.put(BranchConst.STREET,branch.getStreet());
        contentValues.put(BranchConst.HOUSE_NUMBER,branch.getHouseNumber());
        contentValues.put(BranchConst.BRANCH_NUMBER,branch.getBranchNumber());
        contentValues.put(BranchConst.PARKING_SPACES,branch.getParkingSpaces());
        return contentValues;
    }

    public static ContentValues ClientToContentValues(Client client)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ClientConst.ID,client.get_id());
        contentValues.put(ClientConst.NAME,client.getName());
        contentValues.put(ClientConst.LAST_NAME,client.getLastName());
        contentValues.put(ClientConst.EMAIL,client.getEmailAddress());
        contentValues.put(ClientConst.PHONE,client.getPhone());
        contentValues.put(ClientConst.CREDIT_CARD,client.getCreditCardNumber());

        return contentValues;
    }

    public static ContentValues CarModelToContentValues(CarModel carModel)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CarModelConst.ACCELERATION,carModel.getAcceleration());
        contentValues.put(CarModelConst.CAPACITY,carModel.getEngineCapacity());
        contentValues.put(CarModelConst.CODE_NUMBER,carModel.getCodeNumber());
        contentValues.put(CarModelConst.CODE_REQUIRED,carModel.isCodeRequired());
        contentValues.put(CarModelConst.COMPANY,carModel.getCarCompany());
        contentValues.put(CarModelConst.CONVERTIBLE,carModel.isConvertible());
        contentValues.put(CarModelConst.DOORS,carModel.getDoors());
        contentValues.put(CarModelConst.GEAR,carModel.getGearBox().toString());
        contentValues.put(CarModelConst.HORSE_POWER,carModel.getHorsePower());
        contentValues.put(CarModelConst.KM_TANK,carModel.getKilometersPerTank());
        contentValues.put(CarModelConst.MAX_SPEED,carModel.getMaxSpeed());
        contentValues.put(CarModelConst.MODEL_CODE,carModel.getModelCode());
        contentValues.put(CarModelConst.MODEL_NAME,carModel.getModelName());
        contentValues.put(CarModelConst.SEATS,carModel.getSeats());
        contentValues.put(CarModelConst.TRUNK_VOLUME,carModel.getTrunkVolume());
        contentValues.put(CarModelConst.TURBO,carModel.isBuildInTurbo());

        return contentValues;
    }

    public static ContentValues CarToContentValues(Car car)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CarConst.CAR_NUMBER,car.getCarNumber());
        contentValues.put(CarConst.CAR_COLOR,car.getCarColor());
        contentValues.put(CarConst.MODEL_CODE,car.getModelCode());
        contentValues.put(CarConst.KILOMETERS,car.getKilometers());
        contentValues.put(CarConst.HOME_BRANCH,car.getHomeBranch());
        return contentValues;
    }

    public static ContentValues ReservationToContentValues(Reservation reservation)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ReservationConst.ADDED_FUEL,reservation.getAddedFuel());
        contentValues.put(ReservationConst.CAR_NUMBER,reservation.getCarNumber());
        contentValues.put(ReservationConst.CLIENT_NUMBER,reservation.getClientNumber());
        contentValues.put(ReservationConst.KM_START,reservation.getKilometersStart());
        contentValues.put(ReservationConst.KM_END,reservation.getKilometersEnd());
        contentValues.put(ReservationConst.PAYMENT,reservation.getFinalPayment());
        contentValues.put(ReservationConst.RESERV_CLOSED,reservation.isReservationClosed());
        contentValues.put(ReservationConst.RESERV_NUMBER,reservation.getReservationNumber());
        contentValues.put(ReservationConst.TANK_FILLED,reservation.isTankFilled());

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // like MySQL Format
        String dateString = dateFormat.format(reservation.getRentEnd());
        contentValues.put(ReservationConst.RENT_END,dateString);

        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateString = dateFormat1.format(reservation.getRentStart());
        contentValues.put(ReservationConst.RENT_START,dateString);

        return contentValues;
    }

    public static ContentValues UserClientToContentValues(UserClient user)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserClientConst.USER_NAME,user.getUserName());
        contentValues.put(UserClientConst.PASSWORD,user.getPassword());
        contentValues.put(UserClientConst.ID,user.get_id());
        return contentValues;
    }


    public static Branch ContentValuesToBranch(ContentValues contentValues)
    {
        Branch branch =new Branch();
        //branch.setAddress(ContentValuesToAddress(contentValues));
        branch.setStreet(contentValues.getAsString(BranchConst.STREET));
        branch.setCity(contentValues.getAsString(BranchConst.CITY));
        branch.setHouseNumber(contentValues.getAsInteger(BranchConst.HOUSE_NUMBER));
        branch.setParkingSpaces(contentValues.getAsInteger(BranchConst.PARKING_SPACES));
        branch.setBranchNumber(contentValues.getAsInteger(BranchConst.BRANCH_NUMBER));
        return branch;
    }

    public static Car ContentValuesToCar (ContentValues contentValues)
    {
        Car car = new Car();
        car.setCarColor(contentValues.getAsInteger(CarConst.CAR_COLOR));
        car.setCarNumber(contentValues.getAsString(CarConst.CAR_NUMBER));
        car.setKilometers(contentValues.getAsDouble(CarConst.KILOMETERS));
        car.setHomeBranch(contentValues.getAsInteger(CarConst.HOME_BRANCH));
        car.setModelCode(contentValues.getAsString(CarConst.MODEL_CODE));
        return car;
    }

    public static CarModel ContentValuesToCarModel (ContentValues contentValues)
    {
        CarModel carModel = new CarModel();
        carModel.setAcceleration(contentValues.getAsDouble(CarModelConst.ACCELERATION));
        carModel.setBuildInTurbo(contentValues.getAsBoolean(CarModelConst.TURBO));
        carModel.setCarCompany(contentValues.getAsString(CarModelConst.COMPANY));
        carModel.setCodeNumber(contentValues.getAsInteger(CarModelConst.CODE_NUMBER));
        carModel.setCodeRequired(contentValues.getAsBoolean(CarModelConst.CODE_REQUIRED));
        carModel.setConvertible(contentValues.getAsBoolean(CarModelConst.CONVERTIBLE));
        carModel.setDoors(contentValues.getAsInteger(CarModelConst.DOORS));
        carModel.setEngineCapacity(contentValues.getAsDouble(CarModelConst.CAPACITY));
        carModel.setGearBox(GEAR.valueOf(contentValues.getAsString(CarModelConst.GEAR)));
        carModel.setHorsePower(contentValues.getAsDouble(CarModelConst.HORSE_POWER));
        carModel.setKilometersPerTank(contentValues.getAsDouble(CarModelConst.KM_TANK));
        carModel.setMaxSpeed(contentValues.getAsInteger(CarModelConst.MAX_SPEED));
        carModel.setModelCode(contentValues.getAsString(CarModelConst.MODEL_CODE));
        carModel.setModelName(contentValues.getAsString(CarModelConst.MODEL_NAME));
        carModel.setSeats(contentValues.getAsInteger(CarModelConst.SEATS));
        carModel.setTrunkVolume(contentValues.getAsDouble(CarModelConst.TRUNK_VOLUME));
        return carModel;
    }

    public static Client ContentValuesToClient (ContentValues contentValues)
    {
        Client client = new Client();
        client.set_id(contentValues.getAsInteger(ClientConst.ID));
        client.setCreditCardNumber(contentValues.getAsString(ClientConst.CREDIT_CARD));
        client.setEmailAddress(contentValues.getAsString(ClientConst.EMAIL));
        client.setLastName(contentValues.getAsString(ClientConst.LAST_NAME));
        client.setName(contentValues.getAsString(ClientConst.NAME));
        return client;
    }

    public static Reservation ContentValuesToReservation (ContentValues contentValues)
    {
        Reservation reservation = new Reservation();
        reservation.setCarNumber(contentValues.getAsString(ReservationConst.CAR_NUMBER));
        reservation.setAddedFuel(contentValues.getAsDouble(ReservationConst.ADDED_FUEL));
        reservation.setClientNumber(contentValues.getAsInteger(ReservationConst.CLIENT_NUMBER));
        reservation.setFinalPayment(contentValues.getAsDouble(ReservationConst.PAYMENT));
        reservation.setKilometersEnd(contentValues.getAsDouble(ReservationConst.KM_END));
        reservation.setKilometersStart(contentValues.getAsDouble(ReservationConst.KM_START));
        reservation.setReservationClosed(contentValues.getAsBoolean(ReservationConst.RESERV_CLOSED));
        reservation.setReservationNumber(contentValues.getAsInteger(ReservationConst.RESERV_NUMBER));
        reservation.setTankFilled(contentValues.getAsBoolean(ReservationConst.TANK_FILLED));

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = contentValues.getAsString(ReservationConst.RENT_START);
        try {
            reservation.setRentStart(dateFormat.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String dateString1 = contentValues.getAsString(ReservationConst.RENT_END);
        try {
            reservation.setRentEnd(dateFormat.parse(dateString1));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return reservation;
    }

    public static UserClient ContentValuesToUserClient (ContentValues contentValues)
    {
        UserClient user = new UserClient();
        user.setPassword(contentValues.getAsString(UserClientConst.PASSWORD));
        user.setUserName(contentValues.getAsString(UserClientConst.USER_NAME));
        user.set_id(contentValues.getAsInteger(UserClientConst.ID));
        return user;
    }
}
