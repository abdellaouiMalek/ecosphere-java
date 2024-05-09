package tn.esprit.models;

public class Comment {
    // attributs
    private int id,idpost;
    private String contenu ;

    public Comment() {
    }
    // constructor
    public Comment(int id, String contenu,int idpost) {
        this.id = id;
        this.contenu = contenu;
    }
    public Comment( String contenu, int idpost) {
        this.contenu = contenu;
        this.idpost = idpost;
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
    public int getIdpost() {
        return idpost;
    }
    public void setIdpost(int idpost) {
        this.idpost = idpost;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", contenu='" + contenu + '\'' +
                '}';
    }
}
