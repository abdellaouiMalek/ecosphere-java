
package tn.esprit.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Hello
 */
public class MaConnexion {
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

    //db credentials 
    final String URL=Statics.DATA_BASE_URL;//premier bib que je utioliseur = jdbc 
    final String USERNAME="root"; 
    final String PASSWORD ="";

     private Connection cnx;
 
     static  MaConnexion instance;
    
    private MaConnexion(){
            
        try {
            cnx = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("connection succed");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public Connection getCnx() {
        return cnx;
    }

    //3
    public static MaConnexion getInstance() {
        if(instance == null)
            instance = new MaConnexion();
        
        return instance;
    }
    
    
    


      
    
    

}
