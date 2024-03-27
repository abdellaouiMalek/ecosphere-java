package tn.esprit.models;

public class Comment {
    // attributs
    private int id;
    private String contenu ;

    // constructor
    public Comment(int id, String contenu) {
        this.id = id;
        this.contenu = contenu;
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

    // display
    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", contenu='" + contenu + '\'' +
                '}';
    }
}
