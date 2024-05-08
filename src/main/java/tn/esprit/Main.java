package tn.esprit;

import tn.esprit.models.Comment;
import tn.esprit.services.CommentServices;
import tn.esprit.services.PostServices;
import tn.esprit.util.DBconnection;

public class Main {
    public static void main(String[] args) {
        DBconnection cnx = DBconnection.getInstance();
        PostServices post = new PostServices();
        //Post p = new Post("Esprit", "Tunis", "content10", new Date());
        //post.add(p);
        //System.out.println("*********************"+p.getId());
        CommentServices comment = new CommentServices();
        Comment c = new Comment( "contenu20",62);
        System.out.println(post.GetPostById(62).getId());
        comment.add(c, post.GetPostById(62).getId());
        //List<Post> l = post.getAll();
        //System.out.println(l);
         //post.delete(p);
    }

}