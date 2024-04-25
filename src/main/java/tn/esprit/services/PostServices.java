package tn.esprit.services;
import tn.esprit.util.DBconnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import tn.esprit.models.Post;
public class PostServices implements IService<Post> {
    Connection cnx = DBconnection.getInstance().getCnx();

    @Override
    public void add(Post post) {
        String req = "INSERT INTO `post`( `title`,`auteur`, `content`, `createdat` ) VALUES (?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setString(1, post.getTitle());
            stm.setString(2, post.getAuteur());
            stm.setString(3, post.getContent());
            java.util.Date currentDate = new java.util.Date();
            stm.setDate(4, new java.sql.Date(currentDate.getTime())); // Convert java.util.Date to java.sql.Date

            stm.executeUpdate();
            System.out.println("Post Added Successfully!");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public  List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        String query = "SELECT * FROM post";
        try (PreparedStatement statement = cnx.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                Post post = new Post();
                post.setId(resultSet.getInt("id"));
                post.setTitle(resultSet.getString("title"));
                post.setAuteur(resultSet.getString("auteur"));
                post.setContent(resultSet.getString("content"));
                post.setCreatedat(resultSet.getDate("createdat"));
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public void update(Post post) {
        String req = "UPDATE post SET title = ?,auteur = ?,  content = ?, createdat = ? WHERE id = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setString(1, post.getTitle());
            stm.setString(2, post.getAuteur());
            stm.setString(3, post.getContent());
            stm.setDate(4, new java.sql.Date(post.getCreatedat().getTime())); // Convert LocalDateTime to java.sql.Date
            stm.setInt(5, post.getId());
            stm.executeUpdate();
            System.out.println("Post Updated Successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void delete(Post post) {
        String req = "DELETE FROM post WHERE id = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setInt(1, post.getId());
            stm.executeUpdate();
            System.out.println("Post Deleted Successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
