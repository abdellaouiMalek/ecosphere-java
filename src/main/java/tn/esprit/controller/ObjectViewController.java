package tn.esprit.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import tn.esprit.models.Object;
import tn.esprit.services.PaymentService;
import tn.esprit.services.ServiceObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ObjectViewController {


    @FXML
    private  Label totalPriceLabel ;
    @FXML
    private HBox objectCardContainer; // Container to hold the cards

    @FXML
    private Button showTotalButton;

    @FXML
    private Button payButton;

    private final ServiceObject serviceObject = new ServiceObject();

    private List<HBox> selectedCards = new ArrayList<>();

    @FXML
    void initialize() {
        populateObjectCards();
    }

    private void populateObjectCards() {
        List<Object> objects = serviceObject.getAll();
        for (Object object : objects) {
            HBox card = createObjectCard(object);
            objectCardContainer.getChildren().add(card);
        }
    }

    private HBox createObjectCard(Object object) {
        HBox card = new HBox();
        card.getStyleClass().add("card"); // Add CSS class for styling
        card.setSpacing(10); // Add spacing between elements

        // Create labels to display object information
        Label nameLabel = new Label("Name: " + object.getName());
        Label typeLabel = new Label("Type: " + object.getType());
        Label descriptionLabel = new Label("Description: " + object.getDescription());
        Label ageLabel = new Label("Age: " + object.getAge());
        Label priceLabel = new Label("Price: " + object.getPrice());

        // Add labels to the card layout
        VBox labelsContainer = new VBox(); // VBox to hold labels vertically
        labelsContainer.getChildren().addAll(nameLabel, typeLabel, descriptionLabel, ageLabel, priceLabel);
        card.getChildren().add(labelsContainer);

        // Add event handler to toggle selection
        card.setOnMouseClicked(event -> toggleSelection(card));

        return card;
    }




    private void toggleSelection(HBox card) {
        if (selectedCards.contains(card)) {
            selectedCards.remove(card);
            card.getStyleClass().remove("selected");
        } else {
            selectedCards.add(card);
            card.getStyleClass().add("selected");
        }
        updatePayButtonState();
    }

    private void updatePayButtonState() {
        payButton.setDisable(selectedCards.isEmpty());
    }

    @FXML
    void showTotalPrice() {
        // Calculate total price and display
        double totalPrice = calculateTotalPrice();
        totalPriceLabel.setText("Total Price: $" + String.format("%.2f", totalPrice));
    }

    private double calculateTotalPrice() {
        double totalPrice = 0;
        for (HBox card : selectedCards) {
            VBox labelsContainer = (VBox) card.getChildren().get(0); // Assuming labelsContainer is the first child
            Label priceLabel = (Label) labelsContainer.getChildren().get(labelsContainer.getChildren().size() - 1);
            double price = Double.parseDouble(priceLabel.getText().substring(7)); // Remove "Price: " prefix
            totalPrice += price;
        }
        return totalPrice;
    }


    @FXML
    void BackBtn(ActionEvent event) {
        // Get the source node (the button)
        Node source = (Node) event.getSource();
        // Get the scene containing the button
        Scene scene = source.getScene();
        // Change the root node of the scene to MyTest.fxml
        try {
            Parent myTestParent = FXMLLoader.load(getClass().getResource("/MyTest.fxml"));
            scene.setRoot(myTestParent);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception
        }
    }

    private static final String STRIPE_API_KEY = "sk_test_51PDyHcRwyGFgTalHSV15ShXa3kNwZwwjcGa1XafrGokLamVpSvCiN8njuEMNg4GP3TPnYS5ZL25ttwmnKcENhjQ800SUHUyCir";

    @FXML
    void payTotalPrice() {
        // Calculate total price
        double totalPrice = calculateTotalPrice();

        // Generate a Stripe token using Stripe.js on the client-side
        // Pass the token to this method for payment processing
        String stripeToken = STRIPE_API_KEY;
        String currency = "usd"; // or any other currency you want to use

        // Process payment using PaymentService
        boolean paymentSuccessful = PaymentService.processPayment(stripeToken, (int) (totalPrice * 100), currency);

        if (paymentSuccessful) {
            // Payment successful, perform further actions (e.g., clear selection, show success message, etc.)
            clearSelection();
            System.out.println("Payment successful!");
        } else {
            // Payment failed, handle accordingly (e.g., show error message, allow retry, etc.)
            System.out.println("Payment failed. Please try again.");
        }
    }


    private void clearSelection() {
        selectedCards.clear();
        objectCardContainer.getChildren().forEach(card -> card.getStyleClass().remove("selected"));
        updatePayButtonState();
    }

}