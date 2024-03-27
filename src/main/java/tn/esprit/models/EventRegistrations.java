package tn.esprit.models;

import java.sql.Time;
import java.util.Date;

public class EventRegistrations {
    // attribut
    private int id ;
    private Date registrationDate ;
    private Time registrationTime;
    private String status;

    // constructor
    public EventRegistrations(int id, Date registrationDate, Time registrationTime, String status) {
        this.id = id;
        this.registrationDate = registrationDate;
        this.registrationTime = registrationTime;
        this.status = status;
    }

    public EventRegistrations(Date registrationDate, Time registrationTime, String status) {
        this.registrationDate = registrationDate;
        this.registrationTime = registrationTime;
        this.status = status;
    }

    // getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Time getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Time registrationTime) {
        this.registrationTime = registrationTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // display
    @Override
    public String toString() {
        return "EventRegistrations{" +
                "id=" + id +
                ", registrationDate=" + registrationDate +
                ", registrationTime=" + registrationTime +
                ", status='" + status + '\'' +
                '}';
    }
}
