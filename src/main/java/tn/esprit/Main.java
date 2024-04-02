package tn.esprit;

import tn.esprit.models.Carpooling;
import tn.esprit.services.CarpoolingService;
import tn.esprit.util.DBconnection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        DBconnection cnx = DBconnection.getInstance();
        Scanner scanner = new Scanner(System.in);
        CarpoolingService carpoolingService = new CarpoolingService();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        /* // Adding a new carpooling
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
        */

        /* // Delete an existing carpooling
        try {
            System.out.println("Enter the ID of the carpooling to delete:");
            int carpoolingId = scanner.nextInt();
            scanner.nextLine();

            Carpooling carpooling = new Carpooling();
            carpooling.setId(carpoolingId);

            carpoolingService.delete(carpooling);
            System.out.println("Carpooling with ID " + carpoolingId + " deleted successfully!");

        } catch (SQLException e) {
            System.out.println("An error occurred while communicating with the database.");
            e.printStackTrace();
        }*/

       /* // Updating a carpooling
        try {
            System.out.println("Enter the ID of the carpooling to update:");
            int carpoolingId = scanner.nextInt();
            scanner.nextLine();

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

            Carpooling updatedCarpooling = new Carpooling(carpoolingId, departure, destination, departureDate, arrivalDate, time, price);

            carpoolingService.update(updatedCarpooling);
            System.out.println("Carpooling with ID " + carpoolingId + " updated successfully!");

        } catch (ParseException e) {
            System.out.println("Error parsing the date/time. Please ensure you're using the correct format.");
        } */
        // get all carpoolings
        try {
            List<Carpooling> carpoolings = carpoolingService.getAll();

            System.out.println("All carpooling available:");
            for (Carpooling carpooling : carpoolings) {
                System.out.println(carpooling);
            }

        } catch (Exception e) {
            System.out.println("An error occurred while communicating with the database.");
            e.printStackTrace();
        }
    }
    }

