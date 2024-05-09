package tn.esprit.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tn.esprit.models.Role;
import tn.esprit.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.esprit.services.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.image.PNGTranscoder;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import static tn.esprit.services.UserService.emailOfAccountWillVerif;

public class Register implements Initializable {

    @FXML
    private ImageView ivImageregister;

    @FXML
    private Label ctrlfname;

    @FXML
    private Label ctrlmail;

    @FXML
    private Label ctrlname;

    @FXML
    private Label ctrlpass;

    @FXML
    private Label ctrlphone;

    @FXML
    private ComboBox<String> cmbbrole;


    @FXML
    private TextField tffamname;

    @FXML
    private TextField tfmail;

    @FXML
    private TextField tfname;

    @FXML
    private TextField tfpassword;

    @FXML
    private TextField tfphone;

    @FXML
    private TextField tfphoto;
    @FXML
    private Button Auth;

    UserService us = new UserService();


    @FXML
    void addPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(new Stage());
        tfphoto.setText(file.toString());
        System.out.println(file.toString());
    }
    public void setProfilePicture(String svgImagePath) {
        try {
            // Create a PNG transcoder
            Transcoder transcoder = new PNGTranscoder();

            // Set the input and output for the transcoder
            TranscoderInput input = new TranscoderInput(new FileInputStream("C:/Users/Abdessalem-Masmoudi/Downloads/logo.svg"));
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            TranscoderOutput output = new TranscoderOutput(outputStream);

            // Perform the transcoding
            transcoder.transcode(input, output);

            // Create an Image from the transcoded PNG data
            Image image = new Image(new ByteArrayInputStream(outputStream.toByteArray()));

            // Set the image to the ImageView
            ivImageregister.setImage(image);
        } catch (FileNotFoundException ex) {
            System.out.println("Error loading SVG image: " + ex.getMessage());
        } catch (TranscoderException ex) {
            System.out.println("Error transcoding SVG image: " + ex.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Populate the combo box with choices
        ObservableList<String> options = FXCollections.observableArrayList(
                 "CONSUMER", "VISITOR", "PRODUCTOR");
        cmbbrole.setItems(options);

        // Set default selection if needed
        cmbbrole.getSelectionModel().selectFirst(); // Select the first item by default
    }

    @FXML
    void cbmRole(ActionEvent event) {
        String selectedValue = cmbbrole.getSelectionModel().getSelectedItem();
        System.out.println("Selected: " + selectedValue);
    }

    private void reset() {
        tfname.setText("");
        tffamname.setText("");
        tfmail.setText("");
        tfphone.setText("");
        tfpassword.setText("");
        tfphoto.setText("");
    }
    @FXML
    void GoLogin(ActionEvent event) {
        try {
            // Load Register.fxml using ClassLoader
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Login.fxml"));
            Parent root = loader.load();

            // Replace the current scene with the new scene loaded from Register.fxml
            Auth.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println("Error loading Login.fxml: " + ex.getMessage());
        }
    }
    @FXML
    void Register(ActionEvent event) {
        boolean ctrlpassBool = true;
        boolean ctrlnameBool = true;
        boolean ctrlfnameBool = true;
        boolean ctrlmailBool = true;
        boolean ctrlphoneBool = true;

        // Validate input fields
        if (tfname.getText().isEmpty()) {
            ctrlname.setText("Field is empty");
            ctrlnameBool = false;
        }
        if (tffamname.getText().isEmpty()) {
            ctrlfname.setText("Field is empty");
            ctrlfnameBool = false;
        }
        if (tfmail.getText().isEmpty()) {
            ctrlmail.setText("Field is empty");
            ctrlmailBool = false;
        }
        if (tfphone.getText().isEmpty()) {
            ctrlphone.setText("Field is empty");
            ctrlphoneBool = false;
        }
        if (tfpassword.getText().isEmpty()) {
            ctrlpass.setText("Field is empty");
            ctrlpassBool = false;
        }
        if (!(ctrlpassBool && ctrlphoneBool && ctrlmailBool && ctrlnameBool && ctrlfnameBool)) {
            return;
        }
        try {
            // Generate a random salt for password hashing
            String salt = BCrypt.gensalt();

            // Hash the user's password with the generated salt
            String hashedPassword = BCrypt.hashpw(tfpassword.getText(), salt);

            // Create a new User object with hashed password and other details
            User u = new User();
            u.setFirst_name(tfname.getText());
            u.setLast_name(tffamname.getText());
            u.setEmail(tfmail.getText());
            u.setPhone_number(tfphone.getText());
            u.setPassword(hashedPassword); // Store the hashed password
            u.setPicture(tfphoto.getText());
            u.setRole(Role.valueOf(cmbbrole.getSelectionModel().getSelectedItem()));


            us.add(u);


            reset();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration Successful");
            alert.setHeaderText(null);
            alert.setContentText("User registered successfully!");
            alert.showAndWait();

            try {
                emailOfAccountWillVerif = u.getEmail();
                // Load Register.fxml using ClassLoader
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("verificationCode.fxml"));
                Parent root = loader.load();

                // Replace the current scene with the new scene loaded from Register.fxml
                Auth.getScene().setRoot(root);
            } catch (IOException ex) {
                System.out.println("Error loading Login.fxml: " + ex.getMessage());
            }



            System.out.println("User registered successfully!");
        } catch (Exception ex) {
            System.out.println("Error registering user: " + ex.getMessage());
        }
    }

}










