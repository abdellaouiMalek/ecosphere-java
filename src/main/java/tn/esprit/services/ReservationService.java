package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Carpooling;
import tn.esprit.models.Reservation;
import tn.esprit.models.User;
import tn.esprit.util.DBconnection;

import java.sql.*;
import java.util.ArrayList;
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
        String query = "DELETE FROM reservation WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, reservation.getId());
            statement.executeUpdate();
        }
    }

    @Override
    public List<Reservation> getAll() {
        List<Reservation> reservations = new ArrayList<>();
        String req = "SELECT * FROM reservation";
        try (Statement statement = cnx.createStatement();
             ResultSet result = statement.executeQuery(req)) {
            while (result.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(result.getInt("id"));
                reservations.add(reservation);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }


    public int getReservationCountForCarpooling(int carpoolingId) throws SQLException {
        int reservationCount = 0;
            String sql = "SELECT COUNT(*) FROM reservation WHERE carpooling_id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setInt(1, carpoolingId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    reservationCount = resultSet.getInt(1);
                }
            }
        }
        return reservationCount;
    }
}