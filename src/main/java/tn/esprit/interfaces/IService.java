package tn.esprit.interfaces;

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {

    void add(T t) throws SQLException;

    void update(T t);

    void delete(T t) throws SQLException;

    List<T> getAll();

    List<T> search(String searchTerm) throws SQLException;

    void addEventRating(int eventId, int rating) throws SQLException;

    double calculateAverageRating(int eventId) throws SQLException;


}
