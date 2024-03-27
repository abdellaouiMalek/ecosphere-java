package tn.esprit;

import tn.esprit.models.Role;
import tn.esprit.models.Session;
import tn.esprit.models.User;
import tn.esprit.services.UserService;
import tn.esprit.util.DBconnection;

public class Main {
    public static void main(String[] args) {

        DBconnection cnx = DBconnection.getInstance();

        User u = new User(1,"slouma","slouma","slouma","123",123F,"ghassen", Role.ADMIN);
        UserService us = new UserService();
//          us.add(u);
//        us.update(u);
//        System.out.println(us.getById(1));
//        System.out.println(us.getAll());
        us.login(u.getEmail(), u.getPassword());
        System.out.println(Session.getLoggedInUser());

    }
}