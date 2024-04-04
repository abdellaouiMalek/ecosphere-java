package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Event;
import tn.esprit.util.DBconnection;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EventService implements IService<Event> {

    Connection cnx = DBconnection.getInstance().getCnx();

    @Override
    public void add(Event event) throws SQLException {


        // Get the current date
        java.util.Date currentDateUtil = new java.util.Date();
        java.sql.Date currentDateSql = new java.sql.Date(currentDateUtil.getTime());

        // Validate date to ensure it's not before the current date
        if (event.getDate().before(currentDateSql)) {
            throw new IllegalArgumentException("Date cannot be before the current date.");
        }

        // Insert the event into the database
        String req = "INSERT INTO `event`(`event_name`, `address`, `date`, `time`, `location`, `objective`, `description`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, event.getEventName());
            ps.setString(2, event.getAddress());
            ps.setDate(3, new java.sql.Date(event.getDate().getTime()));
            ps.setTime(4, event.getTime());
            ps.setString(5, event.getLocation());
            ps.setString(6, event.getObjective());
            ps.setString(7, event.getDescription());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred while adding the event: " + e.getMessage(), e);
        }
    }


    @Override
    public void update(Event event) {
        String req = "UPDATE `event` SET event_name = ?, address = ?, date = ?, time = ?, location = ?, objective = ?, description = ? WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, event.getEventName());
            ps.setString(2, event.getAddress());
            ps.setDate(3, new java.sql.Date(event.getDate().getTime()));
            ps.setTime(4, event.getTime());
            ps.setString(5, event.getLocation());
            ps.setString(6, event.getObjective());
            ps.setString(7, event.getDescription());
            ps.setInt(8, event.getId());  // Set the value for the id parameter
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(Event t) throws SQLException {

        try {
            String req = "DELETE FROM event WHERE id = ?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, t.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Event> getAll() {
        List<Event> events = new ArrayList<>();
        try {
            String req = "SELECT * FROM event";
            PreparedStatement statement = cnx.prepareStatement(req);
            ResultSet res = statement.executeQuery();

            while (res.next()) {
                Event event = new Event();
                event.setId(res.getInt("id"));
                event.setEventName(res.getString("event_name"));
                event.setAddress(res.getString("address"));
                event.setLocation(res.getString("location"));
                event.setObjective(res.getString("objective"));
                event.setImage(res.getString("image"));
                event.setDescription(res.getString("description"));
                event.setDate(res.getDate("date"));
                event.setTime(res.getTime("time"));

                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }


    @Override
    public List<Event> search(String searchTerm) throws SQLException {
        List<Event> filteredEvents = new ArrayList<>();
        String sql = "SELECT * FROM event WHERE event_name LIKE ?";
        PreparedStatement ps = cnx.prepareStatement(sql);
        ps.setString(1, "%" + searchTerm + "%");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            Event event = new Event();
            event.setId(rs.getInt("id"));
            event.setEventName(rs.getString("event_name"));
            event.setAddress(rs.getString("address"));
            event.setLocation(rs.getString("location"));
            event.setObjective(rs.getString("objective"));
            event.setImage(rs.getString("image"));
            event.setDescription(rs.getString("description"));
            event.setDate(rs.getDate("date"));
            event.setTime(rs.getTime("time"));
            filteredEvents.add(event);
        }
        return filteredEvents;
    }


}