package tn.esprit.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import tn.esprit.models.Event;
import tn.esprit.services.EventService;


import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AllEvents implements Initializable  {

    @FXML
    private Button addEventBtn;

    @FXML
    private ScrollPane eListScrollPane;

    @FXML
    private GridPane eventListGP;

    @FXML
    private TextField searchBarTf;

    @FXML
    private RadioButton showOnlyActiveRb;

    @FXML
    private RadioButton showOnlyEndedRb;

    @FXML
    private ChoiceBox<String> sortBoxCB;

    @FXML
    private Button ReturnHome;


    EventService es = new EventService();
    private String sortCriteria = "Start Date";
    private boolean showOnlyActiveEvents = false;
    private boolean showOnlyEndedEvents = false;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            //Filtre
            ToggleGroup group = new ToggleGroup();
            showOnlyActiveRb.setToggleGroup(group);
            showOnlyEndedRb.setToggleGroup(group);
            showOnlyActiveRb.setOnAction(event -> {
                showOnlyActiveEvents = showOnlyActiveRb.isSelected();
                showOnlyEndedEvents = showOnlyEndedRb.isSelected();
                searchSort(searchBarTf.getText(), sortCriteria);
            });
            showOnlyEndedRb.setOnAction(event -> {
                showOnlyActiveEvents = showOnlyActiveRb.isSelected();
                showOnlyEndedEvents = showOnlyEndedRb.isSelected();
                searchSort(searchBarTf.getText(), sortCriteria);
            });


            eListScrollPane.setFitToWidth(true);
            List<Event> events = es.getAll();
            int row = 1;
            int column = 0;
            for (int i = 0; i < events.size(); i++){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/SingleEvent.fxml"));
                AnchorPane pane = loader.load();
                SingleEvent controller = loader.getController();
                controller.setData(events.get(i));
                eventListGP.add(pane, column, row);
                row++;
            }
            //Tri criterias:
            sortBoxCB.setValue("None");
            sortBoxCB.getItems().addAll("event name", "category", "Start Date");
            sortBoxCB.setOnAction(event -> {
                sortCriteria = sortBoxCB.getValue();
            });
            //Search
            searchBarTf.textProperty().addListener((observable, oldValue, newValue) -> {
                try {
                    searchSort(newValue, sortCriteria);
                } catch (Exception e) {
                    System.out.println("Error in searchSort: " + e.getMessage());
                    e.printStackTrace();
                }
            });


            //Sort
            sortBoxCB.valueProperty().addListener((observable, oldValue, newValue) -> {
                searchSort(searchBarTf.getText(), newValue);
            });

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }




    @FXML
    private void addEventRedirect(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddEvent.fxml"));
            Parent root = loader.load();
            // Get the current scene and replace its root with the new root
            eventListGP.getScene().setRoot(root);
        } catch (IOException ex) {
            System.out.println("Error loading AddEvent.fxml: " + ex.getMessage());
        }
    }

    private void searchSort(String searchTerm, String sortBy){
        try {
            List<Event> filteredEvents = es.search(searchTerm, sortBy);
            if (showOnlyActiveEvents) {
                filteredEvents = filteredEvents.stream()
                        .filter(event -> event.getDate().after(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())))
                        .collect(Collectors.toList());
            } else if (showOnlyEndedEvents) {
                filteredEvents = filteredEvents.stream()
                        .filter(event -> event.getDate().before(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant())))
                        .collect(Collectors.toList());
            }
            eventListGP.getChildren().clear();
            int rowS = 1;
            int columnS = 0;
            for (int i = 0; i < filteredEvents.size(); i++){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/SingleEvent.fxml"));
                AnchorPane pane = loader.load();
                SingleEvent controller = loader.getController();
                controller.setData(filteredEvents.get(i));
                eventListGP.add(pane, columnS, rowS);
                rowS++;
            }
        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void ReturnHome(ActionEvent event) {
        try {
            // Load the AllEvents.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/home.fxml"));
            Parent root = loader.load();

            // Set up the stage
            Stage stage = (Stage) ReturnHome.getScene().getWindow();
            stage.setScene(new Scene(root));

        } catch (IOException ex) {
            System.out.println("Error loading home.fxml: " + ex.getMessage());
        }

    }

}

