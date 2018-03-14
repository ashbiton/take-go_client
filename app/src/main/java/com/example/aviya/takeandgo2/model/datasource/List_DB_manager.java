package com.example.aviya.takeandgo2.model.datasource;

import android.content.ContentValues;

import com.example.aviya.takeandgo2.model.backend.CompanyConsts;
import com.example.aviya.takeandgo2.model.backend.PHPTools;
import com.example.aviya.takeandgo2.model.entities.Branch;
import com.example.aviya.takeandgo2.model.entities.Car;
import com.example.aviya.takeandgo2.model.entities.CarModel;
import com.example.aviya.takeandgo2.model.entities.Client;
import com.example.aviya.takeandgo2.model.entities.Reservation;
import com.example.aviya.takeandgo2.model.entities.UserClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by aviya on 27/12/2017.
 */

public class List_DB_manager {

    private final String WEB_URL = "http://aviyashu.vlab.jct.ac.il/Company/";

    static List<Car> carList;
    static List<Branch> branchList;
    static List<Client> clientList;
    static List<Reservation> reservationList;
    static List<UserClient> userClientList;
    static List<CarModel> carModelList;
/*
    static {
        carList = new ArrayList<>();
        branchList = new ArrayList<>();
        clientList = new ArrayList<>();
        reservationList = new ArrayList<>();
        userClientList = new ArrayList<>();
        carModelList = new ArrayList<>();

        carList.add(new Car(1234,"123rt",350,"123456789", Color.BLUE));
        carList.add(new Car(2235,"156nj",1500,"38965789", Color.MAGENTA));
        carList.add(new Car(1234,"14yy56",700,"328620789", Color.CYAN));
        carList.add(new Car(546,"345bfg",40,"77689054",Color.LTGRAY));
        carList.add(new Car(9856,"d720km",700,"44356798",Color.RED));
        carList.add(new Car(9856,"14yy56",480,"101246778",Color.BLUE));

        branchList.add(new Branch(33,"Kedumim","Hagit",40,1234));
        branchList.add(new Branch(15,"Jerusalem","Bracha",200,2235));
        branchList.add(new Branch(46,"Netanya","Shomron",120,546));
        branchList.add(new Branch(76,"Petah Tikva","Zar",150,9856));

        carModelList.add(new CarModel("123rt","Honda","Slash",2300, GEAR.automatic,5,4,30,50,false,300,false,1200,3200,false,-1));
        carModelList.add(new CarModel("14yy56","Toyota","Corola",1500, GEAR.manual,9,4,50,30,true,250,false,1200,4200,true,2340));
        carModelList.add(new CarModel("156nj","Siat","Ibiza",2000, GEAR.automatic,5,2,60,60,true,350,true,3000,1500,true,8852));
        carModelList.add(new CarModel("345bfg","Mercedes","Bens",3000, GEAR.automatic,4,4,60,70,false,300,true,1000,5200,true,5546));
        carModelList.add(new CarModel("d720km","Suzuki","splash",2500, GEAR.manual,5,4,40,30,true,160,false,600,3000,false,-1));

        clientList.add(new Client(313416067,"aviya","biton","0502293998","aviyasholy2@gmail.com","123456789"));
        clientList.add(new Client(554367898,"yona","biton","0506235639","biton@yahoo.co.il","987654321"));

        userClientList.add(new UserClient(313416067,"1","a"));
        userClientList.add(new UserClient(554367898,"q1w2e3","yona"));
    }*/
    //region Lists functions
    //region help functions

    /**
     * change the format of a word to all lower case except the first
     * letter that is upper case
     * @param str to change format of
     * @return a new string wit the desired format
     */
    public String toProperFormat(String str){
        if (str == null || str.length() == 0)
            return str;
        String[] words = str.split(" ");
        StringBuilder builder = new StringBuilder();
        for (String word:words) {
            String firstLetter = String.valueOf(word.charAt(0));
            firstLetter = firstLetter.toUpperCase();
            String _word = word.toLowerCase();
            _word = _word.substring(1);
            builder.append(firstLetter+_word);
            if(words.length != 1)
                builder.append(" ");
        }
        return builder.toString();
    }

    //region find functions

