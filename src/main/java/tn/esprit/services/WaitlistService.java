package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Waitlist;
import tn.esprit.util.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
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
    public List<Waitlist> getAll() {
        return null;
    }
}
