package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Event;
import tn.esprit.util.DBconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventService implements IService<Event> {

    Connection cnx = DBconnection.getInstance().getCnx();

    @Override
    public void add(Event event) throws SQLException {
        String req = "INSERT INTO `event`(`event_name`, `address`, `date`, `time`, `location`, `objective`, `description`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, event.getEventName());
        ps.setString(2, event.getAddress());
        ps.setDate(3, new java.sql.Date(event.getDate().getTime()));
        ps.setTime(4, event.getTime());
        ps.setString(5, event.getLocation());
        ps.setString(6, event.getObjective());
        ps.setString(7, event.getDescription());

        ps.executeUpdate();
        ps.close();
    }

    @Override
    public void update(Event event) {
        String req = "UPDATE `event` SET event_name= ?, address = ?, date = ?, time = ?, location= ?, objective = ?, description = ? WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, event.getEventName());
            ps.setString(2, event.getAddress());
            ps.setDate(3, new java.sql.Date(event.getDate().getTime()));
            ps.setTime(4, event.getTime());
            ps.setString(5, event.getLocation());
            ps.setString(6, event.getObjective());
            ps.setString(7, event.getDescription());
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

}