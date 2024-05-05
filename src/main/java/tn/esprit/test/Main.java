package tn.esprit.test;

import tn.esprit.models.Carpooling;
import tn.esprit.models.Reservation;
import tn.esprit.services.CarpoolingService;
import tn.esprit.services.ReservationService;
import tn.esprit.models.Role;
import tn.esprit.models.SessionUser;
import tn.esprit.models.User;
import tn.esprit.services.UserService;
import tn.esprit.util.DBconnection;
import tn.esprit.util.SmsService;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        DBconnection cnx = DBconnection.getInstance();
        Scanner scanner = new Scanner(System.in);

        UserService userService = new UserService();
        ReservationService reservationService = new ReservationService();
        CarpoolingService carpoolingService = new CarpoolingService();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        try {
            System.out.println("Enter the carpooling ID: ");
            int carpoolingId = Integer.parseInt(scanner.nextLine());

            Carpooling carpooling = carpoolingService.getById(carpoolingId);
            if (carpooling != null) {
                System.out.println("Carpooling Details:");
                System.out.println("Departure: " + carpooling.getDeparture());
                System.out.println("Destination: " + carpooling.getDestination());
            } else {
                System.out.println("No carpooling found with ID " + carpoolingId);
            }
        } catch (SQLException e) {
            System.out.println("A database error occurred: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Invalid carpooling ID format");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }

        /*// Search for carpooling
        try {
            System.out.println("Enter your user ID: ");
                int userId = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter the carpooling ID you wish to reserve: ");
            int carpoolingId = Integer.parseInt(scanner.nextLine());
            Reservation newReservation = new Reservation(0, userId, carpoolingId);
            reservationService.add(newReservation);

            String userPhoneNumber = userService.getUserPhoneNumber(userId);

            String message = "Your reservation has been successfully added!";
            SmsService.sendReservationConfirmationSMS(userPhoneNumber, message);

            System.out.println("Reservation added successfully!");
        } catch (SQLException e) {
            System.out.println("A database error occurred: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
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
      /*  // get all carpoolings
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
        */

        /* // search for a carpooling
        System.out.println("Enter departure:");
        String departure = scanner.nextLine();

        System.out.println("Enter destination:");
        String destination = scanner.nextLine();

        System.out.println("Enter arrival date (YYYY-MM-DD):");
        Date arrivalDate;
        try {
            arrivalDate = new java.sql.Date(dateFormat.parse(scanner.nextLine()).getTime());
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please enter date in the format YYYY-MM-DD.");
            return;
        }

        try {
            List<Carpooling> matchingCarpoolings = carpoolingService.search(departure, destination, arrivalDate);
            if (matchingCarpoolings.isEmpty()) {
                System.out.println("No carpoolings found matching the specified criteria.");
            } else {
                System.out.println("Carpoolings matching the specified criteria:");
                for (Carpooling carpooling : matchingCarpoolings) {
                    System.out.println(carpooling);
                }
            }
        } catch (SQLException e) {
            System.out.println("An error occurred while communicating with the database.");
            e.printStackTrace();
        }*/



       // User u = new User(1, "admin", "slouma", "slouma.masmoudi@gmail.com", "123", "123F", "picture", Role.ADMIN);
 //       us.add(u);
//        us.update(u);
//        System.out.println(us.getById(1));
//        System.out.println(us.getAll());
     //   us.login(u.getEmail(), u.getPassword());

    }
}

