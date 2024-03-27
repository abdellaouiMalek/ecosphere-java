package tn.esprit.models;

import java.sql.Time;
import java.util.Date;

public class Event {
    // attribut
    private int id ;
    private String eventName , adress , location , objective , image, description;
    private Date date;
    private Time time;

    // constructor
    public Event(int id, String eventName, String adress, String location, String objective, String image, String description, Date date, Time time) {
        this.id = id;
        this.eventName = eventName;
        this.adress = adress;
        this.location = location;
        this.objective = objective;
        this.image = image;
        this.description = description;
        this.date = date;
        this.time = time;
    }
    public Event(String eventName, String adress, String location, String objective, String image, String description, Date date, Time time) {
        this.eventName = eventName;
        this.adress = adress;
        this.location = location;
        this.objective = objective;
        this.image = image;
        this.description = description;
        this.date = date;
        this.time = time;
    }
    // getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    // display
    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", adress='" + adress + '\'' +
                ", location='" + location + '\'' +
                ", objective='" + objective + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
