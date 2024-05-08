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
    public void add(Comment comment ,int id) {
        String req = "INSERT INTO `comment`(`contenu`,`idpost`) VALUES (?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setString(1, comment.getContenu());
            stm.setInt(2, comment.getIdpost());
            System.out.println(id);
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
                int post;
                post = PostServices.GetPostById(resultSet.getInt("post_id")).getId();
                comments.add(comment);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return comments;
    }
    @Override
    public void update(Comment comment) {
        String req = "UPDATE comment SET contenu = ? WHERE id = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setString(1, comment.getContenu());
            stm.setInt(2, comment.getId());
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

