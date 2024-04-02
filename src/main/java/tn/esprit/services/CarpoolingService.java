package tn.esprit.services;

import tn.esprit.interfaces.IService;
import tn.esprit.models.Carpooling;
import tn.esprit.util.DBconnection;

import java.sql.*;
import java.util.List;

public class CarpoolingService implements IService<Carpooling> {

    // DB connection
    Connection cnx = DBconnection.getInstance().getCnx();

    // Add a new carpooling
    @Override
    public void add(Carpooling carpooling) throws SQLException {
        String req = "INSERT INTO `carpooling` (`departure_date`, `arrival_date`, `departure`, `destination`, `price`, `time`) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)) {
            ps.setDate(1, new Date(carpooling.getDepartureDate().getTime()));
            ps.setDate(2, new Date(carpooling.getArrivalDate().getTime()));
            ps.setString(3, carpooling.getDeparture());
            ps.setString(4, carpooling.getDestination());
            ps.setDouble(5, carpooling.getPrice());
            ps.setTime(6, carpooling.getTime());
            ps.executeUpdate();
        }
    }

    @Override
    public void update(Carpooling carpooling) {
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

    @Override
    public List<Carpooling> getAll() {
        return null;
    }
}
