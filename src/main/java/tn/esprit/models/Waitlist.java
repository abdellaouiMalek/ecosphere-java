package tn.esprit.models;

public class Waitlist {
    // attribut
    private int id ;
    private int userID;
    private int carpoolingID;
    // constructors
    public Waitlist() {
    }

    public Waitlist(int id) {
        this.id = id;
    }

    public Waitlist(int id, int userID, int carpoolingID) {
        this.id = id;
        this.userID = userID;
        this.carpoolingID = carpoolingID;
    }

    public Waitlist(int userID, int carpoolingID) {
        this.userID = userID;
        this.carpoolingID = carpoolingID;
    }

    // getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    // display
    @Override
    public String toString() {
        return "Waitlist{" +
                "id=" + id +
                ", userID=" + userID +
                ", carpoolingID=" + carpoolingID +
                '}';
    }
}
