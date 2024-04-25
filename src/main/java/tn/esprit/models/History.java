package tn.esprit.models;

import java.util.Date;

public class History {
    private int id ;
    private String initialCondition , name ;
    private Date date;

    // constructor
    public History(int id, String initialCondition, String name, Date date) {
        this.id = id;
        this.initialCondition = initialCondition;
        this.name = name;
        this.date = date;
    }
    public History( String initialCondition, String name, Date date) {

        this.initialCondition = initialCondition;
        this.name = name;
        this.date = date;
    }

    public void add(History history) {
    }

    public History() {

    }


    // getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInitialCondition() {
        return initialCondition;
    }

    public void setInitialCondition(String initialCondition) {
        this.initialCondition = initialCondition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public java.sql.Date getDate() {
        return (java.sql.Date) date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    // display
    @Override
    public String toString() {
        return "History{" +
                "id=" + id +
                ", initialCondition='" + initialCondition + '\'' +
                ", name='" + name + '\'' +
                ", date=" + date +
                '}';
    }

}
