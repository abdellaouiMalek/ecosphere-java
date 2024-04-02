package tn.esprit;


import tn.esprit.models.Carpooling;
import tn.esprit.services.CarpoolingService;
import tn.esprit.util.DBconnection;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        DBconnection cnx = DBconnection.getInstance();
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        try {
            System.out.println("Enter departure location:");
            String departure = scanner.nextLine();

            System.out.println("Enter destination location:");
            String destination = scanner.nextLine();

            System.out.println("Enter departure date (YYYY-MM-DD):");
            Date departureDate = dateFormat.parse(scanner.nextLine());

            System.out.println("Enter arrival date (YYYY-MM-DD):");
            Date arrivalDate = dateFormat.parse(scanner.nextLine());

            System.out.println("Enter departure time (HH:MM):");
            java.sql.Time time = new java.sql.Time(timeFormat.parse(scanner.nextLine()).getTime());

            System.out.println("Enter price:");
            double price = scanner.nextDouble();

          /*  in case u want to add the carpooling using an empty constructor
            Carpooling carpooling = new Carpooling();
            carpooling.setDeparture(departure);
            carpooling.setDestination(destination);
            carpooling.setDepartureDate(departureDate);
            carpooling.setArrivalDate(arrivalDate);
            carpooling.setTime(time);
            carpooling.setPrice(price); */

            Carpooling carpooling = new Carpooling(0, departure, destination, departureDate, arrivalDate, time, price);

            CarpoolingService carpoolingService = new CarpoolingService();
            carpoolingService.add(carpooling);
            System.out.println("Carpooling added successfully!");

        } catch (ParseException e) {
            System.out.println("Error parsing the date/time. Please ensure you're using the correct format.");
        } catch (SQLException e) {
            System.out.println("An error occurred while communicating with the database.");
            e.printStackTrace();
        }
    }
    }

