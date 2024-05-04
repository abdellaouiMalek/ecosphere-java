package tn.esprit.models;

public  class Session {

    public static int  id;
    public static String first_name;
    public static String last_name;
    public static String email;
    public static String password;
    public static float phone_number;
    public static String picture;
    public static Role role;

    public  static User getLoggedInUser(){
        return new User(Session.id,Session.first_name,Session.last_name,Session.email,Session.password,Session.phone_number,Session.picture,Session.role);
    }

    
}
