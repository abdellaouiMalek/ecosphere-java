package tn.esprit.services;

import tn.esprit.models.Comment;
import tn.esprit.models.Post;
import tn.esprit.util.DBconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommentServices implements IService<Comment> {
    Connection cnx = DBconnection.getInstance().getCnx();

    @Override
    public void add(Comment comment) {
        String req = "INSERT INTO `comment`(`contenu`, `publicationDate`) VALUES (?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setString(1, comment.getContenu());
            java.util.Date currentDate = new java.util.Date();
            stm.setDate(2, new java.sql.Date(currentDate.getTime())); // Convert java.util.Date to java.sql.Date
            stm.executeUpdate();
            System.out.println("Comment Added Successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Comment> getAll() {
        List<Comment> comments = new ArrayList<>();
        String req = "SELECT * FROM comment";
        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            ResultSet resultSet = stm.executeQuery();
            while (resultSet.next()) {
                Comment comment = new Comment();
                comment.setId(resultSet.getInt("id"));
                comment.setContenu(resultSet.getString("contenu"));
                comment.setPublicationDate(resultSet.getDate("publicationDate"));
                comments.add(comment);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return comments;
    }
    @Override
    public void update(Comment comment) {
        String req = "UPDATE comment SET contenu = ?, publicationDate = ? WHERE id = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setString(1, comment.getContenu());
            stm.setDate(2, new java.sql.Date(comment.getPublicationDate().getTime()));
            stm.setInt(3, comment.getId());
            stm.executeUpdate();
            System.out.println("Comment Updated Successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(Comment comment) {
        String req = "DELETE FROM comment WHERE id = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setInt(1, comment.getId());
            stm.executeUpdate();
            System.out.println("Comment Deleted Successfully!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

