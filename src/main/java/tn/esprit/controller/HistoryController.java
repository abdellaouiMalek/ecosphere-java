package tn.esprit.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import tn.esprit.models.History;
import tn.esprit.services.ServiceHistory;

import javax.naming.Name;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {
    ObservableList<History> HistoryList = FXCollections.observableArrayList();
    ServiceHistory post = new ServiceHistory();


    @FXML
    private Button Histiory_btn;

    @FXML
    private AnchorPane Histiory_form;

    @FXML
    private TableView<History> Histiory_tableView;

    @FXML
    private AnchorPane add_Histiory;

    @FXML
    private Button add_btn;

    @FXML
    private DatePicker createdat;

    @FXML
    private Button delete_btn;

    @FXML
    private Button menu_btn;

    @FXML
    private ComboBox<?> sharinghub_intcond;

    @FXML
    private TextField tf_id;

    @FXML
    private TextField tf_name;

    @FXML
    private Button update_btn;

    @FXML
    private Label username;

    @FXML
    void delete_btn(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HistoryList = FXCollections.observableArrayList();
        Histiory_tableView.setItems(HistoryList);
        // Initialize TableColumn instances
        //post_col_auteur = new TableColumn<>("Auteur");
        //post_col_title = new TableColumn<>("Title");
        //post_col_contenu= new TableColumn<>("Contenu");
        //post_col_createdat = new TableColumn<>("Date");

        // Bind TableView columns to Publication properties
        //.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        TableColumn<History, String> sharinghub_col_id = new TableColumn<>("ID Object");
        TableColumn<History, String> sharinghub_col_name = new TableColumn<>("Name");
        TableColumn<History, String> sharinghub_col_initialcond = new TableColumn<>("initialCondition");
        TableColumn<History, Date> sharinghub_col_date = new TableColumn<>("date");

        sharinghub_col_id.setCellValueFactory(new PropertyValueFactory<>("Id Object"));
        sharinghub_col_name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        sharinghub_col_initialcond.setCellValueFactory(new PropertyValueFactory<>("initialCondition"));
        sharinghub_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        // Add columns to the TableView
        Histiory_tableView.getColumns().addAll(sharinghub_col_id, sharinghub_col_name, sharinghub_col_initialcond, sharinghub_col_date);
        // Set preferred widths for columns
        refreshTable();
        sharinghub_col_id.setPrefWidth(100);
        sharinghub_col_name.setPrefWidth(100);
        sharinghub_col_initialcond.setPrefWidth(100);
        sharinghub_col_date.setPrefWidth(100);

    }

    private void refreshTable() {
        ServiceHistory historyServices = new ServiceHistory();
        List<History> HistoryList = historyServices.getAll();
        Histiory_tableView.setItems(FXCollections.observableArrayList(HistoryList));
    }

    @FXML
    void add_btn() {
        try {
            String name = tf_name.getText();
            String initialCondition = (String) sharinghub_intcond.getSelectionModel().getSelectedItem();
            if (name.isEmpty() || initialCondition.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields ");
                return;
            }
            History newHistory = new History();
            newHistory.setName(name);
            newHistory.setInitialCondition(initialCondition);
            newHistory.setDate(new Date());
            ServiceHistory newpostService = new ServiceHistory();
            newpostService.add(newHistory);
            // Ajoutez cette ligne pour récupérer l'ID auto-incrémenté après l'ajout dans la base de données
            int newId = newHistory.getId();
            HistoryList.add(newHistory);
            newHistory.setId(newId); // Mettez à jour l'ID dans l'objet Publication avec l'ID auto-incrémenté
            refreshTable();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Publication added successfully.");
            clearFields();
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while adding the publication.");
        }
    }

    private void clearFields() {
        tf_name.clear();
        sharinghub_intcond.getSelectionModel().clearSelection();

    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void update_btn() {
        History selectedHistory = Histiory_tableView.getSelectionModel().getSelectedItem();
        if (selectedHistory != null) {
            try {
                String name = tf_name.getText();
                String initialCndition = (String) sharinghub_intcond.getSelectionModel().getSelectedItem();

                if (name.isEmpty() || initialCndition.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Please fill in all fields.");
                    return;
                }
                selectedHistory.setName(name);
                selectedHistory.setInitialCondition(initialCndition);

                ServiceHistory history = new ServiceHistory();
                history.update(selectedHistory);
                refreshTable();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Post updated successfully.");

                clearFields();
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while updating the post.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Please select a post to update.");
        }

//    public void delete_btn () {
  //          History selectedPost = Histiory_tableView.getSelectionModel().getSelectedItem();
    //        if (selectedPost != null) {
      //          try {
        //            ServiceHistory HistoryServices = new ServiceHistory();
          //          HistoryServices.delete(selectedPost);
            //        HistoryList.remove(selectedPost); // Supprimez également la publication de votre liste observable
              //      refreshTable();
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Post deleted successfully.");
                //} catch (Exception e) {
                  //  e.printStackTrace();
                    //showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while deleting the post.");
                //}
//} else {
  //              showAlert(Alert.AlertType.ERROR, "Error", "Please select a post to delete.");
    //        }
      //  }

    }
}
