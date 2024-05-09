package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Comment;
import tn.esprit.models.EventRating;
import tn.esprit.models.Waitlist;
import tn.esprit.util.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class WaitlistService implements IService<Waitlist> {
    // DB connection
    Connection cnx = DBconnection.getInstance().getCnx();

    @Override
    public void add(Waitlist waitlist) throws SQLException {
        String sql = "INSERT INTO waitlist (user_id, carpooling_id) VALUES (?, ?)";
        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setInt(1, waitlist.getUserID());
            statement.setInt(2, waitlist.getCarpoolingID());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("adding to the waitlist failed");
            }
        }
    }

    @Override
    public void update(Waitlist waitlist) {

    }

    @Override
    public void delete(Waitlist waitlist) throws SQLException {

    }

    @Override
    public void add(Comment comment, int id) {

    }

    @Override
    public List<Waitlist> getAll() {
        return null;
    }

    @Override
    public List<Waitlist> search(String searchTerm, String sortBy) throws SQLException {
        return null;
    }

    @Override
    public void addEventRating(EventRating eventRating) throws SQLException {

    }

    @Override
    public double calculateAverageRating(int eventId) throws SQLException {
        return 0;
    }


    public Waitlist getFirstUserOnWaitlist(int carpoolingId) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Waitlist firstUser = null;

        try {
            connection = DBconnection.getInstance().getCnx();
            String sql = "SELECT * FROM waitlist WHERE carpooling_id = ? ORDER BY id ASC LIMIT 1";
            statement = connection.prepareStatement(sql);
            statement.setInt(1, carpoolingId);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("user_id");
                firstUser = new Waitlist(userId, carpoolingId);
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return firstUser;
    }
}

