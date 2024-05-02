package tn.esprit.test;

import tn.esprit.models.Role;
import tn.esprit.models.SessionUser;
import tn.esprit.models.User;
import tn.esprit.services.UserService;
import tn.esprit.util.DBconnection;

public class Main {
    public static void main(String[] args) {

        DBconnection cnx = DBconnection.getInstance();

       // User u = new User(1, "admin", "slouma", "slouma.masmoudi@gmail.com", "123", "123F", "picture", Role.ADMIN);
        UserService us = new UserService();
 //       us.add(u);
//        us.update(u);
//        System.out.println(us.getById(1));
//        System.out.println(us.getAll());
     //   us.login(u.getEmail(), u.getPassword());

    }
}

