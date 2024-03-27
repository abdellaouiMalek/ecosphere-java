package tn.esprit.models;

public class EventRating {
    // attribut
    private int id, rating;
    // constructor
    public EventRating(int rating) {
        this.rating = rating;
    }
    public EventRating(int id, int rating) {
        this.id = id;
        this.rating = rating;
    }
    // getters & setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    // display
    @Override
    public String toString() {
        return "EventRating{" +
                "id=" + id +
                ", rating=" + rating +
                '}';
    }
}
