package tn.esprit.models;

public class Object {
    private int id , age ;
    private String type , picture , description , name ;
    private float price ;

    // constructor
    public Object(int id, int age, String type, String picture, String description, String name, float price) {
        this.id = id;
        this.age = age;
        this.type = type;
        this.picture = picture;
        this.description = description;
        this.name = name;
        this.price = price;
    }

    public Object(int age, String type, String picture, String description, String name, float price) {
        this.age = age;
        this.type = type;
        this.picture = picture;
        this.description = description;
        this.name = name;
        this.price = price;
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
}
