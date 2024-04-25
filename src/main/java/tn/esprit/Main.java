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
import java.util.Date;
import java.util.List;

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

        ServiceHistory sh = new ServiceHistory();
        History history = new History("good", "waffu", new Date());
        history.add(history);


        // Create a new Date object representing the current date
        Date currentDate = new Date();

        // Create a new History object using the constructor
        /*History history = new History(26,"good", "waffu", new Date());
        ServiceHistory hs = new ServiceHistory();
        hs.add(history);*/


    }
    
}