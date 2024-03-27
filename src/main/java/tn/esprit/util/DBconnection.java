package tn.esprit.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBconnection {
    // DB credentials
    final String URL ="jdbc:mysql://localhost:3306/ecosphere";
    final String USR = "root" ;
    final String PWD = "" ;

    // Attribut
    private Connection cnx ;
    private static DBconnection instance ;

    // Constructor
    private DBconnection()
    {
        try {
            cnx = DriverManager.getConnection(URL, USR , PWD);
            System.out.println("connection successfully done");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getCnx() {
        return cnx;
    }

    public static DBconnection getInstance()
    {
        if (instance == null)
            instance = new DBconnection() ;
        return instance ;
    }
}
