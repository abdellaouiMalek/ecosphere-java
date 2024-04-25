package tn.esprit.services;

import tn.esprit.models.History;
import tn.esprit.util.DBconnection;

import java.sql.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceHistory implements IService <History> {
    Connection cnx = DBconnection.getInstance().getCnx();
    private long Date;


    public void add(History history) {

        String req = "INSERT INTO History (`name`, `initialCondition`, `date`) VALUES (?,?,?)";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            //ps.setString(1, String.valueOf(history.getId()));

            ps.setString(1, history.getName());
            ps.setString(2, history.getInitialCondition());
            //java.util.Date currentDate = new java.util.Date();
            ps.setDate(3, new java.sql.Date(history.getDate().getTime())); // Convert java.util.Date to java.sql.Date
            ps.executeUpdate();
            System.out.println("History Added Successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(History history) {
        try {
            PreparedStatement ste;
            ste = cnx.prepareStatement(
                    "UPDATE `Object` SET `name`=?, `type`=?, `description`=?, `age`=?, `picture`=?, `price`=? WHERE id=?");
            ste.setString(1,history.getName());
            ste.setString(2, history.getInitialCondition());
            ste.setDate(3, history.getDate());
            ste.setLong(7, history.getId());

            ste.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }


    @Override
    public void update(int id, Object o) {

    }


    @Override
    public void delete(History history) {
        int i = 0;
        try {
            Statement ste = cnx.createStatement();
            String sql = "DELETE FROM `History` WHERE id=" + history.getId();
            i = ste.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceObject.class.getName()).log(Level.SEVERE, null, ex);
        }

    }



    @Override
    public List<History> getAll() {
        return null;
    }

    @Override
    public History getOne(int id) {
        try {
            String req = "SELECT * FROM Object WHERE id = " + id;
            PreparedStatement st = cnx.prepareStatement(req);
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
//                System.out.println("Offer getted");
                return new History((rs.getInt("id")), rs.getString("name"), rs.getString("intialCondition"), rs.getDate("date"));

            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
