package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.*;
import tn.esprit.util.DBconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService {

    // DB connection
    Connection cnx = DBconnection.getInstance().getCnx();

    public List<Reservation> getReservationsForCurrentUser(int userId) throws SQLException {
        List<Reservation> userReservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation WHERE user_id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setInt(1, userId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Reservation reservation = new Reservation();
                    reservation.setId(resultSet.getInt("id"));
                    reservation.setUserID(resultSet.getInt("user_id"));
                    reservation.setCarpoolingID(resultSet.getInt("carpooling_id"));
                    userReservations.add(reservation);
                }
            }
        }
        return userReservations;
    }

    public Reservation getById(int reservationId) throws SQLException {
        String sql = "SELECT * FROM reservation WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(sql)) {
            statement.setInt(1, reservationId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Reservation reservation = new Reservation();
                    reservation.setId(resultSet.getInt("id"));
                    reservation.setUserID(resultSet.getInt("user_id"));
                    reservation.setCarpoolingID(resultSet.getInt("carpooling_id"));
                    return reservation;
                } else {
                    return null;
                }
            }
        }
    }

    // Add a new reservation
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
    public void update(Reservation reservation) {
    }
    public void delete(Reservation reservation) throws SQLException {
        String query = "DELETE FROM reservation WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, reservation.getId());
            statement.executeUpdate();
        }
    }


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

    public List<Reservation> search(String searchTerm, String sortBy) throws SQLException {
        return null;
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