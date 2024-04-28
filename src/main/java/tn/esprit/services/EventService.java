package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Category;
import tn.esprit.models.Event;
import tn.esprit.models.EventRating;
import tn.esprit.models.EventRegistrations;
import tn.esprit.util.DBconnection;

import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        String req = "INSERT INTO `event`(`event_name`, `address`, `date`, `time`, `location`, `objective`, `description`,`image`, `category_id`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setString(1, event.getEventName());
            ps.setString(2, event.getAddress());
            ps.setDate(3, new java.sql.Date(event.getDate().getTime()));
            ps.setTime(4, event.getTime());
            ps.setString(5, event.getLocation());
            ps.setString(6, event.getObjective());
            ps.setString(7, event.getDescription());
            ps.setString(8, event.getImage());
            ps.setInt(9, event.getCategory().getId());


            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error occurred while adding the event: " + e.getMessage(), e);
        }
    }


    @Override
    public void update(Event event) {
        String req = "UPDATE `event` SET event_name = ?, address = ?, date = ?, time = ?, location = ?, objective = ?, description = ?, category_id = ? WHERE id = ?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, event.getEventName());
            ps.setString(2, event.getAddress());
            ps.setDate(3, new java.sql.Date(event.getDate().getTime()));
            ps.setTime(4, event.getTime());
            ps.setString(5, event.getLocation());
            ps.setString(6, event.getObjective());
            ps.setString(7, event.getDescription());
            ps.setInt(8, event.getCategory().getId());
            ps.setInt(9, event.getId());  // Set the value for the id parameter
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

    private Category getCategoryById(int categoryId) throws SQLException {
        String req = "SELECT * FROM category WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, categoryId);
            ResultSet res = ps.executeQuery();
            if (res.next()) {
                Category category = new Category();
                category.setId(res.getInt("id"));
                category.setName(res.getString("name"));
                return category;
            }
        }
        return null; // Return null if category not found
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

                // Retrieve category for the event
                int categoryId = res.getInt("category_id");
                Category category = getCategoryById(categoryId);
                event.setCategory(category);

                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return events;
    }


    @Override
    public List<Event> search(String searchTerm, String sortBy) throws SQLException {
        List<Event> filteredEvents = new ArrayList<>();
        String sql = "SELECT * FROM event WHERE event_name LIKE ?";
        if (sortBy != null) {
            switch (sortBy) {
                case "event name":
                    sql += " ORDER BY event_name ASC";
                    break;
                case "category":
                    sql += " ORDER BY category ASC";
                    break;
                case "Start Date":
                    sql += " ORDER BY date ASC";
                    break;
                default:
                    // No sorting
                    break;
            }
        }
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
            // Retrieve category for the event
            int categoryId = rs.getInt("category_id");
            Category category = getCategoryById(categoryId);
            event.setCategory(category);
            filteredEvents.add(event);
        }
        return filteredEvents;
    }

    // Add event rating
    public void addEventRating(EventRating eventRating) throws SQLException {
        String req = "INSERT INTO event_rating(event_id, rating) VALUES (?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setInt(1, eventRating.getId()); // Extract eventId from EventRating
            ps.setInt(2, eventRating.getRating()); // Extract rating from EventRating
            ps.executeUpdate();
        }
    }

    public double calculateAverageRating(int eventId) throws SQLException {
        String sql = "SELECT AVG(rating) AS average_rating FROM event_rating WHERE event_id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("average_rating");
            }
        }
        return 0.0; // Default value if no rating is found
    }


    public void saveRegistration(EventRegistrations registration) throws SQLException {
        try {
            String req = "INSERT INTO event_registrations (registration_date, registration_time, status, event_id) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setDate(1, new java.sql.Date(registration.getRegistrationDate().getTime()));
            ps.setTime(2, registration.getRegistrationTime());
            ps.setString(3, registration.getStatus());
            ps.setInt(4, registration.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isInterested(int eventId, int userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM event_registrations WHERE event_id = ? AND (user_id = ? OR user_id IS NULL)";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ps.setInt(2, userId);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        }
        return false;
    }

    public void cancelInterest(int eventId, int userId) throws SQLException {
        String sql = "DELETE FROM event_registrations WHERE event_id = ? AND (user_id = ? OR user_id IS NULL)";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    // Method to retrieve rating counts for a specific event
    public Map<Integer, Integer> getRatingCounts(int eventId) throws SQLException {
        Map<Integer, Integer> ratingCounts = new HashMap<>();

        String sql = "SELECT rating, COUNT(*) AS count FROM event_rating WHERE event_id = ? GROUP BY rating";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int rating = rs.getInt("rating");
                int count = rs.getInt("count");
                ratingCounts.put(rating, count);
            }
        }

        return ratingCounts;
    }

    // Method to retrieve the count of users interested in a specific event
    public int getInterestedUserCount(int eventId) throws SQLException {
        int interestedCount = 0;

        String sql = "SELECT COUNT(*) FROM event_registrations WHERE event_id = ? AND status = 'Interested'";
        try (PreparedStatement ps = cnx.prepareStatement(sql)) {
            ps.setInt(1, eventId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    interestedCount = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving interested user count: " + e.getMessage(), e);
        }

        return interestedCount;
    }
}