package tn.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.models.Carpooling;
import tn.esprit.services.CarpoolingService;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Search {
    @FXML
    private TextField date;

    @FXML
    private TextField departure;

    @FXML
    private TextField destination;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @FXML
    void search(ActionEvent event) throws ParseException {
        String dep = departure.getText();
        String dest = destination.getText();
        String dateString = date.getText();

        try {
            java.util.Date utilDate = dateFormat.parse(dateString);
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

            CarpoolingService carpoolingService = new CarpoolingService();
            List<Carpooling> searchResults = carpoolingService.search(dep, dest, sqlDate);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/carpooling/allCarpoolings.fxml"));
            Parent root = loader.load();

            AllCarpoolings controller = loader.getController();

            controller.displaySearchResults(searchResults);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }

    }

