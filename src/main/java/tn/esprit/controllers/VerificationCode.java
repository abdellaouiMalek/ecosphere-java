package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import tn.esprit.services.UserService;

import java.io.IOException;

import static tn.esprit.services.UserService.emailOfAccountWillVerif;
import static tn.esprit.services.UserService.verificationCodeOfUser;

public class VerificationCode {

    @FXML
    private TextField verificationCode;

    UserService us = new UserService();

    @FXML
    void verifierAction(ActionEvent event) {
        System.out.println(verificationCodeOfUser);
        if(verificationCodeOfUser.equals(verificationCode.getText())){
            us.updateVerifAccount(emailOfAccountWillVerif);




//             nav to home page uncommit this and change the fxml page
            try {
                // Load Register.fxml using ClassLoader
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Login.fxml")); //change thislogin.fxml to home page after creation
                Parent root = loader.load();

                // Replace the current scene with the new scene loaded from Register.fxml
                verificationCode.getScene().setRoot(root);
            } catch (IOException ex) {
                System.out.println("Error loading Login.fxml: " + ex.getMessage());
            }
        }

    }

}
