package tn.esprit.models;

import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDateTime;
import java.util.Date;

public class Post {
    // attributs
    private int id;
    private String title;
    private String auteur;
    private String image;
    //image;
    private String content;

    // constructor
    public Post(int id, String title, String auteur, String content,String image) {
        this.id = id;
        this.title = title;
        this.auteur = auteur;
        this.image = image;
        this.content = content;
    }

    public Post(String title, String auteur, String content, String image) {
        this.title = title;
        this.auteur = auteur;
        this.image = image;
        this.content = content;
    }

    public Post() {
    }

    // getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImage(String imagePath) {
    }

    public String getImage() {
        return image;
    }

    //display
    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", auteur='" + auteur + '\'' +
                //", image='" + image + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public ObservableValue<String> titleProperty() {
        return new SimpleStringProperty(title);
    }

    public ObservableValue<String> contentProperty() {
        return new SimpleStringProperty(content);
    }

    public ObservableValue<String> AuteurProperty() {
        return new SimpleStringProperty(auteur);
    }

    public ObservableValue<Date> DateProperty() {
        return null;
    }

}
