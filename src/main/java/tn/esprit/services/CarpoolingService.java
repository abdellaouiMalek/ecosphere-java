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
        String query = "INSERT INTO `carpooling` (`departure_date`, `arrival_date`, `departure`, `destination`, `price`, `time`) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = cnx.prepareStatement(query)) {
            preparedStatement.setDate(1, new Date(carpooling.getDepartureDate().getTime()));
            preparedStatement.setDate(2, new Date(carpooling.getArrivalDate().getTime()));
            preparedStatement.setString(3, carpooling.getDeparture());
            preparedStatement.setString(4, carpooling.getDestination());
            preparedStatement.setDouble(5, carpooling.getPrice());
            preparedStatement.setTime(6, carpooling.getTime());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(Carpooling carpooling) {
    }

    @Override
    public void delete(Carpooling carpooling) throws SQLException {

    }

    @Override
    public List<Carpooling> getAll() {
        return null;
    }
}
