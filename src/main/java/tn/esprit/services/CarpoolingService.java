package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Carpooling;
import tn.esprit.models.EventRating;
import tn.esprit.util.DBconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CarpoolingService implements IService<Carpooling> {

    // DB connection
    Connection cnx = DBconnection.getInstance().getCnx();

    // Add a new carpooling
    @Override
    public void add(Carpooling carpooling) throws SQLException {
        String sql = "INSERT INTO carpooling (user_id,departure, destination, departure_date, arrival_date, time, price,seat) VALUES (?,?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setInt(1, carpooling.getUserID());
            st.setString(2, carpooling.getDeparture());
            st.setString(3, carpooling.getDestination());
            st.setDate(4, new java.sql.Date(carpooling.getDepartureDate().getTime()));
            st.setDate(5, new java.sql.Date(carpooling.getArrivalDate().getTime()));
            st.setTime(6, carpooling.getTime());
            st.setDouble(7, carpooling.getPrice());
            st.setInt(8,carpooling.getSeat());

            int affectedRows = st.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating carpooling failed, no rows affected.");
            }

            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    carpooling.setId(id);
                } else {
                    throw new SQLException("Creating carpooling failed, no ID obtained.");
                }
            }
        }
    }

    // update an existing carpooling
    @Override
    public void update(Carpooling carpooling) {
        String req = "UPDATE `carpooling` SET " +
                "departure_date = IFNULL(?, departure_date), " +
                "arrival_date = IFNULL(?, arrival_date), " +
                "departure = IFNULL(?, departure), " +
                "destination = IFNULL(?, destination), " +
                "price = IFNULL(?, price), " +
                "time = IFNULL(?, time), " +
                "seat = IFNULL(?, seat) " +
                "WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setDate(1, carpooling.getDepartureDate() != null ? new Date(carpooling.getDepartureDate().getTime()) : null);
            ps.setDate(2, carpooling.getArrivalDate() != null ? new Date(carpooling.getArrivalDate().getTime()) : null);
            ps.setString(3, carpooling.getDeparture());
            ps.setString(4, carpooling.getDestination());
            ps.setDouble(5, carpooling.getPrice());
            ps.setTime(6, carpooling.getTime());
            ps.setInt(7, carpooling.getSeat());
            ps.setInt(8, carpooling.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Delete an existing carpooling
    @Override
    public void delete(Carpooling carpooling) throws SQLException {
        String query = "DELETE FROM carpooling WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, carpooling.getId());
            statement.executeUpdate();
        }
    }
    // get all carpoolings available on the db
    @Override
    public List<Carpooling> getAll()   {
        List<Carpooling> carpoolings = new ArrayList<>();
        String req = "SELECT * FROM carpooling";
        try (Statement statement = cnx.createStatement();
             ResultSet result = statement.executeQuery(req)) {
            while (result.next()) {
                Carpooling carpooling = new Carpooling();
                carpooling.setId(result.getInt("id"));
              //  carpooling.setDepartureDate(result.getDate("departure_date"));
               // carpooling.setArrivalDate(result.getDate("arrival_date"));
                carpooling.setDeparture(result.getString("departure"));
                carpooling.setDestination(result.getString("destination"));
                carpooling.setPrice(result.getDouble("price"));
                carpooling.setTime(result.getTime("time"));
                carpooling.setSeat(result.getInt("seat"));
                carpoolings.add(carpooling);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return carpoolings;
    }

    @Override
    public List<Carpooling> search(String searchTerm, String sortBy) throws SQLException {
        return null;
    }

    @Override
    public void addEventRating(EventRating eventRating) throws SQLException {

    }

    @Override
    public double calculateAverageRating(int eventId) throws SQLException {
        return 0;
    }

    // search for a carpooling
    public List<Carpooling> search(String departure, String destination, Date arrivalDate) throws SQLException {
        List<Carpooling> carpoolings = new ArrayList<>();
        String req = "SELECT * FROM carpooling WHERE departure = ? AND destination = ? AND arrival_date = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, departure);
            ps.setString(2, destination);
            ps.setDate(3, arrivalDate);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Carpooling carpooling = new Carpooling();
                carpooling.setId(resultSet.getInt("id"));
                carpooling.setUserID(resultSet.getInt("user_id"));
                carpooling.setDeparture(resultSet.getString("departure"));
                carpooling.setDestination(resultSet.getString("destination"));
                carpooling.setDepartureDate(resultSet.getDate("departure_date"));
                carpooling.setArrivalDate(resultSet.getDate("arrival_date"));
                carpooling.setTime(resultSet.getTime("time"));
                carpooling.setPrice(resultSet.getDouble("price"));
                carpooling.setSeat(resultSet.getInt("seat"));
                carpoolings.add(carpooling);
            }
        }
        return carpoolings;
    }

    // Get a carpooling by ID
    public Carpooling getById(int id) throws SQLException {
        String req = "SELECT * FROM carpooling WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, id);
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return new Carpooling(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("departure"),
                        resultSet.getString("destination"),
                        resultSet.getDate("departure_date"),
                        resultSet.getDate("arrival_date"),
                        resultSet.getTime("time"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("seat")
                );
            }
        }
        return null;
    }

    public int getCarpoolingSeats(int carpoolingId) throws SQLException {
        String sql = "SELECT seat FROM carpooling WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, carpoolingId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("seat");
            }
        }
        return 0;
    }

    public List<Carpooling> getCarpoolingsByUserId(int userId) throws SQLException {
        List<Carpooling> carpoolings = new ArrayList<>();
        String req = "SELECT * FROM carpooling WHERE user_id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                Carpooling carpooling = new Carpooling();
                carpooling.setId(resultSet.getInt("id"));
                carpooling.setUserID(resultSet.getInt("user_id"));
                carpooling.setDeparture(resultSet.getString("departure"));
                carpooling.setDestination(resultSet.getString("destination"));
                carpooling.setDepartureDate(resultSet.getDate("departure_date"));
                carpooling.setArrivalDate(resultSet.getDate("arrival_date"));
                carpooling.setTime(resultSet.getTime("time"));
                carpooling.setPrice(resultSet.getDouble("price"));
                carpooling.setSeat(resultSet.getInt("seat"));
                carpoolings.add(carpooling);
            }
        }
        return carpoolings;
    }
}
