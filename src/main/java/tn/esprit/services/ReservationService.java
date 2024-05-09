package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.*;
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
    public void add(Comment comment, int id) {

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
                reservation.setUserID(result.getInt("user_id"));
                reservation.setCarpoolingID(result.getInt("carpooling_id"));

                reservations.add(reservation);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
    }

    @Override
    public List<Reservation> search(String searchTerm, String sortBy) throws SQLException {
        return null;
    }

    @Override
    public void addEventRating(EventRating eventRating) throws SQLException {

    }

    @Override
    public double calculateAverageRating(int eventId) throws SQLException {
        return 0;
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