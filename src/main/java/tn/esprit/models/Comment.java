package tn.esprit.models;

import java.time.LocalDateTime;
import java.util.Date;

public class Comment {
    // attributs
    private int id;
    private String contenu ;
    public Date publicationDate ;


    public Comment() {
    }
    // constructor
    public Comment(int id, String contenu,Date publicationDate) {
        this.id = id;
        this.contenu = contenu;
        this.publicationDate = publicationDate;
    }

    public Comment( String contenu,Date publicationDate) {
        this.contenu = contenu;
        this.publicationDate = publicationDate;
    }
    public Comment(String contenu) {
        this.contenu = contenu;
    }
    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    // display
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", contenu='" + contenu + '\'' +
                '}';
    }

    public void setPublicationDate(LocalDateTime now) {
        return;
    }
}
