package tn.esprit.models;

import java.util.List;
import java.util.Objects;

public class Object {
    private int id , age ;
    private String type , picture , description , name ;
    private float price ;

    private List<History> histories;
    public Object() {

    }



    public Object(int age, String type, String picture, String description, String name, float price) {
        this.age = age;
        this.type = type;
        this.picture = picture;
        this.description = description;
        this.name = name;
        this.price = price;
        this.histories = histories;
    }

    // getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }

    // display
    @Override
    public String toString() {
        return "Object{" +
                "id=" + id +
                ", age=" + age +
                ", type='" + type + '\'' +
                ", picture='" + picture + '\'' +
                ", description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Object that = (Object) o;
        return id == that.id;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
