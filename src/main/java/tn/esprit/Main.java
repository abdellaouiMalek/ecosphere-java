package tn.esprit;

import tn.esprit.models.Comment;
import tn.esprit.models.Post;
import tn.esprit.services.CommentServices;
import tn.esprit.services.PostServices;
import tn.esprit.util.DBconnection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        DBconnection cnx = DBconnection.getInstance();
        //PostServices post = new PostServices();
        //Post p = new Post("auteue22322", "auteur1232636", "content1", new Date());
        //post.add(p);
        CommentServices comment = new CommentServices();
        Comment c = new Comment( "contenu1",new Date());
        comment.add(c);
        //List<Post> l = post.getAll();
        //System.out.println(l);
         //post.delete(p);
    }

}