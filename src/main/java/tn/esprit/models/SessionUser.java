package tn.esprit.models;

public  class SessionUser {
    public static  User loggedUser;

    public static void setLoggedUser(User loggedUser) {
        SessionUser.loggedUser = loggedUser;
    }

    public static User getLoggedUser() {
        return loggedUser;
    }

}
