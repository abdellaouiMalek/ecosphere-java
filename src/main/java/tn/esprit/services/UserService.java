package tn.esprit.services;


import tn.esprit.models.Role;
import tn.esprit.models.User;
import tn.esprit.util.DBconnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IUserService<User>{

    private Connection cnx;

    public UserService() {
        cnx = DBconnection.getInstance().getCnx();
    }
    @Override
    public void add(User user) {
        String req = "INSERT INTO `user`(`first_name`, `last_name`, `email`, `password`, `phone_number`, `picture`, `role`) VALUES (?,?,?,?,?,?,?)";
        try {
            PreparedStatement stm  = cnx.prepareStatement(req);
            stm.setString(1,user.getFirst_name());
            stm.setString(2,user.getLast_name());
            stm.setString(3,user.getEmail());
            stm.setString(4,user.getPassword());
            stm.setString(5,user.getPhone_number());
            stm.setString(6,user.getPicture());
            stm.setString(7,user.getRole().toString());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<User> getAll() {
        String sql = "select * from user";

        List<User> users = new ArrayList<>();

        try {
            Statement statement = cnx.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setFirst_name(resultSet.getString(2));
                user.setLast_name(resultSet.getString(3));
                user.setEmail(resultSet.getString(4));
                user.setPassword(resultSet.getString(5));
                user.setPhone_number(resultSet.getString(6));
                user.setPicture(resultSet.getString(7));
                String roleName = resultSet.getString(8);
                Role role = Role.valueOf(roleName);
                user.setRole(role);

                users.add(user);
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return users;
    }

    @Override
    public User getById(int id) {
        String sql = "SELECT * FROM `user` WHERE `id` = ?";
        User user = new User();
        try {
            PreparedStatement pstm = cnx.prepareStatement(sql);
            pstm.setInt(1, id );
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(id));
                user.setFirst_name(resultSet.getString(2));
                user.setLast_name(resultSet.getString(3));
                user.setEmail(resultSet.getString(4));
                user.setPassword(resultSet.getString(5));
                user.setPhone_number(resultSet.getString(6));
                user.setPicture(resultSet.getString(7));
                String roleName = resultSet.getString(8);
                Role role = Role.valueOf(roleName);
                user.setRole(role);
            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
return user;
    }

    @Override
    public void update(User user) {

        String req ="UPDATE `user` SET  `first_name`= ?,`last_name`=? ,`email`=?,`password`=?,`phone_number`=?,`picture`=?,`role`=? WHERE `id`=?";
        try {
            PreparedStatement stm  = cnx.prepareStatement(req);
            stm.setString(1,user.getFirst_name());
            stm.setString(2,user.getLast_name());
            stm.setString(3,user.getEmail());
            stm.setString(4,user.getPassword());
            stm.setString(5,user.getPhone_number());
            stm.setString(6,user.getPicture());
            stm.setString(7,user.getRole().toString());
            stm.setInt(8,user.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(User user) {
        String req = "delete from user where id = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(req);
            pstm.setInt(1, user.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());        }

    }

    public String getUserPhoneNumber(int userId) {
        String phoneNumber = null;
        try {
            String sql = "SELECT phone_number FROM `user` WHERE id = ?";
            PreparedStatement pstm = cnx.prepareStatement(sql);
            pstm.setInt(1, userId);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                phoneNumber = resultSet.getString("phone_number");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return phoneNumber;
    }
}
