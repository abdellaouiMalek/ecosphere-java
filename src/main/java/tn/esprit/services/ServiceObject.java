package tn.esprit.services;

import tn.esprit.models.History;
import tn.esprit.models.Object;
import tn.esprit.tools.Statics;
import tn.esprit.tools.UtilityFunctions;
import tn.esprit.util.DBconnection;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServiceObject  {

    Connection cnx = DBconnection.getInstance().getCnx();


    public void add(Object o) {
        String reqAdd = "INSERT INTO `object`( `name`, `type`, `description`, `age`, `picture`, `price`) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ste = cnx.prepareStatement(reqAdd)){
            ste.setString(1, o.getName());
            ste.setString(2, o.getType());
            ste.setString(3, o.getDescription());
            ste.setInt(4, o.getAge());
            ste.setString(5, o.getPicture());
            ste.setFloat(6, o.getPrice());
            ste.executeUpdate();
            System.out.println("Object ajouté avec succès");

        } catch (SQLException e) {
        e.printStackTrace();
        }
    }


        public void update(Object o) {
            String reqUpdate="UPDATE `object` SET `name`=?, `type`=?, `description`=?, `age`=?, `picture`=?, `price`=? WHERE id=?";

            try (PreparedStatement ste = cnx.prepareStatement(reqUpdate)){
                 ste.setString(1, o.getName());
                 ste.setString(2, o.getType());
                 ste.setString(3, o.getDescription());
                 ste.setInt(4, o.getAge());
                 ste.setString(5,o.getPicture());
                 ste.setFloat(6, o.getPrice());
                 ste.setLong(7, o.getId());
                int rowsAffected = ste.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Object mis à jour avec succès");
                } else {
                    System.out.println("Aucun Object trouvé avec cet ID pour la mise à jour.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    public Object getOne(int id) {
        String req = "SELECT * FROM object WHERE id =?";
        try (PreparedStatement ste = cnx.prepareStatement(req)){
            ste.setInt(1,id);
            ResultSet rs = ste.executeQuery(req);
            if(rs.next()){
                Object o = new Object();
                o.setId(rs.getInt(1));
                o.setName(rs.getString(2));
                o.setType(rs.getString(3));
                o.setDescription( rs.getString(4));
                o.setAge( rs.getInt(5));
                o.setPicture(rs.getString(6));
                o.setPrice(rs.getFloat(7));
                return o;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retourne null si le produit n'est pas trouvé
    }


    // GET ALL HISTORY BY OBJECT ID
    public List<History> getAllHistoryByObject(int foreignId) {
        List<History> histories = new ArrayList<>();
        String req = "SELECT * " +
                "FROM history h " +
                "JOIN object o ON h.object_id = o.id " +
                "WHERE o.id = "+foreignId;
        try (Statement st = cnx.createStatement();
             ResultSet rs = st.executeQuery(req)) {

            while (rs.next()) {
                // Retrieve data from each row in the result set and create History objects
                History h = new History();
                h.setName(rs.getString("name"));
                h.setInitialCondition(rs.getString("initialCondition"));
                h.setDate(rs.getDate("date"));
                h.setId(rs.getInt("id"));
                h.setObject_id(rs.getInt("object_id"));
                histories.add(h);
            }

        }
       catch (SQLException e) {
            System.out.println("probleme here");
            e.printStackTrace();
        }
        if(histories.size() == 0)    return null;
        System.out.println(histories);
        return histories;
    }




    public List<Object> getAll() {
        List<Object> objects = new ArrayList<>();
        String req = "SELECT * FROM Object";
        try {
            Statement st = cnx.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                Object o = new Object();
                o.setId(rs.getInt(1));
                o.setName(rs.getString(2));
                o.setType(rs.getString(3));
                o.setDescription( rs.getString(4));
                o.setAge( rs.getInt(5));
                o.setPicture(rs.getString(6));
                o.setPrice(rs.getFloat(7));
                objects.add(o);
            }

        } catch (SQLException e) {
            System.out.println("Error getting all objects: " +e.getMessage());
        }

        return objects;
    }
    public Object findByName(String name){
            String sql = "SELECT * FROM Object WHERE name LIKE %?% ";
        try (PreparedStatement st = cnx.prepareStatement(sql)){
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return extractObjetFromResultSet(rs);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;// Si aucun objet n'est trouvé ou en cas d'erreur
    }

    private Object extractObjetFromResultSet(ResultSet rs)  throws SQLException{
        Object o = new Object();
        o.setId(rs.getInt(1));
        o.setName(rs.getString(2));
        o.setType(rs.getString(3));
        o.setDescription( rs.getString(4));
        o.setAge( rs.getInt(5));
        o.setPicture(rs.getString(6));
        o.setPrice(rs.getFloat(7));
        return o;
    }

    public int delete(int id)throws SQLException {
        int i = 0;
        try {
            Statement ste = cnx.createStatement();
            String sql = "DELETE FROM `Object` WHERE id=" + id;
            i = ste.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(ServiceObject.class.getName()).log(Level.SEVERE, null, ex);
        }
        return i;
    }



}
