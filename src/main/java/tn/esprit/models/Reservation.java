package tn.esprit.models;

public class Reservation {
    // attribut
    private int id ;
    private int userID;
    private int carpoolingID;
    // constructor
    public Reservation(int id) {
        this.id = id;
    }

    public Reservation(int userID, int carpoolingID) {
        this.userID = userID;
        this.carpoolingID = carpoolingID;
    }

    public Reservation(int id, int userID, int carpoolingID) {
        this.id = id;
        this.userID = userID;
        this.carpoolingID = carpoolingID;
    }

    // getters & setters
public int getUserID() {
    return userID;
}

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getCarpoolingID() {
        return carpoolingID;
    }

    public void setCarpoolingID(int carpoolingID) {
        this.carpoolingID = carpoolingID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    // display

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", userID=" + userID +
                ", carpoolingID=" + carpoolingID +
                '}';
    }
}
