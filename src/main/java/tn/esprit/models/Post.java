package tn.esprit.models;

import java.util.Date;

public class Post {
    // attributs
    private int id;
    private String title, auteur , image, content;
    private Date createdat ;
    // constructor
    public Post(int id, String title, String auteur, String image, String content, Date createdat) {
        this.id = id;
        this.title = title;
        this.auteur = auteur;
        this.image = image;
        this.content = content;
        this.createdat = createdat;
    }
    public Post(String title, String auteur, String image, String content, Date createdat) {
        this.title = title;
        this.auteur = auteur;
        this.image = image;
        this.content = content;
        this.createdat = createdat;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreatedat() {
        return createdat;
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
                ", image='" + image + '\'' +
                ", content='" + content + '\'' +
                ", createdat=" + createdat +
                '}';
    }
}
