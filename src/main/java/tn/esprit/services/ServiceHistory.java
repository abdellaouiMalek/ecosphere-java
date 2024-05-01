package tn.esprit.services;

import tn.esprit.models.History;
import tn.esprit.models.Object;
import tn.esprit.util.DBconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceHistory  {
    Connection cnx = DBconnection.getInstance().getCnx();


    public void add(History history) {
        String req = "INSERT INTO History (`name`, `initialCondition`, `date`) VALUES (?,?,?)";
        try (PreparedStatement ps = cnx.prepareStatement(req)){
            ps.setString(1, history.getName());
            ps.setString(2, history.getInitialCondition());
            ps.setDate(3, new java.sql.Date(history.getDate().getTime())); // Convert java.util.Date to java.sql.Date
            ps.executeUpdate();
            System.out.println("History Added Successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(History history) {
        String req = "UPDATE `History` SET `name`=?, `initialCondition`=?, `date`=? WHERE id=?";
        try (PreparedStatement stm = cnx.prepareStatement(req)){
            stm.setString(1,history.getName());
            stm.setString(2, history.getInitialCondition());
            stm.setDate(3, new java.sql.Date(history.getDate().getTime())); // Convert java.util.Date to java.sql.Date
            stm.setLong(4, history.getId());
            int rowsAffected = stm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("history mis à jour avec succès");
            } else {
                System.out.println("Aucun history trouvé avec cet ID pour la mise à jour.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public History getOne(int id) {
        String req = "SELECT * FROM History WHERE id =?";

        try (PreparedStatement st = cnx.prepareStatement(req)){
            st.setInt(1,id);
            ResultSet rs = st.executeQuery(req);
            if(rs.next()){
                History h = new History();
                h.setName(rs.getString(1));
                h.setInitialCondition(rs.getString(2));
                h.setDate( rs.getDate(3));
                h.setId(rs.getInt(4));
                return h;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retourne null si le produit n'est pas trouvé
    }

    public List<History> getAll() {
        List<History> history = new ArrayList<>();
        String req = "SELECT * FROM History";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                History h = new History();
                h.setName(rs.getString(1));
                h.setInitialCondition(rs.getString(2));
                h.setDate(rs.getDate(3));
                h.setId(rs.getInt(4));
                history.add(h);
            }

        } catch (SQLException e) {
            System.out.println("Error getting all history: " +e.getMessage());
        }

        return history;
    }

    public History findByName(String name){
        String sql = "SELECT * FROM History WHERE name = ?";
        try (PreparedStatement st = cnx.prepareStatement(sql)){
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return extractHistoryFromResultSet(rs);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;// Si aucun objet n'est trouvé ou en cas d'erreur
    }

    private History extractHistoryFromResultSet(ResultSet rs)  throws SQLException{
        History h = new History();
        h.setId(rs.getInt(1));
        h.setName(rs.getString(2));
        h.setInitialCondition(rs.getString(3));
        h.setDate( rs.getDate(4));
        return h;
    }

    public int delete(int id)throws SQLException {
        int i = 0;
        try {
            Statement ste = cnx.createStatement();
            String sql = "DELETE FROM `History` WHERE id=" + id;
            i = ste.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceHistory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }
}






