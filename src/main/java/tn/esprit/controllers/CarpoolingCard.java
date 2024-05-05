package tn.esprit.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import tn.esprit.models.Carpooling;
import tn.esprit.models.User;
import tn.esprit.services.UserService;

import java.io.File;

public class CarpoolingCard {
    @FXML
    private Label departureLabel;

    @FXML
    private Label destinationLabel;

    @FXML
    private Label price;
    @FXML
    private Label time;
    @FXML
    private Label usernamel;
    @FXML
    private ImageView userPic;

    public void setCarpooling(Carpooling carpooling) {
        departureLabel.setText(carpooling.getDeparture());
        destinationLabel.setText(carpooling.getDestination());
        price.setText(String.valueOf(carpooling.getPrice()));
        time.setText(String.valueOf(carpooling.getTime()));

        int userId = carpooling.getUserID();
        UserService userService = new UserService();
        User user = userService.getById(userId);

        if (user != null) {
            usernamel.setText(user.getFirst_name()+" "+user.getLast_name());

            // Load and set the user's picture
            String pictureUrl = user.getPicture();
            if (pictureUrl != null && !pictureUrl.isEmpty()) {
                try {
                    // Check if the URL starts with a recognized protocol
                    if (pictureUrl.startsWith("http")) {
                        // Load the image from the URL
                        Image image = new Image(pictureUrl);
                        userPic.setImage(image);
                    } else {
                        // If not, assume it's a local file path
                        File file = new File(pictureUrl);
                        Image image = new Image(file.toURI().toString());
                        userPic.setImage(image);
                    }

                    // Set the dimensions of the ImageView
                    userPic.setFitWidth(50); // Set your desired width
                    userPic.setFitHeight(50); // Set your desired height

                    // Remove the preserveRatio attribute to avoid stretching the image
                    userPic.setPreserveRatio(false);

                    // Clip the ImageView to a Circle shape
                    Circle clip = new Circle();
                    clip.setCenterX(userPic.getFitWidth() / 2);
                    clip.setCenterY(userPic.getFitHeight() / 2);
                    clip.setRadius(userPic.getFitWidth() / 2);
                    userPic.setClip(clip);
                } catch (Exception e) {
                    e.printStackTrace();
                    // Handle image loading error
                }
            } else {
                // If no picture is available, you can set a default image
                userPic.setImage(new Image("/path/to/default/image.png"));
            }

        } else {
            usernamel.setText("Unknown");
        }
    }
}