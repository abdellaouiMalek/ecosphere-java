package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Reservation;
import tn.esprit.models.User;
import tn.esprit.util.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class ReservationService implements IService<Reservation> {

    // DB connection
    Connection cnx = DBconnection.getInstance().getCnx();
    // Add a new reservation
    @Override
    public void add(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservation (user_id, carpooling_id) VALUES (?, ?)";
        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setInt(1, reservation.getUserID());
            statement.setInt(2, reservation.getCarpoolingID());

            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating reservation failed");
            }
        }
    }

@Override
    public void update(Reservation reservation) {

    }

    @Override
    public void delete(Reservation reservation) throws SQLException {

    }

    @Override
    public List<Reservation> getAll() {
        return null;
    }
}
