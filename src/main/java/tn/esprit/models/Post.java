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
    //image;
    private String content;
    public Date createdat;

    // constructor
    public Post(int id, String title, String auteur, String content, Date createdat) {
        this.id = id;
        this.title = title;
        this.auteur = auteur;
        //this.image = image;
        this.content = content;
        this.createdat = createdat;
    }

    public Post(String title, String auteur, String content, Date createdat) {
        this.title = title;
        this.auteur = auteur;
        //this.image = image;
        this.content = content;
        this.createdat = createdat;
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

    //public String getImage() {
        //return image;
    //}

   // public void setImage(String image) {
      //  this.image = image;
    //}

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public java.sql.Date getCreatedat() {
        return (java.sql.Date) createdat;
    }

    public void setCreatedat(Date createdat) {
        this.createdat = createdat;
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
                ", createdat=" + createdat +
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


    public void setCreatedat(LocalDateTime now) {
    }
}
