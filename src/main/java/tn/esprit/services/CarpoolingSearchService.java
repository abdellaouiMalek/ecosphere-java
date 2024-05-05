package tn.esprit.services;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import tn.esprit.controllers.AllCarpoolings;
import tn.esprit.models.Carpooling;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class CarpoolingSearchService {
    public void search(ActionEvent event, TextField departure, TextField destination, DatePicker departureDate) throws ParseException {
        String dep = departure.getText();
        String dest = destination.getText();
        LocalDate selectedDate = departureDate.getValue();

        try {
            Date utilDate = java.sql.Date.valueOf(selectedDate);
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
