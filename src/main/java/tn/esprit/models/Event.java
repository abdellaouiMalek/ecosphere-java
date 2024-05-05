package tn.esprit.models;

import java.sql.Time;
import java.util.Date;

public class Event {
    // attribut
    private int id ;
    private String eventName , address , location , objective , image, description;
    private Date date;
    private Time time;
    private Category category;
    private int userId;


    // constructor
    public Event(int id, String eventName, String address, String location, String objective, String image, String description, Category category, Date date, Time time) {
        this.id = id;
        this.eventName = eventName;
        this.address = address;
        this.location = location;
        this.objective = objective;
        this.image = image;
        this.description = description;
        this.date = date;
        this.time = time;
        this.category = category;
        this.userId = userId;

    }
    public Event(String eventName, String address, String location, String objective, String image, String description, Category category, Date date, Time time) {
        this.eventName = eventName;
        this.address = address;
        this.location = location;
        this.objective = objective;
        this.image = image;
        this.description = description;
        this.date = date;
        this.time = time;
        this.category = category;
        this.userId = userId;


    }

    public Event() {

    }

    public Event(int eventId, String eventName, String address, Date date, Time time, String location, String objective, String description, Category category) {
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Date getDate() { return date; }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    // display
    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", eventName='" + eventName + '\'' +
                ", address='" + address + '\'' +
                ", location='" + location + '\'' +
                ", objective='" + objective + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", userId=" + userId +
                '}';
    }
}
