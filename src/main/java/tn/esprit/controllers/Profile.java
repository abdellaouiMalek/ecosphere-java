package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import tn.esprit.models.SessionUser;
import tn.esprit.models.User;

import java.io.IOException;


public class Profile {
    @FXML
    private Button ToHomePage;

    @FXML
    private Circle big_circle;

    @FXML
    private Label email;

    @FXML
    private Label first_name;

    @FXML
    private Label last_name;

    @FXML
    private Label tel;

    @FXML
    private Label verified;

    public static int profileCheck;

    public static User user;

//    @FXML
//    public void navigateToHomePage(ActionEvent event) {
//        try {
//            String pathTo = "";
//            String nameTo = "";
//            if(SessionUser.loggedUser.getRole().equals("Client") || connectedUser.getRoles().equals("Employé(e)")) {
//                pathTo = "/UserHomePage.fxml";
//                nameTo = "Siyahi Bank | Home Page";
//            } else{
//                pathTo = "/AdminHomePage.fxml";
//                nameTo = "Siyahi Bank | Dashboard";
//            }
//            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource(pathTo));
//            Scene ajouterUserScene = new Scene(ajouterUserParent);
//            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
//            window.setScene(ajouterUserScene);
//            window.setTitle(nameTo);
//            window.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    private void navigateToUserSection(ActionEvent event) {
//        try {
//            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/Users.fxml"));
//            Scene ajouterUserScene = new Scene(ajouterUserParent);
//            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
//            window.setScene(ajouterUserScene);
//            window.setTitle("Siyahi Bank | Gestion des Utilisateurs");
//            window.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    void navigateToTransactions(ActionEvent event) {
//        try {
//            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/Transactions.fxml"));
//            Scene ajouterUserScene = new Scene(ajouterUserParent);
//            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
//            window.setScene(ajouterUserScene);
//            window.setTitle("Siyahi Bank | Gestion des Transactions");
//            window.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @FXML
    private void updateUser(ActionEvent event) {
        try {
            Parent ajouterUserParent = FXMLLoader.load(getClass().getResource("/EditProfile.fxml"));
            Scene ajouterUserScene = new Scene(ajouterUserParent);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(ajouterUserScene);
            window.setTitle(" Edit Profile");
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
   void initialize() {

        first_name.setText(SessionUser.loggedUser.getFirst_name());
        last_name.setText(SessionUser.loggedUser.getLast_name());
        email.setText(SessionUser.loggedUser.getEmail());

        tel.setText(SessionUser.loggedUser.getPhone_number());
            verified.setText("compte verifier");


  }
//    private void setUserValues(User user){
//        if(SessionUser.loggedUser.getRole().equals("Admin") || SessionUser.loggedUser.getRole().equals("Consumer")){
//            if (user.isVerified()) {
//                activity.setText("[Activé]");
//                activity.setStyle("-fx-text-fill: green;");
//            } else {
//                activity.setText("[Bloqué]");
//                activity.setStyle("-fx-text-fill: red;");
//            }
////            date_creation.setText(user.getDate_creation_c().toString());
////            role.setText(user.getRoles());
//        }
//
//        first_name.setText(user.getFirst_name());
//        last_name.setText(user.getLast_name());
//        verified.setText(String.valueOf(user.isVerified()));
//        tel.setText(String.valueOf(user.getPhone_number()));
//        email.setText(user.getEmail());
//
//        //Setting the user picture
//        String imageName = user.getPicture();
//        String imagePath = "/uploads/user/" + imageName;
//        Image image = new Image((Objects.requireNonNull(getClass().getResource(imagePath))).toExternalForm());
//        big_circle.setFill(new ImagePattern(image));
//    }
//
//    @FXML
//    void initialize() {
//        if (profileCheck == 1) { //Going into ProfileAdmin.fxml from "Profile" menuItem.
//            setUserValues(connectedUser);
//        } else { //Going into ProfileAdmin.fxml from "TableView" as an Admin or Super_Admin
//            setUserValues(user);
//        }
//    }
}