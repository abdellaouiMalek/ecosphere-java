package tn.esprit;

import tn.esprit.models.History;
import tn.esprit.models.Object;
import tn.esprit.services.ServiceHistory;
import tn.esprit.services.ServiceObject;
import tn.esprit.util.DBconnection;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Month;
import java.sql.Date;
import java.util.List;
import java.time.LocalDate;


public class Main {
    public static void main(String[] args) {
        DBconnection cnx = DBconnection.getInstance();
        /*ServiceObject ob = new ServiceObject();
        ServiceHistory sh = new ServiceHistory();

        // Create a SimpleDateFormat object with the desired date format
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            // Parse the date string into a Date object
            Date specificDate = sdf.parse("2024-04-11");
            History history = new History("good", "waffu", new Date());
            history.add(history);
        } catch (ParseException e) {
            // Handle parsing exception
            e.printStackTrace();        }*/


        LocalDate currentDate = LocalDate.now();

        // Convert to java.sql.Date
        Date sqlDate = Date.valueOf(currentDate);


        ServiceHistory sh = new ServiceHistory();
        // History history = new History("good", "waffu",  sqlDate);
        // sh.add(history);

        // History history2 = sh.getOne(1);
        // history2.setName("up");
        // sh.update(history2);

     //   History history3 = sh.getOne(5);
       // sh.delete(history3);


        // Create a new Date object representing the current date

        // Create a new History object using the constructor
        /*History history = new History(26,"good", "waffu", new Date());
        ServiceHistory hs = new ServiceHistory();
        hs.add(history);*/


    }
    
}