    /**
     * look for a specific car in the database , given a unique identifier
     * @param carNumber unique identifier for car
     * @return the car that matches the argument or null
     */
    public Car findCar(String carNumber){
        for (Car car:allCars())
            if(car.getCarNumber().equals(carNumber))
                return car;
        return null;
    }

    /**
     * look for a specific branch in the database , given a unique identifier
     * @param branchNumber unique identifier for branch
     * @return the branch that matches the argument or null
     */
    public Branch findBranch(int branchNumber){
        for (Branch branch:allBranches())
            if(branch.getBranchNumber() == branchNumber)
                return branch;
        return null;
    }

    /**
     * look for a specific client in the database , given a unique identifier
     * @param id unique identifier for client
     * @return the client that matches the argument or null
     */
    private Client findClient(int id){
        for (Client client:allClients())
            if(client.get_id() == id)
                return client;
        return null;
    }

    /**
     * look for a specific client in the database , given a unique identifier
     * @param id unique identifier for client
     * @return the client that matches the argument or null
     */
    public UserClient findUser (int id){
        for (UserClient userClient:allUserClients())
            if(userClient.get_id() == id)
                return userClient;
        return null;
    }

    /**
     * look for a specific reservation in the database , given a unique identifier
     * @param reservationNumber unique identifier for reservation
     * @return the reservation that matches the argument or null
     */
    public Reservation findReservation(int reservationNumber){
        for (Reservation reservation : allReservations())
            if(reservation.getReservationNumber() == reservationNumber)
                return reservation;
        return null;
    }

    /**
     * look for a specific car model in the database , given a unique identifier
     * @param modelCode unique identifier for car model
     * @return the car model that matches the argument or null
     */
    public CarModel findCarModel(String modelCode){
        for(CarModel model :carModelList)
            if(model.getModelCode().equals(modelCode))
                return model;
        return null;
    }
    //endregion

    /**
     * get the open reservation for a specific car
     * @param carNumber a unique identifier to help find the car
     * @return if reservation exists, return it, otherwise return null
     */
    private Reservation openReservationForCar(String carNumber){
        for (Reservation reservation:allReservations())
            if(reservation.getCarNumber().equals(carNumber) && !reservation.isReservationClosed())
                return reservation;
        return null;
    }

    public Reservation openReservationForClient(int id){
        for (Reservation reservation:allReservations())
            if(reservation.getClientNumber() == id && !reservation.isReservationClosed())
                return reservation;
        return null;
    }

    /**
     * find if a specific car belongs to a specific branch , given branch and car
     * @param branchNumber branch number we look in
     * @param car we want to know if belong to the above branch
     * @return true if the car ome branch is the given branch, else false
     */
    private boolean carInBranch(int branchNumber , Car car){
        if(car.getHomeBranch() == branchNumber)
            return true;
        return false;
    }

    /**
     * calculate the final payment for reservation , according to the usage features
     * @param res the reservation we wish to get te final payment for
     * @return the payment
     */
    private double calculatePayment(Reservation res) {
        double finalPayment = 0 ;
        long daysOfRent = daysBetween(res.getRentStart(),res.getRentEnd());
        double hoursOfRent = hoursOfRent(res,daysOfRent);
        finalPayment = (hoursOfRent + daysOfRent * 24.0) * 15 ; //15 dollars for hour rent
        return finalPayment;
    }

    /**
     * this function purpose is to add to calculate the hours of rent in the end and start date
     * the function is a elp function fo the final payment
     * @param res is a reservation with the wanted dates
     * @return the hours from rent start till end of that day + the hours from morning of rent end till the minute rent ended
     */
    private double hoursOfRent(Reservation res,long days) {
        Calendar cal = Calendar.getInstance();
        double hours;
        if(days > 0) {
            //calculate hours of day of end rent
            cal.setTime(res.getRentEnd());
            hours = cal.get(Calendar.HOUR_OF_DAY);
            hours += cal.get(Calendar.MINUTE) / 60.0;
            //calculate hours of day of start rent
            cal.setTime(res.getRentStart());
            hours += (24 - cal.get(Calendar.HOUR_OF_DAY));
            hours += ((60 - cal.get(Calendar.MINUTE)) / 60.0);
        }
        else{ //its the same day
            Calendar cal1 = Calendar.getInstance();
            cal.setTime(res.getRentEnd());
            cal1.setTime(res.getRentStart());
            hours = cal.get(Calendar.HOUR_OF_DAY) - cal1.get(Calendar.HOUR_OF_DAY);
            hours += cal.get(Calendar.MINUTE) / 60.0;
            hours -= cal1.get(Calendar.MINUTE) / 60.0;
            if(hours < 0)
                hours = 0;
        }
        return hours;
    }

