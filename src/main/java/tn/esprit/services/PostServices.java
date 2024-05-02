package tn.esprit.services;
import tn.esprit.util.DBconnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import tn.esprit.models.Post;
public class PostServices implements IService2<Post> {
    static Connection cnx = DBconnection.getInstance().getCnx();

    @Override
    public void add(Post post) {
        String req = "INSERT INTO `post`( `title`,`auteur`, `content` ,`image` ) VALUES (?,?,?,?)";
        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setString(1, post.getTitle());
            stm.setString(2, post.getAuteur());
            stm.setString(3, post.getContent());
            stm.setString(4, post.getImage());
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
                post.setImage(resultSet.getString("image"));
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }
    @Override
    public void update(Post post) {
        String req = "UPDATE post SET title = ?,auteur = ?, content = ?, image = ? WHERE id = ?";
        try {
            PreparedStatement stm = cnx.prepareStatement(req);
            stm.setString(1, post.getTitle());
            stm.setString(2, post.getAuteur());
            stm.setString(3, post.getContent());
            stm.setString(4, post.getImage());
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
    public static Post GetPostById(int id) {
            String req = "SELECT * FROM post WHERE id = ?";
            Post  post =null;
            try  {
                PreparedStatement st =cnx.prepareStatement(req);
                st.setInt(1,id);
                ResultSet res = st.executeQuery();
                if(res.next()){
                    post = new Post();
                    post.setId(res.getInt("id"));
                    post.setAuteur(res.getString("auteur"));
                    post.setTitle(res.getString("title"));
                    post.setContent(res.getString("content"));
                    post.setImage(res.getString("image"));
                }
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'envoi de la requête : " + e.getMessage());
            }
            return post;
        }
    public static boolean containsBadwords(String text) {
        List<String> badWords = Arrays.asList("debile", "malin","merde", "idiot");
        for (String word : badWords) {
            if (text.toLowerCase().contains(word.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
}
