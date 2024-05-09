package tn.esprit.models;

import java.sql.Time;
import java.util.Date;

public class Carpooling {
    // attribut
    private int id , userID ,seat;
    private String departure , destination;
    private Date departureDate , arrivalDate ;
    private Time time ;
    private double price ;

    // constructor
    public Carpooling(int userID, String departure, String destination, Date departureDate, Date arrivalDate, Time time, double price , int seat) {
        this.userID = userID;
        this.departure = departure;
        this.destination = destination;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.time = time;
        this.price = price;
        this.seat = seat;
    }
    public Carpooling(int userID, String departure, String destination, Date departureDate, Date arrivalDate, Time time, double price ) {
        this.userID = userID;
        this.departure = departure;
        this.destination = destination;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.time = time;
        this.price = price;
    }

    public Carpooling(int userID, String departure, String destination, Date departureDate, Time time, double price ,int seat) {
        this.userID = userID;
        this.seat = seat;
        this.departure = departure;
        this.destination = destination;
        this.departureDate = departureDate;
        this.time = time;
        this.price = price;
    }

    public Carpooling(int id, int userID, String departure, String destination, Date departureDate, Date arrivalDate, Time time, double price, int seat) {
        this.id = id;
        this.userID = userID;
        this.departure = departure;
        this.destination = destination;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.time = time;
        this.price = price;
        this.seat = seat;
    }

    public Carpooling(int id) {
        this.id = id;
    }

    public Carpooling() {
    }

    public Carpooling(String departure, String destination, Date departureDate, Date arrivalDate, Time time, double price) {
        this.departure = departure;
        this.destination = destination;
        this.departureDate = departureDate;
        this.arrivalDate = arrivalDate;
        this.time = time;
        this.price = price;
    }
    // getters & setters

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat) {
        this.seat = seat;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public Date getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // display
    @Override
    public String toString() {
        return "Carpooling{" +
                "id=" + id +
                ", departure='" + departure + '\'' +
                ", destination='" + destination + '\'' +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                ", time=" + time +
                ", price=" + price +
                '}';
    }
}