    /**
     * calculate the days between two dates, assuming startDate < = endDate
     * the function adds one day to the start date , keeping increasing counter by the day
     * until we get to te end date
     * @param startDate the earlier date
     * @param endDate the later date
     * @return the difference in days between the two dates
     */
    private long daysBetween(Date startDate, Date endDate) {
        Calendar sDate = getDatePart(startDate);
        Calendar eDate = getDatePart(endDate);

        long daysBetween = 0;
        while (sDate.before(eDate)) {
            sDate.add(Calendar.DAY_OF_MONTH, 1); // add another day
            daysBetween++;                       // singles another day difference
        }
        return daysBetween;
    }

    /**
     * help function to the daysBetween function
     * the function zeroes all features in calender not related directly to the date itself (a.k.a day month year)
     * the point is to get the right result in the before function in daysBetween , because if the function returns true
     * if the dates have the same dates but different times of the day, therefore to get the right results
     * we need to zeroes the time portion of the date
     * @param date the date to change
     * @return the same date with the time features zeroed
     */
    private Calendar getDatePart(Date date) {
        Calendar cal = Calendar.getInstance();       // get calendar instance
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);            // set hour to midnight
        cal.set(Calendar.MINUTE, 0);                 // set minute in hour
        cal.set(Calendar.SECOND, 0);                 // set second in minute
        cal.set(Calendar.MILLISECOND, 0);            // set millisecond in second
        return cal;
    }

    /**
     * finds if there is ten seconds difference or less between two given dates
     * the function checks if the dates have the same date and then checks the time difference
     * @param now the current time and date
     * @param date is the date we want to check if happens ten seconds or less before now
     * @return true if there is ten or less seconds between now and date, otherwise return false
     */
    private boolean tenOrLessSecondsDiff(Date now , Date date){
        if (date == null)
            return false;
        Calendar calNow = getDatePart(now);
        if(calNow.equals(getDatePart(date))){       // check if the date as the same day, month and year
            calNow.setTime(now);                    // "setting back" the time properties for now
            calNow.add(Calendar.SECOND,-10);        // find the time 10 seconds ago
            if(date.after(calNow.getTime()) ||      // if date is in the last 10 seconds
                    date.equals(calNow.getTime()))  // or date is exactly 10 seconds ago
                return true;
        }
        return false;
    }
//endregion

    //region get all functions
    public List<Branch> allBranches(){
        return branchList;
    }
    public List<Car> allCars(){
        return carList;
    }
    public  List<Client> allClients(){
        return clientList;
    }
    public List<Reservation> allReservations(){
        return reservationList;
    }
    public List<UserClient> allUserClients(){return userClientList;}
    //public List<CarModel> allCarModels(){return carModelList;}
