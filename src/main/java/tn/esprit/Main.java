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

import tn.esprit.models.Comment;
import tn.esprit.models.Post;
import tn.esprit.services.CommentServices;
import tn.esprit.services.PostServices;

public class Main {

    public static void main(String[] args) {
        // Create an instance of EventService
        EventService es = new EventService();

        // Create a Scanner object to read input from the terminal
        Scanner scanner = new Scanner(System.in);

/*
        //***********add
        // Prompt the user to enter event details
        System.out.println("Enter event details:");
        // Input validation for Event Name
        String eventName;
        do {
            System.out.print("Event Name: ");
            eventName = scanner.nextLine().trim();
        } while (eventName.isEmpty());

        // Input validation for Address
        String address;
        do {
            System.out.print("Address: ");
            address = scanner.nextLine().trim();
        } while (address.isEmpty());

        // Input validation for Location
        String location;
        do {
            System.out.print("Location: ");
            location = scanner.nextLine().trim();
        } while (location.isEmpty());

        // Input validation for Objective
        String objective;
        do {
            System.out.print("Objective: ");
            objective = scanner.nextLine().trim();
        } while (objective.isEmpty());

        // Input validation for Image (can be empty)
        System.out.print("Image: ");
        String image = scanner.nextLine().trim(); // No validation needed for this field

        // Input validation for Description
        String description;
        do {
            System.out.print("Description: ");
            description = scanner.nextLine().trim();
        } while (description.isEmpty());

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


        // Create a new Event object with user-entered data
        Event event = new Event(eventName, address, location, objective, image, description, date, time);

        try {
            // Call the add method of EventService to add the event
            es.add(event);
            System.out.println("Event added successfully!");
        } catch (SQLException e) {
            System.err.println("Error occurred while adding the event: " + e.getMessage());
        }


//*************delete
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

        //********* update

        try {
            System.out.println("Enter the ID of the event to update:");
            int eventIdToUpdate = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            System.out.println("Enter updated event name:");
            String updatedEventName = scanner.nextLine();

            System.out.println("Enter updated event address:");
            String updatedAddress = scanner.nextLine();

            System.out.println("Enter updated event date (YYYY-MM-DD):");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date updatedDate = dateFormat.parse(scanner.nextLine());

            System.out.println("Enter updated event time (HH:MM:SS):");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
            Time updatedTime = new Time(timeFormat.parse(scanner.nextLine()).getTime());

            System.out.println("Enter updated event location:");
            String updatedLocation = scanner.nextLine();

            System.out.println("Enter updated event objective:");
            String updatedObjective = scanner.nextLine();

            System.out.println("Enter updated event description:");
            String updatedDescription = scanner.nextLine();

            // Create an Event object with the updated details
            Event updatedEvent = new Event(eventIdToUpdate, updatedEventName, updatedAddress, updatedLocation, updatedObjective, null, updatedDescription, updatedDate, updatedTime);

            // Call the update method of your EventService to update the event
            es.update(updatedEvent);
            System.out.println("Event with ID " + eventIdToUpdate + " updated successfully!");

        } catch (ParseException e) {
            System.out.println("Error parsing the date/time. Please ensure you're using the correct format.");
        }
    }


        // Prompt user to enter search term
        System.out.print("Enter search term: ");
        String searchTerm = scanner.nextLine().trim();

        try {
            // Call search method to search events
            List<Event> searchResults = es.search(searchTerm);

            if (searchResults.isEmpty()) {
                System.out.println("No events found matching the search criteria.");
            } else {
                System.out.println("Search Results:");
                System.out.println("----------------");
                for (Event event : searchResults) {
                    System.out.println(event);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error occurred during event search: " + e.getMessage());


        try {
            // Prompt user to rate an event
            System.out.print("Enter event ID to rate: ");
            int eventId = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character after nextInt()

            System.out.print("Enter rating (1-5): ");
            int rating = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character after nextInt()

            // Add event rating
            es.addEventRating(eventId, rating);

            // Calculate and display the updated average rating
            double updatedAverageRating = es.calculateAverageRating(eventId);
            System.out.println("Event rated successfully!");
            System.out.println("Updated Average Rating for Event ID " + eventId + ": " + updatedAverageRating);

        } catch (SQLException sqlException) {
            System.err.println("Error occurred while rating the event: " + sqlException.getMessage());
        } catch (Exception e) {
            System.err.println("Error occurred: " + e.getMessage());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
*/
    }
}




