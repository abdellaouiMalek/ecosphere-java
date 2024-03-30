package tn.esprit;

import tn.esprit.models.Event;
import tn.esprit.services.EventService;
import tn.esprit.util.DBconnection;

import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Create an instance of EventService
        EventService es = new EventService();

        // Create a Scanner object to read input from the terminal
        Scanner scanner = new Scanner(System.in);

        //***********add
        // Prompt the user to enter event details
        System.out.println("Enter event details:");
        System.out.print("Event Name: ");
        String eventName = scanner.nextLine();

        System.out.print("Address: ");
        String address = scanner.nextLine();

        System.out.print("Location: ");
        String location = scanner.nextLine();

        System.out.print("Objective: ");
        String objective = scanner.nextLine();

        System.out.print("Image: ");
        String image = scanner.nextLine();

        System.out.print("Description: ");
        String description = scanner.nextLine();

        // Assuming you want to enter date and time manually as well
        System.out.print("Date (YYYY-MM-DD): ");
        String dateString = scanner.nextLine();
        Date date = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            System.err.println("Invalid date format. Please enter date in YYYY-MM-DD format.");
            return;
        }

        System.out.print("Time (HH:MM:SS): ");
        String timeString = scanner.nextLine();
        Time time = null;
        try {
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            java.util.Date parsedTime = timeFormat.parse(timeString);
            time = new Time(parsedTime.getTime());
        } catch (ParseException e) {
            System.err.println("Invalid time format. Please enter time in HH:MM:SS format.");
            return;
        }

        //*************delete
        // Create a new Event object with user-entered data
        Event event = new Event(eventName, address, location, objective, image, description, date, time);

        try {
            // Call the add method of EventService to add the event
            es.add(event);
            System.out.println("Event added successfully!");
        } catch (SQLException e) {
            System.err.println("Error occurred while adding the event: " + e.getMessage());
        }

        // Prompt the user to enter the ID of the event they want to delete
        System.out.print("Enter the ID of the event you want to delete: ");
        int eventId = scanner.nextInt();

        // Create an Event object with the provided ID
        Event eventToDelete = new Event();
        eventToDelete.setId(eventId);

        try {
            // Call the delete method of EventService to delete the event
            es.delete(eventToDelete);
            System.out.println("Event deleted successfully!");
        } catch (SQLException e) {
            System.err.println("Error occurred while deleting the event: " + e.getMessage());
        }

        // Close the Scanner
        scanner.close();


        //*********show all
        // Retrieve the list of events
        List<Event> events = es.getAll();

        // Check if there are any events
        if (events.isEmpty()) {
            System.out.println("No events found.");
        } else {
            // Display events in a more appealing format
            System.out.println("List of Events:");
            System.out.println("----------------");
            for (Event eventItem : events) { // Rename variable to eventItem
                System.out.println("Event ID: " + eventItem.getId());
                System.out.println("Event Name: " + eventItem.getEventName());
                System.out.println("Location: " + eventItem.getLocation());
                System.out.println("Date: " + eventItem.getDate());
                System.out.println("Time: " + eventItem.getTime());
                System.out.println("Objective: " + eventItem.getObjective());
                System.out.println("Description: " + eventItem.getDescription());
                System.out.println("----------------");
            }
        }

        // Initialize the DBconnection
        DBconnection cnx = DBconnection.getInstance();


    }
}