//endregion

    //region client functions
    /**
     * checks if a client exists in te data base
     * @param id the client unique identifier
     * @return true if the client exists, false otherwise
     */
    public boolean existClient(int id){
        if(findClient(id) == null)
            return false;
        return true;
    }

    /**
     * add client to the data base if not already exists
     * @param client the client to add
     * @return the client unique identifier if adding successful
     * @throws Exception if the client already exists in te database or adding didn't work
     */
    public int addClient(Client client) throws Exception {
        if(existClient(client.get_id()))
            throw new Exception("the client already exists in the system");
//        try{
//            clientList.add(client);
//            return client.get_id();
//        }
//        catch (Exception e){
//            throw e;
//        }
        ContentValues cv = CompanyConsts.ClientToContentValues(client);
        try
        {
            PHPTools.POST(WEB_URL+"/addClient.php",cv);
            clientList.add(client);
            return client.get_id();
        }
        catch (Exception e){
            throw e;
        }
    }
    //endregion

    //region available cars functions
    /**
     * all available-for-renting cars in the data base
     * @return list of available cars
     */
    public List<Car> availableCars(){
        List<Car> cars = new ArrayList<>();
        for (Car car:allCars()){
            Reservation res = openReservationForCar(car.getCarNumber());
            if(res == null)// if there is no reservation for the car
                cars.add(car);
        }
        return cars;
    }

    /**
     * finds all the available cars in a specific branch
     * @param branchNumber unique identifier of the branch we want
     * @return a list of available cars
     */
    public List<Car> availableCarsForBranch(int branchNumber){
        List<Car> cars = new ArrayList<>();
        for (Car car:availableCars())
            if (carInBranch(branchNumber,car))
                cars.add(car);
        return cars;
    }
    //endregion

    /**
     * update the kilometers of a car, was used for lists not used in php
     * @param carNumber car unique identifier to find the cat to update
     * @param kilometers value to add to kilometers (can be negative)
     * @return the new updated number of kilometers fo the car
     * @throws Exception in case car not found
     */
    public double updateCarKilometers(String carNumber , double kilometers) throws Exception {
        Car car = findCar(carNumber);
        if(car == null)
            throw new Exception("this car does not exists in the system");
        for (Car c:allCars())
            if (c.getCarNumber().equals(carNumber)) {//finds the car
                c.setKilometers(c.getKilometers() + kilometers); // update the kilometers
            }
        return kilometers;
    }

    //region reservation functions

    /**
     * finds all reservation that are not closed
     * @return a list of reservation with desired feature
     */
    public List<Reservation> openReservations(){
        List<Reservation> reservations = new ArrayList<>();
        for (Reservation res:allReservations())
            if(!res.isReservationClosed())
                reservations.add(res);
        return reservations;
    }

    /**
     * add reservation to the database, the function validate the reservation
     * and initialize specific properties, like assigning reservation number, kilometers and the date
     * @param reservation the reservation to add to the database
     * @return reservation number
     * @throws Exception if car does not exist in te database or it already rented
     */
    public int addReservation(Reservation reservation) throws Exception {
        /*
        //initializing fields
        if(openReservationForClient(reservation.getClientNumber()) != null)
            throw new Exception("you already have an open reservation");
        reservation.setReservationNumber(Reservation.RESERVATION_NUMBER++);
        reservation.setRentStart(Calendar.getInstance().getTime());
        reservation.setReservationClosed(false);
        reservationList.add(reservation);
        return reservation.getReservationNumber();*/

        if(openReservationForClient(reservation.getClientNumber()) != null)
            throw new Exception("you already have an open reservation");
        reservation.setRentStart(Calendar.getInstance().getTime());
        reservation.setRentEnd(Calendar.getInstance().getTime()); // assigned so it can be converted to content values
        reservation.setReservationClosed(false);
        int reservationNumber = 0;
        ContentValues cv = CompanyConsts.ReservationToContentValues(reservation);
        try{
            String str = PHPTools.POST(WEB_URL+"/addReservation.php",cv);
            reservationNumber = Integer.parseInt(str);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        reservation.setReservationNumber(reservationNumber);
        reservationList.add(reservation);
        return reservationNumber;
    }

    /**
     * close an exisiting reservation
     * @param reservationNumber of the reservation to close
     * @param kilometers added to the car in the reservation who needs to be updated
     * @param addedFuel if the client filled fuel, it contains how much it costs
     * @return the final payment fo the reservation
     * @throws Exception if closing the reservation or updating the car kilometers is unsuccessful
     */
    public double closeReservation (int reservationNumber , double kilometers , double addedFuel) throws Exception {
        /*int index = findReservationIndex(reservationNumber);
        if(index == -1)
            throw new Exception("reservation not found in the system");
        reservationList.get(index).setReservationClosed(true);
        reservationList.get(index).setKilometersEnd(reservationList.get(index).getKilometersStart() + kilometers);
        reservationList.get(index).setRentEnd(Calendar.getInstance().getTime());
        updateCarKilometers(reservationList.get(index).getCarNumber(),kilometers);
        if(addedFuel == 0)
            reservationList.get(index).setTankFilled(false);
        else
            reservationList.get(index).setTankFilled(true);
        reservationList.get(index).setAddedFuel(addedFuel);
        double payment = calculatePayment(reservationList.get(index));
        reservationList.get(index).setFinalPayment(payment);
        return payment;*/
        Reservation reservation = findReservation(reservationNumber);
        reservation.setReservationClosed(true);
        reservation.setRentEnd(Calendar.getInstance().getTime());
        reservation.setKilometersEnd(reservation.getKilometersStart() + kilometers);
        if(addedFuel == 0)
            reservation.setTankFilled(false);
        else
            reservation.setTankFilled(true);
        reservation.setAddedFuel(addedFuel);
        double finalPayment = calculatePayment(reservation);
        reservation.setFinalPayment(finalPayment);
        ContentValues cv = CompanyConsts.ReservationToContentValues(reservation);

        //update car kilometers
        Car car = findCar(reservation.getCarNumber());
        car.setKilometers(reservation.getKilometersEnd());
        ContentValues cv1 = CompanyConsts.CarToContentValues(car);

        try{
            PHPTools.POST(WEB_URL+"closeReservation.php",cv);
            getAllReservations();
            PHPTools.POST(WEB_URL+"updateCar.php",cv1);
            getAllCars();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return finalPayment;
    }

    /**
     * find the index of a reservation in the list in order to update it
     * @param reservationNumber to search for
     * @return the index of the reservation if found, else return -1
     */
    private int findReservationIndex(int reservationNumber) {
        for(int i=0; i< allReservations().size();i++)
            if(allReservations().get(i).getReservationNumber()  == reservationNumber)
                return i;

        return -1;
    }

    public boolean reservClosedTenSecnds(){
        Date now = Calendar.getInstance().getTime();
        for (Reservation reservation:allReservations()){
            if (reservation.isReservationClosed() && tenOrLessSecondsDiff(now,reservation.getRentEnd()))
                return true;
        }

        return false;
    }
    //endregion

    //region client validate input functions

    /**
     * checks the id the client provided and check if valid (according to minister instructions)
     * @param id of the client
     * @return true if id is valid , else false
     */
    public boolean checkId(String id)
    {
        if(id.length() != 9)
            return false;
        int sum=0; // sums the numbers to check if the validation digit is correct
        for(int i=0; i<9;i++){
            if(i%2==0)
                sum += Integer.parseInt(String.valueOf(id.charAt(i)));
            else
            {
                int number = Integer.parseInt(String.valueOf(id.charAt(i))) * 2;
                if(number>9)
                    sum += 1  + (number - 10);
                else
                    sum += number;
            }
        }

        if(sum%10 ==0)
            return true;
        return false;
    }

    /**
     * this function validates cellphone number
     * check if cellphone number is 10 characters long and starts with "05"
     * @param phone the cellphone to check
     * @return true or false
     */
    public boolean checkPhone(String phone){
        if(phone.contains("-"))
            phone = phone.replaceAll("-","");
        if (!phone.startsWith("05"))
            return false;
        if(phone.length() != 10)
            return false;
        return true;
    }

    /**
     * validates email address
     * @param email to validate
     * @return true if email address is validated, else false
     */
    public boolean checkEmailAddress(String email){
        if(!email.contains("@"))           // email must contain @
            return false;
        if(!email.contains("."))           // there is no domain
            return false;
        String[] parts = email.split("@"); // split to user name and domain parts
        if(parts.length != 2)              // cannot have more than one @
            return false;
        if(!parts[1].contains("."))        // domain must contain at least one dot
            return false;
        return true;
    }
    //endregion

    //region userClient functions

    /**
     * check if user name exists in the system
     * @param userName
     * @return
     */
    public boolean existUserName(String userName){
        for(UserClient user:allUserClients())
            if(user.getUserName().equals(userName))
                return true;
        return false;
    }

    /**
     * checks if a user client exists in the system
     * @param id unique identifier for userClient
     * @return true if does exist, else false
     */
    public boolean existUserClient(int id){
        if (findUser(id) != null)
            return true;
        return false;
    }

    /**
     * find a client by user name
     * @param userName a unique identifier for client
     * @return the client if found, else null
     */
    public Client findClientByUser(String userName){
        for (UserClient user:allUserClients())
            if(user.getUserName().equals(userName))
                return findClient(user.get_id());
        return null;
    }

    /**
     * add new user client to the list
     * @param userClient to add to the list
     */
    public void addUserClient(UserClient userClient){
//        try{
//            userClientList.add(userClient);
//        }
//        catch (Exception e){
//            throw e;
//        }
        ContentValues cv = CompanyConsts.UserClientToContentValues(userClient);
        try{
            PHPTools.POST(WEB_URL+"/addUserClient.php",cv);
            userClientList.add(userClient);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *find if there is a matc between client , user name and password
     * @param userName to match to password
     * @param password to match to user name
     * @return client id if matches, else -1
     */
    public int userClientMatch(String userName,String password){
        Client client = findClientByUser(userName);
        if(client == null)
            return -1;
        UserClient userClient = findUser(client.get_id());
        if(userClient == null)
            return -1;
        if (userClient.getPassword().equals(password))
            return client.get_id();
        return -1;
    }
    //endregion

    //region SQL functions
    public void initializeLists(){
        carList = new ArrayList<>();
        branchList = new ArrayList<>();
        clientList = new ArrayList<>();
        reservationList = new ArrayList<>();
        userClientList = new ArrayList<>();
        carModelList = new ArrayList<>();
        getAllUserClients();
        getAllClients();
        getAllBranches();
        getAllCars();
        getAllReservations();
        getAllModels();
    }

    private List<UserClient> getAllUserClients() {
        try{
            String result = PHPTools.GET(WEB_URL+"userClients.php");
            JSONArray array = new JSONObject(result).getJSONArray("userClients");
            for(int i=0; i<array.length();i++){
                JSONObject jsonObject = array.getJSONObject(i);
                ContentValues contentValues = PHPTools.JsonToContentValues(jsonObject);
                UserClient user = CompanyConsts.ContentValuesToUserClient(contentValues);

                userClientList.add(user);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return userClientList;
    }

    private List<CarModel> getAllModels() {
        try{
            String result = PHPTools.GET(WEB_URL+"models.php");
            JSONArray array = new JSONObject(result).getJSONArray("models");
            for(int i=0; i<array.length();i++){
                JSONObject jsonObject = array.getJSONObject(i);
                ContentValues contentValues = PHPTools.JsonToContentValues(jsonObject);
                CarModel model = CompanyConsts.ContentValuesToCarModel(contentValues);

                carModelList.add(model);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return carModelList;
    }

    private List<Reservation> getAllReservations() {
        try{
            String result = PHPTools.GET(WEB_URL+"/reservations.php");
            JSONArray array = new JSONObject(result).getJSONArray("reservations");
            for(int i=0; i<array.length();i++){
                JSONObject jsonObject = array.getJSONObject(i);
                ContentValues contentValues = PHPTools.JsonToContentValues(jsonObject);
                Reservation reservation = CompanyConsts.ContentValuesToReservation(contentValues);

                reservationList.add(reservation);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return reservationList;
    }

    private List<Car> getAllCars() {
        try{
            String result = PHPTools.GET(WEB_URL+"cars.php");
            JSONArray array = new JSONObject(result).getJSONArray("cars");
            for(int i=0; i<array.length();i++){
                JSONObject jsonObject = array.getJSONObject(i);
                ContentValues contentValues = PHPTools.JsonToContentValues(jsonObject);
                Car car = CompanyConsts.ContentValuesToCar(contentValues);

                carList.add(car);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return carList;
    }

    private List<Branch> getAllBranches() {
        try{
        String result = PHPTools.GET(WEB_URL+"branches.php");
        JSONArray array = new JSONObject(result).getJSONArray("branches");
        for(int i=0; i<array.length();i++){
            JSONObject jsonObject = array.getJSONObject(i);
            ContentValues contentValues = PHPTools.JsonToContentValues(jsonObject);
            Branch branch = CompanyConsts.ContentValuesToBranch(contentValues);

            branchList.add(branch);
        }
    }
    catch (Exception e){
        e.printStackTrace();
    }
        return branchList;
    }

    private List<Client> getAllClients() {
        try{
            String result = PHPTools.GET(WEB_URL+"clients.php");
            JSONArray array = new JSONObject(result).getJSONArray("clients");
            for(int i=0; i<array.length();i++){
                JSONObject jsonObject = array.getJSONObject(i);
                ContentValues contentValues = PHPTools.JsonToContentValues(jsonObject);
                Client client = CompanyConsts.ContentValuesToClient(contentValues);
                
                clientList.add(client);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return clientList;
    }

    //endregion
}
